package com.pray.entity.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * BorrowStatus
 *
 * @author Cotton Eye Joe
 * @since 2024/10/20 15:27
 */
@Getter
public enum BorrowStatus {
    FAILED(0),
    SUCCESS(1),
    BORROWED(2);
    final int code;

    BorrowStatus(int code) {
        this.code = code;
    }

}
