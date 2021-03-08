package cn.delei;

import java.util.ArrayList;
import java.util.List;

/**
 * HashCode 相关工具类
 * <p>生成相同 HashCode 的字符串https://notes.mengxin.science/2018/01/20/how-to-generate-strings-with-same-hashcode/</p>
 */
public class HashCodeUtil {
    private static String[] base = new String[]{"Aa", "BB"};

    public static List<String> generateN(int n) {
        if (n <= 0) {
            return null;
        }
        List<String> list = generateOne(null);
        for (int i = 1; i < n; ++i) {
            list = generateOne(list);
        }

        return list;
    }


    public static List<String> generateOne(List<String> strList) {
        if ((null == strList) || (0 == strList.size())) {
            strList = new ArrayList<>();
            for (int i = 0; i < base.length; ++i) {
                strList.add(base[i]);
            }

            return strList;
        }

        List<String> result = new ArrayList<String>();

        for (int i = 0; i < base.length; ++i) {
            for (String str : strList) {
                result.add(base[i] + str);
            }
        }

        return result;
    }
}
