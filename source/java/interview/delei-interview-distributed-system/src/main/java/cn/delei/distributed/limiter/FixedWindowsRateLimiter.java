package cn.delei.distributed.limiter;

import cn.delei.util.PrintUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 固定窗口限流简单实现
 *
 * @author deleiguo
 */
public class FixedWindowsRateLimiter {
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

    public boolean tryAcquire() {
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

    public static void main(String[] args) throws Exception {
        int win = 3000;
        int threshold = 20;
        // 时间窗口为1000毫秒，阀值为20
        FixedWindowsRateLimiter counterLimiter = new FixedWindowsRateLimiter(win, threshold);

        int count = 0;
        int size = 50;
        PrintUtil.printTitle("01-模拟" + size + "次请求");
        // 模拟请求，看多少能通过
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
}
