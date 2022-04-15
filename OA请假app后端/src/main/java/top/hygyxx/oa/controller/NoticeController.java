package top.hygyxx.oa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.hygyxx.oa.common.ApiResponse;
import top.hygyxx.oa.common.Constant;
import top.hygyxx.oa.entity.Notice;
import top.hygyxx.oa.entity.User;
import top.hygyxx.oa.exception.MallExceptionEnum;
import top.hygyxx.oa.service.NoticeService;

import javax.servlet.http.HttpSession;

@RestController
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    /**
     * 获取请假审批流程通知列表
     * @param session
     * @return
     */
    @GetMapping("/noticeList")
    public ApiResponse noticeList(HttpSession session,Integer pageNum,Integer pageSize){
        if(pageNum==null){
            pageNum=1;
        }
        if (pageSize==null){
            pageSize = 5;
        }
        User user = (User) session.getAttribute(Constant.LOGIN_USER);
        //未登录
        if (user==null){
            return ApiResponse.error(MallExceptionEnum.NO_LOGIN);
        }
        Page<Notice> noticePage = noticeService.selectPageByReceiverId(user.getUserId(),pageNum,pageSize);
        return ApiResponse.success(noticePage);
    }
}
