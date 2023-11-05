package dev.kennethgjohnson.mtpc.basics.concurrentcollections.latches;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchApp {
  public static void main(String[] args) {
    System.out.println("CountdownLatchApp: starting...");

    String problemData = "sffohfiuwh9hwgerhv23230eifvkf203kf02j928gf9283jreurhgfpfgoibfgvfo1n23rf09ejfdjqepurht9823hgfsdnaoir23r90283";
    List<String> workDataChunks = splitProblemIntoChunks(problemData, 5);

    ExecutorService executor = Executors.newFixedThreadPool(workDataChunks.size());
    /*
     * ExecutorService executor = Executors.newFixedThreadPool(2);
     */
    /* ExecutorService executor = Executors.newSingleThreadExecutor(); */

    CountDownLatch countdownLatch = new CountDownLatch(workDataChunks.size());
    List<CountdownLatchWorker> workers = new ArrayList<>();
    for (int i = 0; i < workDataChunks.size(); i++) {
      int workerId = i + 1;
      System.out.println(
          String.format("CountdownLatchApp: enqueing worker %d with chunk '%s'...", workerId, workDataChunks.get(i)));
      CountdownLatchWorker worker = new CountdownLatchWorker(i + 1, countdownLatch, workDataChunks.get(i));
      workers.add(worker);
      executor.execute(worker);
    }
    executor.shutdown();

    System.out.println("CountdownLatchApp: waiting for latch...");
    try {
      countdownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("CountdownLatchApp: latch triggered.");

    System.out.println("CountdownLatchApp: writing results...");
    for (CountdownLatchWorker worker : workers) {
      System.out
          .println(String.format("CountdownLatchApp: '%d' result '%s'", worker.getWorkerId(), worker.getResultData()));
    }

    System.out.println("CountdownLatchApp: done.");
  }

  private static List<String> splitProblemIntoChunks(String problemData, Integer chunkSize) {
    List<String> response = new ArrayList<>();
    int chunkCount = problemData.length() / chunkSize;
    if ((problemData.length() % chunkSize) != 0) {
      chunkCount = chunkCount + 1;
    }

    for (int i = 0; i < chunkCount; i++) {
      int startingPosition = i * chunkSize;
      int chunkBasedEndPosition = startingPosition + chunkSize;
      int endPosition = (chunkBasedEndPosition <= problemData.length()) ? (startingPosition + chunkSize)
          : problemData.length();
      response.add(problemData.substring(startingPosition, endPosition));
    }
    return response;
  }
}