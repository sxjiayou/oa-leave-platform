package top.hygyxx.oa.exception;

public enum MallExceptionEnum {
    NO_USE_ExamineAndApprove(1000,"无效的审批流程"),
    NO_TASK_EXIST(1001,"未找到待处理任务"),
    APPLICATION_FAILED(1002,"请假单审核失败"),
    SUBMIN_FAILED(1003,"请假申请异常"),
    USERNAME_EXIST(2001,"用户已存在"),
    USERNAME_NO_EXIST(2003,"用户不存在"),
    PASSWORD_ERROR(2004,"密码错误"),
    INFO_EMPTY(2002,"信息不完整"),
    INSET_ERROR(2005,"插入失败"),
    NO_LOGIN(2006,"用户未登录"),
    MKDIR_FAILED(2007, "创建文件夹失败"),
    SYSTEM_ERROR(3000,"系统异常");
    Integer code;
    String msg;

    MallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
