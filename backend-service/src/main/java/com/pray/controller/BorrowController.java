package com.pray.controller;

import com.pray.service.BookService;
import com.pray.service.BookUserService;
import com.pray.service.BorrowService;
import com.pray.utils.Result;
import jakarta.annotation.Resource;
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
        int isBorrowed = bookUserService.borrowBook(userId, bookId);
        List<Map<String, Object>> mapList = borrowService.selectBorrowDetails(userId, bookId);
        if (isBorrowed==1) {
            return Result.ok(mapList.get(0),"借阅成功");
        } else if (isBorrowed==2) {
            return Result.fail("借阅失败，这本书你已经借过了");
        }
        return Result.fail("借阅失败");
    }
}
