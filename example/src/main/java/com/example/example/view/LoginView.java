package com.example.example.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.example.R;
import com.example.example.activity.login.LoginActivity;
import com.example.example.activity.login.MainActivity;
import com.example.example.contract.LoginContract;
import com.example.example.engine.AppConstants;
import com.example.example.ui.EditTextWatcher;

public class LoginView implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private EditText mUserName, mPassword;
    private Button btnLogin;
    private ImageView pwdDelete;
    private ImageView userDelete;

    private LoginView(final LoginActivity activity) {
        //登录事件
        btnLogin = (Button) activity.findViewById(R.id.sign_in_button);
        assert btnLogin != null;
        btnLogin.setEnabled(false);
        mUserName = (EditText) activity.findViewById(R.id.user_name);
        mPassword = (EditText) activity.findViewById(R.id.user_password);
        userDelete = (ImageView) activity.findViewById(R.id.iv_delete_user);
        pwdDelete = (ImageView) activity.findViewById(R.id.iv_delete_pwd);
        mUserName.addTextChangedListener(new EditTextWatcher(btnLogin, mPassword, userDelete));
        mPassword.addTextChangedListener(new EditTextWatcher(btnLogin, mUserName, pwdDelete));

        userDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName.setText("");
            }
        });
        pwdDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPassword.setText("");
            }
        });

        mUserName.setInputType(InputType.TYPE_CLASS_TEXT);
        mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        mUserName.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                } else {
                    userDelete.setVisibility(View.GONE);
                }
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                } else {
                    pwdDelete.setVisibility(View.GONE);
                }
            }
        });

        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnLogin.setClickable(false);
                        if (isMobileNO(mUserName.getText().toString())) {
                            mPresenter.getDevice(mUserName.getText().toString(), mPassword.getText().toString());
                        } else {
                            mPresenter.login(mUserName.getText().toString(), mPassword.getText().toString());
                        }
                    }
                });
        TextView mForgetPassword = (TextView) activity.findViewById(R.id.forget_password_button);
        assert mForgetPassword != null;
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        activity.getWindow().getDecorView().findViewById(android.R.id.content).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Rect rect = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if ((rect.bottom - rect.top) > activity.getWindow().getDecorView().getHeight() * 4 / 5) {
                    if (mUserName.hasFocus()) {
                        mUserName.clearFocus();
                    }
                    if (mPassword.hasFocus()) {
                        mPassword.clearFocus();
                    }
                } else {
                    if (mUserName.hasFocus() && !TextUtils.isEmpty(mUserName.getText())) {
                        userDelete.setVisibility(View.VISIBLE);
                    }
                    if (mPassword.hasFocus() && !TextUtils.isEmpty(mPassword.getText())) {
                        pwdDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public static LoginView newInstance(LoginActivity activity) {
        return new LoginView(activity);
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void drawTitleBar() {

    }

    public void cancelButtonSetting() {
        btnLogin.setClickable(true);
    }

    private static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3578]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }
}
