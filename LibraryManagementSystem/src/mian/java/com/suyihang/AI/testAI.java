package com.suyihang.AI;

public class testAI {
    public static void main(String[] args) {
        try {
            System.out.println(WAIPainting.waitForImage(WAIPainting.task_submit("老人与海图书封面")));
            System.out.println(WAIDialogue.vivogpt("你是谁"));
            System.out.println(WAIDialogue.vivogpt("我刚刚问的问题是什么"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
