package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    
    
}
