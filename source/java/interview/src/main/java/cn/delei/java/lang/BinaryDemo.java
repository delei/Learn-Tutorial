package cn.delei.java.lang;

import cn.delei.PrintUtil;

/**
 * 字节、位运算等 Demo
 *
 * @author deleiguo
 */
public class BinaryDemo {
    public static void main(String[] args) {
//        binaryDemo(666);
//        bitwiseOperator();
        useCase();
    }


    /**
     * 进制转换
     *
     * @param value 需要转换的十进制
     */
    static void binaryDemo(int value) {
        PrintUtil.printDivider("十进制 to 其他进制");
        String str = Integer.toBinaryString(value);
        String octalString = Integer.toOctalString(value);
        String hexString = Integer.toHexString(value);
        System.out.printf("%s转二进制\t结果: %s\n", value, str);
        System.out.printf("%s转八进制\t结果: %s\n", value, octalString);
        System.out.printf("%s转十六进制\t结果: %s\n", value, hexString);

        PrintUtil.printDivider("其他进制 to 十进制");
        System.out.printf("%s\t二进制转十进制\t结果: %s\n", str, Integer.valueOf(str, 2).toString());
        System.out.printf("%s\t八进制转十进制结果: \t%s\n", octalString, Integer.valueOf(octalString, 8).toString());
        System.out.printf("%s\t十六进制转十进制结果: \t%s\n", hexString, Integer.valueOf(hexString, 16).toString());
    }

    /**
     * 位运算
     */
    static void bitwiseOperator() {
        PrintUtil.printDivider("位运算");
        int i = 5;
        int j = 6;
        System.out.printf("i=%s 转二进制\t结果: %s\n", i, Integer.toBinaryString(i));
        System.out.printf("j=%s 转二进制\t结果: %s\n", j, Integer.toBinaryString(j));
        PrintUtil.printDivider();
        // &：按位与(同为1则1，否则为0)
        System.out.printf("& 运算\t二进制结果: %s\n", Integer.toBinaryString(i & j));
        System.out.printf("& 运算\t十进制结果: %s\n", (i & j));
        PrintUtil.printDivider();
        // |：按位或(同为0则0，否则为1)
        System.out.printf("| 运算\t二进制结果: %s\n", Integer.toBinaryString(i | j));
        System.out.printf("| 运算\t十进制结果: %s\n", (i | j));
        PrintUtil.printDivider();
        // ~：按位非(0为1，1为0)
        System.out.printf("~i 运算\t二进制结果: %s\n", Integer.toBinaryString(~i));
        System.out.printf("~i 运算\t十进制结果: %s\n", (~i));
        PrintUtil.printDivider();
        // ^：按位异或(相同为0，不同为1)
        System.out.printf("^ 运算\t二进制结果: %s\n", Integer.toBinaryString(i ^ j));
        System.out.printf("^ 运算\t十进制结果: %s\n", (i ^ j));
        PrintUtil.printDivider();
        // <<：按位左移(相当于左侧的值乘以2的 N次方)
        System.out.printf("i<<2 运算\t二进制结果: %s\n", Integer.toBinaryString(i << 2));
        System.out.printf("i<<2 运算\t十进制结果: %s\n", (i << 2));
        PrintUtil.printDivider();
        // >>：按位右移(相当于左侧的值除以2的 N次方)
        System.out.printf("i>>2 运算\t二进制结果: %s\n", Integer.toBinaryString(i >> 2));
        System.out.printf("i>>2 运算\t十进制结果: %s\n", (i >> 2));
        // >>>：无符号右移(相当于左侧的值除以2的 N次方,高位补0)
        System.out.printf("i>>> 运算\t二进制结果: %s\n", Integer.toBinaryString(i >>> 2));
        System.out.printf("i>>>2 运算\t十进制结果: %s\n", (i >>> 2));
    }

    /**
     * 位运算使用场景
     */
    static void useCase() {
        PrintUtil.printDivider("位运算使用场景");
        int i = 4;
        int j = 5;
        System.out.printf("i=%s 转二进制\t结果: %s\n", i, Integer.toBinaryString(i));
        System.out.printf("j=%s 转二进制\t结果: %s\n", j, Integer.toBinaryString(j));
        PrintUtil.printDivider();
        // 判断奇偶数
        System.out.printf("i是否为偶数: %s\n", (i & 1) == 0);
        System.out.printf("j是否为偶数: %s\n", (j & 1) == 0);
        PrintUtil.printDivider();
        // 交换值(不需要中间变量)
        i = i ^ j;
        j = i ^ j;
        i = i ^ j;
        System.out.printf("交换结果: i=%s\tj=%s\n", i, j);
    }
}
