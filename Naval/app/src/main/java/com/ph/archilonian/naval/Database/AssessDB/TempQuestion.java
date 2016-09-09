package com.ph.archilonian.naval.Database.AssessDB;

public class TempQuestion {

    private int qid;
    private String qset;
    private String lid;
    private String qitem;
    private String qans;
    private String quserans;

    public TempQuestion() {
        qid = 0;
        qset = "";
        lid = "";
        qitem = "";
        qans = "";
        quserans = "";
    }

    public TempQuestion(String qset, String lid, String qitem, String qans, String quserans) {
        this.qset = qset;
        this.lid = lid;
        this.qitem = qitem;
        this.qans = qans;
        this.quserans = quserans;

    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQset() {
        return qset;
    }

    public void setQset(String qset) {
        this.qset = qset;
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

    public String getQuserans() {
        return quserans;
    }

    public void setQuserans(String quserans) {
        this.quserans = quserans;
    }
}