package com.sap.challenge.constants;

/**
 * 
 * enum to hold consumption of different transportation types
 *
 */
public enum Consumption {
  SMALL_DIESEL(142), SMALL_PETROL(154), SMALL_PLUGIN_HYBRID(73), SMALL_ELECTRIC(50), MEDIUM_DIESEL(
      171), MEDIUM_PETROL(192), MEDIUM_PLUGIN_HYBRID(110), MEDIUM_ELECTRIC(58), LARGE_DIESEL(
          209), LARGE_PETROL(282), LARGE_PLUGIN_HYBRID(126), LARGE_ELECTRIC(73), BUS(27), TRAIN(6);

  private Integer value;

  private Consumption(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
