package top.hygyxx.oa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@TableName("adm_process_flow")
public class ProcessFlow {
    @TableId(type = IdType.AUTO)
    private Long processId;
    private Long formId;
    private Long operatorId;
    private String action;
    private String result;
    private String reason;
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date audiTime;
    private Integer orderNo;
    private String state;
    private Integer isLast;

    public ProcessFlow(Long formId, Long operatorId, String action, String result, String reason,
                       Date createTime, Date audiTime, Integer orderNo, String state, Integer isLast) {
        this.formId = formId;
        this.operatorId = operatorId;
        this.action = action;
        this.result = result;
        this.reason = reason;
        this.createTime = createTime;
        this.audiTime = audiTime;
        this.orderNo = orderNo;
        this.state = state;
        this.isLast = isLast;
    }

    public ProcessFlow() {
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public Date getAudiTime() {
        return audiTime;
    }

    public void setAudiTime(Date audiTime) {
        this.audiTime = audiTime;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }
}
