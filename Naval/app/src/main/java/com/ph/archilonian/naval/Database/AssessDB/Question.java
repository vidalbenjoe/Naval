package com.ph.archilonian.naval.Database.AssessDB;


public class Question {

    private int qid;
    private String lid;
    private String qitem;
    private String qans;
    private String opta;
    private String optb;
    private String optc;
    private String optd;

    public Question() {
        qid = 0;
        lid = "";
        qitem = "";
        qans = "";
        opta = "";
        optb = "";
        optc = "";
        optd = "";
    }

    public Question(String lid, String qitem, String qans, String qopta, String qoptb, String qoptc, String qoptd) {
        this.lid = lid;
        this.qitem = qitem;
        this.qans = qans;
        this.opta = qopta;
        this.optb = qoptb;
        this.optc = qoptc;
        this.optd = qoptd;

    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getQitem() {
        return qitem;
    }

    public void setQitem(String qitem) {
        this.qitem = qitem;
    }

    public String getQans() {
        return qans;
    }

    public void setQans(String qans) {
        this.qans = qans;
    }

    public String getOpta() {
        return opta;
    }

    public void setOpta(String opta) {
        this.opta = opta;
    }

    public String getOptb() {
        return optb;
    }

    public void setOptb(String optb) {
        this.optb = optb;
    }

    public String getOptc() {
        return optc;
    }

    public void setOptc(String optc) {
        this.optc = optc;
    }

    public String getOptd() {
        return optd;
    }

    public void setOptd(String optd) {
        this.optd = optd;
    }


}
