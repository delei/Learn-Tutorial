package cn.delei.distributed.limiter;

import cn.delei.util.PrintUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiteTest {

    public static void main(String[] args) throws Exception {
        // fixedTest();
        // slideTest();
        leakyBucketTest();
    }

    /**
     * 固定窗口限流
     */
    static void fixedTest() throws Exception {
        int win = 3000;
        int threshold = 20;
        // 时间窗口为1000毫秒，阀值为20
        FixedWindowsRateLimiter counterLimiter = new FixedWindowsRateLimiter(win, threshold);

        // 统计通过数量
        int count = 0;

        // 模拟请求
        int size = 50;
        PrintUtil.printTitle("01-模拟" + size + "次请求");
        for (int i = 0; i < size; i++) {
            if (counterLimiter.tryAcquire()) {
                count++;
            }
        }
        System.out.printf("模拟 %s 次请求，通过 %s ,限流 %s \n", size, count, (size - count));

        //模拟时间窗口
        Thread.sleep(win + 200);

        PrintUtil.printTitle("02-时间窗口过后，模拟" + size + "次请求");
        count = 0;
        for (int i = 0; i < size; i++) {
            if (counterLimiter.tryAcquire()) {
                count++;
            }
        }
        System.out.printf("模拟 %s 次请求，通过 %s ,限流 %s \n", size, count, (size - count));
    }

    /**
     * 滑动窗口限流
     */
    static void slideTest() throws Exception {
        int limit = 20;
        SildeWindowRateLimiter limiter = new SildeWindowRateLimiter(1000, limit, 10);

        Thread.sleep(3000);

        int count = 0;
        int failCount = 0;
        int size = 50;
        int group = 100;

        PrintUtil.printTitle("模拟100组,每次间隔150ms,50次请求");
        for (int j = 0; j < group; j++) {
            count = 0;
            for (int i = 0; i < size; i++) {
                if (limiter.tryAcquire()) {
                    count++;
                }
            }
            Thread.sleep(150);

            // 模拟请求，看多少能通过
            for (int i = 0; i < size; i++) {
                if (limiter.tryAcquire()) {
                    count++;
                }
            }
            if (count > limit) {
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                failCount++;
            }
            Thread.sleep((int) (Math.random() * 100));
        }
        System.out.printf("模拟 %s 次请求，限流失败组数 %s \n", size, failCount);
    }


    private static void leakyBucketTest() throws Exception {
        LeakyBucketRateLimiter rateLimiter = new LeakyBucketRateLimiter(20, 20);

        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        // 存储流量的队列
        // 当 tryAcquire 返回 true 时，将请求入队，然后再以固定频率从队列中取出请求进行处理
        Queue<Integer> queue = new LinkedList<>();
        // 模拟请求  不确定速率注水
        singleThread.execute(() -> {
            int count = 0;
            while (true) {
                count++;
                boolean flag = rateLimiter.tryAcquire();
                if (flag) {
                    queue.offer(count);
                    System.out.println(count + "--------流量被放行--------");
                } else {
                    System.out.println(count + "流量被限制");
                }
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 模拟处理请求 固定速率漏水
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (!queue.isEmpty()) {
                System.out.println(queue.poll() + "被处理");
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        // 保证主线程不会退出
        // while (true) {
        //     Thread.sleep(10000);
        // }
    }

}
