package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.FileOperationException;
import com.habsida.moragoproject.model.entity.File;
import com.habsida.moragoproject.model.input.FileInput;
import com.habsida.moragoproject.repository.FileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findAll(){
        return fileRepository.findAll();
    }

    public Page<File> findAllPaged(PageRequest pageRequest) {
        return fileRepository.findAll(pageRequest);
    }

    public File findById(Long id){
        return fileRepository.findById(id)
                .orElseThrow(()-> new FileOperationException("File was not found by Id: " + id));
    }

    public Boolean delete(Long id){
        File file = findById(id);

        try {
            Path filePath = Paths.get(file.getPath());
            Files.delete(filePath);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        fileRepository.deleteById(id);
        return true;
    }

    public File update(Long id, FileInput fileInput){
        File file = findById(id);

        if (fileInput.getOriginalTitle() != null && !fileInput.getOriginalTitle().isEmpty()) {
            file.setOriginalTitle(fileInput.getOriginalTitle());
        }

        if (fileInput.getPath() != null  && !fileInput.getPath().isEmpty()) {
            file.setPath(fileInput.getPath());
        }

        if (fileInput.getType() != null  && !fileInput.getType().isEmpty()) {
            file.setType(fileInput.getType());
        }

        return fileRepository.save(file);
    }
}
