package com.inerrsia.myapplication;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by nick on 05/10/16.
 */
public class RequestBuilder {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //Login request body
    public static RequestBody LoginBody(String username, String password, String token) {
        return new FormBody.Builder()
                .add("action", "login")
                .add("format", "json")
                .add("username", username)
                .add("password", password)
                .add("logintoken", token)
                .build();
    }

    public static Request buildURL() {
        RequestBody body = RequestBody.create(JSON, "{\"url\":\"https://www.w3.org/TR/SVGTiny12/examples/textArea01.png\"}");
        return new Request.Builder()
                .url("https://dev.projectoxford.ai")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", "32b9625266704cd88158d57de6a47eeb")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Save-Data", "on")
                .build();

    }

}
