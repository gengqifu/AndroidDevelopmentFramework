package com.example.example.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.example.utils.Utils;

public class EditTextWatcher implements TextWatcher {
    private final View mButton1;
    private View mButton2;
    private EditText mEditText;
    private EditText mEditTextOther;
    private ImageView mDeleteIcon;

    private boolean isPhoneNumber = false;

    public EditTextWatcher(View view, boolean phoneNumber) {
        mButton1 = view;
        isPhoneNumber = phoneNumber;
    }

    public EditTextWatcher(View view1, View view2) {
        mButton1 = view1;
        mButton2 = view2;
    }

    public EditTextWatcher(Button view, EditText editText, ImageView deleteView) {
        mButton1 = view;
        this.mEditText = editText;
        this.mDeleteIcon = deleteView;
    }

    public EditTextWatcher(Button view, EditText editText, EditText editTextOther, ImageView deleteView) {
        mButton1 = view;
        this.mEditText = editText;
        this.mEditTextOther = editTextOther;
        this.mDeleteIcon = deleteView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //判断按钮是否激活
        if (s.toString().trim().length() > 0) {
            if (mEditText == null) {
                if (isPhoneNumber) {
                    if (Utils.isMobileNO(s.toString().trim())) {
                        mButton1.setEnabled(true);
                    } else {
                        mButton1.setEnabled(false);
                    }
                } else {
                    mButton1.setEnabled(true);
                }
            } else {
                if (!mEditText.getText().toString().equals("")) {
                    if (mEditTextOther == null) {
                        mButton1.setEnabled(true);
                    } else {
                        if (!mEditTextOther.getText().toString().equals("")) {
                            mButton1.setEnabled(true);
                        } else {
                            mButton1.setEnabled(false);
                        }
                    }
                } else {
                    mButton1.setEnabled(false);
                }
            }
            if (mButton2 != null) {
                mButton2.setEnabled(true);
            }
        } else {
            mButton1.setEnabled(false);
            if (mButton2 != null) {
                mButton2.setEnabled(false);
            }
        }

        //文本删除按钮
        if (mDeleteIcon != null) {
            mDeleteIcon.setVisibility(s.toString().trim().length() > 0 ? View.VISIBLE : View.GONE);
        }

    }
}