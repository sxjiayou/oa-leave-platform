package top.hygyxx.oa.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.hygyxx.oa.common.ApiResponse;

import java.util.ArrayList;
import java.util.List;
/**
 * 拦截异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


        /**
         * 系统异常
         * @param e
         * @return
         */
        @ExceptionHandler(Exception.class)
        @ResponseBody
        public Object handlerException(Exception e){
            log.error("Default Exception:{}",e);
            return ApiResponse.error(top.hygyxx.oa.exception.MallExceptionEnum.SYSTEM_ERROR);
        }

        /**
         * 自定义异常
         * @param e
         * @return
         */
        @ExceptionHandler(BusinessException.class)
        @ResponseBody
        public Object handlerBusinessException(BusinessException e){
            log.error("BusinessException:{}",e);
            return ApiResponse.error(e.getCode(),e.getMsg());
        }

}

