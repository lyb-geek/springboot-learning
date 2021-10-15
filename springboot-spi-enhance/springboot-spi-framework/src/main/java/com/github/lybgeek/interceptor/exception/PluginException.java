package com.github.lybgeek.interceptor.exception;

public class PluginException extends RuntimeException {

  private static final long serialVersionUID = 8548771664564998595L;

  public PluginException() {
    super();
  }

  public PluginException(String message) {
    super(message);
  }

  public PluginException(String message, Throwable cause) {
    super(message, cause);
  }

  public PluginException(Throwable cause) {
    super(cause);
  }
}