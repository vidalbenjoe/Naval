package com.ph.archilonian.naval.Database.AssessDB;

public class QuizModel {
    public static String assessmentCategory;
    public static int weekCount;
    public static String assessmentSem;

    public static String getAssessmentSem() {
        return assessmentSem;
    }

    public static void setAssessmentSem(String assessmentSem) {
        QuizModel.assessmentSem = assessmentSem;
    }


    public static String getAssessmentCategory() {
        return assessmentCategory;
    }

    public static void setAssessmentCategory(String assessmentCategory) {
        QuizModel.assessmentCategory = assessmentCategory;
    }

    public static int getWeekCount() {
        return weekCount;
    }

    public static void setWeekCount(int weekCount) {
        QuizModel.weekCount = weekCount;
    }

}
