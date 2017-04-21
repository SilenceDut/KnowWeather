
package com.silencedut.knowweather.scheduler.exception;
/**
 * Created by SilenceDut on 16/10/28.
 *
 * Wrapper around Exceptions used to manage default errors.
 */
public class DefaultErrorBundle implements ErrorBundle {

  private static final String DEFAULT_ERROR_MSG = "Unknown error";
  private String mErrorMsg;

  private final Exception exception;

  public DefaultErrorBundle( String msg) {
    exception = null;
    this.mErrorMsg = msg;
  }

  public DefaultErrorBundle(Exception exception) {
    this.exception = exception;
  }

  public DefaultErrorBundle(Exception exception, String msg) {
    this.exception = exception;
    this.mErrorMsg = msg;
  }

  @Override
  public Exception getException() {
    return exception;
  }

  @Override
  public String getErrorMessage() {
    return (mErrorMsg != null) ? mErrorMsg : DEFAULT_ERROR_MSG;
  }
}
