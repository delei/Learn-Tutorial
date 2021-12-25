package cn.delei.util;

import cn.hutool.core.util.StrUtil;

/**
 * 打印相关工具类
 *
 * @author deleiguo
 */
public class PrintUtil {
    private static final int PRINT_MAX_LENGTH = 100;
    private static final int PRINT_MIN_LENGTH = 15;

    /**
     * 打印标题
     */
    public static void printTitle() {
        printDivider();
    }

    /**
     * 打印子分割线
     */
    public static void printDivider() {
        System.out.println("\n----------------------------------------------------------------\n");
    }

    public static void printDivider(String title) {
        if (StrUtil.isNotBlank(title)) {
            System.out.printf("|====> %-5s \n", title);
        } else {
            printDivider();
        }
    }

    /**
     * 打印标题分割线
     *
     * @param title 标题
     */
    public static void printTitle(String title) {
        if (StrUtil.isNotBlank(title)) {
            System.out.printf("+ ------------------[ %-10s ]------------------ +\n", caclu(title));
        } else {
            printTitle();
        }
    }

    /**
     * 打印Free Memory
     *
     * @param memory
     */
    public static void printlnMemory(int memory) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.freeMemory() / memory + "M(free)/" + runtime.totalMemory() / memory + "M(total)");
    }

    private static String caclu(String str) {
        int length = str.length();
        StringBuilder prefixBuilder = new StringBuilder();
        StringBuilder suffixBuilder = new StringBuilder();
        if (length <= PRINT_MIN_LENGTH) {
            int i = 0;
            do {
                prefixBuilder.append(" ");
                suffixBuilder.append(" ");
                i++;
            } while (i < (PRINT_MIN_LENGTH - length) / 2);
        }
        return prefixBuilder.toString() + str + suffixBuilder.toString();
    }

    public static void main(String[] args) {
        PrintUtil.printDivider("aaaa");
        PrintUtil.printTitle("bbbbbbbbbbb");
        PrintUtil.printDivider("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }
}
