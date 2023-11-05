package dev.kennethgjohnson.mtpc.basics.concurrentcollections.latches;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CountdownLatchAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    CountdownLatchApp.main(arguments);
    assertTrue(true);
  }
}