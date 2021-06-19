package cn.delei.java.lang.proxy;

/**
 * 代理模式：真正实现逻辑的目标类
 *
 * @author deleiguo
 */
public class SubjectImpl implements SubjectInterface {
    @Override
    public String save(String param) {
        System.out.println("[save]param=" + param);
        return param + "-save";
    }

    @Override
    public String update(String param) {
        System.out.println("[update]param=" + param);
        return param + "-update";
    }
}
