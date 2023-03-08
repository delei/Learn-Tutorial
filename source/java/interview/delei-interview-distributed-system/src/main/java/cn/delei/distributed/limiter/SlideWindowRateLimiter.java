package cn.delei.distributed.limiter;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 滑动窗口限流简单实现
 *
 * @author deleiguo
 */
public class SlideWindowRateLimiter implements IRateLimiter {
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

    private SlideWindowRateLimiter() {
    }

    public SlideWindowRateLimiter(int windowSize, int limit, int splitNum) {
        this.limit = limit;
        this.windowSize = windowSize;
        this.splitNum = splitNum;
        counters = new AtomicInteger[splitNum];
        index = 0;
        startTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();

        // 计算滑动小窗口的数量
        long windowsNum = Math.max(now - windowSize - startTime, 0) / (windowSize / splitNum);
        // 滑动窗口
        slideWindow(windowsNum);

        int count = 0;
        // 所有小窗口的计数总和
        for (int i = 0; i < splitNum; i++) {
            count += counters[i].get();
        }

        // 如果计数总和超过阀值不可放行
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
        if (windowsNum == 0) {
            return;
        }
        long slideNum = Math.min(windowsNum, splitNum);
        for (int i = 0; i < slideNum; i++) {
            index = (index + 1) % splitNum;
            counters[index] = new AtomicInteger(0);
        }
        // 更新滑动窗口时间
        startTime = startTime + windowsNum * (windowSize / splitNum);
    }

}
