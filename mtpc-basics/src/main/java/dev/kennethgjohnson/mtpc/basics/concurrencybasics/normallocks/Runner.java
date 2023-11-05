package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

class Runner implements Runnable {
  private String instanceName;

  private String getInstanceName() {
    return instanceName;
  }

  private void setInstanceName(final String instanceName) {
    this.instanceName = instanceName;
  }

  public Runner(final String instanceName) {
    this.setInstanceName(instanceName);
  }

  @Override
  public void run() {
    for (int i = 0; i < 25; i++) {
      System.out.println(String.format("Runner%s is running: %d", this.getInstanceName(), i));
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

}