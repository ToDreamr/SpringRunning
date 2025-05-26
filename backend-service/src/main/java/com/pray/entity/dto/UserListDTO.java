package com.pray.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserListVO
 *
 * @author 春江花朝秋月夜
 * @since 2024/3/26 22:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserListDTO extends CommonDTO{

    private int[] userId;

    private String[] userName;
}
