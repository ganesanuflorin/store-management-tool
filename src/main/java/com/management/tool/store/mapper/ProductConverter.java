package com.management.tool.store.mapper;

import com.management.tool.store.dto.ProductDto;
import com.management.tool.store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductConverter {

    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto productDto);
}
