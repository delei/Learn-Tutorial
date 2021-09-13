package cn.delei.designpattern.chain.handler;

/**
 * 具体处理：主管审批
 *
 * @author deleiguo
 */
public class ManagerHandler implements RequestHandler {
    @Override
    public boolean process(Request request) {
        if (request.getAmount() < 0) {
            System.out.println(ManagerHandler.class.getName() + ": reject");
            return false;
        }
        System.out.println(ManagerHandler.class.getName() + ": pass");
        return true;
    }
}
