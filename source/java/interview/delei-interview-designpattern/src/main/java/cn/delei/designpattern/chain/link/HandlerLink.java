package cn.delei.designpattern.chain.link;

/**
 * 链路
 *
 * @author deleiguo
 */
public abstract class HandlerLink {

    /**
     * next 下一个具体处理引用
     */
    private HandlerLink next;

    public HandlerLink next() {
        return next;
    }

    public HandlerLink addNext(HandlerLink next) {
        this.next = next;
        return this;
    }

    public abstract boolean doHandler(FlowRequest request);
}
