package cn.delei.designpattern.chain.link;

/**
 * 具体处理：部门领导审批
 *
 * @author deleiguo
 */
public class DeptLeaderLink extends HandlerLink {
    @Override
    public boolean doHandler(FlowRequest request) {
        if (request.getAmount() > 100) {
            System.out.println(DeptLeaderLink.class.getName() + ": reject");
            return false;
        }
        System.out.println(DeptLeaderLink.class.getName() + ": pass");
        HandlerLink next = super.next();
        if (null == next) {
            // 如果是最后一个返回结果
            return true;
        }
        // 如果不是最后一个，继续执行下一个处理器
        return next.doHandler(request);
    }
}
