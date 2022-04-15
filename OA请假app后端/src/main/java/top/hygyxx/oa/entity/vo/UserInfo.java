package top.hygyxx.oa.entity.vo;

import lombok.Data;
import lombok.ToString;
import top.hygyxx.oa.entity.Node;

import java.util.List;

/**
 * 员工信息
 */
@Data
@ToString
public class UserInfo {
    private Long employeeId;
    private String name;
    private  String departmentName;
    private String title;
    private Integer level;
    private List<Node> nodeList;
}
