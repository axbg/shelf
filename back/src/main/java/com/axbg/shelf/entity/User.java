package com.axbg.shelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "id", "email", "lastReset", "collectionList" })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String firstName;

    private String photo;

    private Instant lastReset;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Collection> collectionList;

    public User(String email, String firstName, String photo) {
        this.email = email;
        this.firstName = firstName;
        this.photo = photo;
        this.lastReset = Instant.now().minusSeconds(60);
    }
}
