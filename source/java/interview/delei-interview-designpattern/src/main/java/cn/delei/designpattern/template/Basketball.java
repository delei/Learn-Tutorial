package cn.delei.designpattern.template;

/**
 * 子类：篮球
 *
 * @author deleiguo
 */
public class Basketball extends AbstractGame {

    public Basketball() {
        super("Basketball");
    }

    @Override
    void init() {
        System.out.println(Basketball.class.getName() + ": init()");
    }

    @Override
    void play() {
        System.out.println(Basketball.class.getName() + ": play()");
    }

    @Override
    void quit() {
        System.out.println(Basketball.class.getName() + ": quit()");
    }
}
