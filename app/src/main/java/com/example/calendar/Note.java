package com.example.calendar;

public class Note {
    private String title;
    private String Content;

    public Note(){


    }
    public String getTitle(){

        return title;

    }

    public void setTitle(String title){

        this.title = title;
    }

    public String getContent(){

        return Content;
    }
    public void setContent(String content){

        this.Content = content;
    }
    public Note(String title, String content){

        this.title = title;
        this.Content = content;


    }


}
