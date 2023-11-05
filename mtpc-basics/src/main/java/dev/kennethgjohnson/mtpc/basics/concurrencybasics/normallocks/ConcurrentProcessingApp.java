package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

public class ConcurrentProcessingApp {
  public static void main(final String[] args) {
    System.out.println("Starting...");
    Thread t1 = new Thread(new Runner("1"));
    Thread t2 = new Thread(new Runner("2"));
    RunnerThread t3 = new RunnerThread("3");
    t1.start();
    t2.start();
    t3.start();
    try {
      t1.join();
      t2.join();
      t3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Completed.");
  }
}
