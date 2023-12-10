package com.dharussalam.schoolnoticesapp;

public class fileinfomodel {

    String tittle, quiz, date, fileurl;

    fileinfomodel()
    {

    }

    public fileinfomodel(String tittle, String quiz, String date, String fileurl) {
        this.tittle = tittle;
        this.quiz = quiz;
        this.date = date;
        this.fileurl = fileurl;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
