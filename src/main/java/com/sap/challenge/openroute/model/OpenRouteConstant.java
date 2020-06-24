package com.sap.challenge.openroute.model;

/**
 * 
 * Enum to hold JSON reply keys of OpenRoute
 *
 */
public enum OpenRouteConstant {
  FEATURES("features"), GEOMETRY("geometry"), COORDINATES("coordinates"), DISTANCES(
      "distances"), OPENROUTE_URL("https://api.openrouteservice.org");

  private String value;

  private OpenRouteConstant(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
