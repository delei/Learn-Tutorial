package cn.delei.algorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串匹配-BM
 *
 * @author deleiguo
 */
public class BoyerMooreMatch implements IStringMatching {
    @Override
    public int match(String source, String pattern) {
        char[] sChars = source.toCharArray();
        char[] tChars = pattern.toCharArray();
        // 坏字符规则：拿到模式串字符和index的映射
        Map<Character, Integer> map = getCharIndexMap(tChars);
        // 好后缀规则：初始化suffix数组和prefix数组
        int[] suffix = new int[tChars.length];
        boolean[] prefix = new boolean[tChars.length];
        initSuffixAndPrefix(tChars, suffix, prefix);

        // i表示主串与模式串对齐的第一个字符
        int i = 0;
        while (i <= sChars.length - tChars.length) {
            // j代表坏字符对应模式串中的下标
            int j;
            // 模式串从后往前匹配
            for (j = tChars.length - 1; j >= 0; j--) {
                if (sChars[i + j] != tChars[j]) {
                    break;
                }
            }
            if (j < 0) {
                // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                return i;
            }

            // 获取模式串中坏字符的最大index，如果没有找到，则返回-1
            int badCharMaxIndex = map.getOrDefault(sChars[i + j], -1);
            // x代表坏字符规则移动的位数
            int x = j - badCharMaxIndex;
            int y = 0;
            // 如果有好后缀的话
            if (j < tChars.length - 1) {
                // y代表好后缀规则移动的位数
                y = moveByGS(j, tChars.length, suffix, prefix);
            }
            i = i + Math.max(x, y);
        }
        return -1;
    }

    private static Map<Character, Integer> getCharIndexMap(char[] chars) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            map.put(chars[i], i);
        }
        return map;
    }

    /**
     * 初始化模式串的suffix数组和prefix数组
     *
     * @param chars 模式串
     */
    private static void initSuffixAndPrefix(char[] chars, int[] suffix, boolean[] prefix) {
        for (int i = 0; i < chars.length; i++) { // 初始化赋值
            suffix[i] = -1;
            prefix[i] = false;
        }

        // 从前往后遍历模式串中的所有子串
        for (int i = 0; i < chars.length - 1; i++) {
            int j = i; // j代表子串中和后缀子串对比的字符index
            int k = 1; // k代表后缀子串长度

            // 从后往前对比子串的字符和后缀子串的字符是否相等
            while (j >= 0 && chars[j] == chars[chars.length - k]) {
                suffix[k] = j; // 匹配成功，覆盖之前的suffix值
                if (j == 0) {
                    prefix[k] = true; // 该子串为符合条件的前缀子串
                }
                j--; // 子串字符下标往前一位
                k++; // 后缀子串的长度加一位
            }
        }
    }

    /**
     * 计算好后缀规则往后移动的位数
     *
     * @param j 坏字符对应的模式串中的字符下标
     * @param m 模式串的长度
     */
    private static int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        // k代表好后缀的长度
        int k = m - 1 - j;

        // 优先匹配好后缀
        if (suffix[k] != -1) {
            // 移动至前一个匹配到好后缀的位置
            return j - suffix[k] + 1;
        }
        for (int r = j + 2; r <= m - 1; r++) {
            // 有可以匹配的前缀
            if (prefix[m - r] == true) {
                // 移动至匹配成功的前缀的位置
                return r;
            }
        }
        return m;
    }
}
