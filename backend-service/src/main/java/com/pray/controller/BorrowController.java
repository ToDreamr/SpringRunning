package com.pray.controller;

import com.pray.enums.BorrowStatus;
import com.pray.service.dao.BookService;
import com.pray.service.dao.BookUserService;
import com.pray.service.dao.BorrowService;
import com.pray.common.Result;
import com.pray.template.CommonBusinessCallBack;
import com.pray.template.SpTransactionTemplate;
import jakarta.annotation.Resource;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * UserBorrowController
 *
 * @author 春江花朝秋月夜
 * @since 2024/2/19 16:50
 */
@RestController
@RequestMapping(path = "/borrow")
public class BorrowController {
    @Resource
    private BookService bookService;

    @Resource
    private BorrowService borrowService;

    @Resource
    private BookUserService bookUserService;

    @Resource
    private SpTransactionTemplate spTransactionTemplate;

    @Resource
    private TransactionTemplate transactionTemplate;
    /**
     * 获取借阅书籍的数据
     * @return Result<List<List<BorrowedListVO>>>
     */
    @GetMapping
    public Result<?> borrowList() {
        return Result.ok(bookService.borrowList());
    }

    /**
     * 根据借阅人工号和借阅书籍号码借书
     * @param userId 工号
     * @param bookId 书号
     * @return 借阅 信息
     */
    @PostMapping("/{userId}/{bookId}")
    public Result<Map<String, Object>> borrowBook(@PathVariable("userId") int userId,@PathVariable("bookId") int bookId){
        Result<Map<String, Object>> result = new Result<>();
        spTransactionTemplate.executeWithTransaction(new CommonBusinessCallBack() {
            @Override
            public void check() {

            }

            @Override
            public void execute() {
                bookUserService.borrowBook(userId, bookId);
            }
        },transactionTemplate,result);
        List<Map<String, Object>> mapList = borrowService.selectBorrowDetails(userId, bookId);
        return result.data(mapList.get(0));
    }
}
