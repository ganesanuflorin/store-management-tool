package com.management.tool.store.service;

import com.management.tool.store.dto.ProductDto;
import com.management.tool.store.entity.Product;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.exceptions.ProductNotFoundException;
import com.management.tool.store.mapper.ProductConverter;
import com.management.tool.store.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
            logger.info("Product with code: " + product.getCode() + " has added with successfully");
        } else {
            logger.error("Product already exist");
            throw new ProductAddException("Product already exist");
        }
    }

    public ProductDto getProductByCode(Long code) {
        Optional<Product> product = productRepository.findByCode(code);
        if (product.isPresent()) {
            ProductDto productDto = productConverter.toDto(product.get());
            logger.info("Product with code: " + code + " was found");
            return productDto;
        } else {
            logger.error("Product with code: " + code + " not found");
            throw new ProductNotFoundException("Product not found");
        }
    }

    public void changeProductPrice(Double price, Long code) {
        Optional<Product> product = productRepository.findByCode(code);
        if (product.isPresent()) {
            Double oldPrice = product.get().getPrice();
            product.get().setPrice(price);
            productRepository.save(product.get());
            logger.info("The price of the product with code: " + code +
                    " has been changed from" + oldPrice + " to " + price);
        } else {
            logger.error("Product with code: " + code + " not found");
            throw new ProductNotFoundException("Product not found");
        }
    }

    public void removeProduct(Long code) {
        productRepository.delete(productRepository.findByCode(code).orElseThrow(() -> {
            logger.error("Product with code: " + code + " not found");
            throw new ProductNotFoundException("Product not found");}));
        logger.info("Product with code: " + code + " was removed");
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productConverter::toDto).collect(Collectors.toList());
    }
}
