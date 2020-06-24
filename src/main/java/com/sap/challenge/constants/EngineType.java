package com.sap.challenge.constants;

/**
 * 
 * Enum to hold constant values of engine types
 *
 */
public enum EngineType {
  DIESEL("diesel"), PETROL("petrol"), PLUGIN_HYBRID("plugin-hybrid"), ELECTRIC("electric");

  private String value;

  private EngineType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
