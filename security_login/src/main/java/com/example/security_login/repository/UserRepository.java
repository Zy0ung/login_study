package com.example.login.repository;

import com.example.login.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jiyoung
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);
}