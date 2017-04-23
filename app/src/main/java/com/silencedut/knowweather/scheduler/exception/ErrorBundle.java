
package com.silencedut.knowweather.scheduler.exception;

/**
 * Created by SilenceDut on 16/10/28.
 *
 * Interface to represent a wrapper around an {@link Exception} to manage errors.
 */
public class ErrorBundle {
  private String mDefaultErrorMsg = "Unknown error";
  private String mErrorMsg;

  private final Exception exception;

  public ErrorBundle(String msg) {
    exception = null;
    this.mErrorMsg = msg;
  }

  public ErrorBundle(Exception exception) {
    this.exception = exception;
  }

  public ErrorBundle(Exception exception, String msg) {
    this.exception = exception;
    this.mErrorMsg = msg;
  }

  public Exception getException() {
    return exception;
  }

  public String getErrorMessage() {

    if (exception != null) {
      mDefaultErrorMsg = exception.getMessage();
    }
    return (mErrorMsg != null) ? mErrorMsg : mDefaultErrorMsg;
  }
}

