package com.example.macbookpro.myapp.db;

/**
 * Created by macbookpro on 3/20/16.
 */
public final class JuiceContract {
    // 安全性允许调用者实力话该类
    JuiceContract() {}

    public final static Integer VERSION= 1;
    public final static String TABLE = "Juices";
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_JUICE_NAME = "name";
    // public final static String COLUMN_JUICE_PRICE = "price"; Dependent on size
    public final static String COLUMN_JUICE_ALLERGEN = "allergen";
    public final static String COLUMN_JUICE_SEASON = "season";


    public final static String CREATE_TABLE = "Create table " + TABLE + "(" +
            COLUMN_ID + "integer primary key," +
            COLUMN_JUICE_NAME + "varchar(100), " +
            // COLUMN_JUICE_PRICE + "decimal(3,2), " +
            COLUMN_JUICE_ALLERGEN + "array," +
            COLUMN_JUICE_SEASON + "array" +
            ")";

    public final static String DROP_TABLE = "Drop table if exists " + TABLE;
}
