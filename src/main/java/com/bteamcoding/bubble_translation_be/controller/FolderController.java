package com.bteamcoding.bubble_translation_be.controller;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;
import com.bteamcoding.bubble_translation_be.dto.request.FolderSyncRequest;
import com.bteamcoding.bubble_translation_be.dto.response.ApiResponse;
import com.bteamcoding.bubble_translation_be.dto.response.FolderSyncResponse;
import com.bteamcoding.bubble_translation_be.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @PostMapping("/sync")
    ApiResponse<FolderSyncResponse> syncFolders(
            @RequestParam("userId") Long userId,
            @RequestBody FolderSyncRequest request
    ) {
        List<FolderDto> synced = folderService.syncFolders(userId, request.getFolders(), request.getLastSyncTime());

        FolderSyncResponse folderSyncResponse = new FolderSyncResponse();
        folderSyncResponse.setSynced(synced);
        folderSyncResponse.setConflicts(Collections.emptyList());

        return ApiResponse.<FolderSyncResponse>builder()
                .result(folderSyncResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<FolderDto>> getAll(@RequestParam Long userId) {
        return ApiResponse.<List<FolderDto>>builder()
                .result(folderService.getAllFolders(userId))
                .build();
    }
}
