package top.huajieyu001;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author huajieyu
 * @Date 2026/3/21 1:36
 * @Version 1.0
 * @Description TODO
 */
@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        for (int i = 10001; i < 11000; i++) {
            System.out.println(i);
        }
    }
}
