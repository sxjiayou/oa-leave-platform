package top.hygyxx.oa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hygyxx.oa.entity.Node;
import top.hygyxx.oa.entity.User;

import java.util.List;
@Mapper
public interface RbacDao extends BaseMapper<Node> {
    public List<Node> selectNodeByUserId(Long userId);
}
