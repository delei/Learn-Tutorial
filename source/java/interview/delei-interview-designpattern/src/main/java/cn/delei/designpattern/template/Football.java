package cn.delei.designpattern.template;

/**
 * 子类：足球
 *
 * @author deleiguo
 */
public class Football extends AbstractGame {

    public Football() {
        super("Football");
    }

    @Override
    void init() {
        System.out.println(Football.class.getName() + ": init()");
    }

    @Override
    void play() {
        System.out.println(Football.class.getName() + ": play()");
    }

    @Override
    void quit() {
        System.out.println(Football.class.getName() + ": quit()");
    }
}
