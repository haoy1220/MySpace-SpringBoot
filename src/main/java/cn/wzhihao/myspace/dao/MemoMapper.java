package cn.wzhihao.myspace.dao;

import cn.wzhihao.myspace.entity.Memo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MemoMapper extends Mapper<Memo> {

    @Select("select * from myspace_memo where memo_type=#{type} and user_email=#{email}\n" +
            "order by create_time desc")
    List<Memo> selectByTypeAndEmail(int type, String email);

    @Select("select * from myspace_memo where FROM_UNIXTIME(memo_date/1000,'%e')=#{day} AND FROM_UNIXTIME(memo_date/1000,'%m') =#{month} AND memo_type!=0")
    List<Memo> selectMemo(int day, int month);

    @Select("select * from myspace_memo where FROM_UNIXTIME(pre_date/1000,'%Y')=#{year}  AND FROM_UNIXTIME(pre_date/1000,'%m') =#{month} AND FROM_UNIXTIME(pre_date/1000,'%e')=#{day} AND memo_type!=0")
    List<Memo> selectPreMemo(int day, int month,int year);
}
