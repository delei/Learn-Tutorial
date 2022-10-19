package cn.delei.distributed.limiter;

public abstract class CustomRateLimiter {
    /**
     * 尝试获取流量
     *
     * @return true 表示当前流量可以放行，否则表示拒绝
     */
    protected abstract boolean tryAcquire();
}
