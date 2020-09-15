package com.demo.microservices.servicelibs.exception;



import java.io.Serializable;
import com.demo.microservices.servicelibs.dto.AdditionalStatus;
import lombok.Builder;
import lombok.Data;

/*
 * @author kaihe
 * 
 */

@Data
@Builder
public class ServerError implements Serializable {

  private static final long serialVersionUID = 1L;

  private String serverStatusCode;
  private String statusDesc;
  private AdditionalStatus.Severity severity;

}

