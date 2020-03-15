package cn.wzhihao.myspace.service.Impl;

import cn.wzhihao.myspace.common.Const;
import cn.wzhihao.myspace.common.Result;
import cn.wzhihao.myspace.dao.UserMapper;
import cn.wzhihao.myspace.entity.User;
import cn.wzhihao.myspace.service.IUserService;
import cn.wzhihao.myspace.utils.EmailUtil;
import cn.wzhihao.myspace.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

@Transactional
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private UserMapper userMapper;


    //登录
    @Override
    public Result<User> login(String email, String password) {
        //数据库是否存在
        Result result = checkValid(email);
        if (result.getCode() == Const.StatusCode.EMAIL_EXISTS) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(MD5Util.MD5EncodeUtf8(password));
            user = userMapper.selectOne(user);
            if (user == null) {
                return Result.Error(Const.StatusCode.PWD_ERROR, "密码错误");
            }

            //todo 记录最后登录时间
//
            user.setUpdateTime(Calendar.getInstance().getTimeInMillis() + "");
            userMapper.updateByPrimaryKeySelective(user);
            //将密码置空再返回
            user.setPassword(StringUtils.EMPTY);
            return Result.SuccessByData("登录成功", user);
        } else if (result.getCode() == Const.StatusCode.EMAIL_NEED_ACTIVE) {
            return result;
        } else {
            return Result.Error(Const.StatusCode.EMAIL_NOT_EXISTS, "邮箱不存在");
        }
    }


    //校验邮箱
    @Override
    public Result checkValid(String email) {
        User user = new User();
        user.setEmail(email);
        user = userMapper.selectOne(user);
        if (user != null) {
            if (user.getActiveState() == Const.Active.YES) {
                return Result.Error(Const.StatusCode.EMAIL_EXISTS, "邮箱已存在，请重新输入");
            } else {
                return Result.Error(Const.StatusCode.EMAIL_NEED_ACTIVE, "邮箱尚未激活，请前往激活");
            }
        }
        return Result.Success("邮箱可用");
    }

    //注册
    @Override
    public Result register(User user) {

        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setActiveState(Const.Active.NO);

        long time = Calendar.getInstance().getTimeInMillis();
        //todo 记录创建时间
        user.setCreateTime(time + "");
        user.setUpdateTime(time + "");

        String activeCode = MD5Util.MD5EncodeUtf8(user.getEmail() + user.getPassword() + user.getNickname() + user.getId() + time);
        String expTime = (time + 1000 * 60 * 60 * 24) + "";
//        String expTime = (time + 1000 * 24) + "";

        user.setActiveCode(activeCode);
        user.setExpTime(expTime);

        int res = userMapper.insertSelective(user);
        if (res == 0) {
            return Result.Error(Const.StatusCode.REGISTER_ERROR, "注册失败");
        } else {
            SimpleMailMessage simpleMailMessage = EmailUtil.sendActiveEmail(Const.URL, user.getId(), user.getActiveCode(), user.getEmail(), Const.ADMIN_EMAIL);
            javaMailSender.send(simpleMailMessage);
            return Result.Success("注册成功,请前往邮箱激活");
        }
    }

    @Override
    public Result activeCode(String id, String activeCode) {
        User user = new User();
        user.setId(id);
        user.setActiveCode(activeCode);
        user = userMapper.selectOne(user);
        if (user == null || user.getActiveState() == Const.Active.YES) {
            //用户或激活码不存在
            return Result.Error(Const.StatusCode.EMAIL_NOT_EXISTS, "邮箱已激活或激活码无效");
        } else {
            long currTime = Calendar.getInstance().getTimeInMillis();
            if (currTime > Long.parseLong(user.getExpTime())) {
                //激活码过期
                userMapper.deleteByPrimaryKey(user);
                return Result.Error(Const.StatusCode.ACTIVE_EXP, "激活码已过期，请重新注册！！！");
            } else {
                //激活码有效
                user.setActiveState(Const.Active.YES);
                user.setUpdateTime(currTime + "");
                userMapper.updateByPrimaryKey(user);
                return Result.Success("激活成功，请前往登录！！！");
            }
        }
    }


    //发送验证码
    @Override
    public Result verifyEmail(String email) {
        //前端
        Result result = checkValid(email);
        if (result.getCode() == Const.StatusCode.EMAIL_EXISTS) {
            User user = new User();
            user.setEmail(email);
            user = userMapper.selectOne(user);


            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            long time = Calendar.getInstance().getTimeInMillis();
            String expTime = (time + 1000 * 5 * 60) + "";

            user.setExpTime(expTime);
            user.setUpdateTime(time + "");
            user.setVerifyCode(verifyCode);

            userMapper.updateByPrimaryKeySelective(user);
            SimpleMailMessage simpleMailMessage = EmailUtil.sendVerifyEmail(Const.URL, user.getVerifyCode(), user.getEmail(), Const.ADMIN_EMAIL);
            javaMailSender.send(simpleMailMessage);
            return Result.Success("验证码已发送至邮箱，请前往邮箱获取！");
        } else {
            return Result.Error(Const.StatusCode.EMAIL_NOT_EXISTS, "邮箱不存在或未激活");
        }
    }

    //重置密码
    @Override
    public Result resetPassword(String email, String newPassword, String verifyCode) {
        User user = new User();
        user.setEmail(email);
        user.setVerifyCode(verifyCode);
        user = userMapper.selectOne(user);
        if (user == null) {
            return Result.Error(Const.StatusCode.VERIFY_ERROR, "邮箱或验证码错误");
        } else {
            long currTime = Calendar.getInstance().getTimeInMillis();
            if (currTime > Long.parseLong(user.getExpTime())) {
                user.setExpTime("0");
                user.setVerifyCode("");
                userMapper.updateByPrimaryKeySelective(user);
                return Result.Error(Const.StatusCode.VERIFY_ERROR, "验证码已过期");
            } else {
                user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
                user.setVerifyCode("");
                user.setExpTime("0");
                userMapper.updateByPrimaryKeySelective(user);
                return Result.Success("密码重置成功，请重新登录！");
            }
        }
    }

    //获取个人信息
    @Override
    public Result<User> getUserInfo(String id) {
        User user = new User();
        user.setId(id);
        user = userMapper.selectOne(user);
        if (user == null){
            return Result.Error(Const.StatusCode.ID_NOT_EXISTS,"找不到当前用户信息");
        }
        user.setPassword(StringUtils.EMPTY);
        return Result.SuccessByData("成功返回",user);
    }

    //更新个人信息
    @Override
    public Result<User> updateUserInfo(User user) {
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res == 0){
            return Result.Error(Const.StatusCode.SQL_ERROR,"更新失败");
        }else {
            user = userMapper.selectOne(user);
            return Result.SuccessByData("更新个人信息成功",user);
        }
    }


}
