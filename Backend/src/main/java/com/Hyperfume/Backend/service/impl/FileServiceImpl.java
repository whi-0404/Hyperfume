package com.Hyperfume.Backend.service.impl;

import com.Hyperfume.Backend.configuration.FileUploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileServiceImpl extends AbstractFileService {

    public FileServiceImpl(FileUploadProperties fileUploadProperties) {
        super(fileUploadProperties);
    }
}
