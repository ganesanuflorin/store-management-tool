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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

    @Test
    public void testChangeProductPrice_WhenProductExists() {
        Long code = 123L;
        Double newPrice = 15.0;
        Product product = new Product();
        product.setCode(code);
        product.setPrice(10.0);

        when(productRepository.findByCode(code)).thenReturn(Optional.of(product));
        productService.changeProductPrice(newPrice, code);
        product.setPrice(newPrice);
        verify(productRepository).save(product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testChangeProductPrice_WhenProductDoesNotExist() {
        Long code = 123L;
        Double newPrice = 15.0;

        productService.changeProductPrice(newPrice, code);
    }
    @Test
    public void testRemoveProduct_WhenProductExists() {
        Long code = 123L;
        Product product = new Product();
        product.setCode(code);
        when(productRepository.findByCode(code)).thenReturn(Optional.of(product));
        productService.removeProduct(code);
        verify(productRepository).delete(product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testRemoveProduct_WhenProductDoesNotExist() {
        Long code = 123L;
        productService.removeProduct(code);
    }

    @Test
    public void testGetAllProducts_WhenNoProducts() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        List<ProductDto> result = productService.getAllProducts();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllProducts_WhenProductsExist() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDto> expectedProductDtoList = new ArrayList<>();
        expectedProductDtoList.add(new ProductDto());
        expectedProductDtoList.add(new ProductDto());
        when(productConverter.toDto(any(Product.class))).thenReturn(new ProductDto());
        List<ProductDto> result = productService.getAllProducts();
        assertEquals(productList.size(), result.size());
        verify(productConverter, times(productList.size())).toDto(any(Product.class));
    }
}
