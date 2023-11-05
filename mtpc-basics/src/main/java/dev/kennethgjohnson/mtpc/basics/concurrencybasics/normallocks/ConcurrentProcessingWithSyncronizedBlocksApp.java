package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

/// Now that counterLock and counterLock2 works independantly both incrementCounter
/// and incrementCounter2 can run concurrently.
public class ConcurrentProcessingWithSyncronizedBlocksApp {
  private static int counter = 0;
  private static int counter2 = 0;
  private static Object counterLock = new Object();
  private static Object counter2Lock = new Object();

  // This syncronized is performing a lock on the
  // counterLock
  public static void incrementCounter() {
    synchronized (counterLock) {
      counter++;
    }
  }

  // This syncronized is performing a lock on the
  // counter2Lock
  public static void incrementCounter2() {
    synchronized (counter2Lock) {
      counter2++;
    }
  }

  public static void main(final String[] args) {
    System.out.println("Starting...");
    Runnable worker = () -> {
      for (int i = 0; i < 10000; i++) {
        incrementCounter();
        incrementCounter2();
      }
    };

    Thread t1 = new Thread(worker);
    Thread t2 = new Thread(worker);
    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(String.format("counter:%d", counter));
    System.out.println(String.format("counter2:%d", counter2));
    System.out.println("Completed.");
  }

}