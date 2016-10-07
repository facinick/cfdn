package com.inerrsia.myapplication;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ApiCall {
    public static final MediaType JSON
            = MediaType.parse("application/octet-stream");

    //GET network request
    public static String GET(OkHttpClient client, HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    //POST network request
    public static String POST(OkHttpClient client, byte[] rbody) throws IOException {
        RequestBody DATA = RequestBody.create(JSON, rbody);
        Request request = new Request.Builder()
                .url("https://api.projectoxford.ai/vision/v1.0/ocr?language=unk&detectOrientation =true")
                .post(DATA)
                .addHeader("Ocp-Apim-Subscription-Key", "32b9625266704cd88158d57de6a47eeb")
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Save-Data", "on")
                .build();

//        RequestBody body = RequestBody.create(JSON, "{\"url\":\"https://www.w3.org/TR/SVGTiny12/examples/textArea01.png\"}");
//        Request request = new Request.Builder()
//                .url("https://api.projectoxford.ai/vision/v1.0/ocr?language=unk&detectOrientation =true")
//                .post(body)
//                .addHeader("Ocp-Apim-Subscription-Key", "32b9625266704cd88158d57de6a47eeb")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("X-Requested-With", "XMLHttpRequest")
//                .addHeader("Save-Data", "on")
//                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}