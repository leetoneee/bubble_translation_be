package com.bteamcoding.bubble_translation_be.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "words")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Word {
    @Id
    String id;

    @Column(nullable = false)
    String word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    Folder folder;

    @Column(name = "updated_at")
    Long updatedAt;

    @Column(nullable = false)
    Boolean deleted;
}
