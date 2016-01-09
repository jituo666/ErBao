package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    private final static int DB_VERSION = 1;
    private final static String PACKAGE_NAME = "com.xiaoyu.erbao";
    ////

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(DB_VERSION, PACKAGE_NAME);
        addDayRecord(schema);
        addNewsList(schema);
        addKnownledgeList(schema);
        new DaoGenerator().generateAll(schema, "/Users/jituo/Dev/Fav/ErBao/app/src/gen-db");
    }

    //每日记录
    private static void addDayRecord(Schema schema) {
        Entity note = schema.addEntity("DayRecord");
        note.addIdProperty();
        note.addDateProperty("date");
        note.addBooleanProperty("yesuan");
        note.addBooleanProperty("aiai");
        note.addBooleanProperty("sport");
        note.addBooleanProperty("yjstart");
        note.addBooleanProperty("yjend");
//
        note.addFloatProperty("tiwen");
        note.addIntProperty("szpl");
        note.addIntProperty("szzy");
        note.addIntProperty("baidai");
        note.addIntProperty("feeling");
        note.addStringProperty("symptom");
        note.addStringProperty("note");
        note.addBooleanProperty("makewish");
    }


    // 新闻文章
    private static void addNewsList(Schema schema) {
        Entity newsList = schema.addEntity("NewsList");
        newsList.addIdProperty();
        newsList.addIntProperty("newsId");
        newsList.addIntProperty("newsType");
        newsList.addStringProperty("title");
        newsList.addStringProperty("summary");
        newsList.addStringProperty("thumPicUrl");
        newsList.addStringProperty("contentText");
        newsList.addStringProperty("contentUrls");
        newsList.addDateProperty("date");
        newsList.addBooleanProperty("read");
        newsList.addStringProperty("eyes");
        newsList.addStringProperty("says");
    }

    //知识文章
    private static void addKnownledgeList(Schema schema) {
        Entity newsList = schema.addEntity("KnownledgeList");
        newsList.addIdProperty();
        newsList.addIntProperty("knownlegeId");
        newsList.addStringProperty("title");
        newsList.addStringProperty("summary");
        newsList.addStringProperty("thumPicUrl");
        newsList.addStringProperty("contentText");
        newsList.addStringProperty("contentUrls");
        newsList.addDateProperty("date");
        newsList.addBooleanProperty("read");
        newsList.addStringProperty("eyes");
        newsList.addStringProperty("favorite");
    }
}

