package com.sap.challenge.constants;

/**
 * 
 * Enum to hold transportation Type constants
 *
 */
public enum TransportationType {
  CAR("car"), TRAIN("train"), BUS("bus");

  private String value;

  private TransportationType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
