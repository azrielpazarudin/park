package com.park.park.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.park.park.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByNim(String nim);
}
