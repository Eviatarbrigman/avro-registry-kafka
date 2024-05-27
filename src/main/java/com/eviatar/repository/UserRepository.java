package com.eviatar.repository;

import com.eviatar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM avro.users WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);

}
