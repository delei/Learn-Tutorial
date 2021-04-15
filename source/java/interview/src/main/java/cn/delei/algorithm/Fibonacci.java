package cn.delei.algorithm;

import cn.hutool.core.date.StopWatch;

/**
 * 斐波那契数列: 1、1、2、3、5、8、13、21、34、…… 公式 : F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)(n>=3，n∈N*)
 * <p>
 * LeetCode 70:爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 注意：给定 n 是一个正整数。
 *
 * @author deleiguo
 */
public class Fibonacci {
    private static final StopWatch stopWatch = new StopWatch();

    public static void main(String[] args) {
//        solutionDemo(5); // N比较小
//        solutionDemo(30); // N比较大
        System.out.println(solution04(19, 98));
        if (!stopWatch.isRunning()) {
            System.out.println(stopWatch.prettyPrint());
        }
    }

    static void solutionDemo(int n) {
        int result = 0;
        stopWatch.start("普通递归");
        result = solution01(n);
        System.out.printf("N=%s\tResult=%s\n", n, result);
        stopWatch.stop();
        stopWatch.start("尾部递归");
        result = solution02(n, 1, 1);
        System.out.printf("N=%s\tResult=%s\n", n, result);
        stopWatch.stop();
        stopWatch.start("For循环");
        result = solution03(n);
        System.out.printf("N=%s\tResult=%s\n", n, result);
        stopWatch.stop();
    }

    /**
     * 普通递归
     * 当N过大时，性能急剧下降
     *
     * @param n
     * @return
     */
    static int solution01(int n) {
        if (n <= 1) {
            return 1;
        }
        if (n < 3) {
            return n;
        }
        return solution01(n - 1) + solution01(n - 2);
    }

    /**
     * 尾部递归
     *
     * @param n
     * @return
     */
    static int solution02(int n, int a, int b) {
        if (n <= 1) {
            return b;
        }
        return solution02(n - 1, b, a + b);
    }

    /**
     * for循环，中间变量交替位置
     * 时间复杂度：循环执行 nn 次，每次花费常数的时间代价，故渐进时间复杂度为 O(n)
     * 空间复杂度：这里只用了常数个变量作为辅助空间，故渐进空间复杂度为 O(1)
     *
     * @param n
     * @return
     */
    static int solution03(int n) {
        // 1 1 2 3 5
        if (n <= 1) {
            return n;
        }
        int a = 0;
        int b = 0;
        int sum = 1;
        for (int i = 1; i <= n; i++) {
            a = b;
            b = sum;
            sum = a + b;
        }
        return sum;
    }

    static int solution04(int n, int k) {
        /**
         * f(n)=f(n-2)+f(n-1),+ 为拼接
         * f1=0
         * f2=1
         * f3=f1+f2=01
         * f4=f2+f3=101
         * f5=f3+f4=01101
         * f6=f4+f5=10101101
         */
        int[] leng = new int[n + 1];
        leng[0] = 0;
        leng[1] = leng[2] = 1;
        for (int i = 1; i <= n; i++) {
            leng[i] = leng[i - 2] + leng[i - 1];
        }
        if (k > leng.length) {
            return -1;
        }
        if (k <= leng[n - 2]) {
            return solution04(n - 2, k);
        } else if (k > leng[n - 2]) {
            return solution04(n - 1, k - leng[n - 2]);
        }
        return leng[k];

    }

}
