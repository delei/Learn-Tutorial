package cn.delei.designpattern.chain.handler;

/**
 * 具体处理：财务审批
 *
 * @author deleiguo
 */
public class FinanceHandler implements RequestHandler {
    @Override
    public boolean process(Request request) {
        if (request.getAmount() > 100) {
            System.out.println(FinanceHandler.class.getName() + ": reject");
            return false;
        }
        System.out.println(FinanceHandler.class.getName() + ": pass");
        return true;
    }
}
