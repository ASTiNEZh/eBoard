package ru.astinezh.userscrud.entity;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "surname")
    private String surname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
}
