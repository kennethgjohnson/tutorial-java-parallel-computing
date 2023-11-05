package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

import java.util.List;
import java.util.ArrayList;

class ProducerConsumer {
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
    synchronized (list) {
      this.stop = false;

      while (!stop) {
        int listsize = list.size();
        if (listsize < limit) {
          System.out.println(String.format("P: Size is: %d, adding %d", listsize, nextInt));
          list.add(nextInt);
          nextInt++;
        } else {
          System.out.println("P: Limit reached. Setting lock to notify others waiting, and going to sleep waiting...");
          list.notify();
          list.wait();
        }
        Thread.sleep(200);
      }

      // We need to notify during shutdown to get sleeping threads to see the stop
      // signal.
      this.list.notifyAll();
    }
    System.out.println("P: ended.");
  }

  public void consume() throws InterruptedException {
    System.out.println("C: started.");
    synchronized (this.list) {
      this.stop = false;

      while (!this.stop) {
        if (list.size() > 0) {
          System.out.print(String.format("C: Removing value %d, ", list.get(0)));
          list.remove(0);
          System.out.println(String.format(" size is %d...", list.size()));
        } else {
          System.out
              .println("C: The list is empty. Setting lock to notify others waiting, and going to sleep waiting...");
          list.notify();
          list.wait();
        }
        Thread.sleep(200);
      }

      // We need to notify during shutdown to get sleeping threads to see the stop
      // signal.
      this.list.notifyAll();
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

public class ProducerConsumerApp {

  public static void main(String[] args) {
    ProducerConsumer pc = new ProducerConsumer(5);

    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          pc.produce();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          pc.consume();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
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