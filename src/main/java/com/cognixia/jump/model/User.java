package com.cognixia.jump.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -4458870115303573931L;
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    @Id
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long addressId;
    private Role userRole;

    public User() {
        this(-1L, "N/A", "N/A", "N/A", "N/A", -1L, Role.ROLE_USER);
    }

    public User(Long id, String email, String password, String firstName, String lastName, Long addressId, Role userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressId = addressId;
        this.userRole = userRole;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressId=" + addressId +
                ", userRole=" + userRole +
                '}';
    }
}
