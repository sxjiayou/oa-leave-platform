package top.hygyxx.oa.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.hygyxx.oa.entity.Node;
import top.hygyxx.oa.entity.User;
import top.hygyxx.oa.entity.vo.UserInfo;
import top.hygyxx.oa.service.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    /**
     * 登录检查
     * @throws NoSuchAlgorithmException
     */
    @Test
    void checkLogin() throws NoSuchAlgorithmException {
        User user = userService.checkLogin("m8", "test");
        System.out.println(user);
    }

    /**
     * 获取用户能访问的模块和用户信息
     */
    @Test
    void selectuserInfoByUserId() {
        User user = new User();
        user.setUserId(1l);
        user.setEmployeeId(1l);
        UserInfo userInfo= userService.selectUserInfoByUser(user);
        System.out.println(userInfo);

    }
}
