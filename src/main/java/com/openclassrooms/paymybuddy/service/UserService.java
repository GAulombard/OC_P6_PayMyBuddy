package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.exception.UserAlreadyExistException;
import com.openclassrooms.paymybuddy.exception.UserNotFoundException;
import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The type User service.
 */
@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets users.
     *
     * @return the users
     */
    public Iterable<User> getUsers() {
        LOGGER.info("Process to get all users");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC,"lastName"));
    }

    /**
     * Save user.
     *
     * @param user the user
     * @throws UserAlreadyExistException the user already exist exception
     */
    public void saveUser(User user) throws UserAlreadyExistException {
        LOGGER.info("Processing to save a new user in database");
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exist in database");
        }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole("ROLE_USER");
            userRepository.save(user);
    }

    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<User> findUserById(int id) throws UserNotFoundException {
        LOGGER.info("Processing to find a user by id");

        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }

        return userRepository.findById(id);
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public User getUserById(int id) throws UserNotFoundException {

        LOGGER.info("Processing to find a user by id");

        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }

        return userRepository.getById(id);
    }

    /**
     * Remove user by id.
     *
     * @param id the id
     */
    public void removeUserById(int id) throws UserNotFoundException {
        LOGGER.info("Processing to remove user by id");

        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

    /**
     * Get total account balance by user id atomic reference.
     *
     * @param id the id
     * @return the atomic reference
     */
    public AtomicReference<Double> getTotalAccountBalanceByUserId(int id){
        LOGGER.info("Processing to get total account balance by user Id");

        AtomicReference<Double> result= new AtomicReference<>((double) 0);
        List<BankAccount> bankAccounts = userRepository.getById(id).getAccountList();

        if(bankAccounts == null) return result;

        bankAccounts.iterator().forEachRemaining(bankAccount -> {
            if(bankAccount.isDeleted()==false) {
                result.updateAndGet(v -> new Double((double) (v + bankAccount.getBalance())));
            }

        });

        return result;
    }

    /**
     * Find user by email user.
     *
     * @param email the email
     * @return the user
     */
    public User findUserByEmail(String email) {
        LOGGER.info("Processing to find a user by email");
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Update role by id.
     *
     * @param id   the id
     * @param role the role
     * @throws UserNotFoundException the user not found exception
     */
    public void updateRoleById(int id, String role) throws UserNotFoundException {
        LOGGER.info("Processing to update role by Id");

        if(userRepository.existsById(id)) {
            userRepository.updateRole(id, role);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Update deleted by id.
     *
     * @param id the id
     * @throws UserNotFoundException the user not found exception
     */
    public void updateDeletedById(int id) throws UserNotFoundException {
        LOGGER.info("Processing to recover user by Id");
        if(userRepository.existsById(id)) {
            userRepository.updateDeleted(id);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public boolean userIsRemovedById(int id) throws UserNotFoundException {
        LOGGER.info("Processing to find if the user has been removed");

        if(userRepository.existsById(id)) {
            if (userRepository.getById((id)).isDeleted() == false) return false;
        } else {
            throw new UserNotFoundException("User not found");
        }

        return true;
    }
}
