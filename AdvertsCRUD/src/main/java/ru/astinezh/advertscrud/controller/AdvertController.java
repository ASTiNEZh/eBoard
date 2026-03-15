package ru.astinezh.advertscrud.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ASTiNEZh.controller.AdvertsApi;
import ru.ASTiNEZh.dto.AdvertDTO;
import ru.astinezh.advertscrud.entity.Advert;
import ru.astinezh.advertscrud.service.AdvertService;

import java.util.UUID;

@RestController
public class AdvertController implements AdvertsApi {
    private final ModelMapper modelMapper = new ModelMapper();
    private final AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Override
    public ResponseEntity<Void> createAdvert(AdvertDTO advertDTO) {
        advertService.create(modelMapper.map(advertDTO, Advert.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdvertDTO> deleteAdvert(UUID uuid) {
        advertService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdvertDTO> getAdvert(UUID uuid) {
        return ResponseEntity.ok(modelMapper.map(advertService.findById(uuid), AdvertDTO.class));
    }

    @Override
    public ResponseEntity<AdvertDTO> updateAdvert(UUID uuid, AdvertDTO advertDTO) {
        advertService.update(uuid, modelMapper.map(advertDTO, Advert.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
