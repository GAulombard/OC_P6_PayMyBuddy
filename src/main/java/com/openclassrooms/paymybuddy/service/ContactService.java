package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.Contact;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ContactRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Contact service.
 */
@Service
public class ContactService {

    private final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Save contact relationship.
     *
     * @param user        the user
     * @param userContact the user contact
     * @throws UserNotFoundException the user not found exception
     */
    public void saveContactRelationship(MyUserDetails user, User userContact) throws UserNotFoundException {
        LOGGER.info("Processing to save a new relationship");
        Contact contact = new Contact();

        if (userContact == null) {
            throw new UserNotFoundException("User not found");
        }
        contact.setContactUserId(userContact);
        contact.setUserId(userService.getUserById(user.getUserID()));

        contactRepository.save(contact);
    }

    /**
     * Delete contact by user id and contact user id.
     *
     * @param userId        the user id
     * @param contactUserId the contact user id
     */
    public void deleteContactByUserIdAndContactUserId(int userId, int contactUserId) throws UserNotFoundException {
        LOGGER.info("Processing to delete contact");

        if (!userRepository.existsById(userId) || !userRepository.existsById(contactUserId)) {
            throw new UserNotFoundException("User not found in data base");
        } else {
            try {
                try {
                    contactRepository.deleteContactByContactId(userId, contactUserId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    contactRepository.deleteContactByContactId(contactUserId, userId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets all my contact by id.
     *
     * @param id the id
     * @return the all my contact by id
     */
    public List<User> getAllMyContactById(int id) {
        LOGGER.info("Processing to get all my contact");

        List<User> result = new ArrayList<>();

        result.addAll(userService.getUserById(id).getContactList());
        result.addAll(userService.getUserById(id).getContactListOf());

        return result;
    }

}
