package com.example.examplelib.net;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.examplelib.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class UrlConfigManager {
    private static ArrayList<URLData> urlList;

    private static void fetchUrlDataFromXml(final Context context) {
        urlList = new ArrayList<>();

        final XmlResourceParser xmlParser = context
                .getResources().getXml(R.xml.url);

        int eventCode;
        try {
            eventCode = xmlParser.getEventType();
            while (eventCode != XmlPullParser.END_DOCUMENT) {
                switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("Node".equals(xmlParser.getName())) {
                            final String key = xmlParser.getAttributeValue(null,
                                    "Key");
                            final URLData urlData = new URLData();
                            urlData.setKey(key);
                            urlData.setExpires(Long.parseLong(xmlParser
                                    .getAttributeValue(null, "Expires")));
                            urlData.setNetType(xmlParser.getAttributeValue(null,
                                    "NetType"));
                            urlData.setMockClass(xmlParser.getAttributeValue(null,
                                    "MockClass"));
                            urlData.setUrl(xmlParser.getAttributeValue(null, "Url"));
                            urlList.add(urlData);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventCode = xmlParser.next();
            }
        } catch (final XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            xmlParser.close();
        }
    }

    public static URLData findURL(final Context context,
                                  final String findKey) {
        // 如果urlList还没有数据（第一次），或者被回收了，那么（重新）加载xml
        if (urlList == null || urlList.isEmpty())
            fetchUrlDataFromXml(context);

        for (URLData data : urlList) {
            if (findKey.equals(data.getKey())) {
                return data;
            }
        }

        return null;
    }
}