// 名前: Tram, Thuy
package com.ra.bakerysystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig dùng để cấu hình các tài nguyên tĩnh (static resources) cho ứng dụng Spring Boot.
 * Trong trường hợp này, class dùng để cấu hình truy cập các file upload (ảnh, tài liệu, v.v.).
 *  Ví dụ:
 *  - File nằm trong: src/main/resources/uploads/image.jpg
 *  - Truy cập qua URL: http://localhost:8080/uploads/image.jpg
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")  // URL pattern dùng để truy cập file
                .addResourceLocations("classpath:/uploads/");   // Thư mục chứa file trong project
    }
}

