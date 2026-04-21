package ru.astinezh.advertscrud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "owner_uuid")
    private User owner;

    @ManyToOne()
    @JoinColumn(name = "advert_uuid")
    private Advert advert;

    @Column(name = "deleted")
    private boolean deleted;
}
