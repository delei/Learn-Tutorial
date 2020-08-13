package cn.delei.java.basic;

import cn.hutool.core.date.StopWatch;

public class ExceptionDemo {

    private int times;

    public ExceptionDemo(int times) {
        this.times = times;
    }

    public void newObject() {
        for (int i = 0; i < times; i++) {
            new Object();
        }
    }

    public void newException() {
        for (int i = 0; i < times; i++) {
            new Exception();
        }
    }

    public void catchException() {
        for (int i = 0; i < times; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        // 创建10000次的耗时
        ExceptionDemo test = new ExceptionDemo(10000);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("建立对象");
        test.newObject();
        stopWatch.stop();

        stopWatch.start("建立异常对象");
        test.newException();
        stopWatch.stop();

        stopWatch.start("建立、抛出并接住异常对象");
        test.catchException();
        stopWatch.stop();
        
        System.out.println(stopWatch.prettyPrint());
    }
}
