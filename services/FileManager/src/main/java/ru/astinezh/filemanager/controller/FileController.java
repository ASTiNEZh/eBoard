package ru.astinezh.filemanager.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.ASTiNEZh.controller.FilesApi;
import ru.astinezh.filemanager.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class FileController implements FilesApi {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<Resource> filesIdGet(UUID uuid) {
        ResponseEntity<Resource> response = null;
        try {
            response = ResponseEntity.ok(fileService.getFileAsResource(uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public ResponseEntity<Void> filesUploadPost(List<MultipartFile> list, @Valid UUID uuid) {
        try {
            fileService.saveFiles(list, uuid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
