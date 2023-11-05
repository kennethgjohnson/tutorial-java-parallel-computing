package dev.kennethgjohnson.mtpc.basics.concurrencybasics.normallocks;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SequentialProcessingAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    SequentialProcessingApp.main(arguments);
    assertTrue(true);
  }
}
