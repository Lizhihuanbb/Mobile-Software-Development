package com.jnu.mybill.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DBControler {
    public static SQLiteDatabase sqLiteDatabase;
    public static void init(Context context){
        SQLDdataBase temp =new SQLDdataBase(context);
        sqLiteDatabase=temp.getWritableDatabase();
    }
    public static ArrayList<BillType> getBillTypeList(int kind){
        ArrayList<BillType> billTypes=new ArrayList<BillType>();
        String sql="select * from billtype where kind = "+kind;
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int coverreSourceid = cursor.getInt(cursor.getColumnIndex("coverResourceId"));
            @SuppressLint("Range") int mykind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            BillType typeBean = new BillType(id, typename, coverreSourceid,kind);
            billTypes.add(typeBean);
        }
        cursor.close();
        return billTypes;
    }
    public static void insertItemToBillListDB(BillList billList){

        ContentValues values=new ContentValues();
        values.put("catagory",billList.getCatagory());
        values.put("remarks",billList.getRemarks());
        values.put("coverreSourceid",billList.getCoverreSourceid());
        values.put("money",billList.getMoney());
        values.put("kind",billList.getKind());
        values.put("time",billList.getTime());
        values.put("year",billList.getYear());
        values.put("month",billList.getMonth());
        values.put("day",billList.getDay());
        sqLiteDatabase.insert("billList",null,values);
        Log.i("animee","insert OK!!!!!");
    }
    public static ArrayList<BillList> getBillList(){
        ArrayList<BillList>list = new ArrayList<>();
        String sql = "select * from billList order by id desc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String catagory = cursor.getString(cursor.getColumnIndex("catagory"));
            @SuppressLint("Range") String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex("month"));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex("day"));
            @SuppressLint("Range") int coverreSourceid = cursor.getInt(cursor.getColumnIndex("coverreSourceid"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") Double money = cursor.getDouble(cursor.getColumnIndex("money"));
            BillList billList = new BillList(id, catagory, remarks, coverreSourceid, money, kind,time, year, month, day);
            list.add(billList);
        }
        cursor.close();
        return list;
    }
}
