package com.bteamcoding.bubble_translation_be.mapper;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;
import com.bteamcoding.bubble_translation_be.entity.Folder;
import com.bteamcoding.bubble_translation_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    @Mapping(source = "user.id", target = "userId")
    FolderDto toDto(Folder folder);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.updatedAt", target = "updatedAt")
    @Mapping(source = "dto.deleted", target = "deleted")
    @Mapping(source = "user", target = "user")
    Folder toEntity(FolderDto dto, User user);
}
