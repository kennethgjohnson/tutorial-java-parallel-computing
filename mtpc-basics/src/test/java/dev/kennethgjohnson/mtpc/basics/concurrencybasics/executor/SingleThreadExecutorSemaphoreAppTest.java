package dev.kennethgjohnson.mtpc.basics.concurrencybasics.executor;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SingleThreadExecutorSemaphoreAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    SingleThreadExecutorSemaphoreApp.main(arguments);
    assertTrue(true);
  }
}