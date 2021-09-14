package cn.delei.designpattern.delegate;

import java.util.HashMap;
import java.util.Map;

/**
 * 委派者角色：团队Leader
 *
 * @author deleiguo
 */
public class TeamLeader implements Employee {
    private Map<String, Employee> members = new HashMap<>();

    public TeamLeader() {
        members.put("CODE", new EmployeeA());
        members.put("UI", new EmployeeB());
    }

    @Override
    public void work(String task) {
        if (!members.containsKey(task)) {
            System.out.println("这个任务" + task + "超出团队的能力范围");
            return;
        }
        members.get(task).work(task);
    }
}
