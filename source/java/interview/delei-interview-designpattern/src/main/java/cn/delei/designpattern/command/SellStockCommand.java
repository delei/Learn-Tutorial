package cn.delei.designpattern.command;

/**
 * 接口实现：卖出命令
 *
 * @author deleigo
 */
public class SellStockCommand implements OrderCommand {

    private Stock stock;

    public SellStockCommand(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void execute() {
        stock.sell();
    }
}
