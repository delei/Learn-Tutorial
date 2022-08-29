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
    }
}
