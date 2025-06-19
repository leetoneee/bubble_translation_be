package com.bteamcoding.bubble_translation_be.repository;

import com.bteamcoding.bubble_translation_be.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, String> {
    List<Folder> findByUserId(Long userId);

    List<Folder> findByUserIdAndDeletedFalse(Long userId);

    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.deleted = false")
    List<Folder> findFoldersByUserId(@Param("userId") Long userId);
}
