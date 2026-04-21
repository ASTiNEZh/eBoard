package ru.astinezh.filemanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final Path storagePath;

    public FileService(@Value("${app.storage.root}") String storageDir) {
        this.storagePath = Paths.get(storageDir).toAbsolutePath();
        try {
            Files.createDirectories(storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create storage folder: " + storagePath, e);
        }
    }

    public void saveFiles(List<MultipartFile> files, UUID uuid) throws IOException {
        for (MultipartFile file : files) {

            Path filePath = storagePath.resolve(uuid.toString() + ".png");

            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE_NEW);
        }
    }

    public ByteArrayResource getFileAsResource(UUID uuid) throws IOException {
        Path filePath = storagePath.resolve(uuid.toString() + ".png");

        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found: " + uuid);
        }

        return new ByteArrayResource(Files.readAllBytes(filePath));
    }
}
