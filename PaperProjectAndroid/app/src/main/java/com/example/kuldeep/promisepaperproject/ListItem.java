package com.example.kuldeep.promisepaperproject;

import android.graphics.pdf.PdfDocument;

/**
 * Created by Kuldeep on 7/29/2017.
 */




public class ListItem {
    String[] months = { "Jan", "Feb" , "Mar", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
    String show;
    String subjectname;
    String coursename;
    int semstart;
    int semend;
    int semval;
    String pathoffile;
    PdfDocument paperpdf;
    int paperID;
    int courseID;
    int subjectID;
    int year;
    int month;

    public ListItem(int paperID, int year, int month, int subjectID, String pathoffile, String subjectname, int semval, int coursID, String coursename, int semstart , int semend, String show) {
        this.show = months[month-1] + "-" + year;
        this.paperID = paperID;
        this.subjectID = subjectID;
        this.year = year;
        this.month = month;
        this.pathoffile = pathoffile;
        this.subjectname = subjectname;
        this.semval = semval;
        this.courseID = coursID;
        this.coursename = coursename;
        this.semstart = semstart;
        this.semend = semend;

    }

    public ListItem(int paperID, int year, int month, int subjectID, String pathoffile){
        this.show = months[month-1] + "-" + year;
        this.paperID = paperID;
        this.subjectID = subjectID;
        this.year = year;
        this.month = month;
        this.pathoffile = pathoffile;

    }

    public ListItem(String subjectname, int subjectID, int semval, int courseID, String show) {
        this(show);
        this.subjectname = subjectname;
        this.subjectID = subjectID;
        this.semval = semval;
        this.courseID = courseID;
    }

    public ListItem(int semval, int courseID, String coursename, String show) {
        this(show);
        this.coursename = coursename;
        this.semval = semval;
        this.courseID = courseID;
    }

    public ListItem(int courseID, String coursename, int semend, int semstart, String show) {
        this(show);
        this.courseID = courseID;
        this.semend = semend;
        this.semstart = semstart;
        this.coursename = coursename;
    }

    public ListItem(String show)
    {
        this.show = show;
    }


    @Override
    public String toString() {
        return show;
    }

}
