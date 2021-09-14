package cn.delei.designpattern.delegate;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        Boss boss = new Boss();
        TeamLeader teamLeader = new TeamLeader();

        boss.command("CODE", teamLeader);
        boss.command("UI", teamLeader);
        boss.command("TEST", teamLeader);
    }

}
