package com.sap.challenge.utility;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import com.sap.challenge.constants.Consumption;
import com.sap.challenge.constants.EngineType;
import com.sap.challenge.constants.Size;
import com.sap.challenge.constants.TransportationType;
import com.sap.challenge.constants.utility.TransportationConsumption;
import com.sap.challenge.exception.InvalidValueException;

public class TransportationConsumptionTest {

  private TransportationConsumption transportationConsumption;

  @Before
  public void init() {
    transportationConsumption = TransportationConsumption.getInstance();
  }

  @Test(expected = InvalidValueException.class)
  public void getOtherConsumptionNullValue() throws InvalidValueException {
    transportationConsumption.getOtherConsumption(null);
  }

  @Test(expected = InvalidValueException.class)
  public void getCarConsumptionSizeNull() throws InvalidValueException {
    transportationConsumption.getCarConsumption(null, EngineType.DIESEL);
  }

  @Test(expected = InvalidValueException.class)
  public void getCarConsumptionEngineNull() throws InvalidValueException {
    transportationConsumption.getCarConsumption(Size.LARGE, null);
  }

  @Test
  public void getOtherConsumptionPositive() throws InvalidValueException {
    assertEquals(transportationConsumption.getOtherConsumption(TransportationType.BUS),
        Consumption.BUS.getValue());

    assertEquals(transportationConsumption.getOtherConsumption(TransportationType.TRAIN),
        Consumption.TRAIN.getValue());
  }

  @Test
  public void getCarConsumptionPositive() throws InvalidValueException {
    assertEquals(transportationConsumption.getCarConsumption(Size.SMALL, EngineType.DIESEL),
        Consumption.SMALL_DIESEL.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.SMALL, EngineType.ELECTRIC),
        Consumption.SMALL_ELECTRIC.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.SMALL, EngineType.PETROL),
        Consumption.SMALL_PETROL.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.SMALL, EngineType.PLUGIN_HYBRID),
        Consumption.SMALL_PLUGIN_HYBRID.getValue());

    assertEquals(transportationConsumption.getCarConsumption(Size.MEDIUM, EngineType.DIESEL),
        Consumption.MEDIUM_DIESEL.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.MEDIUM, EngineType.ELECTRIC),
        Consumption.MEDIUM_ELECTRIC.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.MEDIUM, EngineType.PETROL),
        Consumption.MEDIUM_PETROL.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.MEDIUM, EngineType.PLUGIN_HYBRID),
        Consumption.MEDIUM_PLUGIN_HYBRID.getValue());

    assertEquals(transportationConsumption.getCarConsumption(Size.LARGE, EngineType.DIESEL),
        Consumption.LARGE_DIESEL.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.LARGE, EngineType.ELECTRIC),
        Consumption.LARGE_ELECTRIC.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.LARGE, EngineType.PETROL),
        Consumption.LARGE_PETROL.getValue());
    assertEquals(transportationConsumption.getCarConsumption(Size.LARGE, EngineType.PLUGIN_HYBRID),
        Consumption.LARGE_PLUGIN_HYBRID.getValue());
  }
}
