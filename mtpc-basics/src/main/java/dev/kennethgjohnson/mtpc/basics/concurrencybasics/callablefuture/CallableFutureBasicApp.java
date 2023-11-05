package dev.kennethgjohnson.mtpc.basics.concurrencybasics.callablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Demonstrates how an implementation of Callable aka FakeCommand can be used by
 * an ExecutorService's submit command to submit work to be done and then make
 * the value available with its get method, and shows if the value is ready with
 * it's isDone method.
 * 
 * The get method is a blocking method, so checking for isDone before calling a
 * block will allow the first result available to be yielded.
 */
public class CallableFutureBasicApp {
  public static void main(String[] args) {
    System.out.println("CallableFutureAppBasic: Running");
    List<FakeCommand> commandsToThread = new ArrayList<>();
    commandsToThread.add(new FakeCommand(3000, 2000000));
    for (int i = 0; i < 10; i++) {
      commandsToThread.add(new FakeCommand(500, i));
    }

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    List<Future<Integer>> futureResults = new ArrayList<>();
    for (FakeCommand fakeCommand : commandsToThread) {
      futureResults.add(executorService.submit(fakeCommand));
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

class FakeCommand implements Callable<Integer> {
  private Integer waitMiliseconds = 0;
  private Integer returnValue = 0;

  public FakeCommand(Integer waitMiliseconds, Integer returnValue) {
    this.waitMiliseconds = waitMiliseconds;
    this.returnValue = returnValue;
  }

  @Override
  public Integer call() throws Exception {
    Thread.sleep(waitMiliseconds);
    return returnValue;
  }
}
