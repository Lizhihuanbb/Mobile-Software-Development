package com.jnu.mybill.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
