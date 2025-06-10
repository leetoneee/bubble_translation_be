package com.bteamcoding.bubble_translation_be.excception;

public class ResourceAlreadyExistsException extends AppException {
    public ResourceAlreadyExistsException(String msg) {
        super(ErrorCode.USER_EXISTED);
    }
}
