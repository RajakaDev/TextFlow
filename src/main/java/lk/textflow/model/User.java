package lk.textflow.model;

import java.time.LocalDateTime;

public class User {

    private int userId;
    private String name;
    private String username;
    private String passwordHash;
    private String role;
    private String position;
    private String contactNumber;
    private String status;
    private LocalDateTime createdAt;

    //Empty Contructor
    public User(){

    }

    //Full Constructor
    public User(int userId,
                String name,
                String username,
                String passwordHash,
                String role,
                String position,
                String contactNumber,
                String status,
                LocalDateTime createdAt)
    {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.position = position;
        this.contactNumber = contactNumber;
        this.status = status;
        this.createdAt = LocalDateTime.now();

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}