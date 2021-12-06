package com.jnu.booklistmainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager2=findViewById(R.id.viewPager);
        viewPager2.setAdapter(new MyFragmentAdapter(this));

        TabLayout tabLayoutHeader=findViewById(R.id.tabLayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayoutHeader, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Book");
                        break;
                    case 1:
                        tab.setText("News");
                        break;
                    case 2:
                        tab.setText("Sellers");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
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
                    return BookFragment.newInstance();
                case 1:
                    return WebViewFragment.newInstance();
                default:
//                    return WebViewFragment.newInstance();
                    return MapViewFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}