package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Contact;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private UserService userService;

    public void saveContactRelationship(MyUserDetails user, User userContact) throws UserNotFoundException {
        LOGGER.info("Processing to save a new relationship");
        Contact contact = new Contact();

        if (userContact==null) {
            throw new UserNotFoundException("User not found");
        }
        contact.setContactUserId(userContact);
        contact.setUserId(userService.getUserById(user.getUserID()));

        contactRepository.save(contact);
    }
}
