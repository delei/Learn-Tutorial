package cn.delei.designpattern.chain.handler;

/**
 * 需要处理的请求对象
 *
 * @author deleiguo
 */
public class Request {
    private String name;
    private int amount;

    public Request(String name, int amount) {
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
