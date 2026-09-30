package com.sp.jobportal.config.web;

import com.sp.jobportal.security.util.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CorsProperties corsProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO Auto-generated method stub
        registry.addMapping("/api/**")
                .allowedOrigins(corsProperties.getAllowedOrigins().get(0))
                .allowedMethods(corsProperties.getAllowedMethods().get(0))
                .allowedHeaders(corsProperties.getAllowedHeaders().get(0))
                .allowCredentials(corsProperties.isAllowedCredentials())
                .maxAge(corsProperties.getMaxAge());
    }

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.useMediaTypeParameter(MediaType.parseMediaType("application/vnd.easyapp+json"), "v")
                .addSupportedVersions("1.0", "2.0", "3.0").setDefaultVersion("1.0");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", __ -> true);
    }

}
