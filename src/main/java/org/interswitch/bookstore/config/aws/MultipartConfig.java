package org.interswitch.bookstore.config.aws;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


@Configuration
public class MultipartConfig {
    @Value("${FILE_SIZE}")
    private String FILE_SIZE;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(FILE_SIZE));
        factory.setMaxRequestSize(DataSize.parse(FILE_SIZE));
        return factory.createMultipartConfig();
    }
}