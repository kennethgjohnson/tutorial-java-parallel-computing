package dev.kennethgjohnson.mtpc.basics.concurrencybasics.callablefuture;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CallableFutureBasicAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    CallableFutureBasicApp.main(arguments);
    assertTrue(true);
  }
}