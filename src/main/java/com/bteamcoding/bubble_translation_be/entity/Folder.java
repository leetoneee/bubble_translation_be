package com.bteamcoding.bubble_translation_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "folders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Folder {
    @Id
    String id;

    @Column(nullable = false)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)  // Thêm mối quan hệ với User
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "updated_at")
    Long updatedAt;

    @Column(nullable = false)
    Boolean deleted;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Word> words;
}
