package cn.delei.designpattern.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

    private List<OrderCommand> commandList = new ArrayList<>();


    public void addCommand(OrderCommand command) {
        commandList.add(command);
    }

    public void executeCommand() {
        commandList.forEach(OrderCommand::execute);
        commandList.clear();
    }
}
