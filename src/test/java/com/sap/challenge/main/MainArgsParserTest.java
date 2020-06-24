package com.sap.challenge.main;

import static org.junit.Assert.assertEquals;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;
import com.sap.challenge.constants.EngineType;
import com.sap.challenge.constants.Size;
import com.sap.challenge.constants.TransportationType;
import com.sap.challenge.exception.InvalidValueException;

/**
 * 
 * MainArgsParser Test Class
 *
 */
public class MainArgsParserTest {

  private String[] positiveArgs;

  private String[] missingStartArgsOption;

  private String[] missingEndArgsOption;

  private String[] missingTransportationArgsOption;

  private String[] invalidTransportationCarArgsOption;

  private String[] invalidTransportationEngineArgsOption;

  private String[] invalidTransportationSizeArgsOption;

  private String[] invalidTransportationFormatArgsOption;

  private String[] invalidTransportationFormatArgsOption_2;

  @Before
  public void init() {
    positiveArgs = new String[] {"-start", "berlin", "-end", "hamburg", "-transportation-method",
        "medium-diesel-car"};

    missingStartArgsOption =
        new String[] {"berlin", "-end", "hamburg", "-transportation-method", "medium-diesel-car"};

    missingEndArgsOption =
        new String[] {"-start", "berlin", "hamburg", "-transportation-method", "medium-diesel-car"};

    missingTransportationArgsOption =
        new String[] {"-start", "berlin", "-end", "hamburg", "medium-diesel-car"};

    invalidTransportationCarArgsOption = new String[] {"-start", "berlin", "-end", "hamburg",
        "-transportation-method", "medium-diesel-car1"};

    invalidTransportationEngineArgsOption = new String[] {"-start", "berlin", "-end", "hamburg",
        "-transportation-method", "medium-diesel1-car"};

    invalidTransportationSizeArgsOption = new String[] {"-start", "berlin", "-end", "hamburg",
        "-transportation-method", "medium1-diesel-car"};

    invalidTransportationFormatArgsOption = new String[] {"-start", "berlin", "-end", "hamburg",
        "-transportation-method", "medium-dieselcar"};

    invalidTransportationFormatArgsOption_2 = new String[] {"-start", "berlin", "-end", "hamburg",
        "-transportation-method", "mediumdieselcar"};
  }

  @Test
  public void parseArgsPositive() throws ParseException, InvalidValueException {
    MainArgs args = MainArgsParser.parseArgs(positiveArgs);

    assertEquals(args.getSourceCity(), "berlin");
    assertEquals(args.getDestinationCity(), "hamburg");
    assertEquals(args.getEngine(), EngineType.DIESEL);
    assertEquals(args.getSize(), Size.MEDIUM);
    assertEquals(args.getTransportation(), TransportationType.CAR);

  }

  @Test(expected = ParseException.class)
  public void parseArgsMissingStartArg() throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(missingStartArgsOption);
  }

  @Test(expected = ParseException.class)
  public void parseArgsMissingEndArg() throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(missingEndArgsOption);
  }

  @Test(expected = ParseException.class)
  public void parseArgsMissingTransportationArgTest() throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(missingTransportationArgsOption);
  }

  @Test(expected = InvalidValueException.class)
  public void parseArgsInvalidTransportationArgCar() throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(invalidTransportationCarArgsOption);
  }

  @Test(expected = InvalidValueException.class)
  public void parseArgsInvalidTransportationArgSize() throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(invalidTransportationSizeArgsOption);
  }

  @Test(expected = InvalidValueException.class)
  public void parseArgsInvalidTransportationArgEngine()
      throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(invalidTransportationEngineArgsOption);
  }

  @Test(expected = InvalidValueException.class)
  public void parseArgsInvalidTransportationArgFormat()
      throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(invalidTransportationFormatArgsOption);
  }

  @Test(expected = InvalidValueException.class)
  public void parseArgsInvalidTransportationArgFormat_2()
      throws ParseException, InvalidValueException {
    MainArgsParser.parseArgs(invalidTransportationFormatArgsOption_2);
  }


}
