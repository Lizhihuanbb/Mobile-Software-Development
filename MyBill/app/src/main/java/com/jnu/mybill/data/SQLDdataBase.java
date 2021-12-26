package com.jnu.mybill.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jnu.mybill.R;

public class SQLDdataBase extends SQLiteOpenHelper {
    public SQLDdataBase(@Nullable Context context) {
        super(context, "bill.db", null, 1);
    }


    private int id;
    private String catagory;            //类型
    private String remarks;             //备注
    private int coverreSourceid;        //图片的id
    private double money;               //金额
    private int kind;//0为支出，1为收入   //类型
    private String time;                //时间
    private int year;
    private int month;
    private int day;
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table billtype(id integer primary key autoincrement,typename varchar(10),coverResourceId integer,kind integer)";
        sqLiteDatabase.execSQL(sql);
        //创建记账表
//        sql = "create table accounttb(sImageId integer,money float," +
//                "time varchar(60),year integer,month integer,day integer,kind integer)";
//        db.execSQL(sql);
        init(sqLiteDatabase);

//        创建账单条目的数据库
        sql ="create table billList(id integer primary key autoincrement,catagory varchar(10),remarks varchar(80),coverreSourceid integer,"+
                "money DOUBLE,kind integer,time varchar(60),year integer,month integer,day integer)";
        sqLiteDatabase.execSQL(sql);
    }

    private void init(SQLiteDatabase sqLiteDatabase) {
        String sql = "insert into billtype (typename,coverResourceId,kind) values (?,?,?)";
        sqLiteDatabase.execSQL(sql,new Object[]{"其他", R.drawable.other,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"食物", R.drawable.food,0});
//        sqLiteDatabase.execSQL(sql,new Object[]{"日常", R.drawable.daily,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"水果", R.drawable.fruit,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"游戏", R.drawable.game,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"房租水电", R.drawable.home,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"人情礼物", R.drawable.gift,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"聚会聚餐", R.drawable.gathering,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"基金理财", R.drawable.fund,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"医院", R.drawable.salary,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"健身", R.drawable.pe,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"逛街", R.drawable.hiking,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"旅行旅游", R.drawable.trip,0});
        sqLiteDatabase.execSQL(sql,new Object[]{"通讯费用", R.drawable.fee,0});


        sqLiteDatabase.execSQL(sql,new Object[]{"其他", R.drawable.other,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"薪资", R.drawable.fee,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"奖金", R.drawable.salary4,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"借入", R.drawable.borrowing,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"收债", R.drawable.transfer2,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"利息收入", R.drawable.lixi,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"投资回报", R.drawable.fund2,1});
        sqLiteDatabase.execSQL(sql,new Object[]{"意外所得", R.drawable.imaginemoney,1});
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
