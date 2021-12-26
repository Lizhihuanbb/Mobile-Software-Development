package com.jnu.mybill.Fragment;

import com.jnu.mybill.Fragment.BaseRecordFragment;
import com.jnu.mybill.data.DBControler;

public class IncomeFragment extends BaseRecordFragment {
    @Override
    public void initData() {
        billTypes= DBControler.getBillTypeList(1);
    }
}