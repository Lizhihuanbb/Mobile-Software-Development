package com.jnu.mybill.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.mybill.R;
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
    }

    @Override
    public void saveListToDB() {
        billList.setKind(0);
        DBControler.insertItemToBillListDB(billList);
    }

    public static OutcomeFragment newInstance() {
        OutcomeFragment fragment = new OutcomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



}