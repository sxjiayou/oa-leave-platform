package top.hygyxx.oa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@TableName("sys_notice")
@Data
public class Notice {
    @TableId(type = IdType.AUTO)
    private Long noticeId;
    private Long receiverId;
    private String content;
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Notice() {
    }

    public Notice(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
        this.createTime = new Date();
    }

}
