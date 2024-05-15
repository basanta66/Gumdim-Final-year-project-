package com.aoh.ghumdim.security.repo;

import com.aoh.ghumdim.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
//    User findByEmail(String email);
}
