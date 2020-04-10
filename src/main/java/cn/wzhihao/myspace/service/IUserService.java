package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IUserService {
    Result<Map<String,Object>> login(String email, String password);

    Result<String> checkValid(String email);

    Result<String> register(User user);

    Result<String> activeCode(String id, String activeCode);

    Result<String> verifyEmail(String email);

    Result<String> resetPassword(String email, String newPassword, String verifyCode);

    Result<User> getUserInfo(String email);

    Result<User> updateUserInfo(String email, String nickname);

    Result<PageInfo<User>> getUsers(int pageNum, int pageSize);

    Result<String> deleteUser(String id);

    Result<Integer> getUsersInfo();
}
