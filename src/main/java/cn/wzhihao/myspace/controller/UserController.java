package cn.wzhihao.myspace.controller;


import cn.wzhihao.myspace.annotation.VerifyToken;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.User;
import cn.wzhihao.myspace.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService iUserService;


    //登录
    @PostMapping("/login")
    public Result<Map<String, Object>> login(String email, String password) {
        return iUserService.login(email, password);
    }

    //注册
    @PostMapping()
    public Result<String> register(User user) {
        return iUserService.register(user);
    }

    //激活邮箱
    @PostMapping("/active")
    public Result<String> activeEmail(String id, String activeCode) {
        return iUserService.activeCode(id, activeCode);
    }

    //校验邮箱
    @PostMapping("/checkValid")
    public Result<String> checkValid(String email) {
        return iUserService.checkValid(email);
    }

    //发送验证码
    @PostMapping("/verifyEmail")
    public Result<String> verifyEmail(String email) {
        return iUserService.verifyEmail(email);
    }

    //重置密码
    @PutMapping("/resetPassword")
    public Result<String> resetPassword(String email, String newPassword, String verifyCode) {
        return iUserService.resetPassword(email, newPassword, verifyCode);
    }

    //获取个人信息
    @VerifyToken
    @GetMapping("/{email}")
    public Result<User> getUserInfo(@PathVariable(name = "email") String email) {
        return iUserService.getUserInfo(email);
    }

    //更新个人信息
    @VerifyToken
    @PutMapping("/{email}")
    public Result<User> updateUserInfo(@PathVariable(name = "email") String email, String nickname) {
        return iUserService.updateUserInfo(email, nickname);
    }

    @VerifyToken
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<User>> getUsers(@PathVariable int pageNum, @PathVariable int pageSize) {
        return iUserService.getUsers(pageNum, pageSize);
    }

    @VerifyToken
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable String id){
        return iUserService.deleteUser(id);
    }

    @VerifyToken
    @GetMapping("/boy")
    public Result<Integer> getUsersInfo(){
        return iUserService.getUsersInfo();
    }
}
