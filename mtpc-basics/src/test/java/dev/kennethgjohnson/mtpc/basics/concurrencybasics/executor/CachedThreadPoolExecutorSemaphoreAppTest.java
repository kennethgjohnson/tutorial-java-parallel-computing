package dev.kennethgjohnson.mtpc.basics.concurrencybasics.executor;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CachedThreadPoolExecutorSemaphoreAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    CachedThreadPoolExecutorSemaphoreApp.main(arguments);
    assertTrue(true);
  }
}