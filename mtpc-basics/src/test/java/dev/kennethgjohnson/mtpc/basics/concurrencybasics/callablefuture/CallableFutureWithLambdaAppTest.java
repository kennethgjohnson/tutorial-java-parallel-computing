package dev.kennethgjohnson.mtpc.basics.concurrencybasics.callablefuture;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CallableFutureWithLambdaAppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    CallableFutureWithLambdaApp.main(arguments);
    assertTrue(true);
  }
}