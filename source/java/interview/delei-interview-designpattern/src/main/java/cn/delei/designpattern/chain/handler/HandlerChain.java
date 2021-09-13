package cn.delei.designpattern.chain.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理链
 *
 * @author deleiguo
 */
public class HandlerChain {
    /**
     * 链路上的处理器集合，处理顺序和加入集合顺序一致
     */
    private List<RequestHandler> handlers = new ArrayList<>();

    public void addHandler(RequestHandler handler) {
        handlers.add(handler);
    }

    public boolean process(Request request) {
        boolean result = false;
        for (RequestHandler h : handlers) {
            result = h.process(request);
            if (!result) {
                return false;
            }
        }
        return false;
    }
}
