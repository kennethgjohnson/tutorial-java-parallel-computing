package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

public class Worker implements Runnable {
  private String workername = "";

  public String getWorkername() {
    return workername;
  }

  public void setWorkername(String workername) {
    this.workername = workername;
  }

  private volatile boolean isTerminated = false;

  public boolean isTerminated() {
    return isTerminated;
  }

  public void setTerminated(boolean isTerminated) {
    this.isTerminated = isTerminated;
  }

  public Worker(String workername) {
    this.setWorkername(workername);
  }

  @Override
  public void run() {
    while (!this.isTerminated()) {
      System.out.println(String.format("%s is doing work!", this.getWorkername()));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
        this.setTerminated(true);
      }
    }
  }

}
