package ru.astinezh.commentscrud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "owner_uuid")
    private Object owner;

    @Column(name = "advert_uuid")
    private Object advert;

    @Column(name = "deleted")
    private boolean deleted;
}
