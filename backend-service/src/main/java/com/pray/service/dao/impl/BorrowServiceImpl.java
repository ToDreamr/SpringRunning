package com.pray.service.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pray.entity.po.Book;
import com.pray.entity.po.Borrow;
import com.pray.mapper.BookMapper;
import com.pray.mapper.BorrowMapper;
import com.pray.service.dao.BorrowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {
    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BookMapper bookMapper;
    @Override
    public List<Map<String, Object>> getBorrowUsers() {
        return borrowMapper.selectBorrowUser();
    }

    @Override
    public int insertBorrowRecord(int userId, int bookId) {
        return borrowMapper.insertBorrowRecord(userId,bookId);
    }

    @Override
    public List<Map<String, Object>> selectBorrowDetails(int userId, int bookId) {
        return borrowMapper.selectBorrowDetails(userId, bookId);
    }

    @Override
    public List<Book> listBook() {
        return bookMapper.bookList();
    }
}




