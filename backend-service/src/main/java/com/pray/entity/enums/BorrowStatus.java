package com.pray.entity.enums;

/**
 * BorrowStatus
 *
 * @author Cotton Eye Joe
 * @since 2024/10/20 15:27
 */
public enum BorrowStatus {
    FAILED(0),
    SUCCESS(1),
    BORROWED(2);
    int code;

    BorrowStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
