package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

/// Both incrementCounter and incrementCounter2 is essentially locking 
/// on the same class lock, so incrementCounter usage by thread 1 will
/// block incrementCounter2 usage by thread 2, which is not the most 
/// efficient.
public class ConcurrentProcessingWithSyncronizedMethodsApp {
  private static int counter = 0;
  private static int counter2 = 0;

  // This syncronized is performing a lock on the
  // ConcurrentProcessingWithSyncronizedMethodsApp class
  public static synchronized void incrementCounter() {
    counter++;
  }

  // This syncronized is performing a lock on the
  // ConcurrentProcessingWithSyncronizedMethodsApp class
  public static synchronized void incrementCounter2() {
    counter2++;
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