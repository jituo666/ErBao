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
        addTemperatureRecord(schema);
        addDayRecord(schema);
        addNewsList(schema);
        addKnownledgeList(schema);
        new DaoGenerator().generateAll(schema, "/Users/jituo/Dev/Fav/ErBao/app/src/dao");
    }


    // 体温
    private static void addTemperatureRecord(Schema schema){

        Entity temperature = schema.addEntity("TemperatureRecord");
        temperature.addLongProperty("date").primaryKey();
        temperature.addIntProperty("temperature");
    }

    //每日记录
    private static void addDayRecord(Schema schema) {
        Entity note = schema.addEntity("DayRecord");
        note.addLongProperty("date").primaryKey();
        note.addBooleanProperty("yesuan");
        note.addBooleanProperty("aiai");
        note.addBooleanProperty("sport");
        note.addBooleanProperty("yjstart");
        note.addBooleanProperty("yjend");
//
        note.addIntProperty("szpl");
        note.addIntProperty("szzy");
        note.addIntProperty("baidai");
        note.addStringProperty("feeling");
        note.addStringProperty("symptom");
        note.addStringProperty("note");
    }


    // 新闻文章
    private static void addNewsList(Schema schema) {
        Entity newsList = schema.addEntity("NewsList");
        newsList.addIntProperty("newsId").primaryKey();
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
        newsList.addIntProperty("knownlegeId").primaryKey();
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

