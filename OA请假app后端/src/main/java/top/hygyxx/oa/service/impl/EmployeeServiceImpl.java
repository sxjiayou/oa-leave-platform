package top.hygyxx.oa.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.hygyxx.oa.entity.Employee;
import top.hygyxx.oa.mapper.EmployeeDao;
import top.hygyxx.oa.service.EmployeeService;

/**
 * sqlSession.getMapper(EmployeeDao.class)获取接口实现类
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
//dao接口实现查询

    /**
     * 按员工id查找员工信息
     * @param employeeId
     * @return
     */
}
