package com.elasticsearch.helper;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

public class IndexMappingUtils {

    public static String loadAsString(String path) {
        try {
            File resource = new ClassPathResource(path).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        } catch (Exception e) {
            throw new IllegalStateException("failed to read file resource");
        }
    }
}
