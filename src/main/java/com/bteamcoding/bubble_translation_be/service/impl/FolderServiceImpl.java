package com.bteamcoding.bubble_translation_be.service.impl;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;
import com.bteamcoding.bubble_translation_be.entity.Folder;
import com.bteamcoding.bubble_translation_be.entity.User;
import com.bteamcoding.bubble_translation_be.excception.AppException;
import com.bteamcoding.bubble_translation_be.excception.ErrorCode;
import com.bteamcoding.bubble_translation_be.mapper.FolderMapper;
import com.bteamcoding.bubble_translation_be.repository.FolderRepository;
import com.bteamcoding.bubble_translation_be.repository.UserRepository;
import com.bteamcoding.bubble_translation_be.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepo;
    private final UserRepository userRepo;
    private final FolderMapper folderMapper;

    @Override
    public FolderDto saveFolder(FolderDto folderDto) {
        User user = userRepo.findById(folderDto.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Folder folder = folderMapper.toEntity(folderDto, user);
        Folder saved = folderRepo.save(folder);
        return folderMapper.toDto(saved);
    }

    @Override
    public List<FolderDto> syncFolders(Long userId, List<FolderDto> incomingFolders, Long lastSyncTime) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy danh sách các thư mục hiện có trên server
        List<Folder> existing = folderRepo.findFoldersByUserId(userId);

        // Chuyển thành Map để dễ dàng tra cứu Folder theo ID
        Map<String, Folder> existingMap = existing.stream()
                .collect(Collectors.toMap(Folder::getId, Function.identity()));

        // Danh sách thư mục sẽ được lưu vào server
        List<Folder> foldersToSave = new ArrayList<>();
        List<FolderDto> foldersToReturn = new ArrayList<>();

        for (FolderDto incoming : incomingFolders) {
            Folder local = existingMap.get(incoming.getId());

            if (local == null || incoming.getUpdatedAt() > local.getUpdatedAt()) {
                Folder f = folderMapper.toEntity(incoming, user);
                foldersToSave.add(f);
            }
        }

        List<Folder> saved = folderRepo.saveAll(foldersToSave);

        for (Folder existingFolder : existing) {
            boolean existsInIncoming = incomingFolders.stream()
                    .anyMatch(incomingFolder -> incomingFolder.getId().equals(existingFolder.getId()));

            if (!existsInIncoming && existingFolder.getUpdatedAt() > lastSyncTime) {
                FolderDto folderDto = folderMapper.toDto(existingFolder);
                foldersToReturn.add(folderDto);
            }
        }

        return foldersToReturn;
    }

    @Override
    public List<FolderDto> getAllFolders(Long userId) {
        return folderRepo.findByUserIdAndDeletedFalse(userId).stream()
                .map(folderMapper::toDto)
                .collect(Collectors.toList());
    }
}
