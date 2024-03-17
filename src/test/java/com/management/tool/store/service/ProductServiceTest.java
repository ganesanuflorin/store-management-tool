package com.management.tool.store.service;

import com.management.tool.store.dto.ProductDto;
import com.management.tool.store.entity.Product;
import com.management.tool.store.exceptions.ProductAddException;
import com.management.tool.store.exceptions.ProductNotFoundException;
import com.management.tool.store.mapper.ProductConverter;
import com.management.tool.store.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductService productService;

    @Before
    public void setup() {
        when(productRepository.findByCode(anyLong())).thenReturn(Optional.empty());
    }

    @Test
    public void testSaveProduct_WhenProductDoesNotExist() {
        ProductDto productDto = new ProductDto();
        productDto.setCode(1231L);
        when(productConverter.toEntity(productDto)).thenReturn(new Product());
        productService.saveProduct(productDto);
        verify(productRepository).save(any(Product.class));
    }

    @Test(expected = ProductAddException.class)
    public void testSaveProduct_WhenProductExists() {
        ProductDto productDto = new ProductDto();
        productDto.setCode(1231L);
        when(productRepository.findByCode(anyLong())).thenReturn(Optional.of(new Product()));
        productService.saveProduct(productDto);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testGetProductByCode_WhenProductExists() {
        Long code = 1231L;
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        productDto.setCode(code);

        when(productRepository.findByCode(code)).thenReturn(Optional.of(product));
        when(productConverter.toDto(product)).thenReturn(productDto);
        ProductDto result = productService.getProductByCode(code);
        verify(productConverter).toDto(product);
        assertEquals(code, result.getCode());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductByCode_WhenProductDoesNotExist() {
        Long code = 1231L;
        productService.getProductByCode(code);
    }
}
