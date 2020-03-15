package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.User;

public interface IUserService {
    Result<User> login(String email, String password);

    Result checkValid(String email);

    Result register(User user);

    Result activeCode(String id, String activeCode);

    Result verifyEmail(String email);

    Result resetPassword(String email, String newPassword, String verifyCode);

    Result<User> getUserInfo(String id);

    Result<User> updateUserInfo(User user);
}
