package com.challenge.shopcart.common.exception;

public class ElementNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public ElementNotFoundException(String message) {
    super(message != null ? message : "");
  }

  public ElementNotFoundException(String message, Throwable cause) {
    super(message != null ? message : "", cause);
  }
}
