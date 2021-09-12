package cn.delei.designpattern.facade;

/**
 * 子系统：TV
 *
 * @author deleiguo
 */
public class TVSystem {
    public void turnOnTv() {
        System.out.println(TVSystem.class.getName() + ": turnOn TV");
    }

    public void startWatch() {
        System.out.println(TVSystem.class.getName() + ": start watch");
    }
}
