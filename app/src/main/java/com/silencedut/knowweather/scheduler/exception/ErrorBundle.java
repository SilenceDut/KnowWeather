
package com.silencedut.knowweather.scheduler.exception;

/**
 * Created by SilenceDut on 16/10/28.
 *
 * Interface to represent a wrapper around an {@link Exception} to manage errors.
 */
public interface ErrorBundle {
  Exception getException();

  String getErrorMessage();
}
