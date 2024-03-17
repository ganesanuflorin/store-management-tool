package com.management.tool.store.service;

import com.management.tool.store.entity.Product;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public void saveProduct(Product product) {
        if (productRepository.findByProductName(product.getProductName()).isEmpty()) {
            productRepository.save(product);
        } else {
            throw new ProductAddException("Product Already Exist");
        }
    }
}
