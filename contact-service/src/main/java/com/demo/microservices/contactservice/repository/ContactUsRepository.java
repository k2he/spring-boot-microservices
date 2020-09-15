package com.demo.microservices.contactservice.repository;

import org.springframework.stereotype.Repository;
import com.demo.microservices.contactservice.model.ContactUsInfo;
import com.demo.microservices.servicelibs.repository.BaseRepository;

/**
 * @author kaihe
 *
 */
 
@Repository
public interface ContactUsRepository extends BaseRepository<ContactUsInfo, Integer> {

}
