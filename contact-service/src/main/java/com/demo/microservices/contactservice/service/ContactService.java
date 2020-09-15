package com.demo.microservices.contactservice.service;

import java.util.List;
import com.demo.microservices.contactservice.model.ContactUsInfo;

/**
 * @author kaihe
 *
 */

public interface ContactService {
  
  List<ContactUsInfo> getAllContacts();

  ContactUsInfo createContact(ContactUsInfo contact);

  ContactUsInfo getContactById(Integer id);

  ContactUsInfo updateContact(ContactUsInfo contact);

  void deleteContact(Integer id);
}
