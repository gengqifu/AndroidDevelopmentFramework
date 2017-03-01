package com.example.example.view;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.example.R;
import com.example.example.activity.login.MainActivity;
import com.example.example.adapter.MainFragmentPagerAdapter;
import com.example.example.contract.MainContract;
import com.example.example.db.CloudDevice;
import com.example.example.engine.AppConstantInfo;
import com.example.example.fragment.PageFragment;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class MainView implements MainContract.View {
    private final SoftReference<MainActivity> mActivity;

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private MainFragmentPagerAdapter pagerAdapter;

    private List<CloudDevice> mDeviceList = new ArrayList<>();
    private String mToken = "";
    private MainContract.Presenter mPresenter;
    private String mDevice = "";

    private boolean isCloud = false;

    private boolean isThunder = false;

    //顶部标题
    private TextView mDeviceName;
    private ImageView mTopBack, mTopMessage;
    private String title = "";
    private boolean isHomePage = true;

    private FrameLayout mVerifyWrapper;
    private RelativeLayout mToVerify, mOfflineWrapper;
    private TextView mTvOffine;

    private String mSelectDid = "";
    private String mSelectToken = "";
    private String boxName;
    private View view;

    private TextView deviceDown;

    private boolean isSmartHomeShow = true;

    private MainView(MainActivity activity) {
        mActivity = new SoftReference<>(activity);
        mVerifyWrapper = (FrameLayout) mActivity.get().findViewById(R.id.verify_wrapper);

        Button mVerify = (Button) mActivity.get().findViewById(R.id.verify_btn);

        mToVerify = (RelativeLayout) mActivity.get().findViewById(R.id.llt_verify);
        mOfflineWrapper = (RelativeLayout) mActivity.get().findViewById(R.id.offline_wrapper);
        mTvOffine = (TextView) mActivity.get().findViewById(R.id.tv_offine);
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public static MainView newInstance(MainActivity activity) {
        return new MainView(activity);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void initNavigationBar(final boolean isShowPhoneFile) {

        if (isCloud) {
            mDeviceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deviceDown.setBackgroundResource(R.mipmap.ic_home_upward);
                }
            });
        } else {
            if (!isShowPhoneFile) {
                mPresenter.getBoxName();
            }

        }
        MainActivity activity = mActivity.get();
        viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) activity.findViewById(R.id.tabLayout);
        appBarLayout = (AppBarLayout) activity.findViewById(R.id.appbar);
        view = activity.findViewById(R.id.line_tablayout);
        pagerAdapter =
                new MainFragmentPagerAdapter(activity.getSupportFragmentManager(), activity);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mTopBack.getVisibility() == View.VISIBLE) {
                    title = mDeviceName.getText().toString();
                    isHomePage = false;
                }

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }

                if (position != 0) {
                    if (mVerifyWrapper.getVisibility() == View.VISIBLE) {
                        mVerifyWrapper.setVisibility(View.GONE);
                    }
                    mDeviceName.setEnabled(false);
                    deviceDown.setVisibility(View.GONE);
                } else {
                    mDeviceName.setEnabled(true);
                    if (isCloud) {
                        if (AppConstantInfo.CLOUD_DEVICE != null) {
                            if (AppConstantInfo.CLOUD_DEVICE.getCertificate() == null
                                    || AppConstantInfo.CLOUD_DEVICE.getCertificate().equals("")) {
                                mVerifyWrapper.setVisibility(View.VISIBLE);
                            }

                            mDeviceName.setText(AppConstantInfo.CLOUD_DEVICE.getDname());
                        }

                        deviceDown.setVisibility(View.VISIBLE);
                    } else {
                        deviceDown.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }

        if (isThunder) {
            pagerAdapter.setIsThunder(true);
            viewPager.setCurrentItem(1);

            tabLayout.getTabAt(1).getCustomView().setSelected(true);
        } else {
            pagerAdapter.setIsThunder(false);
            if (isShowPhoneFile) {
                tabLayout.getTabAt(1).getCustomView().setSelected(true);
                viewPager.setCurrentItem(1);
                pagerAdapter.setIsShowPhoneFile(true);
                tabLayout.setVisibility(View.GONE);
            } else {
                tabLayout.getTabAt(0).getCustomView().setSelected(true);
            }
        }

        tabLayout.getTabAt(2).getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSmartHomeShow) {
                    viewPager.setCurrentItem(2);
                } else {
                    return;
                }
            }
        });
    }

    @Override
    public void drawTitleBar() {
        final MainActivity activity = mActivity.get();
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        assert toolbar != null;
    }

    public void setToolbarBackListener(View.OnClickListener onClickListener) {
        mTopBack.setOnClickListener(onClickListener);
    }

    public void setToken(String token, String device, boolean isCloud, boolean isThunder, boolean isShowPhoneFile) {
        mToken = token;
        mDevice = device;
        this.isCloud = isCloud;
        this.isThunder = isThunder;


        drawTitleBar();
        initNavigationBar(isShowPhoneFile);

    }

    @Override
    public void setBack() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.get().onBackPressed();
            }
        });
    }

    public void showHomePage() {
        ((PageFragment) pagerAdapter.getItem(0)).getMainData();
    }

    @Override
    public void refreshBoxName(String name) {
        boxName = name;
    }


    @Override
    public void setBoxName(String boxName) {
        this.boxName = boxName;
        if (viewPager.getCurrentItem() == 0) {
            mDeviceName.setText(boxName);
        }
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    public int getCurrentPagePosition() {
        return viewPager.getCurrentItem();
    }

    public void onBackPressed() {
        switch (getCurrentPagePosition()) {
            case 0:
                com.example.example.engine.ExampleApplication.exitApp();
                break;
            case 1:
                com.example.example.engine.ExampleApplication.exitApp();
                break;
            case 2:
                com.example.example.engine.ExampleApplication.exitApp();
                break;
            case 3:
                com.example.example.engine.ExampleApplication.exitApp();
                break;
        }
    }
}
