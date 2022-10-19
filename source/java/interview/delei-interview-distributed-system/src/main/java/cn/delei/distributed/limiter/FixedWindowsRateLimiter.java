package cn.delei.distributed.limiter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 固定窗口限流简单实现
 *
 * @author deleiguo
 */
public class FixedWindowsRateLimiter implements IRateLimiter {
    /**
     * 初始时间
     */
    private long startTime;
    /**
     * 时间窗口限制，单位毫秒
     */
    private int windowSize;
    /**
     * 窗口内限流阀值
     */
    private int threshold;

    private static final AtomicInteger ZERO = new AtomicInteger(0);

    /**
     * 计数器
     */
    private AtomicInteger counter;

    private FixedWindowsRateLimiter() {
    }

    public FixedWindowsRateLimiter(int windowSize, int threshold) {
        this.startTime = System.currentTimeMillis();
        this.windowSize = windowSize;
        this.threshold = threshold;
        this.counter = ZERO;
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        int newCount = this.counter.addAndGet(1);
        // 判断是否在时间窗口内
        if ((now - this.startTime) < this.windowSize) {
            // 判断是否超过限流阀值
            if (newCount <= this.threshold) {
                return true;
            }
            return false;
        } else {
            // 超过时间窗口，重置
            this.startTime = now;
            this.counter.set(0);
            return true;
        }
    }
    
}
