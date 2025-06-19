package com.bteamcoding.bubble_translation_be.service.impl;

import com.bteamcoding.bubble_translation_be.dto.request.FolderDto;
import com.bteamcoding.bubble_translation_be.dto.request.WordDto;
import com.bteamcoding.bubble_translation_be.entity.Folder;
import com.bteamcoding.bubble_translation_be.entity.User;
import com.bteamcoding.bubble_translation_be.entity.Word;
import com.bteamcoding.bubble_translation_be.excception.AppException;
import com.bteamcoding.bubble_translation_be.excception.ErrorCode;
import com.bteamcoding.bubble_translation_be.mapper.FolderMapper;
import com.bteamcoding.bubble_translation_be.mapper.WordMapper;
import com.bteamcoding.bubble_translation_be.repository.FolderRepository;
import com.bteamcoding.bubble_translation_be.repository.UserRepository;
import com.bteamcoding.bubble_translation_be.repository.WordRepository;
import com.bteamcoding.bubble_translation_be.service.FolderService;
import com.bteamcoding.bubble_translation_be.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepo;
    private final FolderRepository folderRepo;
    private final UserRepository userRepo;
    private final WordMapper wordMapper;

    @Override
    public List<WordDto> syncWords(Long userId, List<WordDto> incomingWords, Long lastSyncTime) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy danh sách word đã tồn tại trên server
        List<Word> existingWords = wordRepo.findWordsByUserId(userId);

        Map<String, Word> existingMap = existingWords.stream()
                .collect(Collectors.toMap(Word::getId, Function.identity()));

        List<Word> toSave = new ArrayList<>();
        List<WordDto> toReturn = new ArrayList<>();

        for (WordDto incoming : incomingWords) {
            Word local = existingMap.get(incoming.getId());

            if (local == null || incoming.getUpdatedAt() > local.getUpdatedAt()) {
                Folder folder = folderRepo.findById(incoming.getFolderId())
                        .orElseThrow(() -> new AppException(ErrorCode.FOLDER_NOT_EXISTED));

                Word word = wordMapper.toEntity(incoming, folder);

                word.setFolder(folder);
                toSave.add(word);
            }
        }

        List<Word> saved = wordRepo.saveAll(toSave);

        for (Word existing : existingWords) {
            boolean existsInIncoming = incomingWords.stream()
                    .anyMatch(incoming -> incoming.getId().equals(existing.getId()));
            if (!existsInIncoming && existing.getUpdatedAt() > lastSyncTime) {
                toReturn.add(wordMapper.toDto(existing));
            }
        }

        return toReturn;
    }
}
