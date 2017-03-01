package com.example.examplelib.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UploadFileRequest extends PostRequest {
    private static final String BOUNDARY = "----WebKitFormBoundaryFr2fAmfLhDIE9nsj";
    private static final String ENTRY_BOUNDARY = "--" + BOUNDARY;
    private static final String END_BOUNDARY = ENTRY_BOUNDARY + "--\r\n";

    private String fileName = "";

    public UploadFileRequest(String url, String filename, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        super(url, null, listener, errorListener);
        fileName = filename;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(ENTRY_BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"");
            sb.append("filepath");
            sb.append("\";filename=\"" + fileName);
            sb.append("\"\r\nContent-Type:");
            sb.append("application/x-bittorrent");
            sb.append("\r\n\r\n");
            bos.write(sb.toString().getBytes(getParamsEncoding()));
            bos.write(getValue(fileName));
            bos.write("\r\n".getBytes(getParamsEncoding()));
            bos.write(END_BOUNDARY.getBytes(getParamsEncoding()));
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        return bos.toByteArray();
    }

    public byte[] getValue(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];
            int len = 0;

            //将读取后的数据放置在内存中---ByteArrayOutputStream
            while ((len = fis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }

            fis.close();
            baos.close();

            //返回内存中存储的数据
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            Logger.e(e.getMessage());
            return null;
        } catch (IOException e) {
            Logger.e(e.getMessage());
            return null;
        }
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + BOUNDARY;
    }
}
