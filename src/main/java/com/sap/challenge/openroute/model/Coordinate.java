package com.sap.challenge.openroute.model;

import java.math.BigDecimal;

/**
 * class to hold coordinate points
 * 
 */
public class Coordinate {
  private BigDecimal latitude;
  private BigDecimal longitude;

  public Coordinate(BigDecimal latitude, BigDecimal longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }
}
