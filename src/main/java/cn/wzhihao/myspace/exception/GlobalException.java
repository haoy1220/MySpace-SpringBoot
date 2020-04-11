package cn.wzhihao.myspace.exception;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 * @author     ：hao
 * @date       ：Created in 2020/4/11 15:36
 * @description：全局异常
 **/
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(TokenException.class)
    public Result<String> tokenExceptionHandler(Exception e){
        return Result.Error(Const.StatusCode.NEED_LOGIN,e.getMessage());
    }
}
