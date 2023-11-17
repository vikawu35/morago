package com.habsida.moragoproject.configuration;

import com.habsida.moragoproject.exception.FileOperationException;
import com.habsida.moragoproject.model.entity.File;
import com.habsida.moragoproject.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class FileUtil {

    private final FileRepository fileRepository;

    public FileUtil(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File upload(String filename, MultipartFile multipartFile) {
        byte[] data;
        try {
            data = multipartFile.getBytes();
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        java.io.File iconDir = new java.io.File("/.icon");

        if(!iconDir.exists()) {
            boolean dirCreated = iconDir.mkdir();
            if (!dirCreated) {
                throw new FileOperationException("Failed to create a directory");
            }
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        String fileCode = UUID.randomUUID().toString();
        java.io.File icon = new java.io.File(iconDir, fileCode + "." + extension);

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(icon))){
            outputStream.write(data);
            File file = new File();
            file.setOriginalTitle(filename.substring(0, filename.lastIndexOf(".")));
            file.setType(multipartFile.getContentType());
            file.setPath(iconDir + "\\" + fileCode + "." +  extension);
            return fileRepository.save(file);
        } catch (IOException ex) {
            throw new FileOperationException(ex.getMessage());
        }
    }
}
