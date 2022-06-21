package com.example.apptracnghiem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.apptracnghiem.model.Category;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Question.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db=sqLiteDatabase;

        //biến bảng chuyên mục
        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + "( " +
                Table.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";
        //biến bảng question
        final String QUESTIONS_TABLE = "CREATE TABLE " +
                Table.QuestionsTable.TABLE_NAME + " ( " +
                Table.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                Table.QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
                Table.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + Table.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        //Tao bang
        db.execSQL(CATEGORIES_TABLE);
        db.execSQL(QUESTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    //Các giá trị insert vào bảng chuyên mục

}
