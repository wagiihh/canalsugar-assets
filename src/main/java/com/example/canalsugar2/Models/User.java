package com.example.canalsugar2.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    
    @Email
    @NotEmpty(message = "email cannot be empty ")
    @Column(name = "email", nullable = false)
    private String email;
    

    @NotBlank(message = "First Name is required")
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    @Column(name = "lastname")
    private String lastname;

    @NotBlank(message = "Employee ID is required")
    @Column(name = "employeeid")
    private String employeeid;
    
    @NotBlank(message = "Number is required")
    @Column(name = "number")
    @Pattern(regexp = "\\d{11}", message = "Phone number must be 11 digits")
    private String number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "departmentID", referencedColumnName = "departmentID", insertable = true, updatable = true)
    private Department department;
    


    public User() {
    }

    public User(int userID, String email, String firstname, String lastname, String employeeid, String number, Department department) {
        this.userID = userID;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.employeeid = employeeid;
        this.number = number;
        this.department = department;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public User getUser()
    {
        return this;
    }


    public String getEmployeeid() {
        return this.employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User userID(int userID) {
        setUserID(userID);
        return this;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User firstname(String firstname) {
        setFirstname(firstname);
        return this;
    }

    public User lastname(String lastname) {
        setLastname(lastname);
        return this;
    }

    public User employeeid(String employeeid) {
        setEmployeeid(employeeid);
        return this;
    }

    public User number(String number) {
        setNumber(number);
        return this;
    }

    public User department(Department department) {
        setDepartment(department);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return userID == user.userID && Objects.equals(email, user.email) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(employeeid, user.employeeid) && Objects.equals(number, user.number) && Objects.equals(department, user.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, email, firstname, lastname, employeeid, number, department);
    }

    @Override
    public String toString() {
        return "{" +
            " userID='" + getUserID() + "'" +
            ", email='" + getEmail() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", employeeid='" + getEmployeeid() + "'" +
            ", number='" + getNumber() + "'" +
            ", department='" + getDepartment() + "'" +
            "}";
    }

    
}
