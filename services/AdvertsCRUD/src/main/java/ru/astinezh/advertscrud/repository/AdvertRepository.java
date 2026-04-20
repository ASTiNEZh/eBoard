package ru.astinezh.advertscrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.astinezh.advertscrud.entity.Advert;

import java.util.UUID;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, UUID> {
}
