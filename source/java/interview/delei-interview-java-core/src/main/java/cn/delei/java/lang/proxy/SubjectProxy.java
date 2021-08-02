package cn.delei.java.lang.proxy;

/**
 * JDK 静态代理
 *
 * @author deleiguo
 */
public class SubjectProxy implements SubjectInterface {
    /**
     * 目标对象
     */
    private SubjectInterface targetObject;

    public SubjectProxy(SubjectInterface targetObject) {
        this.targetObject = targetObject;
    }

    void before(String methodName) {
        System.out.println("==> Proxy before:" + methodName);
    }

    void after(String methodName) {
        System.out.println("==> Proxy after:" + methodName);
    }

    /**
     * 代码增强
     * @param param 参数
     * @return String 结果
     */
    @Override
    public String save(String param) {
        before("save");
        String result = targetObject.save(param);
        after("save");
        return result;
    }

    @Override
    public String update(String param) {
        before("update");
        String result = targetObject.update(param);
        after("update");
        return result;
    }
}
