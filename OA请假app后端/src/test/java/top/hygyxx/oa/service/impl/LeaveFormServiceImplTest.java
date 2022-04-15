package top.hygyxx.oa.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.hygyxx.oa.service.LeaveFormService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LeaveFormServiceImplTest {
    @Autowired
    private LeaveFormService leaveFormService;

    /**
     * 创建请假单
     */
    @Test
    void createLeaveForm() {
//        leaveFormService.createLeaveForm();
    }

    /**
     * 未实现
     */
    @Test
    void selectByParams() {
//        leaveFormService.selectByParams();
    }

    /**
     * 获取要处理的请假单list
     */
    @Test
    void getLeaveFormList() {
        List<Map> processing = leaveFormService.getLeaveFormList(1l, "process");
        processing.forEach(p->{
            System.out.println("结果");
            System.out.println(p.toString());
        });
    }

    /**
     * 审批处理
     */
    @Test
    void audit() {
    }
}
