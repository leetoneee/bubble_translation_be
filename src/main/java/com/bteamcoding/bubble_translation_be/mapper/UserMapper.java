package com.bteamcoding.bubble_translation_be.mapper;

import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;
import com.bteamcoding.bubble_translation_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(SignUpRequest dto);

    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(User user);
}
