package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

public class SequentialProcessingApp {
  public static void main(final String[] args) {
    System.out.println("Starting...");
    Runner runner1 = new Runner("1");
    Runner runner2 = new Runner("2");
    runner1.run();
    runner2.run();
    System.out.println("Completed.");
  }
}
