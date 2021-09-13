package cn.delei.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合类
 *
 * @author deleiguo
 */
public class Composite extends Component {

    private List<Component> child;

    public Composite(String name) {
        super(name);
        this.child = new ArrayList<>();
    }

    @Override
    public void print(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("--");
        }

        System.out.println("Composite:" + name);
        for (Component component : child) {
            component.print(level + 1);
        }
    }

    @Override
    public void add(Component component) {
        child.add(component);
    }

    @Override
    public void remove(Component component) {
        child.remove(component);
    }
}
