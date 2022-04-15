package top.hygyxx.oa.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ReqLeaveForm {
    @NotEmpty(message = "请假类别不能为空")
    @Pattern(regexp = "[1-6]", message = "没有该类型")
    String formType;//请假类别 事假 病假 工伤假 婚假 产假 丧假
    @NotEmpty(message = "开始时间不能为空")
    String strStartTime;
    @NotEmpty(message = "结束时间不能为空")
    String strEndTime;
    @NotEmpty(message = "请假理由不能为空")
    String reason;
}
