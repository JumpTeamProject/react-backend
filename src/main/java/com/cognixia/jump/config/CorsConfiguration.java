package com.cognixia.jump.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Cors Configuration for Restaurant Reviews Application.
 * @author David Morales
 * @version v1 (09/05/2020)
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
	/**
	 * Adds the the mappings of the HTTP methods for Cors.
	 * @author David Morales
	 * @param registry the Cors Registry for this application
	 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("*");
    }
}
