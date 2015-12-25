package com.example.bill.third.retrofit;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by bill_lv on 2015/12/23.
 */
public class CustomConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, GitHubService> fromResponseBody(Type type, Annotation[] annotations) {
        return new Converter<ResponseBody, GitHubService>() {
            @Override
            public GitHubService convert(ResponseBody value) throws IOException {
                User user = new User();
                user.setName("custom converter");
                return null;
            }
        };
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new Converter<Object, RequestBody>() {
            @Override
            public RequestBody convert(Object value) throws IOException {
                return RequestBody.create(MediaType.parse(""), value.toString());
            }
        };
    }
}
