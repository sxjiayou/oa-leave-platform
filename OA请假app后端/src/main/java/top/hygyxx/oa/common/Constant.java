package top.hygyxx.oa.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
    public  static final String  LOGIN_USER = "login_user";
    public static final int MANAGER_AUDIT_HOURS=72;//总经理请假审批时间阈值
}
