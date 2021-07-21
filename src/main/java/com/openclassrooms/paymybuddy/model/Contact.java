package com.openclassrooms.paymybuddy.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * The type Contact.
 */
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "contact_user_id")
    private User contactUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets contact user id.
     *
     * @return the contact user id
     */
    public User getContactUserId() {
        return contactUserId;
    }

    /**
     * Sets contact user id.
     *
     * @param contactUserId the contact user id
     */
    public void setContactUserId(User contactUserId) {
        this.contactUserId = contactUserId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public User getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }
}
