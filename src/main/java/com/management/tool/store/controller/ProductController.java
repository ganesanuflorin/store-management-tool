package com.management.tool.store.controller;

import com.management.tool.store.dto.ProductDto;
import com.management.tool.store.dto.ResponseDto;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.exceptions.ProductNotFoundException;
import com.management.tool.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDto> addNewProduct(@RequestBody ProductDto productDto) {
        try {
            productService.saveProduct(productDto);
            ResponseDto responseDto = new ResponseDto("Product added successfully");
            return ResponseEntity.ok(responseDto);
        } catch (ProductAddException e) {
           throw new ProductAddException("Error: product already exist");
        }
    }

    @GetMapping(value = "/findBy")
    public ResponseEntity<ProductDto> getProductByCode(@RequestParam Long code) {
        System.out.println("aici");
        try {
            ProductDto productDto = productService.getProductByCode(code);
            return ResponseEntity.ok(productDto);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Error: product doesn't exist");
        }
    }

}
