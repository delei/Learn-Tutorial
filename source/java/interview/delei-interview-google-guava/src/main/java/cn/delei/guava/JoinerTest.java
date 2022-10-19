package cn.delei.guava;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JoinerTest {

    @Test
    public void joinTest() {
        List<String> dataList = Arrays.asList("aa", "bb", "cc", null, "dd", "ee", null, "ff");
        System.out.println(Joiner.on("-").skipNulls().join(dataList));
        System.out.println(Joiner.on("-").useForNull("NVL").join(dataList));

        System.out.println("if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " +
                "return nil;" +
                "end; " +
                "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " +
                "if (counter > 0) then " +
                "redis.call('pexpire', KEYS[1], ARGV[2]); " +
                "return 0; " +
                "else " +
                "redis.call('del', KEYS[1]); " +
                "redis.call('publish', KEYS[2], ARGV[1]); " +
                "return 1; " +
                "end; " +
                "return nil;");
    }
}
