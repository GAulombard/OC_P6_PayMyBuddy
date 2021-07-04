package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Fee;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.FeeRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(FeeService.class);

    @Autowired
    private FeeRepository feeRepository;

    public Iterable<Fee> getFees() {
        LOGGER.info("Process to get all fees");
        return feeRepository.findAll();
    }
}
