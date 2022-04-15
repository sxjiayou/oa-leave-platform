package top.hygyxx.oa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hygyxx.oa.entity.Notice;

import java.util.List;
@Mapper

public interface NoticeDao extends BaseMapper<Notice> {
    /**
     * 插入通知信息
     *
     * @param notice
     * @return
     */
    public int insert(Notice notice);

    /**
     * 查询通知消息
     * @param receiverId 通知编号
     * @return
     */
    public List<Notice> selectById(Long receiverId);
}
