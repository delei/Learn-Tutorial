package cn.delei.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @author deleiguo
 */
public class InsertSort implements IArraySort {
    @Override
    public int[] sort(int[] data) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(data, data.length);
        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        int temp, j;
        for (int i = 1; i < arr.length; i++) {
            // 记录要插入的数据
            temp = arr[i];
            // 从已经排序的序列最右边的开始比较，找到比其小的数
            j = i;
            while (j > 0 && temp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            // 存在比其小的数，插入
            if (j != i) {
                arr[j] = temp;
            }
        }
        return arr;
    }
}
