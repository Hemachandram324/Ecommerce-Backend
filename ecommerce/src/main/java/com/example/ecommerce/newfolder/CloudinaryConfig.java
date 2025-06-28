package com.example.ecommerce.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlea4rt0w",
                "api_key", "113637878558931",
                "api_secret", "bxTMpKxSFmRHV3STWFo8_l8wU6M",
                "secure", true
        ));
    }
}

