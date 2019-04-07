package com.demo.microservices.testservice.config;

import lombok.Getter;
import lombok.Setter;

public class RequestContext {

  private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

  @Getter
  @Setter
  private String token;

  public static RequestContext getContext() {
    RequestContext result = CONTEXT.get();

    if (result == null) {
      result = new RequestContext();
      CONTEXT.set(result);
    }

    return result;
  }
}
