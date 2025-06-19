package com.bteamcoding.bubble_translation_be.controller;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;
import com.bteamcoding.bubble_translation_be.dto.request.FolderSyncRequest;
import com.bteamcoding.bubble_translation_be.dto.request.WordDto;
import com.bteamcoding.bubble_translation_be.dto.request.WordSyncRequest;
import com.bteamcoding.bubble_translation_be.dto.response.ApiResponse;
import com.bteamcoding.bubble_translation_be.dto.response.FolderSyncResponse;
import com.bteamcoding.bubble_translation_be.dto.response.WordSyncResponse;
import com.bteamcoding.bubble_translation_be.service.FolderService;
import com.bteamcoding.bubble_translation_be.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {
    private final WordService wordService;

    @PostMapping("/sync")
    ApiResponse<WordSyncResponse> syncWords(
            @RequestParam("userId") Long userId,
            @RequestBody WordSyncRequest request
    ) {
        List<WordDto> synced = wordService.syncWords(userId, request.getWords(), request.getLastSyncTime());

        WordSyncResponse wordSyncResponse = new WordSyncResponse();
        wordSyncResponse.setSynced(synced);
        wordSyncResponse.setConflicts(Collections.emptyList());

        return ApiResponse.<WordSyncResponse>builder()
                .result(wordSyncResponse)
                .build();
    }

//    @GetMapping
//    public ApiResponse<List<FolderDto>> getAll(@RequestParam Long userId) {
//        return ApiResponse.<List<FolderDto>>builder()
//                .result(folderService.getAllFolders(userId))
//                .build();
//    }
}
