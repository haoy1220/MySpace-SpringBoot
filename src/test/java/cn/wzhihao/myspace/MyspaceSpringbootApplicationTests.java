package cn.wzhihao.myspace;

import cn.wzhihao.myspace.dao.DiaryMapper;
import cn.wzhihao.myspace.entity.Diary;
import cn.wzhihao.myspace.utils.JwtTokenUtil;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
class MyspaceSpringbootApplicationTests {

    @Autowired
    DiaryMapper diaryMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;
    @Test
    void contextLoads() {
//        Date date = new Date();
//        System.out.println(date);
//
//        Calendar calendar = Calendar.getInstance();
//
//        System.out.println(calendar.getTime());
//        System.out.println(calendar.getTimeInMillis());
//        System.out.println(calendar);

    }

    @Test
    void diaryTest(){
        Diary diary = new Diary();

        System.out.println(diary.toString());
        diary = diaryMapper.selectOne(diary);
        System.out.println(diary.toString());
    }
}
