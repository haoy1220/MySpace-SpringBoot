package cn.wzhihao.myspace.filter;

import cn.wzhihao.myspace.annotation.PassToken;
import cn.wzhihao.myspace.annotation.VerifyToken;
import cn.wzhihao.myspace.dao.UserMapper;
import cn.wzhihao.myspace.entity.User;
import cn.wzhihao.myspace.utils.JwtTokenUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JwtFilter implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = JwtTokenUtil.getToken(request);
//        logger.info("我是拦截器" + token);
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
//            logger.info("我进来了，不需要校验token哦");
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断是否有跳过验证注解
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
//                logger.info("我进来了，这个有标注不需要校验token哦");
                return true;
            }
        }
        //检是否有需要验证注解
        if (method.isAnnotationPresent(VerifyToken.class)) {
            VerifyToken verifyToken = method.getAnnotation(VerifyToken.class);
            if (verifyToken.required()) {
//                logger.info("我进来了，需要校验token哦");
                //执行认证
                if (token == null) {
//                    logger.info("我进来了，你没有token哦");
                    throw new RuntimeException("无token，请重新登录");
                }
                //获取用户邮箱
                String userEmail;
                try {

                    userEmail = JwtTokenUtil.getEmailFromToken(token);
//                    logger.info(userEmail);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("获取不到用户邮箱");
                }

                User user = new User();
                user.setEmail(userEmail);
                user = userMapper.selectOne(user);
                //                验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException j) {
                    throw new RuntimeException("token验证不通过");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
