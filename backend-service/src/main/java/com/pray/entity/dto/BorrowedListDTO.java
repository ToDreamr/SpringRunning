package com.pray.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * BorrowedListVO
 * 使用vo对象代码侵入性太大，后期学习改变
 * @author 春江花朝秋月夜
 * @since 2024/3/26 22:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowedListDTO extends CommonDTO{

    private String  bookNames;

    private List<UserListDTO> users;
}
