package dev.kennethgjohnson.mtpc.basics.concurrencybasics.semaphore;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BasicSemaphoreAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    BasicSemaphoreApp.main(arguments);
    assertTrue(true);
  }
}