package com.axbg.shelf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Logger;

@Configuration
@Profile("docker")
public class FrontEnvInjector implements CommandLineRunner {
    private final static String BACKEND_URL_PLACEHOLDER = "#BACKEND_URL#";
    private final static String CLIENT_ID_PLACEHOLDER = "#GOOGLE_CLIENT_ID#";
    private final static String JS_FILE_PREFIX = "app.";

    private final Logger logger = Logger.getLogger(FrontEnvInjector.class.getName());

    @Value("${shelf.app.google.id}")
    private String clientId;

    @Value("${shelf.app.back.url}")
    private String backUrl;

    @Value("classpath:static/js/*")
    private Resource[] resources;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Trying to inject env values in front-end build");
        if (resources != null) {
            logger.info("Found static resources");
            for (Resource resource : resources) {
                if (resource != null && resource.getFilename() != null && resource.getFilename().startsWith(JS_FILE_PREFIX)) {
                    String content = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8)
                            .replaceAll(BACKEND_URL_PLACEHOLDER, backUrl).replaceAll(CLIENT_ID_PLACEHOLDER, clientId);
                    Files.write(resource.getFile().toPath(), content.getBytes(StandardCharsets.UTF_8));
                    logger.info("Injected values in " + resource.getFilename());
                    break;
                }
            }
        }
    }
}
