package com.axbg.shelf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.lastReset = Instant.now();
    }
}
