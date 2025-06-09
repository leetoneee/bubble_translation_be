package com.bteamcoding.bubble_translation_be.excception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}
