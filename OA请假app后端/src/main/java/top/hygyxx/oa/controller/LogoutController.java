package top.hygyxx.oa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hygyxx.oa.common.ApiResponse;
import top.hygyxx.oa.common.Constant;

import javax.servlet.http.HttpSession;

@RestController
public class LogoutController {

    /**
     * 注销 退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public ApiResponse logout(HttpSession session) {
        session.removeAttribute(Constant.LOGIN_USER);//清除当前会话属性
        session.invalidate();//彻底清除当前会话
        return ApiResponse.success();
    }
}
