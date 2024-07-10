package com.spring.jwt.repositories;

import com.spring.jwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
//@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String email);

}
