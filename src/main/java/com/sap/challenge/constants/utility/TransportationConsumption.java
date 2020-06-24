package com.sap.challenge.constants.utility;

import java.util.HashMap;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sap.challenge.constants.Consumption;
import com.sap.challenge.constants.EngineType;
import com.sap.challenge.constants.Size;
import com.sap.challenge.constants.TransportationType;
import com.sap.challenge.exception.InvalidValueException;

/**
 * 
 * Singleton utility class to hold constant values
 *
 */
public class TransportationConsumption {
  private final Logger logger = LogManager.getLogger(TransportationConsumption.class);

  private HashMap<TransportationType, Integer> otherConsumption;

  private HashMap<Size, HashMap<EngineType, Integer>> carConsumption;

  private static TransportationConsumption instance;

  private TransportationConsumption() {
    initMaps();
  }

  public static TransportationConsumption getInstance() {
    if (instance == null) {
      instance = new TransportationConsumption();
    }
    return instance;
  }

  /**
   * getting consumption of a transportation type. BUS or TRAIN values are supported
   * 
   * @param transportationType
   * @return
   * @throws InvalidValueException
   */
  public Integer getOtherConsumption(TransportationType transportationType)
      throws InvalidValueException {
    logger.debug("Getting Consumption of TransportationType : {} ", transportationType);
    return Optional.ofNullable(otherConsumption.get(transportationType))
        .orElseThrow(() -> new InvalidValueException());
  }

  /**
   * getting consumption of a car with different sizes and engines
   * 
   * @param size
   * @param engineType
   * @return
   * @throws InvalidValueException
   */
  public Integer getCarConsumption(Size size, EngineType engineType) throws InvalidValueException {
    logger.debug("Getting consumption of car with size : {} and engine {}", size, engineType);
    HashMap<EngineType, Integer> engineConsumption = Optional.ofNullable(carConsumption.get(size))
        .orElseThrow(() -> new InvalidValueException());

    return Optional.ofNullable(engineConsumption.get(engineType))
        .orElseThrow(() -> new InvalidValueException());
  }


  private void initMaps() {
    initOtherConsumption();
    initCarConsumption();
  }

  private void initOtherConsumption() {
    otherConsumption = new HashMap<TransportationType, Integer>();
    otherConsumption.put(TransportationType.BUS, Consumption.BUS.getValue());
    otherConsumption.put(TransportationType.TRAIN, Consumption.TRAIN.getValue());
  }

  private void initCarConsumption() {
    carConsumption = new HashMap<Size, HashMap<EngineType, Integer>>();

    carConsumption.put(Size.SMALL, initSmallEngineConsumption());
    carConsumption.put(Size.MEDIUM, initMediumEngineConsumption());
    carConsumption.put(Size.LARGE, initLargeEngineConsumption());
  }

  private HashMap<EngineType, Integer> initSmallEngineConsumption() {
    HashMap<EngineType, Integer> smallEngineConsumption = new HashMap<EngineType, Integer>();
    smallEngineConsumption.put(EngineType.DIESEL, Consumption.SMALL_DIESEL.getValue());
    smallEngineConsumption.put(EngineType.PETROL, Consumption.SMALL_PETROL.getValue());
    smallEngineConsumption.put(EngineType.PLUGIN_HYBRID,
        Consumption.SMALL_PLUGIN_HYBRID.getValue());
    smallEngineConsumption.put(EngineType.ELECTRIC, Consumption.SMALL_ELECTRIC.getValue());

    return smallEngineConsumption;
  }

  private HashMap<EngineType, Integer> initMediumEngineConsumption() {
    HashMap<EngineType, Integer> mediumEngineConsumption = new HashMap<EngineType, Integer>();
    mediumEngineConsumption.put(EngineType.DIESEL, Consumption.MEDIUM_DIESEL.getValue());
    mediumEngineConsumption.put(EngineType.PETROL, Consumption.MEDIUM_PETROL.getValue());
    mediumEngineConsumption.put(EngineType.PLUGIN_HYBRID,
        Consumption.MEDIUM_PLUGIN_HYBRID.getValue());
    mediumEngineConsumption.put(EngineType.ELECTRIC, Consumption.MEDIUM_ELECTRIC.getValue());

    return mediumEngineConsumption;
  }

  private HashMap<EngineType, Integer> initLargeEngineConsumption() {
    HashMap<EngineType, Integer> largeEngineConsumption = new HashMap<EngineType, Integer>();
    largeEngineConsumption.put(EngineType.DIESEL, Consumption.LARGE_DIESEL.getValue());
    largeEngineConsumption.put(EngineType.PETROL, Consumption.LARGE_PETROL.getValue());
    largeEngineConsumption.put(EngineType.PLUGIN_HYBRID,
        Consumption.LARGE_PLUGIN_HYBRID.getValue());
    largeEngineConsumption.put(EngineType.ELECTRIC, Consumption.LARGE_ELECTRIC.getValue());

    return largeEngineConsumption;
  }

}
