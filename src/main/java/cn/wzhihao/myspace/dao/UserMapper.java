package cn.wzhihao.myspace.dao;

import cn.wzhihao.myspace.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    //    检验邮箱是否存在
    @Select("select count(1) from myspace_user where email=#{email}")
    int checkEmail(String email);

    //    检验登录密码
    @Select("select * from myspace_user where email=#{email} and password=#{password}")
    User login(@Param("email") String email, @Param("password") String password);

    //    添加用户
    @Insert("insert into myspace_user (id, nickname, password, \n" +
            "       email, sex, role, create_time, update_time)\n" +
            "       values (#{id,jdbcType=INTEGER}, #{nickname,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, \n" +
            "       #{email,jdbcType=VARCHAR}, #{sex,jdbcType=INTEGER}, #{role,jdbcType=INTEGER}, now(), now())")
    @Override
    int insert(User record);
}
