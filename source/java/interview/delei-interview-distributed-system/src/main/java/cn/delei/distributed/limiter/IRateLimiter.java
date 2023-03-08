package cn.delei.distributed.limiter;

/**
 * 限流接口
 *
 * @author deleiguo
 */
public interface IRateLimiter {
    /**
     * 尝试获取流量
     *
     * @return true 表示当前流量可以放行，否则表示拒绝
     */
    boolean tryAcquire();
}
