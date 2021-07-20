package cn.delei.algorithm.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * #Easy #Medium
 * 有序数组去重
 * 快慢双指针
 *
 * @author deleguo
 */
public class ArraysRemoveDuplicates {

    public static void main(String[] args) {
        /*
         * 有序数组去重
         * 输入：[0, 1, 1, 2, 3, 3]
         * 输出：[0, 1, 2, 3]
         */
        List<Integer> inputList = Arrays.asList(0, 1, 1, 2, 3, 3);
        System.out.println(removeDuplicates01(inputList));
        System.out.println(removeDuplicates02(inputList));

        /*
         * 26. 删除有序数组中的重复项
         * 给你一个有序数组 nums ，请你"原地"删除重复出现的元素，使每个元素只出现一次
         * 不要使用额外的数组空间，你必须在"原地"修改输入数组 并在使用 O(1) 额外空间的条件下完成
         */
        int[] inputArray = new int[]{0, 1, 1, 2, 3, 3};
        System.out.println(Arrays.toString(removeDuplicates03(inputArray)));

        /*
         * 80. 删除有序数组中的重复项 II
         * 给你一个有序数组 nums ，请你"原地"删除重复出现的元素，使每个元素最多出现两次 ，返回删除后数组的新长度。
         * 不要使用额外的数组空间，你必须在"原地"修改输入数组 并在使用 O(1) 额外空间的条件下完成。
         */
        inputArray = new int[]{1, 1, 1, 2, 2, 3};
        System.out.println(Arrays.toString(removeDuplicates04(inputArray)));
    }

    /**
     * 使用Jdk8 Stream
     *
     * @param inputList 输入数组
     * @return List<Integer> 去重后的数组
     */
    static List<Integer> removeDuplicates01(List<Integer> inputList) {
        if (inputList.isEmpty()) {
            throw new IllegalArgumentException("param is null");
        }
        if (inputList.size() < 2) {
            return inputList;
        }
        return inputList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 使用循环 O(N)
     *
     * @param inputList 输入数组
     * @return List<Integer> 去重后的数组
     */
    static List<Integer> removeDuplicates02(List<Integer> inputList) {
        List<Integer> resultList = new ArrayList<>();
        if (inputList.isEmpty()) {
            throw new IllegalArgumentException("param is null");
        }
        if (inputList.size() < 2) {
            return inputList;
        }
        Integer temp = inputList.get(0);
        resultList.add(temp);
        for (Integer i : inputList) {
            if (!i.equals(temp)) {
                resultList.add(i);
            }
            temp = i;
        }
        return resultList;
    }

    static int[] removeDuplicates03(int[] nums) {
        int length = nums.length;
        if (length < 1) {
            return new int[]{};
        }

        int index = 1;
        int result = 1;

        while (index < length) {
            if (nums[index] != nums[index - 1]) {
                nums[result] = nums[index];
                result++;
            }
            index++;
        }
        return nums;
    }

    static int[] removeDuplicates04(int[] nums) {
        int length = nums.length;
        if (length < 2) {
            return new int[]{};
        }

        int index = 2;
        int result = 2;

        while (index < length) {
            if (nums[index] != nums[result - 2]) {
                nums[result] = nums[index];
                result++;
            }
            index++;
        }
        return nums;
    }
}
