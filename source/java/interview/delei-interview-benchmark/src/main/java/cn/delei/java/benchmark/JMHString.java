package cn.delei.java.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * JMH String 基准测试
 *
 * @author deleiguo
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHString {

    /**
     * 拼接长度
     */
    @Param(value = {"10", "100", "500", "1000"})
    private int concatLength;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHString.class.getSimpleName())
                .result(JMHBase.OUTPUT)
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    /**
     * 普通 String 拼接
     *
     * @param blackhole
     */
    @Benchmark
    public void stringConcat(Blackhole blackhole) {
        String a = "";
        for (int i = 0; i < concatLength; i++) {
            a += i;
        }
        blackhole.consume(a);
    }

    /**
     * StringBuilder#append
     *
     * @param blackhole
     */
    @Benchmark
    public void stringBuilderAppend(Blackhole blackhole) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < concatLength; i++) {
            sb.append(i);
        }
        blackhole.consume(sb.toString());
    }

    /**
     * StringBuffer#append
     *
     * @param blackhole
     */
    @Benchmark
    public void stringBufferAppend(Blackhole blackhole) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < concatLength; i++) {
            sb.append(i);
        }
        blackhole.consume(sb.toString());
    }

}
