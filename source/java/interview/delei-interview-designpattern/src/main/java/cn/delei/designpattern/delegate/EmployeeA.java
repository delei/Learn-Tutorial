package cn.delei.designpattern.delegate;

/**
 * 具体任务实现：员工A
 *
 * @author deleiguo
 */
public class EmployeeA implements Employee {
    private String goodAt = "CODE";

    @Override
    public void work(String task) {
        System.out.println("我是员工A，我擅长" + goodAt + ",现在开始做" + task + "工作");
    }
}
