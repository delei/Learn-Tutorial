package cn.delei.java.basic;

public class ExceptionDemo {

    private int times;

    public ExceptionDemo(int times) {
        this.times = times;
    }

    public void newObject() {
        long l = System.nanoTime();
        for (int i = 0; i < times; i++) {
            new Object();
        }
        System.out.printf("%-20s", "建立对象：" + (System.nanoTime() - l));
    }

    public void newException() {
        long l = System.nanoTime();
        for (int i = 0; i < times; i++) {
            new Exception();
        }
        System.out.printf("%-20s", "建立异常对象：" + (System.nanoTime() - l));
    }

    public void catchException() {
        long l = System.nanoTime();
        for (int i = 0; i < times; i++) {
            try {
                throw new Exception();
            } catch (Exception e) {
            }
        }
        System.out.printf("%-20s", "建立、抛出并接住异常对象：" + (System.nanoTime() - l));
    }

    public static void main(String[] args) {
        // 创建10000次的耗时
        ExceptionDemo test = new ExceptionDemo(10000);
        test.newObject();
        test.newException();
        test.catchException();
    }
}
