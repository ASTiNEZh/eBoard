package ru.astinezh.advertscrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astinezh.advertscrud.entity.Advert;
import ru.astinezh.advertscrud.repository.AdvertRepository;

import java.util.UUID;

@Service
public class AdvertService {
    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public Advert findById(UUID uuid) {
        Advert advert = advertRepository.findById(uuid).orElse(null);
        if (advert == null)
            return null;
        else if (!advert.isDeleted())
            return advert;
        else
            return null;
    }

    public void create(Advert advert) {
        if (!advertRepository.existsById(advert.getUuid()))
            advertRepository.save(advert);
    }

    public void update(UUID uuid, Advert advert) {
        if (advertRepository.findById(uuid).isPresent())
            advertRepository.save(advert);
    }

    public void delete(UUID uuid) {
        Advert advert = findById(uuid);
        if (advert != null) {
            advert.setDeleted(true);
            advertRepository.save(advert);
        }
    }
}
