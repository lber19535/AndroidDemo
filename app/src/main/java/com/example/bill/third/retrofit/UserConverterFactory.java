package com.example.bill.third.retrofit;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by bill_lv on 2015/12/23.
 */
public class UserConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, User> fromResponseBody(Type type, Annotation[] annotations) {
        return new Converter<ResponseBody, User>() {
            @Override
            public User convert(ResponseBody value) throws IOException {
                User user = new User();
                user.setName("user converter");
                return user;
            }
        };
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new Converter<User, RequestBody>() {
            @Override
            public RequestBody convert(User value) throws IOException {
                return null;
            }
        };
    }
}
