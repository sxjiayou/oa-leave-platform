package top.hygyxx.oa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hygyxx.oa.common.Constant;
import top.hygyxx.oa.entity.Employee;
import top.hygyxx.oa.entity.LeaveForm;
import top.hygyxx.oa.entity.Notice;
import top.hygyxx.oa.entity.ProcessFlow;
import top.hygyxx.oa.exception.BusinessException;
import top.hygyxx.oa.exception.MallExceptionEnum;
import top.hygyxx.oa.mapper.EmployeeDao;
import top.hygyxx.oa.mapper.LeaveFormDao;
import top.hygyxx.oa.mapper.NoticeDao;
import top.hygyxx.oa.mapper.ProcessFlowDao;
import top.hygyxx.oa.service.LeaveFormService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveFormServiceImpl extends ServiceImpl<LeaveFormDao, LeaveForm> implements LeaveFormService {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private LeaveFormDao leaveFormDao;
    @Autowired
    private ProcessFlowDao processFlowDao;
    @Autowired
    private NoticeDao noticeDao;

    /* 创建请假单
     *
     * @param form 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    public LeaveForm createLeaveForm(LeaveForm form) {
        Employee employee = employeeDao.selectById(form.getEmployeeId());
        Employee dmanager = employeeDao.selectLeader(employee);
        Employee manager = employeeDao.selectLeader(dmanager);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //1.持久化form表单数据，8级以下员工表单状态为processing,8级（总经理）状态为approve
        if (employee.getLevel() == 8) {
            form.setState("approve");
        } else {
            form.setState("processing");
        }

        leaveFormDao.insert(form);
        //2.增加一条流程数据，说明表单已经提交，状态为complete
        ProcessFlow processFlow1 = new ProcessFlow();//流程1
        processFlow1.setFormId(form.getFormId());
        processFlow1.setOperatorId(employee.getEmployeeId());
        processFlow1.setAction("apply");
        processFlow1.setCreateTime(new Date());
        processFlow1.setOrderNo(1);
        processFlow1.setState("complete");
        processFlow1.setIsLast(0);
        processFlowDao.insert(processFlow1);
        //3.分情况创建其余流程数据

        //3.1 7级以下员工，生成部门经理审批任务，请假时间大于请假伐值，还需生成总经理审批任务
        if (employee.getLevel() < 7) {
            ProcessFlow processFlow2 = new ProcessFlow();//流程2
            processFlow2.setFormId(form.getFormId());
            processFlow2.setOperatorId(dmanager.getEmployeeId());
            processFlow2.setAction("audit");
            processFlow2.setCreateTime(new Date());
            processFlow2.setOrderNo(2);
            processFlow2.setState("process");

            //判断时间
            long diff = form.getEndTime().getTime() - form.getStartTime().getTime();
            float hours = diff / (1000 * 60 * 60) * 1f;
            if (hours >= Constant.MANAGER_AUDIT_HOURS) {
                processFlow2.setIsLast(0);
                processFlowDao.insert(processFlow2);

                ProcessFlow processFlow3 = new ProcessFlow();//流程3
                processFlow3.setFormId(form.getFormId());
                processFlow3.setOperatorId(manager.getEmployeeId());
                processFlow3.setAction("audit");
                processFlow3.setCreateTime(new Date());
                processFlow3.setOrderNo(3);
                processFlow3.setState("ready");
                processFlow3.setIsLast(1);
                processFlowDao.insert(processFlow3);
            } else {
                processFlow2.setIsLast(1);
                processFlowDao.insert(processFlow2);
            }
            //员工提交通知
            String noticeContent = String.format("您的请假申请[%s-%s]已提交，请等待上级审批"
                    , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            Notice notice = new Notice(employee.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);

            //部门经理待处理审批通知
            noticeContent = String.format("%s-%s提起请假申请[%s-%s]，请尽快审批"
                    , employee.getTitle(), employee.getName()
                    , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            notice = new Notice(dmanager.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);
        }
        //3.2 7级员工，生成总经理审批任务
        else if (employee.getLevel() == 7) {
            ProcessFlow processFlow2 = new ProcessFlow();//流程2
            processFlow2.setFormId(form.getFormId());
            processFlow2.setOperatorId(manager.getEmployeeId());
            processFlow2.setAction("audit");
            processFlow2.setCreateTime(new Date());
            processFlow2.setOrderNo(2);
            processFlow2.setState("process");
            processFlow2.setIsLast(1);
            processFlowDao.insert(processFlow2);
            //员工提交通知
            String noticeContent = String.format("您的请假申请[%s-%s]已提交，请等待上级审批"
                    , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            Notice notice = new Notice(employee.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);

            //总经理待处理审批通知
            noticeContent = String.format("%s-%s提起请假申请[%s-%s]，请尽快审批"
                    , employee.getTitle(), employee.getName()
                    , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            notice = new Notice(manager.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);
        }
        //3.3 8级员工，生成总经理审批任务，系统自动通过
        else if (employee.getLevel() == 8) {
            ProcessFlow processFlow2 = new ProcessFlow();//流程2
            processFlow2.setFormId(form.getFormId());
            processFlow2.setOperatorId(employee.getEmployeeId());
            processFlow2.setAction("audit");
            processFlow2.setResult("approved");
            processFlow2.setCreateTime(new Date());
            processFlow2.setAudiTime(new Date());
            processFlow2.setReason("自动通过");
            processFlow2.setOrderNo(2);
            processFlow2.setState("complete");
            processFlow2.setIsLast(1);
            processFlowDao.insert(processFlow2);

            //总经理通知
            String noticeContent = String.format("您的请假申请[%s-%s]系统已自动通过."
                    , sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            Notice notice = new Notice(employee.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);

        }
        return form;
    }



    /**
     * 获取指定任务状态及指定经办人对应的请假单列表
     *
     * @param operatorId 经办人id
     * @param state      任务状态ProcessFlow
     * @return 请假单列表
     */
    @Override
    public List<Map> getLeaveFormList(Long operatorId, String state) {

        List<Map> formList = leaveFormDao.selectByParams(operatorId, state);

        return formList;
    }


    /**
     * 审批数据处理
     * @param formId     请假单编号
     * @param operatorId 执行人编号
     * @param result     同意或驳回
     * @param reason     原因
     */
    @Override
    public void audit(Long formId, Long operatorId, String result, String reason) {

        //1.无论同意/驳回，当前任务状态变更为complete
        List<ProcessFlow> list = (List<ProcessFlow>) processFlowDao.selectByFormId(formId);
        if (list.size() == 0) {
            throw new BusinessException(MallExceptionEnum.NO_USE_ExamineAndApprove);
        }
//            获取当前任务processflow对象 stream().filter()用于通过设置条件过滤出元素 ，
        List<ProcessFlow> processFlowList = list.stream().filter(p -> p.getOperatorId().equals(operatorId) && p.getState().equals("process")).collect(Collectors.toList());
        ProcessFlow processFlow = null;
        if (processFlowList.size() == 0) {
            throw new BusinessException(MallExceptionEnum.NO_TASK_EXIST);
        } else {
            //更新状态
            processFlow = processFlowList.get(0);
            processFlow.setState("complete");
            processFlow.setResult(result);
            processFlow.setReason(reason);
            processFlow.setAudiTime(new Date());
            processFlowDao.update(processFlow);
        }
        //   2.如果当前任务是最后一个节点，代表流程结束，更新请假表单状态为对应的approved/refused

        LeaveForm leaveForm = leaveFormDao.selectById(formId);

        Employee employee = employeeDao.selectById(leaveForm.getEmployeeId());//提交人
        Employee operator = employeeDao.selectById(operatorId);//经办人
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (processFlow.getIsLast() == 1) {
            leaveForm.setState(result);//更新结果//approved|refused
            leaveFormDao.update(leaveForm);

            String strResult = null;
            if (result.equals("approved")) {
                strResult = "批准";
            } else if (result.equals("refused")) {
                strResult = "驳回";
            }
            // -- -
            String noticeContent = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见：%s,审批流程结束"
                    , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
                    , operator.getTitle(), operator.getName(), strResult, reason);//发给表单提交人
            Notice notice = new Notice(employee.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);

            noticeContent = String.format("%s%s提起请假申请[%s-%s]您已%s,审批意见：%s,审批流程结束"
                    , employee.getTitle(), employee.getName()
                    , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
                    , strResult, reason);//发给经办人
            notice = new Notice(operator.getEmployeeId(), noticeContent);
            noticeDao.insert(notice);
        } else {
            List<ProcessFlow> readyList = list.stream().filter(p -> p.getState().equals("ready")).collect(Collectors.toList());
//                    3.如果当前任务不是最后一个节点且审批通过，那下一个节点的状态从ready变给process
            if (result.equals("approved")) {
                ProcessFlow readyProcess = readyList.get(0);
                readyProcess.setState("process");
                processFlowDao.update(readyProcess);
                //消息通知提交人，部门经理已通过
                String noticeContent = String.format("您的请假申请[%s-%s]%s%s已被批准,审批意见：%s,请等待总经理审批。"
                        , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
                        , operator.getTitle(), operator.getName(), reason);//发给表单提交人
                Notice notice = new Notice(employee.getEmployeeId(), noticeContent);
                noticeDao.insert(notice);

                //消息通知部门经理，已提交总经理
                noticeContent = String.format("%s%s提起的请假申请[%s-%s]您已批准,审批意见：%s,以提交总经理审批。"
                        , employee.getTitle(), employee.getName()
                        , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime()), reason);//发给部门经理
                notice = new Notice(operator.getEmployeeId(), noticeContent);
                noticeDao.insert(notice);
                //消息通知总经理
                noticeContent = String.format("%s%s提起的请假申请[%s-%s]您已批准,请尽快审批。"
                        , employee.getTitle(), employee.getName()
                        , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime()));//发给部门经理
                notice = new Notice(readyProcess.getOperatorId(), noticeContent);
                noticeDao.insert(notice);
            }
//                    4.如果当前任务不是最后一个节点且审批驳回，则后续所有任务状态变为cancel,请假单状态变为refused
            else if (result.equals("refused")) {
                for (ProcessFlow p : readyList) {
                    p.setState("cancel");
                    processFlowDao.update(p);
                }
                leaveForm.setState("refused");
                leaveFormDao.update(leaveForm);

                String noticeContent = String.format("您的请假申请[%s-%s]%s%s已驳回,审批意见：%s,审批流程结束"
                        , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
                        , operator.getTitle(), operator.getName(), reason);//发给表单提交人
                Notice notice = new Notice(employee.getEmployeeId(), noticeContent);
                noticeDao.insert(notice);

                noticeContent = String.format("%s%s提起请假申请[%s-%s]您已驳回,审批意见：%s,审批流程结束"
                        , employee.getTitle(), employee.getName()
                        , sdf.format(leaveForm.getStartTime()), sdf.format(leaveForm.getEndTime())
                        , reason);//发给经办人
                notice = new Notice(operator.getEmployeeId(), noticeContent);
                noticeDao.insert(notice);
            }
        }
    }

}
