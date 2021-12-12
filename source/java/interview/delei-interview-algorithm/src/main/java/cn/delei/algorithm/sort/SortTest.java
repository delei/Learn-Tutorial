package cn.delei.algorithm.sort;

import cn.delei.util.PrintUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;

/**
 * 排序算法测试
 *
 * @author deleguo
 */
public class SortTest {
    public static void main(String[] args) {
        int[] data = initData(10);
        System.out.println(Arrays.toString(data));
        PrintUtil.printDivider();
        System.out.println(Arrays.toString(new SortStrategy(new BubbleSort()).sort(data)));
    }

    private static int[] initData(int length) {
        if (length < 0 || length > 100) {
            return null;
        }
        int[] data = new int[length];
        for (int i = 0; i < length; i++) {
            data[i] = RandomUtil.randomInt(10, 40);
        }
        return data;
    }
}
