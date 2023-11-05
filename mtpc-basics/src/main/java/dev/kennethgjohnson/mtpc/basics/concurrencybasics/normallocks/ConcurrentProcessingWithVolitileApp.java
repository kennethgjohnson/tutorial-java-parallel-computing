package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

public class ConcurrentProcessingWithVolitileApp {
  public static void main(final String[] args) {
    System.out.println("Starting...");
    Worker w1 = new Worker("1");
    Worker w2 = new Worker("2");
    Worker w3 = new Worker("3");

    Thread t1 = new Thread(w1);
    Thread t2 = new Thread(w2);
    Thread t3 = new Thread(w3);

    Worker[] workers = { w1, w2, w3 };
    Thread[] threads = { t1, t2, t3 };

    t1.start();
    t2.start();
    t3.start();

    try {
      Thread.sleep(3000);

      for (Worker worker : workers) {
        worker.setTerminated(true);
      }

      for (Thread thread : threads) {
        thread.join();
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Completed.");
  }
}
