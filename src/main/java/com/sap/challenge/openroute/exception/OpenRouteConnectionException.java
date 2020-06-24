package com.sap.challenge.openroute.exception;

/**
 * 
 * Exception class in case API connection failure
 *
 */
public class OpenRouteConnectionException extends Exception {
  private static final long serialVersionUID = 1L;
  private static final String EXCEPTION_MESSAGE = "Couldn't connect to OpenRoute API";

  public OpenRouteConnectionException() {
    super(EXCEPTION_MESSAGE);
  }

}
