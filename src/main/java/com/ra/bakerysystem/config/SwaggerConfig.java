// 名前: Tram
package com.ra.bakerysystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig dùng để cấu hình OpenAPI (Swagger).
 * Swagger giúp:
 *  - Sinh tài liệu API tự động
 *  - Test API trực tiếp trên trình duyệt
 *  - Giúp frontend và tester hiểu rõ các endpoint
 * Sau khi chạy project, có thể truy cập:
 *  - Swagger UI: /swagger-ui.html hoặc /swagger-ui/index.html
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(" Bakery App API")  // Tên của API
                        .version("1.0.0")          // Phiên bản của API
                        .description("API documentation for Bakery Management System"));
    }
}
