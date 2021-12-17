package com.jnu.mybill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.mybill.data.DataControler;
import com.jnu.mybill.data.OptionList;

import java.util.List;

public class OptionActivity extends AppCompatActivity {


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
                    return OutcomeFragment.newInstance();
                default:
                    return IncomeFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}