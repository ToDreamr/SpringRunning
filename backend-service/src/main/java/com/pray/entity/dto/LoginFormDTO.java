package com.pray.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * LoginFormDto
 * <p>
 *
 * @author 春江花朝秋月夜
 * @since 2023/8/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
public class LoginFormDTO extends CommonDTO{

    String username;

    String password;

    String code;
}
