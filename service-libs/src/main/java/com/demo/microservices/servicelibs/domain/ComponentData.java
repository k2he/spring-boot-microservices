package com.demo.microservices.servicelibs.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author kaihe
 *
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public abstract class ComponentData {
    
    protected String projectId;
    
    @Version
    protected Long version;
    
    @Enumerated(EnumType.STRING)
    protected ProcessStatus status;
    
    protected String statusCode;
    
    @Transient
    protected String statusDesc;
    
    @Lob
    @Column(columnDefinition = "BLOB")
    protected String requestPayload;
    
    // This data is set by JVM date to match with Application log
    protected LocalDateTime requestDateTime;
    
    @Lob
    @Column(columnDefinition = "BLOB")
    protected String responsePayload;
    
    // This data is set by JVM date to match with Application log
    protected LocalDateTime responseDateTime;
}

