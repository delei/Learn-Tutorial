package cn.delei.algorithm.sort;

import cn.delei.algorithm.SwapNumber;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author deleiguo
 */
public class SelectionSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        int[] arr = Arrays.copyOf(data, data.length);
        // 总共要经过 N-1 轮比较
        int min;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            // 每轮需要比较的次数 N-i
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    // 记录目前能找到的最小值元素的下标
                    min = j;
                }
            }
            // 将找到的最小值和i位置所在的值进行交换
            if (i != min) {
                SwapNumber.swapByBitOpera(arr, i, min);
            }
        }
        return arr;
    }
}
