package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM contact WHERE user_id = :userId AND contact_user_id = :contactUserId", nativeQuery = true)
    void deleteContactByContactId(int userId, int contactUserId);


}
