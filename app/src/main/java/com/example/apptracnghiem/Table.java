package com.example.apptracnghiem;

import android.provider.BaseColumns;

public final class Table {
    private Table() {
    }

    public static class CategoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME = "name";
    }

    //lấy dữ liệu từ bảng questions
    public static class QuestionsTable implements BaseColumns {
        //tên bảng
        public static final String TABLE_NAME = "questions";
        //Câu hỏi
        public static final String COLUMN_QUESTION = "question";
        //Câu trả lời
        public static final String COLUMN_ANSWER = "answer";
        //4 đáp án
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        //id chuyên mục
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }
}
