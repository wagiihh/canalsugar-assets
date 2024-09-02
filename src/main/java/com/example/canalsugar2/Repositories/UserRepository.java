package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByUserID(int userID);
}
