package com.example.macbookpro.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by macbookpro on 3/20/16.
 */
// SQLiteOpenHelper 非常有用,它有助于实力化新的类
// 此类将被 ArticleListFrament 使用
public class JuiceDbHelper extends SQLiteOpenHelper {

    public final static String DB_NAME= "db.Juice";
    // 每次修改schema要增加该值
    public final static Integer DB_VERSION= JuiceContract.VERSION;

    public JuiceDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JuiceContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(JuiceContract.DROP_TABLE);
        onCreate(db);
    }
}
