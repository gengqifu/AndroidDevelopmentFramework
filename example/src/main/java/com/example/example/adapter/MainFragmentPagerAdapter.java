package com.example.example.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.example.R;
import com.example.example.fragment.PageFragment;
import com.example.examplelib.utils.DeviceUtils;

import java.util.ArrayList;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int pageCount;
    private final String[] tabTitles;
    private final Context context;
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private final FragmentManager fragmentManager;

    private boolean isThunder = false;
    private boolean isShowPhoneFile = false;

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.news_title);
        tv.setText(tabTitles[position]);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        if (position == 0) {
            if (DeviceUtils.hasLollipop()) {
                imageView.setBackground(context.getDrawable(R.drawable.home_selector));
            } else {
                imageView.setBackground(context.getResources().getDrawable(R.drawable.home_selector));
            }
        } else if (position == 1) {
            if (DeviceUtils.hasLollipop()) {
                imageView.setBackground(context.getDrawable(R.drawable.cloud_selector));
            } else {
                imageView.setBackground(context.getResources().getDrawable(R.drawable.cloud_selector));
            }
        } else if (position == 2) {
            if (DeviceUtils.hasLollipop()) {
                imageView.setBackground(context.getDrawable(R.drawable.smart_selector));
            } else {
                imageView.setBackground(context.getResources().getDrawable(R.drawable.smart_selector));
            }
        } else if (position == 3) {
            if (DeviceUtils.hasLollipop()) {
                imageView.setBackground(context.getDrawable(R.drawable.user_selector));
            } else {
                imageView.setBackground(context.getResources().getDrawable(R.drawable.user_selector));
            }
        }
        return v;
    }

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
        tabTitles = context.getResources().getStringArray(R.array.home_tab_bar);
        pageCount = tabTitles.length;
        for (int i = 0; i < pageCount; i++) {
            Fragment fragment = new Fragment();
            fragments.add(fragment);
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        PageFragment fragment = PageFragment.newInstance(position + 1, isThunder, isShowPhoneFile);
        fragments.set(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position != 1 && position != 2) {
            //私有云页面进入层次较深的情况下，需要缓存
            super.destroyItem(container, position, object);
        }
    }

    public void setIsThunder(boolean isThunder) {
        this.isThunder = isThunder;
    }

    //展示手机文件
    public void setIsShowPhoneFile(boolean isShowPhoneFile) {
        this.isShowPhoneFile = isShowPhoneFile;
    }

    public void setFragments(int position) {
        if (this.fragments != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment f = fragments.get(position);
            ft.remove(f);
            ft.commit();
            fragmentManager.executePendingTransactions();
            getItem(position);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
