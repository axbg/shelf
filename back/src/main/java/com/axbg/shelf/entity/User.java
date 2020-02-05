package com.axbg.shelf.entity;

import java.time.Instant;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String firstName;

    private Instant lastReset;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Collection> collectionList;

    public User(String email, String firstName) {
        this.email = email;
        this.firstName = firstName;
        this.lastReset = Instant.now();
    }
}
