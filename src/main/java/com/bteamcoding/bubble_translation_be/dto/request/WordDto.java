package com.bteamcoding.bubble_translation_be.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WordDto {
    String id;
    String word;
    String folderId;
    Long updatedAt;
    Boolean deleted;
}
