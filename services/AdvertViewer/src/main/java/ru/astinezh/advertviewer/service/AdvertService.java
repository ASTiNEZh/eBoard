package ru.astinezh.advertviewer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.astinezh.advertviewer.entity.Advert;
import ru.astinezh.advertviewer.repository.AdvertsRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertsRepository advertRepository;

    public Page<Advert> getAdvertsByCategory(UUID categoryUuid, int page, int size, String sortField, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return advertRepository.findByCategoryUuid(categoryUuid, pageable);
    }

    public Page<Advert> getAdverts(int page, int size, String sortField, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return advertRepository.findAll(pageable);
    }
}