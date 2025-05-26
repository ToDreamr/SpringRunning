package com.pray.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * AuthorizeVO
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@Data
public class AuthorizeDTO extends CommonDTO{
    String username;
    Date expireTime;
    String token;
}
