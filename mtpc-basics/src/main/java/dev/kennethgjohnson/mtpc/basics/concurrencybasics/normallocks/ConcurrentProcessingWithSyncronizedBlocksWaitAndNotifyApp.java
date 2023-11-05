package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

class Processor {
  public void produce() throws InterruptedException {
    System.out.println("Producer start...");
    synchronized (this) {
      System.out.println("producing sync (lock consumed)...");
      Thread.sleep(500);
      System.out.println("producing waiting (lock released) (now sleeping)...");
      wait();
      System.out.println("producing recommenced (waked up)...");
      Thread.sleep(500);
      System.out.println("producing end...");
    }
    Thread.sleep(500);
    System.out.println("Producer end.");
  }

  public void consume() throws InterruptedException {
    System.out.println("Consumer start...");
    Thread.sleep(100);
    synchronized (this) {
      System.out.println("consuming sync (lock consumed)...");
      Thread.sleep(100);
      System.out.println("consuming notifying (set signal to wake something up *at the end of the sync block!!*)...");
      // adds signaling to end of sync block to wake up one random thread waiting.
      notify();
      System.out.println("consuming almost end...");
      Thread.sleep(2000);
      System.out.println(
          "consuming sync end (sending notification signals set waking up a random thread needing waking!)...");
    }
    Thread.sleep(2000);
    System.out.println("Consumer end.");
  }
}

public class ConcurrentProcessingWithSyncronizedBlocksWaitAndNotifyApp {
  public static void main(String[] args) {
    System.out.println("Program Start...");
    Processor p = new Processor();
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          p.produce();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          p.consume();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    System.out.println("Threads start...");
    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
      System.out.println("Threads ended.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Program End.");
  }
}