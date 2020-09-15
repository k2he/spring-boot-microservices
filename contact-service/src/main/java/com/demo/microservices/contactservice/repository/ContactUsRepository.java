package com.demo.microservices.contactservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.microservices.contactservice.model.ContactUsInfo;

/**
 * @author kaihe
 *
 */
 
@Repository
public interface ContactUsRepository extends JpaRepository<ContactUsInfo, Integer> {

}
