package cn.delei.algorithm.sort;

import java.util.Arrays;

/**
 * 希尔排序
 *
 * @author deleiguo
 */
public class ShellSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(data, data.length);
        int gap = 1;
        // 分组
        while (gap < arr.length / 3) {
            gap = gap * 3 + 1;
        }
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (int) Math.floor(gap / 3);
        }
        return arr;
    }
}
