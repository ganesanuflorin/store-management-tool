package com.management.tool.store.mapper;

import com.management.tool.store.dto.UserDto;
import com.management.tool.store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserDto toDto(User product);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto productDto);
}
