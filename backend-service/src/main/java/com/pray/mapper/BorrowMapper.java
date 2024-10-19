package com.pray.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pray.entity.po.Borrow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @author Rainy-Heights
* @description 针对表【borrow(借阅表)】的数据库操作Mapper
* @createDate 2024-02-19 16:42:23
* @Entity com.pray.mapper.Borrow
*/
@Repository
@TableName(keepGlobalPrefix = true,value = "tb_book")
public interface BorrowMapper extends BaseMapper<Borrow> {
     /**
      * 返回借阅信息，字段包括 借阅书籍，借阅人，借阅人工号，借阅书籍号
      * @return List<Map<String,Object>>
      */
      //@Select("select borrow_man.username,book.book_name,borrow.book_id ,
     // borrow_man.user_id ,borrow_man.borrow_count from book_user as borrow_man right join borrow on borrow_man.user_id=borrow.user_id left join
     // book on book.book_id=borrow.book_id")
     //TODO 复合查询的索引优化之后再改
     @Select("select tb_book.book_name,mid.username , mid.user_id ,mid.book_id  " +
             "from tb_book inner join" +
             "(select bu.username,bu.user_id,bw.book_id from tb_book_user bu inner join tb_borrow bw on bu.user_id = bw.id )  " +
             "as mid on mid.book_id=tb_book.book_id")
     List<Map<String,Object>> selectBorrowUser();

     /**
      * 添加借阅记录
      * @param userId  借阅人工号
      * @param bookId  借阅书籍号
      * @return int
      */
     @Insert(" insert into tb_borrow(user_id,book_id) VALUE (#{userId},#{bookId})")
     int insertBorrowRecord(@Param("userId") int userId, @Param("bookId") int bookId);

     /**
      * 查询借阅 书籍信息
      * @param userId 借阅人工号
      * @param bookId 借阅书籍号
      * @return List<Map<String,Object>>
      */
     @Select("select bu.username,bn.book_name from tb_book_user as bu right join" +
             " (select book_name from tb_book where book_id=" +
             "(select distinct (book_id) from tb_borrow where tb_borrow.book_id=#{bookId} and tb_borrow.user_id=#{userId}))" +
             " as bn on bu.user_id=#{userId}")
     List<Map<String,Object>> selectBorrowDetails(@Param("userId") int userId, @Param("bookId") int bookId);
}




