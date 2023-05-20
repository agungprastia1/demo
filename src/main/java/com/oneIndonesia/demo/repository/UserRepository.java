package com.oneIndonesia.demo.repository;

import com.oneIndonesia.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<Object> findByEmail(String username);

    @Query(value = "SELECT * FROM `user` a where a.user like %?1% or \"All\" like %?1% ;",nativeQuery = true)
    Optional<User> getByName(String recruiter);
}
