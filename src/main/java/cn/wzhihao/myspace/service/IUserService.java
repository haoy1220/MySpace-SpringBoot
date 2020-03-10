package cn.wzhihao.myspace.service;

import cn.wzhihao.myspace.common.ServerResponse;
import cn.wzhihao.myspace.domain.User;

public interface IUserService {
    ServerResponse<User> login(String email, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);
}
