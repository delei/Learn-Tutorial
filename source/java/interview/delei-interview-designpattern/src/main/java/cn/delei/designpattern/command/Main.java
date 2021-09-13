package cn.delei.designpattern.command;

/**
 * 测试Main方法
 *
 * @author deleiguo
 */
public class Main {

    public static void main(String[] args) {
        Stock stock = new Stock("AAA", 100);
        OrderCommand buyCommand = new BuyStockCommand(stock);
        OrderCommand sellCommand = new SellStockCommand(stock);
        Invoker invoker = new Invoker();
        invoker.addCommand(buyCommand);
        invoker.addCommand(sellCommand);
        invoker.executeCommand();
    }

}
