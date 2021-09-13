package cn.delei.designpattern.template;

/**
 * 抽象顶级类：游戏
 *
 * @author deleiguo
 */
public abstract class AbstractGame {
    protected String name;

    public AbstractGame(String name) {
        this.name = name;
    }

    abstract void init();

    abstract void play();

    abstract void quit();

    /**
     * 顶级逻辑方法
     */
    public void start() {
        init();
        play();
        quit();
    }
}
