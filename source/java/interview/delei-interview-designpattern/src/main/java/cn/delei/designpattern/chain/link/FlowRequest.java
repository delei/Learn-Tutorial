package cn.delei.designpattern.chain.link;

/**
 * 需要处理的请求对象
 *
 * @author deleiguo
 */
public class FlowRequest {
    private String name;
    private int amount;

    public FlowRequest(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
