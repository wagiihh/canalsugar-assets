package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.Admin;
import java.util.List;


public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findByEmail(String email);
    Admin findByAdminID(int adminID);
    
}
