package cn.delei.algorithm.sort;

import java.util.Arrays;

/**
 * 基数排序
 *
 * @author deleiguo
 */
public class RadixSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        int[] arr = Arrays.copyOf(data, data.length);
        return radixSort(arr);
    }

    private static int[] radixSort(int[] arr) {
        if (arr.length == 0) {
            return arr;
        }
        // 求最大绝对值 max
        int max = arr[0];
        for (int value : arr) {
            if (value < 0) {
                value = -value;
            }
            if (max < value) {
                max = value;
            }
        }
        // 求分配总轮次 K
        int K = 0;
        while (max > 0) {
            K += 1;
            max = (max / 10);
        }
        // 新建桶
        int[][] bucketMatrix = new int[20][arr.length];
        // base代表当前循环用来排序的基数，如 1,10，100....
        int base = 1;
        for (int i = 0; i < K; i++) {
            int[] order = new int[20];
            // 放入桶中
            for (int val : arr) {
                // index 表示 val 要放在 20 个桶中的哪一个
                int index = (val % (base * 10)) / base + 10;
                bucketMatrix[index][order[index]++] = val;
            }
            // 收集回数组
            int h = 0;
            for (int k = 0; k < 20; k++) {
                for (int j = 0; j < order[k]; j++) {
                    arr[h++] = bucketMatrix[k][j];
                }
            }
            base *= 10;
        }
        return arr;
    }
}
