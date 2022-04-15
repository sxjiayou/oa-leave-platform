package top.hygyxx.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hygyxx.oa.entity.LeaveForm;

import java.util.List;
import java.util.Map;

public interface LeaveFormService extends IService<LeaveForm> {
    /**
     * 创建表单
     * @param form
     * @return
     */
    LeaveForm createLeaveForm(LeaveForm form);

    /**
     * 获取带处理的请假单列表
     * @param operatorId
     * @param state
     * @return
     */
    public List<Map> getLeaveFormList(Long operatorId, String state);
    /**
     * 审批处理
     * @param result
     * @param reason
     */
    public void audit(Long formId, Long operatorId, String result, String reason);
}
