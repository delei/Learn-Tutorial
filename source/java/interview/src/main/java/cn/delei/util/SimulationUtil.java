package cn.delei.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 模拟工具类
 *
 * @author deleiguo
 */
public class SimulationUtil {

    /**
     * 模拟运行秒数
     *
     * @param second
     * @throws Exception
     */
    public static void runtime(long second) throws Exception {
        runtime(second, ChronoUnit.SECONDS);
    }

    /**
     * 模拟运行一段时间
     *
     * @param runTime 运行时间
     * @param unit    时间单位
     * @throws Exception
     */
    public static void runtime(long runTime, ChronoUnit unit) throws Exception {
        if (runTime < 1) {
            throw new IllegalArgumentException("second must more than 1");
        }
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plus(runTime, unit);
        while (LocalDateTime.now().isBefore(end)) {
        }
    }
}
