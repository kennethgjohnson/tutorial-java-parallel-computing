package dev.kennethgjohnson.mtpc.basics.concurrencybasics.reentrantlocks;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ReentrantLocksAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    ReentrantLocksApp.main(arguments);
    assertTrue(true);
  }
}