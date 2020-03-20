package cn.wzhihao.myspace.dao;

import cn.wzhihao.myspace.entity.Diary;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface DiaryMapper extends Mapper<Diary> {

    @Select("select * from myspace_diary where user_email=#{user_email} order by create_time DESC")
    List<Diary> selectByEmail(String user_email);

    @Update("update myspace_diary set diary_title=#{diaryTitle},diary_body=#{diaryBody} where id=#{id} and user_email=#{userEmail}")
    int updateByEmailAndId(Diary diary);

    @Select("select FROM_UNIXTIME(create_time/1000,'%Y年%m月') months,\n" +
            "COUNT(id) count FROM `myspace_diary`\n" +
            "GROUP BY months ORDER BY months DESC")
    List<Map<String,Integer>> selectArchiveByEmail(String emailFromRequest);

    @Select("select * FROM  myspace_diary\n" +
            "where (diary_title LIKE '%${text}%' or diary_body like '%${text}%') and user_email=#{email}\n" +
            "order by create_time DESC")
    List<Diary> selectByEmailAndText(String email, String text);
}
