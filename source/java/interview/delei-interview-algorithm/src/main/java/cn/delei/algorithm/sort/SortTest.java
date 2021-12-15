package cn.delei.algorithm.sort;

import cn.delei.util.PrintUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 排序算法测试
 *
 * @author deleguo
 */
public class SortTest {

    private static StopWatch stopWatch;
    private static boolean isPrint = true;
    private static final Map<String, IArraySort> algorithm = new LinkedHashMap<>(32);

    public static void main(String[] args) {
        isPrint = false;
        initAlgorithm();
        // 100
        test(100);
        // 1000
        test(1000);
        // 10000
        test(10000);
        // 100000
        //test(100000);

    }

    private static void initAlgorithm() {
        algorithm.put("BubbleSort", new BubbleSort());
        algorithm.put("InsertSort", new InsertSort());
        algorithm.put("MergeSort", new MergeSort());
        algorithm.put("SelectionSort", new SelectionSort());
        algorithm.put("ShellSort", new ShellSort());
        algorithm.put("QuickSort", new QuickSort());
        algorithm.put("HeapSort", new HeapSort());
        algorithm.put("CountingSort", new CountingSort());
        algorithm.put("BucketSort", new BucketSort());
    }

    private static void test(int length) {
        stopWatch = new StopWatch("数据量-" + length);

        int[] data = initData(length);
        if (isPrint) {
            System.out.println(Arrays.toString(data));
        }
        algorithm.forEach((k, v) -> {
            stopWatch.start(k);
            int[] resultArray = new SortStrategy(v).sort(data);
            if (isPrint) {
                PrintUtil.printTitle(k);
                System.out.println(Arrays.toString(resultArray));
            }
            stopWatch.stop();
        });

        PrintUtil.printTitle();
        System.out.println(stopWatch.prettyPrint());
    }

    private static int[] initData(int length) {
        if (length < 0) {
            return null;
        }
        int[] data = new int[length];
        for (int i = 0; i < length; i++) {
            data[i] = RandomUtil.randomInt(10, 99999);
        }
        return data;
    }
}
