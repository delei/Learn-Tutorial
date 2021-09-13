package cn.delei.designpattern.template;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        AbstractGame football = new Football();
        football.start();
        PrintUtil.printDivider();
        AbstractGame basketball = new Basketball();
        basketball.start();
    }

}
