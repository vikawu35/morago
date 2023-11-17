package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.configuration.FileUtil;
import com.habsida.moragoproject.model.entity.File;
import com.habsida.moragoproject.model.input.FileInput;
import com.habsida.moragoproject.service.FileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
public class FileController {

    private final FileService fileService;
    private final FileUtil fileUtil;

    public FileController(FileService fileService, FileUtil fileUtil) {
        this.fileService = fileService;
        this.fileUtil = fileUtil;
    }

    @QueryMapping
    public List<File> getAll(){
        return fileService.findAll();
    }

    @QueryMapping
    public Page<File> getAllFilePaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return fileService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public File getById(Long id){
        return fileService.findById(id);
    }

    @MutationMapping
    public Boolean deleteFileById(@Argument Long id){
        return fileService.delete(id);
    }

    @MutationMapping
    public File updateFile(@Argument Long id, @Argument FileInput fileInput){
        return fileService.update(id, fileInput);
    }

    @MutationMapping
    public File uploadFile (@Argument MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        return fileUtil.upload(filename, multipartFile);
    }
}

