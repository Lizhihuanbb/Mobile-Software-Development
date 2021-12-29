package com.jnu.mybill.Fragment;

import android.os.Bundle;

import com.jnu.mybill.Fragment.BaseRecordFragment;
import com.jnu.mybill.data.BillList;
import com.jnu.mybill.data.DBControler;

public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        billTypes= DBControler.getBillTypeList(1);
    }

    @Override
    public void saveListToDB() {
        billList.setKind(1);
        DBControler.insertItemToBillListDB(billList);
    }
    public static IncomeFragment newInstance(BillList test1) {
        IncomeFragment fragment = new IncomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM1, test1);
        if (test1!=null)
            fragment.setArguments(args);
        return fragment;
    }
}