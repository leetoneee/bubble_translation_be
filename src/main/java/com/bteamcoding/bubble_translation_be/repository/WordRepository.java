package com.bteamcoding.bubble_translation_be.repository;

import com.bteamcoding.bubble_translation_be.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, String> {

    @Query("SELECT w FROM Word w WHERE w.folder.user.id = :userId AND w.deleted = false")
    List<Word> findWordsByUserId(@Param("userId") Long userId);
}
