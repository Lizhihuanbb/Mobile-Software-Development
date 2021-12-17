package com.jnu.mybill.data;

import android.content.Context;
import android.graphics.Path;

import com.jnu.mybill.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataControler {
    public static final String FILE_NAME_Bill = "Bill_data";
    public static final String FILE_NAME_Option = "Option_data";
    private Context context;
    private ArrayList<BillList> billLists;
    private ArrayList<OptionList> optionLists;

    public DataControler(Context context) {
        this.context=context;
    }

    public List<BillList> loadBill(){
        ObjectInputStream file=null;
        billLists = new ArrayList<BillList>();
        try{
            file=new ObjectInputStream(context.openFileInput(FILE_NAME_Bill));
            billLists =(ArrayList<BillList>)file.readObject();
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
        billLists.add(new BillList("食物","备注",R.drawable.food,20.0,1));
        billLists.add(new BillList("食物","备注",R.drawable.food,30.0,1));
        return billLists;
    }

    public List<OptionList> loadOption(){
        ObjectInputStream file=null;
        optionLists = new ArrayList<OptionList>();
        try{
            file=new ObjectInputStream(context.openFileInput(FILE_NAME_Option));
            optionLists =(ArrayList<OptionList>)file.readObject();
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
        optionLists.add(new OptionList(R.drawable.food,"食物"));
        optionLists.add(new OptionList(R.drawable.daily,"日常"));
        optionLists.add(new OptionList(R.drawable.fruit,"水果"));
        optionLists.add(new OptionList(R.drawable.game,"游戏"));
        optionLists.add(new OptionList(R.drawable.home,"房租水电"));
        optionLists.add(new OptionList(R.drawable.gift,"人情礼物"));
        optionLists.add(new OptionList(R.drawable.gathering,"聚会聚餐"));
        optionLists.add(new OptionList(R.drawable.fund,"基金理财"));
        optionLists.add(new OptionList(R.drawable.salary,"医院"));
        optionLists.add(new OptionList(R.drawable.pe,"健身"));
        optionLists.add(new OptionList(R.drawable.hiking,"逛街"));
        optionLists.add(new OptionList(R.drawable.trip,"旅行旅游"));
        optionLists.add(new OptionList(R.drawable.fee,"通讯费用"));
        return optionLists;
    }
    public void saveBill() {
        ObjectOutputStream file = null;
        try {
            file=new ObjectOutputStream(context.openFileOutput(FILE_NAME_Bill,Context.MODE_PRIVATE));
            file.writeObject(billLists);
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

    public void saveOption() {
        ObjectOutputStream file = null;
        try {
            file=new ObjectOutputStream(context.openFileOutput(FILE_NAME_Option,Context.MODE_PRIVATE));
            file.writeObject(optionLists);
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
