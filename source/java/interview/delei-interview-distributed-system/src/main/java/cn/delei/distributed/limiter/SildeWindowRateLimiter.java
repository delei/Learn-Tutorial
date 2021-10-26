package cn.delei.distributed.limiter;

import cn.delei.util.PrintUtil;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 滑动窗口限流简单实现
 *
 * @author deleiguo
 */
public class SildeWindowRateLimiter {
    /**
     * 窗口大小，单位为毫秒
     */
    private int windowSize;
    /**
     * 窗口内限流大小
     */
    private int limit;
    /**
     * 切分小窗口的数目大小
     */
    private int splitNum;
    /**
     * 每个小窗口的计数数组
     */
    private AtomicInteger[] counters;
    /**
     * 当前小窗口计数器的索引
     */
    private int index;
    /**
     * 窗口开始时间
     */
    private long startTime;

    private SildeWindowRateLimiter() {
    }

    public SildeWindowRateLimiter(int windowSize, int limit, int splitNum) {
        this.limit = limit;
        this.windowSize = windowSize;
        this.splitNum = splitNum;
        counters = new AtomicInteger[splitNum];
        index = 0;
        startTime = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // 计算滑动小窗口的数量
        long windowsNum = Math.max(now - windowSize - startTime, 0) / (windowSize / splitNum);
        // 滑动窗口
        slideWindow(windowsNum);

        int count = 0;
        for (int i = 0; i < splitNum; i++) {
            count += counters[i].get();
        }
        if (count >= limit) {
            return false;
        } else {
            counters[index].addAndGet(1);
            return true;
        }
    }

    /**
     * 滑动窗口
     *
     * @param windowsNum
     */
    private void slideWindow(long windowsNum) {
        if (windowsNum == 0)
            return;
        long slideNum = Math.min(windowsNum, splitNum);
        for (int i = 0; i < slideNum; i++) {
            index = (index + 1) % splitNum;
            counters[index] = new AtomicInteger(0);
        }
        // 更新滑动窗口时间
        startTime = startTime + windowsNum * (windowSize / splitNum);
    }

    public static void main(String[] args) throws Exception {
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

}
