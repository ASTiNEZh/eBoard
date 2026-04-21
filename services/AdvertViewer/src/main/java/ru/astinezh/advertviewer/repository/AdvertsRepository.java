package ru.astinezh.advertviewer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.astinezh.advertviewer.entity.Advert;

import java.util.UUID;

public interface AdvertsRepository extends JpaRepository<Advert, UUID> {
    @Query(value = "SELECT DISTINCT a FROM Advert a JOIN a.categories c WHERE c.uuid = :categoryUuid",
            countQuery = "SELECT COUNT(DISTINCT a) FROM Advert a JOIN a.categories c WHERE c.uuid = :categoryUuid")
    Page<Advert> findByCategoryUuid(@Param("categoryUuid") UUID categoryUuid, Pageable pageable);
}
