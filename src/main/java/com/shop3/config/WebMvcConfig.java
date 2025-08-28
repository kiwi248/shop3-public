package com.shop3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")  // 예: C:/shoppingmall/item
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String normalized = uploadPath.replace("\\", "/");
        if (!normalized.endsWith("/")) normalized += "/";

        // Spring은 "file:" 만으로도 동작합니다. (file:/// 도 가능)
        String location = "file:" + normalized;

        registry.addResourceHandler("/images/item/**")
                .addResourceLocations(location);
        // .setCachePeriod(3600)           // 선택: 캐시
        // .resourceChain(true);           // 선택: 리소스 체인
    }
}

