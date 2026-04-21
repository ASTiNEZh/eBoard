package ru.astinezh.advertscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ASTiNEZh.controller.AdvertsApi;
import ru.ASTiNEZh.dto.AdvertDTO;
import ru.astinezh.advertscrud.service.AdvertService;

import java.util.UUID;

@RestController
public class AdvertController implements AdvertsApi {
    private final AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @Override
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<Void> createAdvert(AdvertDTO advertDTO) {
        advertService.create(advertDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<AdvertDTO> deleteAdvert(UUID uuid) {
        advertService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdvertDTO> getAdvert(UUID uuid) {
        return ResponseEntity.ok(advertService.findById(uuid));
    }

    @Override
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<AdvertDTO> updateAdvert(UUID uuid, AdvertDTO advertDTO) {
        advertService.update(uuid, advertDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
