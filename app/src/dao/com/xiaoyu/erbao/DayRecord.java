package com.xiaoyu.erbao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DAY_RECORD.
 */
public class DayRecord {

    private Long date;
    private Boolean yesuan;
    private Boolean aiai;
    private Boolean sport;
    private Boolean yjstart;
    private Boolean yjend;
    private Integer szpl;
    private Integer szzy;
    private Integer baidai;
    private String feeling;
    private String symptom;
    private String note;

    public DayRecord() {
    }

    public DayRecord(Long date) {
        this.date = date;
    }

    public DayRecord(Long date, Boolean yesuan, Boolean aiai, Boolean sport, Boolean yjstart, Boolean yjend, Integer szpl, Integer szzy, Integer baidai, String feeling, String symptom, String note) {
        this.date = date;
        this.yesuan = yesuan;
        this.aiai = aiai;
        this.sport = sport;
        this.yjstart = yjstart;
        this.yjend = yjend;
        this.szpl = szpl;
        this.szzy = szzy;
        this.baidai = baidai;
        this.feeling = feeling;
        this.symptom = symptom;
        this.note = note;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Boolean getYesuan() {
        return yesuan;
    }

    public void setYesuan(Boolean yesuan) {
        this.yesuan = yesuan;
    }

    public Boolean getAiai() {
        return aiai;
    }

    public void setAiai(Boolean aiai) {
        this.aiai = aiai;
    }

    public Boolean getSport() {
        return sport;
    }

    public void setSport(Boolean sport) {
        this.sport = sport;
    }

    public Boolean getYjstart() {
        return yjstart;
    }

    public void setYjstart(Boolean yjstart) {
        this.yjstart = yjstart;
    }

    public Boolean getYjend() {
        return yjend;
    }

    public void setYjend(Boolean yjend) {
        this.yjend = yjend;
    }

    public Integer getSzpl() {
        return szpl;
    }

    public void setSzpl(Integer szpl) {
        this.szpl = szpl;
    }

    public Integer getSzzy() {
        return szzy;
    }

    public void setSzzy(Integer szzy) {
        this.szzy = szzy;
    }

    public Integer getBaidai() {
        return baidai;
    }

    public void setBaidai(Integer baidai) {
        this.baidai = baidai;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
