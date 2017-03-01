package com.example.example.view;

public interface BaseView<T> {
    void setPresenter(T presenter);

    void drawTitleBar();
}
