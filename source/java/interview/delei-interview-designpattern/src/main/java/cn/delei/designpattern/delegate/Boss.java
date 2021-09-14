package cn.delei.designpattern.delegate;

/**
 * 发放任务者: 老板
 *
 * @author deleiguo
 */
public class Boss {
    public void command(String task, TeamLeader leader) {
        leader.work(task);
    }
}
