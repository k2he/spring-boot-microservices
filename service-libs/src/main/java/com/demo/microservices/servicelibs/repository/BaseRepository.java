/**
 * 
 */
package com.demo.microservices.servicelibs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author kaihe
 *
 */

@NoRepositoryBean
public interface BaseRepository<T, PK> extends JpaRepository<T, PK> {

}

