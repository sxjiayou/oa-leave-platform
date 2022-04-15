package top.hygyxx.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.hygyxx.oa.entity.User;
@Mapper
public interface UserDao extends BaseMapper<User> {

}
