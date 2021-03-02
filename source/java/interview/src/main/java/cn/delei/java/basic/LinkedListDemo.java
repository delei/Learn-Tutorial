package cn.delei.java.basic;

import cn.delei.PrintUtil;
import cn.hutool.core.date.StopWatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDemo {

    public static LinkedList<Integer> dataList = new LinkedList<>();

    public static void main(String[] args) {
        add();
    }


    /**
     * 遍历
     */
    static void add() {
        StopWatch stopWatch = new StopWatch();

        int size = 1000000;// 10万,100万
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
        System.err.println(stopWatch.prettyPrint());
    }

}
