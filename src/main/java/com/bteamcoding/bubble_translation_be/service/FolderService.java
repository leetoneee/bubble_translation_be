package com.bteamcoding.bubble_translation_be.service;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;

import java.util.List;

public interface FolderService {
    FolderDto saveFolder(FolderDto folderDto);

    List<FolderDto> syncFolders(Long userId, List<FolderDto> incomingFolders, Long lastSyncTime);

    List<FolderDto> getAllFolders(Long userId);
}

