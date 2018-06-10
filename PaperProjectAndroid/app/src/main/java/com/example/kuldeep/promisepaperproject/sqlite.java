package com.example.kuldeep.promisepaperproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.IntProperty;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuldeep on 8/14/2017.
 */

public class sqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "PaperManager.db";

    private static final String TABLE_COURSE = "courselist";
    private static final String TABLE_SUBJECT = "subjectlist";
    private static final String TABLE_PAPER = "paperlist";

    private static final String COLUMN_COURSE_ID = "courseid";
    private static final String COLUMN_COURSE_NAME = "coursename";
    private static final String COLUMN_SEM_START = "semstart";
    private static final String COLUMN_SEM_END = "semend";
    private static final String COLUMN_SUBJECT_NAME = "subjectname";
    private static final String COLUMN_SUBJECT_ID = "subjectid";
    private static final String COLUMN_SEM_VAL = "semval";
    private static final String COLUMN_PAPER_ID = "paperid";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_PATH_OF_FILE = "pathoffile";

    private String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSE + "("
            + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY," + COLUMN_COURSE_NAME + " VARCHAR(20),"
            +  COLUMN_SEM_START + " INTEGER,"
            + COLUMN_SEM_END + " INTEGER" + ")";

    private String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + "("
            + COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY," + COLUMN_SUBJECT_NAME + " VARCHAR(500),"
            +  COLUMN_COURSE_ID + " INTEGER,"
            + COLUMN_SEM_VAL + " INTEGER" + ")";

    private String CREATE_PAPER_TABLE = "CREATE TABLE " + TABLE_PAPER + "("
            + COLUMN_PAPER_ID + " INTEGER PRIMARY KEY," + COLUMN_YEAR + " INTEGER,"
            +  COLUMN_MONTH + " INTEGER,"
            + COLUMN_SUBJECT_ID + " INTEGER," + COLUMN_PATH_OF_FILE + " TEXT" + ")";

    public sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COURSE_TABLE);
        db.execSQL(CREATE_PAPER_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<ListItem> getCourselist()
    {
        String[] columns = {
                COLUMN_COURSE_ID,
                COLUMN_COURSE_NAME,
                COLUMN_SEM_START,
                COLUMN_SEM_END
        };

        ArrayList<ListItem> itemList = new ArrayList<ListItem>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COURSE,
                columns,
                null,
                null,
                null,
                null,
                null);

        Log.d("CURSORCNT",cursor.getCount() + " ");
        if (cursor.moveToFirst()) {
            do {
                ListItem item = new ListItem(   Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_ID))),
                                                cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME)),
                                                Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SEM_START))),
                                                Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SEM_END))),
                                                cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME)) );
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("HERE AGAIN", itemList.size() + "");
        return itemList;

    }

    public ArrayList<ListItem> getPaperlist(String subjectid)
    {
        String[] columns = {
                COLUMN_PAPER_ID,
                COLUMN_YEAR,
                COLUMN_MONTH,
                COLUMN_SUBJECT_ID,
                COLUMN_PATH_OF_FILE
        };

        ArrayList<ListItem> itemList = new ArrayList<ListItem>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_SUBJECT_ID + " = ? ";

        String[] selectionArgs = {subjectid};

        Cursor cursor = db.query(TABLE_PAPER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        Log.d("CURSORCNT",cursor.getCount() + " ");
        if (cursor.moveToFirst()) {
            do {
                ListItem item = new ListItem(
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PAPER_ID))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_YEAR))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MONTH))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_ID))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PATH_OF_FILE)));
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d("HERE AGAIN", itemList.size() + "");
        return itemList;

    }


    public ArrayList<ListItem> getSemList(String courseID)
    {   Log.d("HERE","SEMLIST " + courseID);
        String[] columns = {
                COLUMN_COURSE_ID,
                COLUMN_COURSE_NAME,
                COLUMN_SEM_START,
                COLUMN_SEM_END
        };
        ArrayList<ListItem> itemList = new ArrayList<ListItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_COURSE_ID + " = ?";
        String[] selectionArg = {courseID};
        Cursor cursor = db.query(TABLE_COURSE,
                columns,
                selection,
                selectionArg,
                null,
                null,
                null);
        Log.d("CURSOR", cursor.getCount() + " ");
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            int semstr = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SEM_START)));
            int semend = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SEM_END)));
            String crsname = cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME));
            int crsID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_ID)));
            Log.d("DEBUGGING", crsID + " " + crsname + " " + " " + semend + "  " + semstr);
            for (int i = semstr; i <= semend; i++) {
                Log.d("DEBUGGING", crsID + " " + crsname + " " + i + " " + semend + "  " + semstr);
                itemList.add(new ListItem(i, crsID, crsname, "Semester " + i));
            }
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public ArrayList<ListItem> getSubList(String courseID, String semVal)
    {   Log.d("HERE","SUBLIST");
        String[] columns = {
                COLUMN_SUBJECT_ID,
                COLUMN_SUBJECT_NAME,
                COLUMN_COURSE_ID,
                COLUMN_SEM_VAL
        };

        ArrayList<ListItem> itemList = new ArrayList<ListItem>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_COURSE_ID + " = ?" + " AND " + COLUMN_SEM_VAL + " = ? ";
        String[] selectionArg = {courseID,semVal };
        Cursor cursor = db.query(TABLE_SUBJECT,
                columns,
                selection,
                selectionArg,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                ListItem item = new ListItem(   cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_ID))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SEM_VAL))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_ID))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)) );
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public void addCourse(int courseID, String courseName, int semStart, int semEnd){
        Log.d("HERE AGAIN AGAIN","DEBUGGED" + semStart + " " + semEnd);

        String[] columns = {
                COLUMN_COURSE_ID,
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String selection  = COLUMN_COURSE_ID + " = ?";

        String[] selectionArgs = {Integer.toString(courseID)};

        Cursor cursor = db.query(TABLE_COURSE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCnt = cursor.getCount();
        Log.d("CURSORCNT2",cursorCnt  + "");
        cursor.close();
        db.close();
        if(cursorCnt==0) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_COURSE_ID, courseID);
            values.put(COLUMN_COURSE_NAME, courseName);
            values.put(COLUMN_SEM_START, semStart);
            values.put(COLUMN_SEM_END, semEnd);
            db.insert(TABLE_COURSE, null, values);
            db.close();
        }
    }

    public void addSubject(int subjectID, String subjectName, int courseID, int semVal, String courseName, int semStart, int semEnd){
        Log.d("SubjectAdd","DEBUGGED" + semStart + " " + semEnd);
        String[] columns = {
                COLUMN_SUBJECT_ID,
                COLUMN_SUBJECT_NAME,
                COLUMN_COURSE_ID,
                COLUMN_SEM_VAL
        };

        SQLiteDatabase db = this.getReadableDatabase();

        String selection  = COLUMN_SUBJECT_ID + " = ?";

        String[] selectionArgs = {Integer.toString(subjectID)};

        Cursor cursor = db.query(TABLE_SUBJECT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
            int cursorCnt = cursor.getCount();
        Log.d("CURSORCNT",cursorCnt  + "");
            cursor.close();
            db.close();
        if(cursorCnt==0) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_SUBJECT_ID, subjectID);
            values.put(COLUMN_SUBJECT_NAME, subjectName);
            values.put(COLUMN_COURSE_ID, courseID);
            values.put(COLUMN_SEM_VAL, semVal);
            db.insert(TABLE_SUBJECT, null, values);
            db.close();
        }
        addCourse(courseID, courseName, semStart, semEnd);
    }

    public void addPaper(int month, int year, int paperID, int subjectID, String pathoffile, String subjectName, int courseID, int semVal, String courseName, int semStart, int semEnd){
                    Log.d("PaperAdd","DEBUGGED");
                   SQLiteDatabase db = this.getWritableDatabase();
                    String[] whereArgs = {Integer.toString(month), Integer.toString(year), Integer.toString(subjectID)};
                    db.delete(TABLE_PAPER, COLUMN_MONTH + " = ? AND " + COLUMN_YEAR + " = ? AND " + COLUMN_SUBJECT_ID + " = ?", whereArgs);
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_PAPER_ID, paperID);
                    values.put(COLUMN_SUBJECT_ID, subjectID);
                    values.put(COLUMN_PATH_OF_FILE, pathoffile);
                    values.put(COLUMN_YEAR, year);
                    values.put(COLUMN_MONTH, month);
                    db.insert(TABLE_PAPER, null, values);
                    db.close();
                    addSubject(subjectID, subjectName, courseID, semVal, courseName, semStart, semEnd);

    }

    public ArrayList<ListItem> getlist(ListItem listItem, int currlistid, Resources resources)
    {
        Log.d("HERE", listItem + " " + currlistid + " " + resources);
        if(currlistid == resources.getInteger(R.integer.selcourse))
        {
            return getCourselist();
        }
        else if(currlistid == resources.getInteger(R.integer.selsemester))
        {
            return getSemList(Integer.toString(listItem.courseID));
        }
        else if(currlistid == resources.getInteger(R.integer.selsubject))
        {
            return getSubList(Integer.toString(listItem.courseID), Integer.toString(listItem.semval));
        }
        else if(currlistid == resources.getInteger(R.integer.selyear))
        {
            return getPaperlist(Integer.toString(listItem.subjectID));
        }
        return null;
    }

