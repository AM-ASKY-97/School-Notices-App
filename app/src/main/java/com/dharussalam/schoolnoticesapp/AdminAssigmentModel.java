package com.dharussalam.schoolnoticesapp;

public class AdminAssigmentModel {

    String tittle, date, quiz, fileurl;

    AdminAssigmentModel()
    {

    }

    public AdminAssigmentModel(String tittle, String date, String quiz, String fileurl) {
        this.tittle = tittle;
        this.date = date;
        this.quiz = quiz;
        this.fileurl = fileurl;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
