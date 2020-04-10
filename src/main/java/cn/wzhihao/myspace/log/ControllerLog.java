package cn.wzhihao.myspace.log;

import cn.wzhihao.myspace.utils.JwtTokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/***
 * @author     ：hao
 * @date       ：Created in 2020/4/8 22:04
 * @description：controller层日记
 **/
@Aspect//声明是切面类
@Component
public class ControllerLog {

    @Autowired
    private HttpServletRequest request;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * cn.wzhihao.myspace.controller.*.*(..))")//明确切入点的范围
    public void log(){
    }

    @Around("log()")//切入点通知方式为around
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        StringBuilder builder = new StringBuilder();
        builder.append("执行方法：").append(joinPoint.getSignature()).append(",").append('\n')
                .append("Email: ").append(JwtTokenUtil.getEmailFromRequest(request)).append(",").append('\n')
                .append("URL: ").append(request.getRequestURI()).append(",").append('\n')
                .append("RequestMethod: ").append(request.getMethod()).append(",").append('\n')
                .append("Args:").append(Arrays.toString(joinPoint.getArgs())).append(",").append('\n')
                .append("ReturnValue: ").append(object == null ? "null" : object.toString()).append(",").append('\n')
                .append("Time: ").append(endTime - startTime).append("ms,").append('\n');
        logger.info(builder.toString());
        return object;//around方式要return结果，不然前端将接收不到结果
    }
}
