package com.demo.microservices.contactservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.demo.microservices.servicelibs.audit.AppAuditAwareImpl;

/**
 * @author kaihe
 *
 */

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new AppAuditAwareImpl();
  }
}
