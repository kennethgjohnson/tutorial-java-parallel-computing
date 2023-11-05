package dev.kennethgjohnson.mtpc.basics.concurrencybasics.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Semaphores are like resources counters, they can be n numeric or boolean
 * aquire provides effective blocking to ensure that the Semphore "active
 * engagements" are not exceeded.
 * 
 * Notice we are creating 10 threads even though only 4 can execute at any
 * time... this is inefficient, we will solve this in future app using thread
 * executor pools, which will scale threads as needed or limit them as needed.
 */
enum Downloader {
  INSTANCE;

  private final Semaphore allowedConcurrentDownloadAttempts = new Semaphore(4, true);

  public void Download(int blockNumber) throws InterruptedException {
    try {
      System.out
          .println(String.format("Downloader for block %d: aquiring semaphore resource for download...", blockNumber));
      allowedConcurrentDownloadAttempts.acquire();
      System.out.println(String.format("Downloader for block %d: semaphore aquired.", blockNumber));
      downloadNextFileChunk(blockNumber);
    } finally {
      System.out
          .println(String.format("Downloader for block %d: aquiring semaphore resource for download...", blockNumber));
      allowedConcurrentDownloadAttempts.release();
    }

  }

  private void downloadNextFileChunk(int blockNumber) throws InterruptedException {
    System.out.println(String.format("Downloader for block %d: Fake Downloading block %d ", blockNumber, blockNumber));
    System.out.println(
        String.format("Downloader for block %d: simulating fake thread sleep for non blocking IO...", blockNumber));
    Thread.sleep(2000);
    System.out.println(String.format("Downloader for block %d: waking up.", blockNumber));
    System.out.println(String.format("Downloader for block %d: download completed.", blockNumber));
  }

}

/**
 * BasicSemaphoreApp
 */
public class BasicSemaphoreApp {
  public static void main(final String[] args) {
    System.out.println("BasicSemaphoreApp: Running");
    final List<Thread> threads = new ArrayList<>();

    // Seting up 10 threads.
    for (Integer i = 0; i < 10; ++i) {
      final int blockNumber = i + 1;
      // Using lambdas to create 20 runnables for the 20 threads.
      threads.add(new Thread(() -> {
        try {
          Downloader.INSTANCE.Download(blockNumber);
        } catch (final InterruptedException e) {
          System.err.println(String.format("Runnable for block %d : Download chunk failed/interrupted.", blockNumber));
        }
      }));
    }

    // Lazy start from list
    for (Thread thread : threads) {
      thread.start();
    }
    // Lazy join waiting for threads to finish running.
    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("BasicSemaphoreApp: Done.");
  }

}