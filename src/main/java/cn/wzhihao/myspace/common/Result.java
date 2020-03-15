package cn.wzhihao.myspace.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.code == Const.StatusCode.OK;
    }

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //返回成功信息和数据
    public static <T> Result<T> SuccessByData(String msg, T data) {
        return new Result<T>(Const.StatusCode.OK, msg, data);
    }

    //返回成功信息
    public static <T> Result<T> Success(String msg) {
        return new Result<T>(Const.StatusCode.OK, msg);
    }

    //返回错误信息
    public static <T> Result<T> Error(Integer code, String msg) {
        return new Result<T>(code, msg);
    }

}
