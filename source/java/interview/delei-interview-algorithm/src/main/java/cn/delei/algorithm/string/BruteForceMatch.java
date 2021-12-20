package cn.delei.algorithm.string;

/**
 * 字符串匹配-BF
 * LeetCode-28. 实现 strStr()
 *
 * @author deleiguo
 */
public class BruteForceMatch implements IStringMatching {
    @Override
    public int match(String source, String pattern) {
        int sourceLength = source.length();
        int patternLength = pattern.length();

        for (int i = 0; i + patternLength <= sourceLength; i++) {
            int j;
            for (j = 0; j < patternLength; j++) {
                if (source.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == patternLength) {
                return i;
            }
        }
        return -1;
    }
}