//    public boolean checkUser(String phone) {
//
//        String[] columns = {
//                COLUMN_USER_ID
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String selection = COLUMN_USER_PHONE + " = ?";
//
//        String[] selectionArgs = {phone};
//
//        Cursor cursor = db.query(TABLE_USER,
//                columns,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//
//        return false;
//    }

//    public void addUser(User user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_NAME, user.getName());
//        values.put(COLUMN_USER_EMAIL, user.getEmail());
//        values.put(COLUMN_USER_PASSWORD, user.getPassword());
//        values.put(COLUMN_USER_PHONE, user.getPhone());
//        db.insert(TABLE_USER, null, values);
//        db.close();
//    }
//    public ArrayList<User> getAllUser() {
//        String[] columns = {
//                COLUMN_USER_ID,
//                COLUMN_USER_EMAIL,
//                COLUMN_USER_NAME,
//                COLUMN_USER_PASSWORD,
//                COLUMN_USER_PHONE
//        };
//
//        ArrayList<User> userList = new ArrayList<User>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_USER,
//                columns,
//                null,
//                null,
//                null,
//                null,
//                null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                User user = new User();
//                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
//                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
//                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
//                user.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
//                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
//                userList.add(user);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return userList;
//    }

//    public boolean checkUser(String phone, String password) {
//
//
//        String[] columns = {
//                COLUMN_USER_ID
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selection = COLUMN_USER_PHONE + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
//
//        String[] selectionArgs = {phone, password};
//
//        Cursor cursor = db.query(TABLE_USER,
//                columns,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                null);
//
//        int cursorCount = cursor.getCount();
//
//        cursor.close();
//        db.close();
//        if (cursorCount > 0) {
//            return true;
//        }
//
//        return false;
//    }
}


