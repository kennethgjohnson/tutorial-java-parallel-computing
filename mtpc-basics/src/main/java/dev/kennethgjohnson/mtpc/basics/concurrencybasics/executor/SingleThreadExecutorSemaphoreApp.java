package dev.kennethgjohnson.mtpc.basics.concurrencybasics.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Duplicate of CachedThreadPoolExecutorSemaphoreApp but using a
 * SingleThreadExecutor insted of a CachedThreadPoolExecutor.
 * 
 * Notice that it only creates one thread, but queues 5 runnables worth of work.
 * 
 * Keeps the max threads to 1.
 */
public class SingleThreadExecutorSemaphoreApp {
  public static void main(final String[] args) {
    System.out.println("SingleThreadExecutorSemaphoreApp: Running");
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Seting up 5 runnables of work.
    for (Integer i = 0; i < 5; ++i) {
      final int blockNumber = i + 1;
      executorService.execute(() -> {
        try {
          Downloader.SingleThreadExecutorSemaphoreApp_INSTANCE.Download(blockNumber);
        } catch (final InterruptedException e) {
          System.err.println(String.format("Runnable for block %d : Download chunk failed/interrupted.", blockNumber));
        }
      });
    }

    // Ask the executor to shutdown - blocks now tasks from being accepted
    executorService.shutdown();
    while (!executorService.isTerminated()) { // waits until all tasks are completed or terminated.
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("SingleThreadExecutorSemaphoreApp: Done.");
  }

}
