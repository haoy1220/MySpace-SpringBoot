package cn.wzhihao.myspace.exception;

/***
 * @author     ：hao
 * @date       ：Created in 2020/4/11 15:36
 * @description：token验证异常
 **/
public class TokenException extends Exception {

    public TokenException(String message) {
        super(message);
    }
}
