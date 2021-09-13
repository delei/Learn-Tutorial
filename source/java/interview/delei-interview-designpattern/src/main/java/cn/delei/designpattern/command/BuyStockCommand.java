package cn.delei.designpattern.command;

/**
 * 接口实现：买入命令
 *
 * @author deleiguo
 */
public class BuyStockCommand implements OrderCommand {

    private Stock stock;

    public BuyStockCommand(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.buy();
    }
}
