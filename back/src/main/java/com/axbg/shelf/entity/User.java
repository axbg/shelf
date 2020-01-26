package com.axbg.shelf.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private Long id;

    @Column(unique = true)
    private String email;

    private String firstName;

    public User(String email, String firstName) {
        this.email = email;
        this.firstName = firstName;
    }
}
