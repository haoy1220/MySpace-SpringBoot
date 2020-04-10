package cn.wzhihao.myspace.dao;

import cn.wzhihao.myspace.entity.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    @Select("select * from myspace_user where email=#{email}")
    User selectOneByEmail(String email);

    @Select("SELECT SUM(CASE when sex=#{boy} then 1 else 0 end)/COUNT(*) FROM `myspace_user`")
    double selectBoy(int boy);
}
