package com.jnu.mybill.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.mybill.Module.SelectCalendarDiaglog;
import com.jnu.mybill.R;
import com.jnu.mybill.data.BillList;
import com.jnu.mybill.data.DBControler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutcomeFragment extends BaseRecordFragment {

//    private static String PARAM1="bill";
//    BillList test;
    @Override
    public void initData() {
        billTypes= DBControler.getBillTypeList(0);
    }

    @Override
    public void saveListToDB() {
        billList.setKind(0);
        DBControler.insertItemToBillListDB(billList);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootview=super.onCreateView(inflater, container, savedInstanceState);
//        if (test!=null){
//            catagory.setText(test.getCatagory());
//            money.setText(String.valueOf(test.getMoney()));
//
//            money.setSelection(money.getText().length());
//            if (test.getRemarks()!=null){
//                remarks.setText(test.getRemarks());
//            }
//            else    remarks.setText("备注");
//            time.setText(test.getTime());
//        }
//        return rootview;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            test = (BillList) getArguments().getSerializable(PARAM1);
//        }
//    }

    public static OutcomeFragment newInstance(BillList test1) {
        OutcomeFragment fragment = new OutcomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM1, test1);
        if (test1!=null)
            fragment.setArguments(args);
        return fragment;
    }



}