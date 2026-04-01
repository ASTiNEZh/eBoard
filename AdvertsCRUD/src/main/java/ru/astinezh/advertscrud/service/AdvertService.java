package ru.astinezh.advertscrud.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ASTiNEZh.dto.AdvertDTO;
import ru.astinezh.advertscrud.entity.Advert;
import ru.astinezh.advertscrud.repository.AdvertRepository;

import java.util.UUID;

@Service
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public AdvertDTO findById(UUID uuid) {
        Advert advert = advertRepository.findById(uuid).orElse(null);
        if (advert == null)
            return null;
        else if (!advert.isDeleted())
            return modelMapper.map(advert, AdvertDTO.class);
        else
            return null;
    }

    public void create(AdvertDTO advertDTO) {
        if (!advertRepository.existsById(advertDTO.getUuid()))
            advertRepository.save(modelMapper.map(advertDTO, Advert.class));
    }

    public void update(UUID uuid, AdvertDTO advertDTO) {
        if (advertRepository.findById(uuid).isPresent())
            advertRepository.save(modelMapper.map(advertDTO, Advert.class));
    }

    public void delete(UUID uuid) {
        Advert advert = advertRepository.findById(uuid).orElse(null);
        if (advert != null) {
            advert.setDeleted(true);
            advertRepository.save(advert);
        }
    }
}
