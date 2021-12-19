package cn.delei.algorithm.sort;

import cn.delei.algorithm.SwapNumber;

import java.util.Arrays;

/**
 * 冒泡排序实现
 *
 * @author deleguo
 */
public class BubbleSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        // 对 data 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(data, data.length);
        boolean flag = true;
        for (int i = 1; i < arr.length; i++) {
            // 标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            flag = true;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    SwapNumber.swapByTemp(arr, j, j + 1);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }
}
