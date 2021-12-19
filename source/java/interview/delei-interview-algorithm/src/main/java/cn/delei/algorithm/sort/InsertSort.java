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
        if (data.length < 2) {
            return data;
        }
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(data, data.length);
        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        int temp, j;
        // i从1开始
        for (int i = 1; i < arr.length; i++) {
            // 记录要插入的数据
            temp = arr[i];
            // 从已经排序的序列最右边的开始比较，找到比其小的数
            j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (arr[j] > temp) {
                    // 数据移动
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            // 插入数据
            arr[j + 1] = temp;
        }
        return arr;
    }
}
