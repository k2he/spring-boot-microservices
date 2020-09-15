package com.demo.microservices.contactservice.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.demo.microservices.contactservice.model.ContactUsInfo;
import com.demo.microservices.contactservice.repository.ContactUsRepository;
import com.demo.microservices.contactservice.util.ContactConstants;
import com.demo.microservices.servicelibs.exception.ResourceNotFoundException;
import com.demo.microservices.servicelibs.service.MessageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@Service("cotactService")
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

  @NonNull
  private ContactUsRepository contactRepository;

  @NonNull
  private MessageService messageService;

  @Override
  public List<ContactUsInfo> getAllContacts() {
    return contactRepository.findAll();
  }

  @Override
  public ContactUsInfo createContact(ContactUsInfo contact) {
    return contactRepository.save(contact);
  }

  @Override
  public ContactUsInfo getContactById(Integer id) {
    String notFoundMsg = messageService.getMessage(ContactConstants.CONTACT_SERVICE_ERROR_NOT_FOUND, id.toString());
    return contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        ContactConstants.CONTACT_SERVICE_ERROR_NOT_FOUND, notFoundMsg));
  }

  @Override
  public ContactUsInfo updateContact(ContactUsInfo contact) {
    String notFoundMsg = messageService.getMessage(ContactConstants.CONTACT_SERVICE_ERROR_NOT_FOUND, contact.toString());
    ContactUsInfo oldContact = contactRepository.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException(
        ContactConstants.CONTACT_SERVICE_ERROR_NOT_FOUND, notFoundMsg));
    oldContact.setName(contact.getName());
    oldContact.setEmail(contact.getEmail());
    oldContact.setPhoneNumber(contact.getPhoneNumber());
    oldContact.setSubject(contact.getSubject());
    oldContact.setMessage(contact.getMessage());

    return contactRepository.save(oldContact);
  }

  @Override
  public void deleteContact(Integer id) {
    ContactUsInfo message = contactRepository.findById(id).get();
    if (message != null) {
      contactRepository.delete(message);
    }
  }
}
