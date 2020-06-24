package com.sap.challenge.exception;

/**
 * 
 * Exception class to refer to invalid inputs by the user
 *
 */
public class InvalidValueException extends Exception {

  private static final long serialVersionUID = 1L;

  private final static String EXCEPTION_MESSAGE = "Value is invalid or null";


  public InvalidValueException() {
    super(EXCEPTION_MESSAGE);
  }

}
