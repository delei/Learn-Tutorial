package cn.delei.designpattern.chain.handler;

/**
 * 抽象处理接口
 *
 * @author deleiguo
 */
public interface RequestHandler {
    /**
     * 处理
     * <p>
     * 如果为true，处理成功
     * 如果为false，拒绝
     * </p>
     *
     * @param request 请求对象
     * @return boolean 处理结果
     */
    boolean process(Request request);
}
