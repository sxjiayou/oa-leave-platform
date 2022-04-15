package top.hygyxx.oa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.hygyxx.oa.entity.Node;
import top.hygyxx.oa.entity.User;
import top.hygyxx.oa.entity.vo.UserInfo;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 检查用户名并返回用户信息
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username, String password) throws NoSuchAlgorithmException;

    /**
     * 获取用户能访问的节点

     * @return
     */
    UserInfo selectUserInfoByUser(User user);
}
