package com.management.tool.store.service;

import com.management.tool.store.dto.ProductDto;
import com.management.tool.store.entity.Product;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.mapper.ProductConverter;
import com.management.tool.store.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public void saveProduct(ProductDto productDto) {
        if (productRepository.findByCode(productDto.getCode()).isEmpty()) {
            Product product = productConverter.toEntity(productDto);
            productRepository.save(product);
            logger.info("Product with code " + product.getCode() + " has added with successfully");
        } else {
            logger.error("Product already exist");
            throw new ProductAddException("Product already exist");
        }
    }
}
