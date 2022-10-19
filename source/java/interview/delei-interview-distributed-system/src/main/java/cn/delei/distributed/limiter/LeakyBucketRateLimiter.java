package cn.delei.distributed.limiter;

/**
 * 漏桶限流
 *
 * @author deleiguo
 */
public class LeakyBucketRateLimiter implements IRateLimiter {
    /**
     * 漏桶的容量
     */
    private final int capacity;
    /**
     * 漏出速率(每秒固定的通过数量)
     */
    private final int rate;
    /**
     * 剩余水量
     */
    private long leftWater;
    /**
     * 上次注入时间
     */
    private long timeStamp = System.currentTimeMillis();

    public LeakyBucketRateLimiter(int rate, int capacity) {
        this.capacity = capacity;
        this.rate = rate;
    }

    @Override
    public synchronized boolean tryAcquire() {
        // 计算剩余水量
        long now = System.currentTimeMillis();
        // 计算时间差
        long timeGap = (now - timeStamp) / 1000;
        // 计算新的剩余水量
        leftWater = Math.max(0, leftWater - timeGap * rate);
        timeStamp = now;

        // 如果未满，则放行；否则限流
        if (leftWater < capacity) {
            leftWater += 1;
            return true;
        }
        return false;
    }
}
