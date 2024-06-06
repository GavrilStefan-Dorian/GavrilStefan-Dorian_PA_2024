//package com.example.OLD.entities;
//import jakarta.persistence.*;
//@Entity
//@Table(name = "appusers", schema="public")
//public class AppUser {
//    @Id
//    @Column(name="id")
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name="firstName")
//
//    private String firstName;
//    @Column(name="lastName")
//
//    private String lastName;
//    @Column(name="userName", unique=true)
//    private String userName;
//    @Column(name="email", unique=true, nullable = false)
//    private String email;
//    @Column(name="password")
//
//    private String password;
//
//    @Column(name="role")
//    private String role;
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//}
