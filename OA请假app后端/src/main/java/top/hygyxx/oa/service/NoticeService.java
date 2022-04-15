package top.hygyxx.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.hygyxx.oa.entity.Notice;

public interface NoticeService extends IService<Notice> {
    public Page<Notice> selectPageByReceiverId(Long receiverId, Integer pageNum, Integer pageSize);
}
