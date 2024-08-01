package com.suyihang.entity;

import java.util.UUID;

public class AIGCrecord {

    private String sessionId;
    private String text;

    public AIGCrecord() {
        this.sessionId = java.util.UUID.randomUUID().toString();
    }
    public AIGCrecord(String text) {
        this.text = text;
        this.sessionId = java.util.UUID.randomUUID().toString();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static void main(String[] args) {

        AIGCrecord aigCrecord = new AIGCrecord();
        System.out.println(aigCrecord.getSessionId());

    }
}
