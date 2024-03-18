package com.management.tool.store.controller;

import com.management.tool.store.dto.ProductDto;
import com.management.tool.store.dto.ResponseDto;
import com.management.tool.store.entity.Product;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.exceptions.ProductNotFoundException;
import com.management.tool.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            return ResponseEntity.ok(new ResponseDto("Product added successfully"));
        } catch (ProductAddException e) {
           throw new ProductAddException("Error: product already exist");
        }
    }

    @GetMapping(value = "/findBy")
    public ResponseEntity<ProductDto> getProductByCode(@RequestParam Long code) {
        try {
            return ResponseEntity.ok(productService.getProductByCode(code));
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Error: product doesn't exist");
        }
    }

    @PutMapping(value = "/change/{price}/code/{code}")
    public ResponseEntity<ResponseDto> changeProductPrice(@PathVariable Double price, @PathVariable Long code) {
        try {
            productService.changeProductPrice(price, code);
            return ResponseEntity.ok(new ResponseDto("Price was successfully changed"));
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Error: product doesn't exist");
        }
    }
}
