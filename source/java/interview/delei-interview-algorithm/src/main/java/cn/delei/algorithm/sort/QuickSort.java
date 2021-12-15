package cn.delei.algorithm.sort;

import cn.delei.algorithm.SwapNumber;

import java.util.Arrays;

/**
 * 快速排序
 *
 * @author deleiguo
 */
public class QuickSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        int[] arr = Arrays.copyOf(data, data.length);
        return quickSort(arr, 0, arr.length - 1);
    }

    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                SwapNumber.swapByTemp(arr, i, index);
                index++;
            }
        }
        SwapNumber.swapByTemp(arr, pivot, index - 1);
        return index - 1;
    }
}
