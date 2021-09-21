package cn.delei.util;

import cn.hutool.core.util.StrUtil;

/**
 * 打印相关工具类
 *
 * @author deleiguo
 */
public class PrintUtil {
    private static final int PRINT_MAX_LENGTH = 100;
    private static final int PRINT_MIN_LENGTH = 20;

    /**
     * 打印长分割线
     */
    public static void printTitle() {
        System.out.println("<---------------------------------------------------------------->");
    }

    /**
     * 打印子分割线
     */
    public static void printDivider() {
        System.out.println("------< *************************************************** >------");
    }

    public static void printDivider(String title) {
        if (StrUtil.isNotBlank(title)) {
            int length = title.length();
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
            String result = prefixBuilder.toString() + title + suffixBuilder.toString();
            System.out.printf(" <--------------------------------[\t %-15s \t]-------------------------------->\n",
                    result);
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
            System.out.printf(">------------------[\t %-15s \t]------------------<\n", title);
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

    public static void main(String[] args) {
        PrintUtil.printDivider("aaaa");
        PrintUtil.printDivider("bbbbbbbbbbb");
        PrintUtil.printDivider("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }
}
