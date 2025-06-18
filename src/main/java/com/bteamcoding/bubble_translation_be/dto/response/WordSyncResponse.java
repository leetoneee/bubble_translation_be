package com.bteamcoding.bubble_translation_be.dto.response;

import com.bteamcoding.bubble_translation_be.dto.request.WordDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WordSyncResponse {
    List<WordDto> synced;
    List<WordDto> conflicts;
}
