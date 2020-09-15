package com.demo.microservices.contactservice.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.microservices.contactservice.model.ContactUsInfo;
import com.demo.microservices.contactservice.service.ContactService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactUsController {

  @NonNull
  private ContactService contactService;

  // Get All contact note messages
  @GetMapping()
  public List<ContactUsInfo> getAllContacts() {
    return contactService.getAllContacts();
  }

  // Create a new contact message
  @PostMapping()
  public ContactUsInfo createContacts(@Valid @RequestBody ContactUsInfo contact) {
    return contactService.createContact(contact);
  }

  // Get a contact
  @GetMapping("/{id}")
  public ResponseEntity<ContactUsInfo> getContactById(@PathVariable(value = "id") Integer id) {
    ContactUsInfo contact = contactService.getContactById(id);
    return ResponseEntity.ok().body(contact);
  }

  // Update a contact message
  @PutMapping("/{id}")
  public ContactUsInfo updateMessage(@PathVariable(value = "id") Integer id,
      @Valid @RequestBody ContactUsInfo info) {
    return contactService.updateContact(info);
  }

  // Delete a contact (set as resolved and leave in database)
  @DeleteMapping("/{id}")
  public void deleteMessage(@PathVariable(value = "id") Integer id) {
    contactService.deleteContact(id);
  }

}
