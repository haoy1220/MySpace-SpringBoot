package cn.wzhihao.myspace.utils;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtil {

    //新建token
    public String createToken(User user) {
        String token = "";
        token = JWT.create()
                .withClaim("email", user.getEmail())//添加email
                .withClaim("role", user.getRole())//添加角色
                .withClaim("nickname",user.getNickname())
                .withExpiresAt(new Date(System.currentTimeMillis() + Const.JwtConfig.EXP_TIME * 1000))//添加过期时间
                .sign(Algorithm.HMAC256(user.getPassword()));//使用用户密码为私钥
        return token;
    }

    //从请求获取token
    public String getToken(HttpServletRequest request) {
        return request.getHeader("token");
    }


    //从request获取邮箱
    public String getEmailFromRequest(HttpServletRequest request) {
        String token = getToken(request);
        return token == null ? null : getEmailFromToken(token);
    }

    //从token获取邮箱
    public String getEmailFromToken(String token) {
        return JWT.decode(token).getClaim("email").asString();
    }
//
//    private Claims getClaimsFromToken(String token) {
//        Cl
//    }

    //从request获取角色
    public String getRoleFromRequest(HttpServletRequest request) {
        String token = getToken(request);
        return token == null ? null : getRoleFromToken(token);
    }

    //从token获取角色
    public String getRoleFromToken(String token) {
        return JWT.decode(token).getClaim("role").asString();
    }

    //从request获取昵称
    public String getNicknameFromRequest(HttpServletRequest request) {
        String token = getToken(request);
        return token == null ? null : getNicknameFromToken(token);
    }

    //从token获取昵称
    public String getNicknameFromToken(String token) {
        return JWT.decode(token).getClaim("nickname").asString();
    }

}
