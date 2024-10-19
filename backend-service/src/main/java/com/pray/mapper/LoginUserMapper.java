package com.pray.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pray.entity.po.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserMapper extends BaseMapper<LoginUser> {
    static final String DATABASE = "spring_runner";

    @Select("select * from "+DATABASE+".tb_user where username=#{username}")
    LoginUser loadUserByUsername(@Param("username") String username);
}




