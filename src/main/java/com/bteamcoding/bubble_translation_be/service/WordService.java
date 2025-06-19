package com.bteamcoding.bubble_translation_be.service;

import com.bteamcoding.bubble_translation_be.dto.request.WordDto;

import java.util.List;

public interface WordService {
//    WordDto saveWord(WordDto wordDto);

    List<WordDto> syncWords(Long userId, List<WordDto> incomingWords, Long lastSyncTime);

//    List<WordDto> getAllFolders(Long userId);
}

