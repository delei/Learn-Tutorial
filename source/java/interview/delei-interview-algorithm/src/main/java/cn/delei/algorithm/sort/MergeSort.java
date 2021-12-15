package cn.delei.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author deleiguo
 */
public class MergeSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        int[] arr = Arrays.copyOf(data, data.length);
        if (arr.length < 2) {
            return arr;
        }
        int middle = (int) Math.floor(arr.length / 2);
        // 二分
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);
        // 治
        return merge(left, right);
    }

    private int[] merge(int[] left, int[] right) {
        // 申请额外空间保存归并之后数据
        int[] result = new int[left.length + right.length];
        int i = 0;

        // 选取两个序列中的较小值放入新数组
        while (left.length > 0 && right.length > 0) {
            if (left[0] <= right[0]) {
                result[i++] = left[0];
                left = Arrays.copyOfRange(left, 1, left.length);
            } else {
                result[i++] = right[0];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }

        // 序列left中多余的元素移入新数组
        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        // 序列right中多余的元素移入新数组
        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }
        return result;
    }
}
