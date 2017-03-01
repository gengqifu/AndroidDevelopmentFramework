package com.example.example.db;

import java.util.List;

interface CoCloudDaoHelperInterface {
    <T> void addData(T t);

    void deleteData(String id);

    <T> T getDataById(String id);

    List getAllData();

    boolean hasKey(String id);

    long getTotalCount();

    void deleteAll();
}
