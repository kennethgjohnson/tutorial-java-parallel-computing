package dev.kennethgjohnson.mtpc.basics.concurrentcollections.latches;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

//Worker to use with count down latch examples
public class CountdownLatchWorker implements Runnable {
  private CountDownLatch countdownLatch;
  private Integer workerId;
  private String workData;

  public CountdownLatchWorker(Integer workerId, CountDownLatch countdownLatch, String workData) {
    this.workerId = workerId;
    this.countdownLatch = countdownLatch;
    this.workData = workData;
  }

  @Override
  public void run() {
    try {
      doWork();
      countdownLatch.countDown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void doWork() throws InterruptedException {
    System.out.println(String.format("LatchWorker: Id %d is working...", workerId));
    Thread.sleep(1000);
    this.workData = workData.toUpperCase() + "-" + UUID.randomUUID().toString();
    System.out.println(String.format("LatchWorker: result stored: %s...", this.workData));
  }

  public String getResultData() {
    return this.workData;
  }

  public Integer getWorkerId() {
    return this.workerId;
  }
}