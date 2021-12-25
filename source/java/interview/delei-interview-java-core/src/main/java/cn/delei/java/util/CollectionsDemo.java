package cn.delei.java.util;

import cn.delei.util.PrintUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * java.util.Collections
 *
 * @author deleiguo
 */
public class CollectionsDemo {
    public static void main(String[] args) {
        PrintUtil.printTitle("sort排序");
        sort();

        PrintUtil.printDivider();

        PrintUtil.printTitle("binarySearch二分查找");
        binarySearch();

        PrintUtil.printDivider();

        PrintUtil.printTitle("shuffle洗牌/混排");
        shuffle();
    }

    static void sort() {
        List<Integer> srcList = getData(8);

        List<Integer> dataList = new ArrayList<>();
        Collections.addAll(dataList, new Integer[srcList.size()]);
        PrintUtil.printDivider("普通sort");
        Collections.copy(dataList, srcList);
        Collections.sort(dataList);
        System.out.println(dataList.toString());

        PrintUtil.printDivider("自定义排序");
        dataList = new ArrayList<>();
        Collections.addAll(dataList, new Integer[srcList.size()]);
        Collections.copy(dataList, srcList);
        Collections.sort(dataList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println(dataList.toString());
    }

    static void binarySearch() {
        List<Integer> srcList = getData(11);
        srcList.add(11);

        // 未排序
        PrintUtil.printDivider("未排序");
        System.out.println(Collections.binarySearch(srcList, 11));

        // 排序
        PrintUtil.printDivider("升序");
        Collections.sort(srcList);
        System.out.println(srcList.toString());
        System.out.println(Collections.binarySearch(srcList, 11));
        PrintUtil.printDivider("降序");
        Collections.reverse(srcList);
        System.out.println(srcList.toString());
        System.out.println(Collections.binarySearch(srcList, 11));
    }

    static void shuffle() {
        List<Integer> dataList = getData(10);
        //Collections.sort(dataList);
        System.out.println(dataList.toString());
        Collections.shuffle(dataList);
        System.out.println(dataList.toString());
    }

    static void reverse() {
        List<Integer> dataList = getData(8);
        Collections.sort(dataList);
        Collections.reverse(dataList);
    }

    static List<Integer> getData(int capacity) {
        List<Integer> dataList = new ArrayList<Integer>(capacity);
        int i = 0;
        int min = 10;
        int max = 100;
        while (i < capacity) {
            i++;
            dataList.add(RandomUtil.randomInt(min, max));
        }
        return dataList;
    }

}
