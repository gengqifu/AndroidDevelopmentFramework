package com.example.example.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.example.activity.login.MainActivity;
import com.example.example.contract.PageContract;

import java.lang.ref.SoftReference;

public class PageView implements PageContract.View {
    private final SoftReference<MainActivity> mActivity;
    private PageContract.Presenter mPresenter;
    private LayoutInflater mInflater;
    private ViewGroup mView;
    private View homePage;
    TextView usableRoom;
    TextView myFile;
    TextView myBackup;
    TextView thunderLoad;
    TextView monitorFile;
    TextView otherFile;
    private boolean isDrawed;
    private TextView recentMove;
    private PopupWindow popupWindow;
    private boolean isStart = true;
    private String mYear;
    private String mMonth;
    private String mDay;
    private String mWeek;
    private ImageView ivOuter;
    private TextView mSceneNameTextView;
    private ImageView ivStart;

    private PopupWindow mScenePopupWindow;

    private PageView(MainActivity activity) {
        isDrawed = false;
        mActivity = new SoftReference<>(activity);
    }

    public static PageView newInstance(MainActivity activity) {
        return new PageView(activity);
    }

    @Override
    public void setPresenter(PageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void drawTitleBar() {

    }

    @Override
    public View showPage(LayoutInflater inflater, int page, boolean isThunder, ViewGroup view, boolean isShowPhoneFile) {
        mInflater = inflater;
        mView = view;
        switch (page) {
            case 1:
                if (!isShowPhoneFile) {
                    mPresenter.getMainData();
                }
                break;
            case 2:

                break;
            case 3:
                break;

            case 4:
                break;
            default:
                break;
        }

        return view;
    }
}
