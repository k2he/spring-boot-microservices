package com.demo.microservices.servicelibs.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kaihe
 *
 */

@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {

  protected String serverStatusCode;
  protected Severity severity;
  protected List<AdditionalStatus> additionalStatus;

  public enum Severity {

    WARNING("Warning"), ERROR("Error");

    private final String value;
    private static final Map<String, Status.Severity> CONSTANTS = new HashMap<String, Status.Severity>();

    static {
      for (Status.Severity c : values()) {
        CONSTANTS.put(c.value, c);
      }
    }

    private Severity(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return this.value;
    }

    @JsonValue
    public String value() {
      return this.value;
    }

    @JsonCreator
    public static Status.Severity fromValue(String value) {
      Status.Severity constant = CONSTANTS.get(value);
      if (constant == null) {
        throw new IllegalArgumentException(value);
      } else {
        return constant;
      }
    }
  }
}
