package cn.delei.java.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Fork Join Demo
 *
 * @author deleiguo
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) {
//        forkJoinSum();
        fibonacciDemo();
    }

    /**
     * 计算1~10000的和
     */
    static void forkJoinSum() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(new SumTask(1, 10000, 500));
        try {
            System.out.println("结果为: " + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void fibonacciDemo() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer result = forkJoinPool.invoke(new FibonacciTask(5));
        System.out.println("结果为: " + result);
    }

    /**
     * 两个范围内的数字求和
     */
    static class SumTask extends RecursiveTask<Integer> {
        private int threshold = 100;
        private final int start;
        private final int end;

        SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        SumTask(int start, int end, int threshold) {
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected Integer compute() {
            if ((end - start) < threshold) {
                int sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                System.out.printf("%-30s\t 执行%s-%s \t 结果为: %s\n", Thread.currentThread().getName(),
                        start, end, sum);
                return sum;
            }
            int mid = (end + start) >> 1;
            System.out.printf("%-30s\t start=%-10s\tend=%-10s\tmid=%-10s\n", "==>拆分", start, end, mid);
            // 如果计算量大于阀值，则拆分为两个任务
            SumTask leftTask = new SumTask(start, mid, threshold);
            SumTask rightTask = new SumTask(mid + 1, end, threshold);
            // 执行子任务
            leftTask.fork();
            rightTask.fork();
            // 等待子任务执行，得到结果
            return leftTask.join() + rightTask.join();
        }
    }

    /**
     * 斐波那契数列: 1、1、2、3、5、8、13、21、34、…… 公式 : F(1)=1，F(2)=1, F(n)=F(n-1)+F(n-2)(n>=3，n∈N*)
     * 从第三个数起，每个数都是前两数之和，这个数列则称为“斐波纳契数列”
     */
    static class FibonacciTask extends RecursiveTask<Integer> {
        private final int n;

        FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            FibonacciTask f1 = new FibonacciTask(n - 1);
            f1.fork(); // 异步执行子任务
            FibonacciTask f2 = new FibonacciTask(n - 2);
            return f2.compute() + f1.join();//join表示f1等待f2完成并返回计算结果
        }
    }
}
