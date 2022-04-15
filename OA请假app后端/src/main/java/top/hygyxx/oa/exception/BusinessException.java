package top.hygyxx.oa.exception;

public class BusinessException extends RuntimeException{
    Integer code;
    String msg;

    public BusinessException(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }



    public BusinessException(top.hygyxx.oa.exception.MallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
