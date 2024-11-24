package com.pray.mybatis;

import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * MybatisInstanceTest
 *
 * @author Cotton Eye Joe
 * @since 2024/11/24 15:54
 */
public class MybatisInstanceTest {
    public static void main(String[] args) {
        //使用build方法来创建SqlSessionFactory，这里我们通过文件输入流传入配置文件
        SqlSession sqlSession = MybatisUtils.openSession(true);

//        List<Object> getQcMethodItems = sqlSession.selectList("selectQcMethodItemsById",1);
        Map<String, Object> getQcMethodItems= sqlSession.selectOne("selectQcMethodItemsById",1);
        getQcMethodItems.entrySet().forEach(System.out::println);
    }
}
