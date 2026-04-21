package ru.astinezh.commentscrud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "owner_uuid")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "advert_category",
            joinColumns = @JoinColumn(name = "advert_uuid"),
            inverseJoinColumns = @JoinColumn(name = "category_uuid")
    )
    private List<Category> categories;

    @OneToMany
    private List<Comment> comments;

    @Column(name = "deleted")
    private boolean deleted;
}
