package com.bteamcoding.bubble_translation_be.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderDto {
    String id;
    String name;
    Long userId;
    Long updatedAt;
    Boolean deleted;
}
