package cn.delei.java.lang.proxy;

/**
 * 代理模式：普通类
 *
 * @author deleiguo
 */
public class SubjectGeneral {
    public String save(String param) {
        System.out.println("[SubjectGeneral save]param=" + param);
        return param + "-save";
    }

    public String update(String param) {
        System.out.println("[SubjectGeneral update]param=" + param);
        return param + "-update";
    }
}
