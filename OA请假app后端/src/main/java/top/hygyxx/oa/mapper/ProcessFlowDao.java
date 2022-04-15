package top.hygyxx.oa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hygyxx.oa.entity.ProcessFlow;

import java.util.List;
@Mapper

public interface ProcessFlowDao extends BaseMapper<ProcessFlow> {
    /**插入处理流程
     * @param processFlow 插入对象
     * @return
     */
    public int insert(ProcessFlow processFlow);

    /**
     * 通过formId查找流程请假
     * @param formId
     * @return
     */
    public List<ProcessFlow> selectByFormId(Long formId);

    /**
     * 更新processFlow流程表
     * @param processFlow
     */
    public void update(ProcessFlow processFlow);
}
