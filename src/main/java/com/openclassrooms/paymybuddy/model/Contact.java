package com.openclassrooms.paymybuddy.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/*@NamedEntityGraph(
        name = "post-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("contactUserId"),
                @NamedAttributeNode("userId"),
        })*/
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(User contactUserId) {
        this.contactUserId = contactUserId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
