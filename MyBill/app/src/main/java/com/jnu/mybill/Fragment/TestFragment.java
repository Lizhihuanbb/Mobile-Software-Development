package com.jnu.mybill.Fragment;

import com.jnu.mybill.data.DBControler;

public class TestFragment extends BaseRecordFragment{
    @Override
    public void initData() {
        super.initData();
        billTypes= DBControler.getBillTypeList(1);
    }
}
