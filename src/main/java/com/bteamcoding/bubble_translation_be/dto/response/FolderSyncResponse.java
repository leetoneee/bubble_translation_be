package com.bteamcoding.bubble_translation_be.dto.response;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderSyncResponse {
    List<FolderDto> synced;
    List<FolderDto> conflicts;
}
