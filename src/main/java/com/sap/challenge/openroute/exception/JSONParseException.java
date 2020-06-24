package com.sap.challenge.openroute.exception;

/**
 * 
 * Exception class in case of JSON parse failure
 *
 */
public class JSONParseException extends Exception {
  private static final long serialVersionUID = 1L;
  private static final String EXCEPTION_MESSAGE = "Could'nt parse JSON Response from OpenRoute";

  public JSONParseException() {
    super(EXCEPTION_MESSAGE);
  }

}
