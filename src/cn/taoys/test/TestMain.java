package cn.taoys.test;

import java.util.List;

import cn.taoys.utils.CommandUtil;

public class TestMain {
	public static void main(String[] args) {
        CommandUtil util = new CommandUtil();
        util.executeCommand("ping -n 10 www.baidu.com");
        printList(util.getStdoutList());
        System.out.println("--------------------");
        printList(util.getErroroutList());
    }

    
    public static void printList(List<String> list){
        for (String string : list) {
            System.out.println(string);
        }
    }
}
