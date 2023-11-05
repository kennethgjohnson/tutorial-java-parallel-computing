package dev.kennethgjohnson.mtpc.basics;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AppTest {

  @Test
  public void shouldBeAbleToExecuteMainWithoutException() {
    String[] arguments = {};
    App.main(arguments);
    assertTrue(true);
  }
}
