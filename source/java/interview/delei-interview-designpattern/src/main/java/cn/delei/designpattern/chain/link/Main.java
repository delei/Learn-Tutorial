package cn.delei.designpattern.chain.link;

import cn.delei.util.PrintUtil;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        // 构建处理链
        HandlerLink handlerLink = new TeamLeaderLink().addNext(new DeptLeaderLink());
        
        PrintUtil.printDivider();
        handlerLink.doHandler(new FlowRequest("办公费用", -10));
        PrintUtil.printDivider();
        handlerLink.doHandler(new FlowRequest("住宿费用", 200));
        PrintUtil.printDivider();
        handlerLink.doHandler(new FlowRequest("出差费用", 20));

    }

}
