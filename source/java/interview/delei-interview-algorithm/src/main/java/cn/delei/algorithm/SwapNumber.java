package cn.delei.algorithm;

import cn.delei.util.PrintUtil;

import java.util.Arrays;

/**
 * 交换数字的方法
 *
 * @author deleiguo
 */
public class SwapNumber {
    public static void main(String[] args) {
        int[] data = new int[]{1, 2};
        PrintUtil.printTitle("临时变量");
        System.out.println(Arrays.toString(data));
        swapByTemp(data, 0, 1);
        System.out.println(Arrays.toString(data));

        data = new int[]{5, 3};
        PrintUtil.printTitle("算法");
        System.out.println(Arrays.toString(data));
        swapByArithmetic(data, 0, 1);
        System.out.println(Arrays.toString(data));

        data = new int[]{11, 2};
        PrintUtil.printTitle("位运算");
        System.out.println(Arrays.toString(data));
        swapByBitOpera(data, 0, 1);
        System.out.println(Arrays.toString(data));
    }

    /**
     * 临时变量法
     *
     * @param array 数据
     * @param i     下标i
     * @param j     下标j
     */
    public static void swapByTemp(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 算数交换
     *
     * @param array 数据
     * @param i     下标i
     * @param j     下标j
     */
    public static void swapByArithmetic(int[] array, int i, int j) {
        array[i] = array[i] + array[j];
        array[j] = array[i] - array[j];
        array[i] = array[i] - array[j];
    }

    /**
     * 位运算交换
     *
     * @param array 数据
     * @param i     下标i
     * @param j     下标j
     */
    public static void swapByBitOpera(int[] array, int i, int j) {
        array[i] = array[i] ^ array[j];
        //array[i]^array[j]^array[j]=array[i]
        array[j] = array[i] ^ array[j];
        //array[i]^array[j]^array[i]=array[j]
        array[i] = array[i] ^ array[j];
    }
}
