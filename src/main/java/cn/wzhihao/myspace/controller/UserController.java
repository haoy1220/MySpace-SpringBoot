package cn.wzhihao.myspace.controller;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.ServerResponse;
import cn.wzhihao.myspace.domain.User;
import cn.wzhihao.myspace.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService iUserService;

    //登录功能
    @PostMapping("/login")
    @ResponseBody
    public ServerResponse<User> login(@RequestParam("email") String email,
                                      @RequestParam("password") String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(email, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

//    退出登录
    @GetMapping("/logout")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

//    注册功能
    @PostMapping("/register")
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

//    校验邮箱冲突
    @GetMapping("/checkValid")
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str,type);
    }

//    忘记密码功能
//    查看个人信息
//    修改个人信息
}
