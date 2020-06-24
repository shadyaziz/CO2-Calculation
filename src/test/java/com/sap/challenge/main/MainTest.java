package com.sap.challenge.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Main class integration black box test class
 *
 */
public class MainTest {
  private Main main;

  private String[] positiveArgs;

  private String[] positiveArgs_2;

  private String[] positiveArgs_3;

  @Before
  public void init() {
    assertTrue(System.getenv("ORS_TOKEN") != null && !System.getenv("ORS_TOKEN").isBlank());

    main = new Main();

    positiveArgs = new String[] {"-start", "hamburg", "-end", "berlin", "-transportation-method",
        "medium-diesel-car"};

    positiveArgs_2 = new String[] {"-start", "Los Angeles", "-end", "New York",
        "-transportation-method=medium-diesel-car"};

    positiveArgs_3 = new String[] {"-end", "New York", "-start", "Los Angeles",
        "-transportation-method=large-electric-car"};

  }

  @Test
  public void blackBoxTesting() {
    BigDecimal result = main.calculateCO2(positiveArgs);
    assertEquals("49.2", result.toString());
  }

  @Test
  public void blackBoxTesting_2() {
    BigDecimal result = main.calculateCO2(positiveArgs_2);
    assertEquals("770.4", result.toString());
  }

  @Test
  public void blackBoxTesting_3() {
    BigDecimal result = main.calculateCO2(positiveArgs_3);
    assertEquals("328.9", result.toString());
  }

}
