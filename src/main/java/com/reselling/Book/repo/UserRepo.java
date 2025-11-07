package com.reselling.Book.repo;

import com.reselling.Book.model.details.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<UserDetails,Integer> {

    boolean existsByEmail(String email);

    Optional<UserDetails> findByEmail(String email);
}
