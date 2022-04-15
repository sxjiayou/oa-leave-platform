package top.hygyxx.oa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hygyxx.oa.common.ApiResponse;
import top.hygyxx.oa.common.Constant;
import top.hygyxx.oa.entity.User;
import top.hygyxx.oa.entity.vo.UserInfo;
import top.hygyxx.oa.exception.MallExceptionEnum;
import top.hygyxx.oa.service.UserService;

import javax.servlet.http.HttpSession;
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录并获取用户信息
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/login")
    public ApiResponse toLogin(User user, HttpSession session){
      try {
          //保存session会话
          User loginUser= userService.checkLogin(user.getUsername(),user.getPassword());//获取用户信息
          if (loginUser==null){
              return ApiResponse.error(MallExceptionEnum.USERNAME_NO_EXIST);
          }
          session.setAttribute(Constant.LOGIN_USER,loginUser);
          String id = session.getId();
          //登录成功
          return ApiResponse.success(id);
      } catch (Exception e){
        log.error(e.getMessage(),e);
          return ApiResponse.error(MallExceptionEnum.SYSTEM_ERROR);
      }
    }

    /**
     *获取用户信号与权限信息 -能访问什么功能
     */
    @GetMapping("/getUserInfo")
    public ApiResponse getNode(HttpSession session){
        User user = (User) session.getAttribute(Constant.LOGIN_USER);//获取login_user Session属性

        UserInfo userInfo = userService.selectUserInfoByUser(user);//得到用户权限内功能
        //放入会话属性
        return ApiResponse.success(userInfo);
    }
}
