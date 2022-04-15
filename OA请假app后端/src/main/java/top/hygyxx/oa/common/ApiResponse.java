package top.hygyxx.oa.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.hygyxx.oa.exception.MallExceptionEnum;

import java.util.HashMap;

@Data
public class ApiResponse<T>  {
    private Integer code;
    private String msg;
    private T data;

    private final static  Integer CODE_OK=2000;
    private final static  String MSG_OK="SUCCESS";

    public ApiResponse(){
        this(CODE_OK,MSG_OK);
    }

    public ApiResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 发送成功 无返回数据
     *
     * @return
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>();
    }

    /**
     * 发送成功 返回数据
     *
     * @return
     */
    public static <T> ApiResponse<T> success(T result) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        //返回数据
        apiResponse.setData(result);
        return apiResponse;
    }

    /**
     * 发送失败
     *
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> error(Integer status,String msg) {
        ApiResponse<T> apiResponse = new ApiResponse<>(status,msg);
        return apiResponse;
    }

    /**
     * 发送失败
     *
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> error(MallExceptionEnum ex) {
        ApiResponse<T> apiResponse = new ApiResponse<>(ex.getCode(),ex.getMsg());
        return apiResponse;
    }

}

