package com.demo.microservices.servicelibs.dto;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdditionalStatus {

    private Integer statusCode; // We are not using it, to keep it here to follow TD API standard
    private String serverStatusCode;
    private AdditionalStatus.Severity severity;
    private String statusDesc;
    
    public enum Severity {

        INFO("Info"),
        WARNING("Warning"),
        ERROR("Error");
        private final String value;
        private final static Map<String, AdditionalStatus.Severity> CONSTANTS = new HashMap<String, AdditionalStatus.Severity>();

        static {
            for (AdditionalStatus.Severity c: values()) {
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
        public static AdditionalStatus.Severity fromValue(String value) {
            AdditionalStatus.Severity constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}

