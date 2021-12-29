package com.jnu.mybill.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jnu.mybill.R;

public class SQLDataBase extends SQLiteOpenHelper {
    public SQLDataBase(@Nullable Context context) {
        super(context, "bill.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "create table billtype(id integer primary key autoincrement,typename varchar(10),coverResourceId integer,kind integer)";
        sqLiteDatabase.execSQL(sql);
        init(sqLiteDatabase);

//        创建账单条目的数据库
        sql ="create table billList(id integer primary key autoincrement,catagory varchar(10),remarks varchar(80),coverreSourceid integer,"+
                "money DOUBLE,kind integer,time varchar(60),year integer,month integer,day integer)";
        sqLiteDatabase.execSQL(sql);
        initBillList(sqLiteDatabase);
    }

    private void initBillList(SQLiteDatabase sqLiteDatabase) {
        String sql = "insert into billList (catagory,remarks,coverreSourceid,money,kind,time,year,month,day) values (?,?,?,?,?,?,?,?,?)";
        sqLiteDatabase.execSQL(sql,new Object[]{"人情礼物", "步行街",R.drawable.gift,22.0,0,"2021年12月28日 06:17",2021,12,28});
        sqLiteDatabase.execSQL(sql,new Object[]{"食物", "汉堡包",R.drawable.food,88.1,0,"2021年12月28日 07:22",2021,12,28});
        sqLiteDatabase.execSQL(sql,new Object[]{"薪资", "HUAWEI",R.drawable.fee,8888.1,1,"2021年12月28日 18:00",2021,12,28});
        sqLiteDatabase.execSQL(sql,new Object[]{"意外所得", "捡到50元",R.drawable.imaginemoney,50.0,1,"2021年12月29日 08:50",2021,12,29});
        sqLiteDatabase.execSQL(sql,new Object[]{"房租水电", "",R.drawable.home,2000.0,0,"2021年12月29日 09:10",2021,12,29});
        sqLiteDatabase.execSQL(sql,new Object[]{"聚会聚餐", "AA海底捞",R.drawable.gathering,300.0,0,"2021年12月29日 18:00",2021,12,29});
        sqLiteDatabase.execSQL(sql,new Object[]{"旅行旅游", "订机票",R.drawable.trip,500.0,0,"2021年12月30日 08:30",2021,12,30});



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
