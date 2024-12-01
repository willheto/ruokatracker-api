package com.ruokatracker.ruokatracker_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ruokatracker.ruokatracker_api.models.user.User;

// Marking the repository as a Spring component for clarity
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    // Method to check if a username already exists
    boolean existsByEmail(String username);

    // Optional: Method to find a user by username for login purposes
    User findByEmail(String username);
}
