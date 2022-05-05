package cn.delei.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 在同一时刻允许至多两个线程的同时访问，表明同步资源数为 2
 * state:0,1,2
 * <p>
 * 设置初始状态 status 为 2，当一个线程进行获取，status 减 1，该 线程释放，则 status 加 1
 *
 * @author deleiguo
 */
public class CustomTwinsLock implements Lock {

    private class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            if (count < 1) {
                throw new IllegalArgumentException();
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int s = getState();
                int newCount = s - arg;
                if (newCount < 0 || compareAndSetState(s, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int s = getState();
                int newCount = s + arg;
                if (compareAndSetState(s, newCount)) {
                    return true;
                }
            }
        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }

    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryReleaseShared(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
