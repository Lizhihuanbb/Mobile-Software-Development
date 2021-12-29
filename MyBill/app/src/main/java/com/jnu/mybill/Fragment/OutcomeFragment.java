package com.jnu.mybill.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jnu.mybill.data.BillList;
import com.jnu.mybill.data.DBControler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutcomeFragment extends BaseRecordFragment {

    @Override
    public void initData() {
        billTypes= DBControler.getBillTypeList(0);
        setKind(0);
    }

    @Override
    public void saveListToDB() {
        billList.setKind(0);
        DBControler.insertItemToBillListDB(billList);
    }


    public static OutcomeFragment newInstance(BillList test1) {
        OutcomeFragment fragment = new OutcomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM1, test1);
        if (test1!=null)
            fragment.setArguments(args);
        return fragment;
    }



}