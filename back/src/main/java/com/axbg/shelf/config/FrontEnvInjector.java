package com.axbg.shelf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@Configuration
@Profile("docker")
public class FrontEnvInjector implements CommandLineRunner {
    private final static String BACKEND_URL_PLACEHOLDER = "#BACKEND_URL#";
    private final static String CLIENT_ID_PLACEHOLDER = "#GOOGLE_CLIENT_ID#";
    private final static String STATIC_PATH = "/static/js";
    private final static String JS_FILE_PREFIX = "app.";

    private final Logger logger = Logger.getLogger(FrontEnvInjector.class.getName());

    @Value("${shelf.app.google.id}")
    private String clientId;

    @Value("${shelf.app.back.url}")
    private String backUrl;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Trying to inject env values in front-end build");
        Path appFile = Files.walk(Path.of(STATIC_PATH))
                .filter(path -> path.getFileName().toString().contains(JS_FILE_PREFIX))
                .findFirst().orElse(null);

        if (appFile != null) {
            logger.info("Found static resource: " + appFile.getFileName());

            String content = Files.readString(appFile).replaceAll(BACKEND_URL_PLACEHOLDER, backUrl).replaceAll(CLIENT_ID_PLACEHOLDER, clientId);
            Files.write(appFile, content.getBytes(StandardCharsets.UTF_8));

            logger.info("Injected values in: " + appFile);
        }
    }
}
