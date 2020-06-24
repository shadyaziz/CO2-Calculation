package com.sap.challenge.constants;

/**
 * 
 * Enum to hold constant values of Transportation sizes
 *
 */
public enum Size {
  SMALL("small"), MEDIUM("medium"), LARGE("large");

  private String value;

  private Size(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
