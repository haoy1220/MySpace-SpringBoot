package cn.wzhihao.myspace;

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

}
