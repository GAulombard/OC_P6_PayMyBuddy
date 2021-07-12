package com.openclassrooms.paymybuddy.model;

import com.openclassrooms.paymybuddy.service.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type My user details.
 */
public class MyUserDetails implements UserDetails {

    private final Logger LOGGER = LoggerFactory.getLogger(MyUserDetails.class);

    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String city;
    private int zip;
    private int phone;
    private List<GrantedAuthority> authorities;
    private List<User> contactList;
    private List<BankAccount> accountList;
    private List<User> contactListOf;

    /**
     * Instantiates a new My user details.
     *
     * @param user the user
     */
    public MyUserDetails(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getUserRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.contactList = user.getContactList();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.phone = user.getPhone();
        this.zip = user.getZip();
        this.userID = user.getUserID();
        this.accountList = user.getAccountList();
        this.contactListOf = user.getContactListOf();

    }

    /**
     * Gets account list.
     *
     * @return the account list
     */
    public List<BankAccount> getAccountList() {
        return accountList;
    }

    /**
     * Gets contact list of.
     *
     * @return the contact list of
     */
    public List<User> getContactListOf() {
        return contactListOf;
    }

    /**
     * Sets contact list of.
     *
     * @param contactListOf the contact list of
     */
    public void setContactListOf(List<User> contactListOf) {
        this.contactListOf = contactListOf;
    }

    /**
     * Sets account list.
     *
     * @param accountList the account list
     */
    public void setAccountList(List<BankAccount> accountList) {
        this.accountList = accountList;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets zip.
     *
     * @return the zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets authorities.
     *
     * @param authorities the authorities
     */
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets contact list.
     *
     * @return the contact list
     */
    public List<User> getContactList() {
        return contactList;
    }

    /**
     * Sets contact list.
     *
     * @param contactList the contact list
     */
    public void setContactList(List<User> contactList) {
        this.contactList = contactList;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LOGGER.info("User authorities: "+authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstName+" "+lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
