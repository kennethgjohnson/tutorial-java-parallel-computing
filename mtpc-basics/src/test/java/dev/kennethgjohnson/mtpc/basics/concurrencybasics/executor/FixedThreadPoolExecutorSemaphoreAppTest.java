package dev.kennethgjohnson.mtpc.basics.concurrencybasics.executor;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FixedThreadPoolExecutorSemaphoreAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    FixedThreadPoolExecutorSemaphoreApp.main(arguments);
    assertTrue(true);
  }
}