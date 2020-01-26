package com.axbg.shelf.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Item {

    @Id
    private Long id;

    private String title;

    private String url;

    private String photo;
}
