package com.jnu.booklistmainactivity.data;

import android.content.Context;

import com.jnu.booklistmainactivity.MainActivity;
import com.jnu.booklistmainactivity.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    public static final String FILE_NAME = "data";
    private Context context;
    private ArrayList<Book> bookArrayList;

    public DataBank(Context context) {
        this.context=context;
    }

    public List<Book> loadData(){
        ObjectInputStream file=null;
        bookArrayList = new ArrayList<Book>();
        try{
            file=new ObjectInputStream(context.openFileInput(FILE_NAME));
            bookArrayList=(ArrayList<Book>)file.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(file!=null){
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bookArrayList.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookArrayList.add(new Book("创新工程实践", R.drawable.book_no_name));
        bookArrayList.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        return bookArrayList;
    }

    public void saveData() {
        ObjectOutputStream file = null;
        try {
            file=new ObjectOutputStream(context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE));
            file.writeObject(bookArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (file!=null){
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
