package top.hygyxx.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hygyxx.oa.entity.Notice;
import top.hygyxx.oa.mapper.NoticeDao;
import top.hygyxx.oa.service.NoticeService;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {
    /**
     * 查询员工接收到的通知消息
     *
     * @param receiverId 接收人ID
     * @return 消息列表
     */
    @Override
    public Page<Notice> selectPageByReceiverId(Long receiverId, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        Page<Notice> page = new Page<>(pageNum, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        //降序
        queryWrapper.orderBy(true, false, "notice_id");
        queryWrapper.eq("receiver_id", receiverId);

        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

}
