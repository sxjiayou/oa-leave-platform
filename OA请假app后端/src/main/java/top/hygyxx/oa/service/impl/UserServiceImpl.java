package top.hygyxx.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hygyxx.oa.entity.Department;
import top.hygyxx.oa.entity.Employee;
import top.hygyxx.oa.entity.Node;
import top.hygyxx.oa.entity.User;
import top.hygyxx.oa.entity.vo.UserInfo;
import top.hygyxx.oa.exception.BusinessException;
import top.hygyxx.oa.exception.MallExceptionEnum;
import top.hygyxx.oa.mapper.DepartmentDao;
import top.hygyxx.oa.mapper.EmployeeDao;
import top.hygyxx.oa.mapper.RbacDao;
import top.hygyxx.oa.mapper.UserDao;
import top.hygyxx.oa.service.UserService;
import top.hygyxx.oa.util.MD5Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RbacDao rbacDao;

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    /**
     * 检测登入账户是否正确
     *
     * @param username 账户名
     * @param password 密码
     * @return User对象，包含用户信息
     */
    public User checkLogin(String username, String password) throws NoSuchAlgorithmException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userDao.selectOne(queryWrapper);
        if (user == null) {
            //抛出用户不存在异常
            throw new BusinessException(MallExceptionEnum.USERNAME_NO_EXIST);
        }
        //输入的密码解析成md5+盐，与数据库后端对比
        String md5 = MD5Util.md5Digest(password, user.getSalt());
        if (!md5.equals(user.getPassword())) {
            throw new BusinessException(MallExceptionEnum.PASSWORD_ERROR);
        }
        return user;
    }

    /**
     * 用户id查找当前用户具备的功能和用户信息
     *
     * @return
     */
    @Override
    public UserInfo selectUserInfoByUser(User user) {
        UserInfo userInfo = new UserInfo();
        //获取权限信息
        List<Node> nodeList = rbacDao.selectNodeByUserId(user.getUserId());
        userInfo.setNodeList(nodeList);
        //获取用户信息
        Employee employee = employeeDao.selectById(user.getEmployeeId());
        BeanUtils.copyProperties(employee,userInfo);
        Department department = departmentDao.selectById(employee.getDepartmentId());
        userInfo.setDepartmentName(department.getDepartmentName());
        return userInfo;
    }
}
