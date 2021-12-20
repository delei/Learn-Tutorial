package cn.delei.algorithm.string;

/**
 * 字符串匹配-KMP
 *
 * @author deleiguo
 */
public class KMPMatch implements IStringMatching {
    @Override
    public int match(String source, String pattern) {
        int s = source.length();
        int p = pattern.length();
        int i = 0;
        int j = 0;
        int[] next = getNext(pattern);

        while (i < s && j < p) {
            // 如果j = -1，或者当前字符匹配成功（即S[i] == P[j]），都令i++，j++
            if (j == -1 || source.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                // 如果j != -1，且当前字符匹配失败（即S[i] != P[j]），则令 i 不变，j = next[j],
                //  next[j]即为j所对应的next值
                j = next[j];
            }
        }
        if (j == p) {
            return i - p;
        } else {
            return -1;
        }
    }

    public int[] getNext(String pattern) {
        int i = -1;
        int j = 0;
        int l = pattern.length();
        int next[] = new int[l];
        next[0] = -1;
        while (j < l - 1) {
            if (i == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                next[j] = i;
            } else {
                i = next[i];
            }
        }
        return next;
    }
}
