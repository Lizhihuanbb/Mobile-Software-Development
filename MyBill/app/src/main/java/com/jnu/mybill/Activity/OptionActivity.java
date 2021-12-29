package com.jnu.mybill.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.mybill.Fragment.IncomeFragment;
import com.jnu.mybill.Fragment.OutcomeFragment;
import com.jnu.mybill.R;
import com.jnu.mybill.data.BillList;

import java.io.Serializable;

public class OptionActivity extends AppCompatActivity {
    BillList billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ImageView imageView=findViewById(R.id.option_exiticon);
        imageView.bringToFront();

        ViewPager2 viewPager2=findViewById(R.id.viewPager);
        viewPager2.setAdapter(new MyFragmentAdapter(this));

        TabLayout tabLayoutHeader=findViewById(R.id.tabLayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayoutHeader, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("支出");
                        break;
                    case 1:
                        tab.setText("收入");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();


        Bundle bundle=getIntent().getExtras();
        if (bundle!=null)
            billList= (BillList) bundle.getSerializable("bill");

    }

    public void onclick(View view) {
        finish();
    }

    private class MyFragmentAdapter extends FragmentStateAdapter {
        public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    if (billList!=null && billList.getKind()==0){
                        return OutcomeFragment.newInstance(billList);
                    }
                    else return OutcomeFragment.newInstance(null);
                default:
                    if (billList!=null && billList.getKind()==1){
                        return IncomeFragment.newInstance(billList);
                    }
                    else return IncomeFragment.newInstance(null);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}