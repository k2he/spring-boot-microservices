package com.demo.microservices.servicelibs.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/*
* @author hekai27
* 
 * This class represents response returned when 
 * restful service provided by this component is called.
 * in cases where these is an error and serverError object 
 * needs to be populated
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorRestResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private Status status;
}
