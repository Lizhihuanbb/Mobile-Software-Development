package com.jnu.mybill.Fragment;

import android.os.Bundle;

import com.jnu.mybill.Fragment.BaseRecordFragment;
import com.jnu.mybill.data.DBControler;

public class IncomeFragment extends BaseRecordFragment {
    @Override
    public void initData() {
        billTypes= DBControler.getBillTypeList(1);
    }

    @Override
    public void saveListToDB() {
        billList.setKind(1);
        DBControler.insertItemToBillListDB(billList);
    }
    public static IncomeFragment newInstance() {
        IncomeFragment fragment = new IncomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
}