package com.openclassrooms.paymybuddy.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The type User.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userID;

    @NotEmpty(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 3, message = "Password should contains at least 3 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private int phone;

    @NotEmpty(message = "Address is mandatory")
    @Column(name = "address")
    private String address;

    @Min(value=1,message = "Zip should be at lead 1 or more")
    @Column(name = "zip")
    private int zip;

    @NotEmpty(message = "City is mandatory")
    @Column(name = "city")
    private String city;

    @Column(name = "user_role")
    private String userRole;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER,
            mappedBy = "accountOwner"
    )
    private List<BankAccount> accountList;

    @ManyToMany
    @JoinTable(
            name = "contact",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_user_id")
    )
    private List<User> contactList;

    @ManyToMany
    @JoinTable(
            name = "contact",
            joinColumns = @JoinColumn(name = "contact_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> contactListOf;

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
     * Gets account list.
     *
     * @return the account list
     */
    public List<BankAccount> getAccountList() {
        return accountList;
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

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
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
     * Gets user role.
     *
     * @return the user role
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Sets user role.
     *
     * @param userRole the user role
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", zip=" + zip +
                ", city='" + city + '\'' +
                ", userRole='" + userRole + '\'' +
                ", accountList=" + accountList +
                ", contactList=" + contactList +
                '}';
    }
}
