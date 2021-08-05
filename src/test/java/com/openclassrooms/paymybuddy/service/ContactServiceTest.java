package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.ContactException;
import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Contact;
import com.openclassrooms.paymybuddy.model.MyUserDetails;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.repository.ContactRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.ContactService;
import com.openclassrooms.paymybuddy.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserService userService;

    private Contact contact;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUpBeforeTest() {
         contactService = new ContactService();
    }

    @Test
    public void test_saveContactRelationship() throws UserNotFoundException, ContactException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = new User();
        contactFriend.setEmail("mail2@mail.fr");

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        when(userService.getUserById(user.getUserID())).thenReturn(user);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        contactService.saveContactRelationship(userDetails,contactFriend);

        assertEquals(contactFriend,contact.getContactUserId());

    }
    @Test
    public void test_saveContactRelationship_shouldThrowUserNotFoundException_whenContactDeleted() throws UserNotFoundException, ContactException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = new User();
        contactFriend.setEmail("mail2@mail.fr");
        contactFriend.setDeleted(true);

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        assertThrows(UserNotFoundException.class,()->contactService.saveContactRelationship(userDetails,contactFriend));


    }

    @Test
    public void test_saveContactRelationship_shouldThrowUserNotFoundException_whenContactNull() throws UserNotFoundException, ContactException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = null;

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        assertThrows(UserNotFoundException.class,()->contactService.saveContactRelationship(userDetails,contactFriend));


    }

    @Test
    public void test_saveContactRelationship_shouldThrowContactException() throws UserNotFoundException, ContactException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = new User();
        contactFriend.setEmail("mail1@mail.fr");

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        assertThrows(ContactException.class,()->contactService.saveContactRelationship(userDetails,contactFriend));

    }

    @Test
    public void test_deleteContactByUserIdAndContactUserId() throws UserNotFoundException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = new User();
        contactFriend.setEmail("mail1@mail.fr");

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        when(userRepository.existsById(anyInt())).thenReturn(true);

        contactService.deleteContactByUserIdAndContactUserId(user.getUserID(), contactFriend.getUserID());

        verify(contactRepository).deleteContactByContactId(user.getUserID(),contactFriend.getUserID());

    }

/*    @Test
    public void test_deleteContactByUserIdAndContactUserId_shouldThrowException() throws UserNotFoundException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = new User();
        contactFriend.setEmail("mail1@mail.fr");

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        when(userRepository.existsById(anyInt())).thenReturn(true);

        doThrow(Exception.class).when(contactRepository).deleteContactByContactId(anyInt(),anyInt());

        assertThrows(Exception.class,()->contactService.deleteContactByUserIdAndContactUserId(12, 5));


    }*/

    @Test
    public void test_deleteContactByUserIdAndContactUserId_shouldThrowUserNotFoundException() throws UserNotFoundException {
        contact = new Contact();
        User user = new User();
        user.setEmail("mail1@mail.fr");
        user.setDeleted(false);
        user.setUserRole("ROLE_USER");
        user.setFirstName("Jean");
        user.setLastName("Michel");
        user.setUserID(1);
        user.setPassword("pass");
        MyUserDetails userDetails = new MyUserDetails(user);
        User contactFriend = new User();
        contactFriend.setEmail("mail1@mail.fr");

        contact.setContactUserId(contactFriend);
        contact.setUserId(user);

        when(userRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(UserNotFoundException.class,()->contactService.deleteContactByUserIdAndContactUserId(user.getUserID(), contactFriend.getUserID()));


    }

    @Test
    public void test_getAllMyContactById() throws UserNotFoundException {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        List<User> contactList = Arrays.asList(user2);
        List<User> contactListOf = Arrays.asList(user3,user4);

        user1.setContactList(contactList);
        user1.setContactListOf(contactListOf);

        when(userService.getUserById(anyInt())).thenReturn(user1);

        assertEquals(3,contactService.getAllMyContactById(user1.getUserID()).size());
    }

    @Test
    public void test_getAllMyContactById_shouldThrowUserNotFoundException() throws UserNotFoundException {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        List<User> contactList = Arrays.asList(user2);
        List<User> contactListOf = Arrays.asList(user3,user4);

        user1.setContactList(contactList);
        user1.setContactListOf(contactListOf);

        when(userService.getUserById(anyInt())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class,()->contactService.getAllMyContactById(user1.getUserID()));

    }

}
