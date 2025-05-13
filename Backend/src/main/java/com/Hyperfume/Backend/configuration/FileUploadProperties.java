package com.Hyperfume.Backend.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "file-upload")
@Data
public class FileUploadProperties {

    private String rootDirectory = "E:\\Final_Hyperfume\\Hyperfume\\Frontend\\src\\assets";

    private Map<String, DirectoryConfig> directories = new HashMap<>();

    private long maxFileSize = 10 * 1024 * 1024;

    @Data
    public static class DirectoryConfig {
        private String path;

        private Map<String, String[]> allowedExtensions = new HashMap<>();

        private Map<String, String[]> allowedMimeTypes = new HashMap<>();

        private Long maxFileSize;
    }

    public DirectoryConfig getDirectoryConfig(String directoryKey) {
        return directories.getOrDefault(directoryKey, new DirectoryConfig());
    }
}
