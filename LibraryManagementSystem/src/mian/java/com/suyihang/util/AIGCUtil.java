package com.suyihang.util;

import com.suyihang.entity.AIGCrecord;

import java.util.ArrayList;

public class AIGCUtil {

    public static ArrayList<AIGCrecord> aigCrecords = new ArrayList<>();

    public static AIGCrecord currentAI = null;

    public static void addAIGCrecord(String text) {
        AIGCrecord record = new AIGCrecord(text);
        aigCrecords.add(record);
    }
}
