package top.hygyxx.oa.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * spring AOP 打印请求和响应信息
 *
 * ！ServletRequestAttributes 获取请求
 */
@Aspect//切面
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * top.hygyxx.oa.controller.*.*(..))")  //切点
    public void webLog() {

    }

    @Before("webLog()") //通知前
    public void doBefore(JoinPoint joinPoint) {
        //收到请求，记录请求内容
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL:{}", request.getRequestURL().toString());
        log.info("HTTP_METHOD:{}", request.getMethod());
        log.info("IP:{}", request.getRemoteAddr());
        log.info("CLASS_METHOD:{}.{}",
                joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName());
        log.info("ARGS:{}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "res",pointcut = "webLog()")//通知后
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完成请求，返回内容
        log.info("RESPONSE:{}",new ObjectMapper().writeValueAsString(res));//对象转json字符串
    }
}
