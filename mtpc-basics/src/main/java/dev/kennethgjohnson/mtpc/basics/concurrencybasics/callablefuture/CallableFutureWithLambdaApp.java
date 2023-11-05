package dev.kennethgjohnson.mtpc.basics.concurrencybasics.callablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Same as CallableFutureBasicApp, but uses lambdas to create the Callables.
 */
public class CallableFutureWithLambdaApp {
  public static void main(String[] args) {
    System.out.println("CallableFutureAppBasic: Running");
    List<Callable<Integer>> commandsToThread = new ArrayList<>();
    commandsToThread.add(() -> {
      Thread.sleep(3000);
      return 2000000;
    });

    for (int i = 0; i < 10; i++) {
      int returnValue = i;
      commandsToThread.add(() -> {
        Thread.sleep(500);
        return returnValue;
      });
    }

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    List<Future<Integer>> futureResults = new ArrayList<>();
    for (Callable<Integer> callable : commandsToThread) {
      futureResults.add(executorService.submit(callable));
    }
    // We have queued our work so we are asking it to shut down so long.
    executorService.shutdown();

    while (!futureResults.isEmpty()) {
      try {
        Future<Integer> result = popAvailableResult(futureResults);
        System.out.println(String.format("Result completed: %d", result.get()));
        Thread.sleep(100);
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    // Wait for executor to finish its work.
    while (!executorService.isTerminated()) { // waits until all tasks are completed or terminated.
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("CallableFutureAppBasic: Done.");
  }

  private static Future<Integer> popAvailableResult(List<Future<Integer>> futureResults) throws InterruptedException {
    while (true) {
      for (int i = 0; i < futureResults.size(); i++) {
        Future<Integer> result = futureResults.get(i);
        if (result.isDone()) {
          futureResults.remove(i);
          return result;
        }
      }
      Thread.sleep(100);
    }
  }
}
