package cn.wzhihao.myspace;

import cn.wzhihao.myspace.utils.MD5Util;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MyspaceSpringbootApplicationTests {


    @Test
    void contextLoads() {

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        Date date = new Date();
        System.out.println(date.getMonth());
        LinkedList linkedList = new LinkedList();
        HashSet set = new HashSet();


    }

    @Test
    void testMD5() {

        MD5Util.MD5EncodeUtf8("123456");


    }

}
