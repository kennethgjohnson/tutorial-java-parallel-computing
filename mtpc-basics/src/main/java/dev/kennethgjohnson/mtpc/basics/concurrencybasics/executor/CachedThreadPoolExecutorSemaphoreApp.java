package dev.kennethgjohnson.mtpc.basics.concurrencybasics.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Duplicate of BasicSemaphoreApp but using execute instead of 10 lazy threads.
 * Executor immediately scales up to 10 threads.
 * 
 * Adds threads as needed.
 * 
 * Includes example on how to wait for threads handled by an executor to
 * complete
 */
public class CachedThreadPoolExecutorSemaphoreApp {
  public static void main(final String[] args) {
    System.out.println("CachedThreadPoolExecutorSemaphoreApp: Running");
    ExecutorService executorService = Executors.newCachedThreadPool();

    // Seting up 10 runnables of work.
    for (Integer i = 0; i < 10; ++i) {
      final int blockNumber = i + 1;
      executorService.execute(() -> {
        try {
          Downloader.CachedThreadPoolExecutorSemaphoreApp_INSTANCE.Download(blockNumber);
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
    System.out.println("CachedThreadPoolExecutorSemaphoreApp: Done.");
  }

}
