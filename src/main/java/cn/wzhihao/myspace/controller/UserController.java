package cn.wzhihao.myspace.controller;


import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.User;
import cn.wzhihao.myspace.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService iUserService;


    //登录
    @ResponseBody
    @PostMapping("/login")
    public Result<User> login(@RequestParam(name = "email") String email,
                              @RequestParam(name = "password") String password, HttpSession session) {
        Result result = iUserService.login(email, password);
        if (result.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, result.getData());
        }
        return result;
    }

    //退出登录
    @ResponseBody
    @GetMapping("/logout")
    public Result logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return Result.Success("退出成功");
    }

    //注册
    @ResponseBody
    @PostMapping("/register")
    public Result register(User user) {
        return iUserService.register(user);
    }

    //激活邮箱
    @GetMapping("/active")
    @ResponseBody
    public Result activeEmail(@RequestParam(name = "id") String id,
                              @RequestParam(name = "activeCode") String activeCode) {
        return iUserService.activeCode(id, activeCode);
    }

    //校验邮箱
    @PostMapping("/checkValid")
    @ResponseBody
    public Result checkValid(@RequestParam(name = "email") String email) {
        return iUserService.checkValid(email);
    }

    //发送验证码
    @PostMapping("/verifyEmail")
    @ResponseBody
    public Result verifyEmail(@RequestParam(name = "email") String email) {
        return iUserService.verifyEmail(email);
    }

    //重置密码
    @PostMapping("/resetPassword")
    @ResponseBody
    public Result resetPassword(@RequestParam(name = "email") String email,
                                @RequestParam(name = "newPassword") String newPassword,
                                @RequestParam(name = "verifyCode") String verifyCode) {
        return iUserService.resetPassword(email, newPassword, verifyCode);
    }

    //获取个人信息
    @ResponseBody
    @GetMapping("/getUserInfo")
    public Result<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.Error(Const.StatusCode.NEED_LOGIN, "请登录后再试");
        } else {
            return iUserService.getUserInfo(user.getId());
        }
    }

    //更新个人信息
    @ResponseBody
    @PostMapping("/updateUserInfo")
    public Result<User> updateUserInfo(HttpSession session, User user) {
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser == null) {
            return Result.Error(Const.StatusCode.NEED_LOGIN, "请登录再试");
        } else {
            user.setId(currUser.getId());
            Result result = iUserService.updateUserInfo(user);
            if (result.isSuccess()) {
                session.setAttribute(Const.CURRENT_USER, result.getData());
            }
            return result;
        }
    }
}
