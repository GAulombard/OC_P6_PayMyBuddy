package com.openclassrooms.paymybuddy.repository;

import com.openclassrooms.paymybuddy.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
}
