package cn.delei.java.util;

import cn.delei.util.PrintUtil;
import cn.hutool.core.date.StopWatch;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * java.util.LinkedList
 *
 * @author deleiguo
 */
public class LinkedListDemo {

    public static LinkedList<Integer> dataList = new LinkedList<>();

    public static void main(String[] args) {
//        add();
        get();
//        opera();
    }


    static void add() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("add");
        final int size = 10;
        dataList = new LinkedList<>();
        // 头插
        for (int i = 0; i < size; i++) {
            dataList.addFirst(i);
        }
        // 尾插
        for (int i = 0; i < size; i++) {
            dataList.add(i + 10);
        }
        System.out.println(dataList);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    static void get() {
        StopWatch stopWatch = new StopWatch();

        final int size = 100000;
        dataList = new LinkedList<>();
        // 头插
        for (int i = 0; i < size; i++) {
            dataList.add(i);
        }
        stopWatch.start("index < (size>>1)");
        System.out.println(dataList.get(size / 2 - 50));
        stopWatch.stop();

        stopWatch.start("index > (size>>1)");
        System.out.println(dataList.get(size / 2 + 50));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    /**
     * 遍历
     */
    static void opera() {
        StopWatch stopWatch = new StopWatch();
        // 10万,100万
        int size = 10;
        // 头插 addFirst(linkFirst)
        stopWatch.start("头插");
        dataList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            dataList.addFirst(i);
        }
        stopWatch.stop();
        stopWatch.start("尾插");
        dataList = new LinkedList<>();
        // 尾插 add/addLast(linkLast)
        for (int i = 0; i < size; i++) {
            dataList.addLast(i);
        }
        stopWatch.stop();
        PrintUtil.printDivider("Add");
        dataList.clear();
        dataList.add(null);
        // 尾插 add/addLast(linkLast)
        for (int i = 0; i < size; i++) {
            dataList.add(i);
        }
        for (int i = 0; i < size; i++) {
            dataList.add(i);
        }
        System.out.println(dataList);

        // 获取
        PrintUtil.printDivider("Get");
        System.out.println(dataList.get(8));
        System.out.println(dataList.getFirst());
        System.out.println(dataList.getLast());

        // remove
        PrintUtil.printDivider("Remove");
        dataList.remove(Integer.valueOf(2));
        dataList.removeFirst();
        dataList.removeLast();
        System.out.println("After remove:" + dataList);

        // 遍历
        size = 100000;
        dataList.clear();
        // 尾插 add/addLast(linkLast)
        for (int i = 0; i < size; i++) {
            dataList.add(i);
        }
        PrintUtil.printDivider("遍历");
        stopWatch.start("Iterator");
        Iterator iterator = dataList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        stopWatch.stop();
        stopWatch.start("For");
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i);
        }
        stopWatch.stop();
        stopWatch.start("增强型For");
        for (Integer temp : dataList) {
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
