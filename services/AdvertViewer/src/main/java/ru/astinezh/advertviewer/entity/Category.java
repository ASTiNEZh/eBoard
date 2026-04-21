package ru.astinezh.advertviewer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @Column(name = "deleted")
    private boolean deleted;
}
