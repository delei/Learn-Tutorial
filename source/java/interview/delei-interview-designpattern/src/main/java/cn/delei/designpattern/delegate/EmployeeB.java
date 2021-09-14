package cn.delei.designpattern.delegate;

/**
 * 具体任务实现：员工B
 *
 * @author deleiguo
 */
public class EmployeeB implements Employee {
    private String goodAt = "UI";

    @Override
    public void work(String task) {
        System.out.println("我是员工B，我擅长" + goodAt + ",现在开始做" + task + "工作");
    }
}
