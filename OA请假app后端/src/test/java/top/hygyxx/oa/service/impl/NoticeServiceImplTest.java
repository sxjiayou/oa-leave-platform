package top.hygyxx.oa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.hygyxx.oa.entity.Notice;
import top.hygyxx.oa.service.NoticeService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NoticeServiceImplTest {
    /**
     * 通知接收测试
     */
    @Autowired
    private NoticeService noticeService;
    @Test
    void selectPageByReceiverId() {
        Page<Notice> page = noticeService.selectPageByReceiverId(1L, 1, 5);
        List<Notice> records = page.getRecords();
        records.forEach(System.out::println);
    }

}
