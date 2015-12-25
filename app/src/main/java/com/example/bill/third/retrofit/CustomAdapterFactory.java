package com.example.bill.third.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit.Call;
import retrofit.CallAdapter;
import retrofit.Retrofit;

/**
 * Created by bill_lv on 2015/12/24.
 */
public class CustomAdapterFactory implements CallAdapter.Factory {
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (Utils.getRawType(returnType) != MyCall.class) {
            return null;
        } else {
            return new MyAdapter(returnType);
        }
    }

    public static class MyAdapter implements CallAdapter<MyCall<User>> {

        Type responseType;

        public MyAdapter(Type returnType) {
            this.responseType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
        }

        public Type responseType() {
            return responseType;
        }

        public <R> MyCall<User> adapt(Call<R> call) {
            return new NewCall();
        }
    }


    public static class NewCall implements MyCall<User> {

        public User string() {
            User user = new User();
            user.setName("xxxx");
            return user;
        }
    }
}
