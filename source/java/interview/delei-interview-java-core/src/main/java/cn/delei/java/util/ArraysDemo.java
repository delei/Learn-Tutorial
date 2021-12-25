package cn.delei.java.util;

import cn.delei.pojo.Student;
import cn.delei.util.PrintUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.Comparator;

/**
 * java.util.Arrays
 *
 * @author deleiguo
 */
public class ArraysDemo {
    
    public static void main(String[] args) {
        PrintUtil.printTitle("基础类型sort");
        basicTypeSort();
        PrintUtil.printDivider();

        PrintUtil.printTitle("Object类型sort");
        objectSort();

        PrintUtil.printTitle("Object类型sort");
        parallelSort();

        PrintUtil.printTitle("binarySearch二分查找");
        binarySearch();
    }

    static void basicTypeSort() {
        StopWatch stopWatch = new StopWatch("基础类型sort");
        int[] dataArray = getDataArray(100);
        stopWatch.start();
        Arrays.sort(dataArray);
        stopWatch.stop();

        dataArray = getDataArray(1000);
        stopWatch.start();
        Arrays.sort(dataArray);
        stopWatch.stop();

        dataArray = getDataArray(10000);
        stopWatch.start();
        Arrays.sort(dataArray);
        stopWatch.stop();
        System.out.print(stopWatch.prettyPrint());
    }

    static void objectSort() {
        /*
         * -Djava.util.Arrays.useLegacyMergeSort=false
         * 001209858 ns/001554098 ns/012679749 ns
         *
         * -Djava.util.Arrays.useLegacyMergeSort=true
         * 000811874 ns/001298883 ns/006404945 ns
         */

        Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return o1.getAge() - o2.getAge();
            }
        };
        StopWatch stopWatch = new StopWatch("Object类型sort");
        Student[] dataArray = getObjectArray(100);
        stopWatch.start();
        Arrays.sort(dataArray, comparator);
        stopWatch.stop();

        dataArray = getObjectArray(1000);
        stopWatch.start();
        Arrays.sort(dataArray, comparator);
        stopWatch.stop();

        dataArray = getObjectArray(10000);
        stopWatch.start();
        Arrays.sort(dataArray, comparator);
        stopWatch.stop();

        System.out.print(stopWatch.prettyPrint());
    }

    static void parallelSort() {
        StopWatch stopWatch = new StopWatch("并行sort");
        int[] dataArray = getDataArray(100);
        stopWatch.start();
        Arrays.parallelSort(dataArray);
        stopWatch.stop();

        dataArray = getDataArray(1000);
        stopWatch.start();
        Arrays.parallelSort(dataArray);
        stopWatch.stop();

        dataArray = getDataArray(10000);
        stopWatch.start();
        Arrays.parallelSort(dataArray);
        stopWatch.stop();
        System.out.print(stopWatch.prettyPrint());
    }

    static void binarySearch() {
        int capacity = 21;
        int[] dataArray = getDataArray(21);
        dataArray[(capacity - 1)] = 11;
        System.out.print(Arrays.toString(dataArray));
        // 未排序
        PrintUtil.printDivider("未排序");
        System.out.println(Arrays.binarySearch(dataArray, 11));

        // 排序
        PrintUtil.printDivider("升序");
        Arrays.sort(dataArray);
        System.out.println(Arrays.binarySearch(dataArray, 11));
    }

    static int[] getDataArray(int capacity) {
        int[] data = new int[capacity];
        int i = -1;
        int min = 10;
        int max = 100;
        while (i < capacity - 1) {
            i++;
            data[i] = RandomUtil.randomInt(min, max);
        }
        return data;
    }


    static Student[] getObjectArray(int capacity) {
        Student[] data = new Student[capacity];
        int i = -1;
        int min = 10;
        int max = 100;
        while (i < capacity - 1) {
            i++;
            data[i] = new Student("Stu-" + i, RandomUtil.randomInt(min, max));
        }
        return data;
    }
}
