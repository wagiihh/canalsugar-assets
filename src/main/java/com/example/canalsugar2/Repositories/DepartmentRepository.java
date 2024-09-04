package com.example.canalsugar2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.canalsugar2.Models.Department;
import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    
    Department findByDepartmentname(String departmentname);
    Department findByDepartmentID(int departmentID);
}
