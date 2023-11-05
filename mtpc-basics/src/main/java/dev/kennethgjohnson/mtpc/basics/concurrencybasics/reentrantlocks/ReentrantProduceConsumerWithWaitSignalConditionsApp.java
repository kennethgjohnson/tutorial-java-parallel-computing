package dev.kennethgjohnson.mtpc.basics.concurrencybasics.reentrantlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProducerConsumer {
  private Lock lock = new ReentrantLock();
  private Condition queueFullCondition = lock.newCondition();
  private Condition queueEmptyCondition = lock.newCondition();

  private List<Integer> list = new ArrayList<>();
  private Integer limit;
  private boolean stop;
  private Integer nextInt = 1;

  public ProducerConsumer(Integer limit) {
    this.setLimit(limit);
    this.stop = true;
  }

  private void setLimit(Integer limit) {
    this.limit = limit;
  }

  public void produce() throws InterruptedException {
    System.out.println("P: started.");

    lock.lock();
    try {
      this.stop = false;

      while (!stop) {
        int listsize = list.size();
        if (listsize < limit) {
          System.out.println(String.format("P: Size is: %d, adding %d", listsize, nextInt));
          list.add(nextInt);
          nextInt++;
        } else {
          System.out.println("P: Limit reached. Setting lock to notify others waiting, and going to sleep waiting...");

          queueFullCondition.signalAll();
          queueEmptyCondition.await();
        }
        Thread.sleep(200);
      }
      // We need to signal during shutdown to get sleeping threads to see the stop
      // signal.
      queueFullCondition.signalAll();
    } finally {
      lock.unlock();
    }
    System.out.println("P: ended.");
  }

  public void consume() throws InterruptedException {
    System.out.println("C: started.");
    lock.lock();
    try {
      this.stop = false;

      while (!this.stop) {
        if (list.size() > 0) {
          System.out.print(String.format("C: Removing value %d, ", list.get(0)));
          list.remove(0);
          System.out.println(String.format(" size is %d...", list.size()));
        } else {
          System.out.println(
              "C: The list is empty. Setting lock to signal others a waiting a signal, and going to sleep awaiting...");
          queueEmptyCondition.signalAll();
          queueFullCondition.await();
        }
        Thread.sleep(200);
      }

      // We need to signal during shutdown to get sleeping threads to see the stop
      // signal.
      queueEmptyCondition.signalAll();
    } finally {
      lock.unlock();
    }
    System.out.println("C: ended.");
  }

  public void stop() {
    this.stop = true;
  }

  public List<Integer> getList() {
    return this.list;
  }
}

public class ReentrantProduceConsumerWithWaitSignalConditionsApp {
  public static void main(String[] args) {
    ProducerConsumer pc = new ProducerConsumer(5);

    System.out.println("S:Scenario Started...");
    Thread t1 = new Thread(() -> {
      try {
        pc.produce();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread t2 = new Thread(() -> {
      try {
        pc.consume();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    t1.start();
    t2.start();

    try {
      System.out.println("main: giving them 7.5 seconds to run.");
      Thread.sleep(7500);
      System.out.println("main: 7.5 seconds finished, commanding them to stop.");
      pc.stop();
      System.out.println("main: waiting for them to stop.");
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(String.format("main: stopped, remaining items in the list: %s", pc.getList().toString()));
  }
}