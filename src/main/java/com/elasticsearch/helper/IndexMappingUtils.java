package com.elasticsearch.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;

public class IndexMappingUtils {
    private static final Logger log = LoggerFactory.getLogger(IndexMappingUtils.class);

    public static String loadAsString(final String path) {
        log.info("path = {}", path);
        try {
            final File resource = new ClassPathResource(path).getFile();
            return new String(Files.readAllBytes(resource.toPath()));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
