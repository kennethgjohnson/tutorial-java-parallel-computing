package dev.kennethgjohnson.mtpc.basics.concurrencybasics.reentrantlocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* Main thing to note in this example is the abscense of interrupt exception handling.
* Also has a small using a lambda runnable argument example for higher order processing.
*/
public class ReentrantLocksApp {
  private static Integer count = 0;
  private static Lock reentrantLock = new ReentrantLock();

  private static void lockIncrement() {
    reentrantLock.lock();
    try {
      noLockIncrement();
    } finally {
      reentrantLock.unlock();
    }
  }

  private static void noLockIncrement() {
    for (Integer i = 0; i < 10000; i++) {
      count++;
    }
  }

  private static void lockAccross2MethodsIncrement() {
    reentrantLock.lock();
    try {
      noLockIncrement();
    } finally {
      pleaseUnlock();
    }
  }

  private static void pleaseUnlock() {
    reentrantLock.unlock();
  }

  public static void main(String[] args) {
    // Notice the concurrent access mistakes of ++ due to no locks.
    runScenarioWith(() -> {
      noLockIncrement();
    }, "No lock");

    // Expected result.
    runScenarioWith(() -> {
      lockIncrement();
    }, "With lock");

    // Expected result with lock / unlock in different methods
    runScenarioWith(() -> {
      lockAccross2MethodsIncrement();
    }, "With lock accross 2 methods");
  }

  private static void runScenarioWith(Runnable r, String description) {
    count = 0;
    Thread t1 = new Thread(r);
    Thread t2 = new Thread(r);

    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(String.format("%s count: %d", description, count));
  }

}