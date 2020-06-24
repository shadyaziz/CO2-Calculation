package com.sap.challenge.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sap.challenge.constants.utility.TransportationConsumption;
import com.sap.challenge.exception.InvalidValueException;
import com.sap.challenge.openroute.adapter.OpenRouteAdapter;
import com.sap.challenge.openroute.exception.JSONParseException;
import com.sap.challenge.openroute.exception.OpenRouteConnectionException;
import com.sap.challenge.openroute.model.Coordinate;

/**
 * 
 * Main class with main function
 *
 */
public class Main {
  private final Logger logger = LogManager.getLogger(Main.class);

  public BigDecimal calculateCO2(String[] args) {
    BigDecimal result = BigDecimal.ZERO;
    try {
      OpenRouteAdapter adapter = new OpenRouteAdapter();
      MainArgs mainArgs = MainArgsParser.parseArgs(args);
      Coordinate source = adapter.getCityCoordinates(mainArgs.getSourceCity());
      Coordinate destination = adapter.getCityCoordinates(mainArgs.getDestinationCity());

      BigDecimal distance = adapter.getDistanceBetweenCoordinates(source, destination);
      Integer consumption;
      switch (mainArgs.getTransportation()) {
        case CAR:
          consumption = TransportationConsumption.getInstance()
              .getCarConsumption(mainArgs.getSize(), mainArgs.getEngine());
          break;
        case BUS:
        case TRAIN:
          consumption = TransportationConsumption.getInstance()
              .getOtherConsumption(mainArgs.getTransportation());
          break;
        default:
          throw new IllegalArgumentException();
      }

      result = distance.multiply(new BigDecimal(consumption)).movePointLeft(3).setScale(1,
          RoundingMode.HALF_DOWN);

      System.out.println(String.format("Your trip caused %skg of CO2-equivalent.", result));
    } catch (OpenRouteConnectionException e) {
      logger.error("Couldn't connect with OpenRoute ", e);
    } catch (JSONParseException e) {
      logger.error("Couldn't parse JSON returned from OpenRoute ", e);
    } catch (ParseException e) {
      logger.error("Couldn't parse commandline arguments", e);
    } catch (InvalidValueException e) {
      logger.error("TransportationType ");
    }
    return result;
  }


  public static void main(String[] args) {
    Main main = new Main();
    main.calculateCO2(args);
  }
}
