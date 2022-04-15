package top.hygyxx.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.hygyxx.oa.entity.LeaveForm;

import java.util.List;
import java.util.Map;

@Mapper
public interface LeaveFormDao extends BaseMapper<LeaveForm> {
    /**
     * 插入表单请假
     * @param leaveForm 请假申请表单信息
     * @return
     */
    public int insert(LeaveForm leaveForm);

    /**
     * 查询当前ID要处理的数据
     * @param pf_operatorId  执行人Id
     * @param pfState 假条状态
     * @return Map结果集
     */

    @MapKey("process_id")
    public List<Map> selectByParams(@Param("pf_operator_id") Long pf_operatorId, @Param("pf_state") String pfState);


    /**
     * 更新表单数据
     * @param leaveForm
     */
    public void update(LeaveForm leaveForm);
}
