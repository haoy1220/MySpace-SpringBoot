package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.dao.UserMapper;
import cn.wzhihao.myspace.entity.User;
import cn.wzhihao.myspace.service.IUserService;
import cn.wzhihao.myspace.utils.EmailUtil;
import cn.wzhihao.myspace.utils.JwtTokenUtil;
import cn.wzhihao.myspace.utils.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private UserMapper userMapper;


    //登录
    @Override
    public Result<Map<String, Object>> login(String email, String password) {
        //数据库是否存在该用户
        User user = new User();
        user.setEmail(email);
        user.setPassword(MD5Util.MD5EncodeUtf8(password));
        user = userMapper.selectOne(user);
        if (user == null) {
            return Result.Error(Const.StatusCode.ERROR, "邮箱不存在或密码错误");
        } else if (user.getActiveState() == Const.Active.NO) {
            return Result.Error(Const.StatusCode.ERROR, "邮箱尚未激活，请前往邮箱激活");
        } else {
            //记录最后登录时间
            user.setUpdateTime(Calendar.getInstance().getTimeInMillis());
            String token = JwtTokenUtil.createToken(user);
            userMapper.updateByPrimaryKeySelective(user);

            //将密码置空再返回
            user.setPassword(StringUtils.EMPTY);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("token", token);
            dataMap.put("user", user);

            return Result.SuccessByData("登录成功", dataMap);
        }
    }


    //校验邮箱
    @Override
    public Result<String> checkValid(String email) {
        User user = new User();
        user.setEmail(email);
        user = userMapper.selectOne(user);
        if (user != null && user.getActiveState() == Const.Active.YES) {
            return Result.Error(Const.StatusCode.ERROR, "邮箱已存在，请重新输入");
        }
        return Result.Success("邮箱可用");
    }

    //注册
    @Override
    public Result<String> register(User user) {

        String id = UUID.randomUUID().toString();
        user.setId(id);
//        logger.info(user.getPassword());
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setActiveState(Const.Active.NO);

        long time = Calendar.getInstance().getTimeInMillis();
        //记录创建时间
        user.setCreateTime(time);
        user.setUpdateTime(time);

        String activeCode = MD5Util.MD5EncodeUtf8(user.getEmail() + user.getPassword() + user.getNickname() + user.getId() + time);

        user.setActiveCode(activeCode);
        user.setExpTime(time + Const.ACTIVE_EXP_TIME);

        int res = userMapper.insertSelective(user);
        if (res == 0) {
            return Result.Error(Const.StatusCode.ERROR, "注册失败");
        } else {
            SimpleMailMessage simpleMailMessage = EmailUtil.sendActiveEmail(user.getId(), user.getActiveCode(), user.getEmail(), Const.ADMIN_EMAIL);
            javaMailSender.send(simpleMailMessage);
            return Result.Success("注册成功,请前往邮箱激活");
        }
    }

    @Override
    public Result<String> activeCode(String id, String activeCode) {
        User user = new User();
//        logger.info(id + activeCode);
        user.setId(id);
        user.setActiveCode(activeCode);
        user = userMapper.selectOne(user);
        if (user == null || user.getActiveState() == Const.Active.YES) {
            //用户或激活码不存在
            return Result.Error(Const.StatusCode.ERROR, "邮箱已激活或激活码无效");
        } else {
            long currTime = Calendar.getInstance().getTimeInMillis();
            if (currTime > user.getExpTime()) {
                //激活码过期，应该清除记录
                userMapper.deleteByPrimaryKey(user);
                return Result.Error(Const.StatusCode.ERROR, "激活码已过期，请重新注册！！！");
            } else {
                //激活码有效，也要清楚激活码，防止重复激活
                user.setActiveCode("");
                user.setExpTime(0L);
                user.setActiveState(Const.Active.YES);
                user.setUpdateTime(currTime);
                userMapper.updateByPrimaryKey(user);
                return Result.Success("激活成功，请前往登录！！！");
            }
        }
    }


    //发送验证码
    @Override
    public Result<String> verifyEmail(String email) {
        //前端
        Result<String> result = checkValid(email);
        if (result.getCode() == Const.StatusCode.ERROR) {
            User user = new User();
            user.setEmail(email);
            user = userMapper.selectOne(user);


            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            long time = Calendar.getInstance().getTimeInMillis();

            user.setExpTime(time + Const.VERIFY_EXP_TIME);
            user.setUpdateTime(time);
            user.setVerifyCode(verifyCode);

            userMapper.updateByPrimaryKeySelective(user);
            SimpleMailMessage simpleMailMessage = EmailUtil.sendVerifyEmail(user.getVerifyCode(), user.getEmail(), Const.ADMIN_EMAIL);
            javaMailSender.send(simpleMailMessage);
            return Result.Success("验证码已发送至邮箱，请前往邮箱获取！");
        } else {
            return Result.Error(Const.StatusCode.ERROR, "邮箱不存在或未激活");
        }
    }

    //重置密码
    @Override
    public Result<String> resetPassword(String email, String newPassword, String verifyCode) {
        User user = new User();
        user.setEmail(email);
        user.setVerifyCode(verifyCode);
        user = userMapper.selectOne(user);
        if (user == null) {
            return Result.Error(Const.StatusCode.ERROR, "邮箱或验证码错误");
        } else {
            long currTime = Calendar.getInstance().getTimeInMillis();
            if (currTime > user.getExpTime()) {
                user.setExpTime(0L);
                user.setVerifyCode("");
                userMapper.updateByPrimaryKeySelective(user);
                return Result.Error(Const.StatusCode.ERROR, "验证码已过期");
            } else {
                user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
                user.setVerifyCode("");
                user.setExpTime(0L);
                userMapper.updateByPrimaryKeySelective(user);
                return Result.Success("密码重置成功，请重新登录！");
            }
        }
    }

    //获取个人信息
    @Override
    public Result<User> getUserInfo(String email) {
        User user = new User();
        user.setEmail(email);
        user = userMapper.selectOne(user);
        if (user == null) {
            return Result.Error(Const.StatusCode.ERROR, "找不到当前用户信息");
        }
        user.setPassword(StringUtils.EMPTY);
        return Result.SuccessByData("成功返回", user);
    }

    //更新个人信息
    @Override
    public Result<User> updateUserInfo(String email, String nickname) {
        User currUser = userMapper.selectOneByEmail(email);
        currUser.setNickname(nickname);
        int res = userMapper.updateByPrimaryKeySelective(currUser);
        if (res == 0) {
            return Result.Error(Const.StatusCode.ERROR, "更新失败");
        } else {
            currUser.setPassword(StringUtils.EMPTY);
            return Result.SuccessByData("更新个人信息成功", currUser);
        }
    }

    @Override
    public Result<PageInfo<User>> getUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return Result.SuccessByData("获取用户列表成功", pageInfo);
    }

    @Override
    public Result<String> deleteUser(String id) {
        userMapper.deleteByPrimaryKey(id);
        return Result.Success("删除用户成功");
    }

    @Override
    public Result<Integer> getUsersInfo() {
        double percentage = userMapper.selectBoy(Const.Sex.BOY);
        int per = (int) (percentage * 100);
        return Result.SuccessByData("查询用户分布成功",per);
    }


}
