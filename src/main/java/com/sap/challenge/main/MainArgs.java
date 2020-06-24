package com.sap.challenge.main;

import com.sap.challenge.constants.EngineType;
import com.sap.challenge.constants.Size;
import com.sap.challenge.constants.TransportationType;

/**
 * 
 * Holds the arguments given by the user
 *
 */
public class MainArgs {
  private String sourceCity;
  private String destinationCity;
  private TransportationType transportation;
  private EngineType engine;
  private Size size;

  public String getSourceCity() {
    return sourceCity;
  }

  public void setSourceCity(String sourceCity) {
    this.sourceCity = sourceCity;
  }

  public String getDestinationCity() {
    return destinationCity;
  }

  public void setDestinationCity(String destinationCity) {
    this.destinationCity = destinationCity;
  }

  public TransportationType getTransportation() {
    return transportation;
  }

  public void setTransportation(TransportationType transportation) {
    this.transportation = transportation;
  }

  public EngineType getEngine() {
    return engine;
  }

  public void setEngine(EngineType engine) {
    this.engine = engine;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }


}
