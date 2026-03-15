package ru.astinezh.advertscrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Adverts")
public class Advert {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "cost")
    private Float cost;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_uuid")
    private Object owner;

    @Column(name = "deleted")
    private boolean deleted;
}
