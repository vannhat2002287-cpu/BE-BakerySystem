package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.ProductRequest;
import com.ra.bakerysystem.model.entity.Product;
import com.ra.bakerysystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/app/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @Value("${path-upload}")
    private String uploadDir;

    @PostMapping(path = "/add-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveProduct(  @ModelAttribute ProductRequest productRequest,
                                          @RequestParam(value = "img", required = false) MultipartFile image) {


        String imagePath = null;

        if (image != null && !image.isEmpty()) {
            try {
                BufferedImage originalImage = ImageIO.read(image.getInputStream());
                if (originalImage == null) {
                    return ResponseEntity.badRequest().body("Tệp tải lên không hợp lệ hoặc bị lỗi!");
                }

                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(image.getInputStream(), uploadPath.resolve(image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

                imagePath = "uploads/" + image.getOriginalFilename();
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu ảnh!");
            }
        }

        //  Truyền null ID khi tạo mới
        Product product = productService.save(productRequest, imagePath, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}

