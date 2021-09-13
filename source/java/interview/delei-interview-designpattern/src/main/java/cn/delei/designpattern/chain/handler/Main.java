package cn.delei.designpattern.chain.handler;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        // 构建处理链
        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(new ManagerHandler());
        handlerChain.addHandler(new FinanceHandler());

        PrintUtil.printDivider();
        handlerChain.process(new Request("办公费用", -10));
        PrintUtil.printDivider();
        handlerChain.process(new Request("住宿费用", 200));
        PrintUtil.printDivider();
        handlerChain.process(new Request("出差费用", 20));
    }

}
