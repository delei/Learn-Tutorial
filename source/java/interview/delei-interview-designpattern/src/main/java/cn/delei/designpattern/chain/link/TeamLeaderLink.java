package cn.delei.designpattern.chain.link;

/**
 * 具体处理：团队领导审批
 *
 * @author deleiguo
 */
public class TeamLeaderLink extends HandlerLink {
    @Override
    public boolean doHandler(FlowRequest request) {
        if (request.getAmount() < 0) {
            System.out.println(TeamLeaderLink.class.getName() + ": reject");
            return false;
        }
        System.out.println(TeamLeaderLink.class.getName() + ": pass");
        HandlerLink next = super.next();
        if (null == next) {
            return true;
        }
        return next.doHandler(request);
    }
}
