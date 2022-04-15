package top.hygyxx.oa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@TableName("sys_node")
public class Node {
    @TableId(type = IdType.AUTO)
    private Long nodeId;
    private Integer nodeType;
    private String nodeName;
    private String url;
    private Integer nodeCode;
    private Long parentId;


    public Node() {
    }

    public Node(Long nodeId, Integer nodeType, String nodeName, String url, Integer nodeCode, Long parentId) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.nodeName = nodeName;
        this.url = url;
        this.nodeCode = nodeCode;
        this.parentId = parentId;
    }

}
