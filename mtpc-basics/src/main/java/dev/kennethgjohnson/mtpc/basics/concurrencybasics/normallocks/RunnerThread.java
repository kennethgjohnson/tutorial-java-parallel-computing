package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

public class RunnerThread extends Thread {
  private Runner internalRunnerInstance;

  public Runner getInternalRunnerInstance() {
    return internalRunnerInstance;
  }

  public void setInternalRunnerInstance(Runner internalRunnerInstance) {
    this.internalRunnerInstance = internalRunnerInstance;
  }

  public RunnerThread(final String instanceName) {
    this.setInternalRunnerInstance(new Runner(instanceName));
  }

  @Override
  public void run() {
    this.internalRunnerInstance.run();
  }
}
