package top.hygyxx.oa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import top.hygyxx.oa.common.ApiResponse;
import top.hygyxx.oa.common.Constant;
import top.hygyxx.oa.entity.LeaveForm;
import top.hygyxx.oa.entity.User;
import top.hygyxx.oa.entity.vo.ReqLeaveForm;
import top.hygyxx.oa.exception.MallExceptionEnum;
import top.hygyxx.oa.service.LeaveFormService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 请假表单提交
 * 请假审批
 */
@Slf4j
@RestController
@RequestMapping("/leave")
public class LeavFormController {
    @Autowired
    private LeaveFormService leaveFormService;

    /**
     * 创建表单 提交请假单
     *
     */
    @PostMapping("/create")
    private ApiResponse create(ReqLeaveForm reqLeaveForm, HttpSession session, BindingResult result) throws IOException {
        //1.接收各项表单数据
        User user = (User) session.getAttribute(Constant.LOGIN_USER);
        if (result.hasErrors()){
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ApiResponse.error(3001,errors.toString());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            LeaveForm form = new LeaveForm();
            form.setEmployeeId(user.getEmployeeId());
            form.setStartTime(sdf.parse(reqLeaveForm.getStrStartTime()));
            form.setEndTime(sdf.parse(reqLeaveForm.getStrEndTime()));
            form.setReason(reqLeaveForm.getReason());
            form.setFormType(Integer.parseInt(reqLeaveForm.getFormType()));
            form.setCreateTime(new Date());
            //2.调用业务逻辑方法
            leaveFormService.createLeaveForm(form);
            return ApiResponse.success();
        } catch (Exception e) {
            log.error("请假申请异常", e);
            return ApiResponse.error(MallExceptionEnum.SUBMIN_FAILED);
        }
    }

    /**
     * 查询需要审核的请假表单列表
     * @throws IOException
     */
    @GetMapping("/list")
    private ApiResponse getLeaveFormList(HttpSession session) throws IOException {
        //获取待处理请假信息
        User user = (User) session.getAttribute(Constant.LOGIN_USER);
        List<Map> list = leaveFormService.getLeaveFormList(user.getEmployeeId(), "process");

        //数据处理
        return ApiResponse.success(list);
    }

    /**
     * 处理审批列表
     * @throws IOException
     */
    @GetMapping("/audit")
    private ApiResponse audit(String formId,String result ,String reason,HttpSession session) throws IOException {
        User user = (User) session.getAttribute(Constant.LOGIN_USER);

        try {
            leaveFormService.audit(Long.parseLong(formId),user.getEmployeeId(),result,reason);
            return ApiResponse.success("审核成功");
        } catch (Exception e) {
            log.error("请假单审核失败", e);
            return ApiResponse.error(MallExceptionEnum.SUBMIN_FAILED);
        }
    }
}
