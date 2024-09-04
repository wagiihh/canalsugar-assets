package com.example.canalsugar2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "Departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentID;

    @NotBlank(message = "Department name is required")
    @Column(name = "departmentname")
    private String departmentname;


    public Department() {
    }

    public Department(int departmentID, String departmentname) {
        this.departmentID = departmentID;
        this.departmentname = departmentname;
    }

    public int getDepartmentID() {
        return this.departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public Department getDepartment()
    {
        return this;
    }

    public String getDepartmentname() {
        return this.departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public Department departmentID(int departmentID) {
        setDepartmentID(departmentID);
        return this;
    }

    public Department Departmentname(String Departmentname) {
        setDepartmentname(Departmentname);
        return this;
    }

   
    
    
}
