package com.bteamcoding.bubble_translation_be.mapper;

import com.bteamcoding.bubble_translation_be.dto.request.SignUpRequest;
import com.bteamcoding.bubble_translation_be.dto.request.UserCreationRequest;
import com.bteamcoding.bubble_translation_be.dto.request.UserUpdateRequest;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponse;
import com.bteamcoding.bubble_translation_be.dto.response.UserResponseWrapper;
import com.bteamcoding.bubble_translation_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(SignUpRequest dto);

    User toUser(UserCreationRequest request);

    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(User user);

    UserResponseWrapper toUserResponseWrapper(UserResponse user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
