package com.bteamcoding.bubble_translation_be.mapper;

import com.bteamcoding.bubble_translation_be.dto.request.WordDto;
import com.bteamcoding.bubble_translation_be.entity.Folder;
import com.bteamcoding.bubble_translation_be.entity.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WordMapper {
    @Mapping(source = "word.folder.id", target = "folderId")
    WordDto toDto(Word word);

    @Mapping(source = "folder", target = "folder")
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.word", target = "word")
    @Mapping(source = "dto.updatedAt", target = "updatedAt")
    @Mapping(source = "dto.deleted", target = "deleted")
    Word toEntity(WordDto dto, Folder folder);
}
