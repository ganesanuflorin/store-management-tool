package com.management.tool.store.controller;

import com.management.tool.store.entity.Product;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping(value = "/add")
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        try {
            productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body("Product added successfully");
        } catch (ProductAddException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error adding product");
        }
    }

}
