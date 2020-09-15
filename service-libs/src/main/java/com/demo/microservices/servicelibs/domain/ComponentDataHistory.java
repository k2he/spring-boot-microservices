package com.demo.microservices.servicelibs.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EqualsAndHashCode(callSuper=true)
@EntityListeners(AuditingEntityListener.class)
public abstract class ComponentDataHistory extends ComponentData {

    @CreatedDate
    protected LocalDateTime archivedDateTime;

}
