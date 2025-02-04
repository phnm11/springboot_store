package com.nam.storespring.exception;

public enum ErrorCode {
    USER_EXISTED(-1, "Username already exists"),
    EMAIL_EXISTED(-2, "Email already exists"),
    USERNAME_INVALID(-10, "Username must be at least 5 characters!"),
    PASSWORD_INVALID(-11, "Password must be at least 8 characters!"),
    REVIEW_INVALID(-12, "Rating must be between 1 and 5 points!"),
    KEY_INVALID(-20, "Invalid message key!"),
    UNCATEGORIZED_EXCEPTION(-999, "Uncategorized error");

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
