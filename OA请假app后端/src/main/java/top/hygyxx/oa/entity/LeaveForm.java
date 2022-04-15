package top.hygyxx.oa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@TableName("adm_leave_form")
public class LeaveForm {
    @TableId(type = IdType.AUTO)
    private Long formId;
    private Long employeeId;
    private Integer formType;
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String reason;
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String state;


    public LeaveForm(Long employeeId, Integer formType, Date startTime, Date endTime, String reason, Date createTime) {
        this.employeeId = employeeId;
        this.formType = formType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.createTime = createTime;
    }

    public LeaveForm(Long employeeId, Integer formType, Date startTime, Date endTime, String reason, Date createTime, String state) {
        this.employeeId = employeeId;
        this.formType = formType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
        this.createTime = createTime;
        this.state = state;
    }

    public LeaveForm() {
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
