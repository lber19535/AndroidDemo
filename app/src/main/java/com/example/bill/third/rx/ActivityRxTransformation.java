package com.example.bill.third.rx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bill.R;
import com.example.bill.databinding.utils.NameUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2015/12/7.
 */
public class ActivityRxTransformation extends AppCompatActivity {

    private static final String TAG = "ActivityRxTransformation";

    private static final int INTERNET_PERMISSION = 0x01;

    @Bind(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_mapping);
        ButterKnife.bind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INTERNET_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadImage();
                break;
            }
            default:
                break;
        }
    }

    /**
     * normal mapping
     * <p/>
     * 1 to 1
     *
     * @param v
     */
    public void onLoadImage(View v) {
        if (checkCallingOrSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            loadImage2();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION);
            else
                Toast.makeText(ActivityRxTransformation.this, "no internet permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImage2() {

        Observable<String> ob1 = Observable.just("http://lber19535.github.io/img/logo.png");
        Logger.d("1");
        Func1<String, Drawable> f1 = new Func1<String, Drawable>() {
            @Override
            public Drawable call(String s) {
                Logger.d("f1 start");
                BitmapDrawable drawable = null;
                try {
                    URL url = new URL(s);
                    URLConnection connection = url.openConnection();
                    drawable = new BitmapDrawable(getResources(), connection.getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Logger.d("f1 end");
                return drawable;
            }
        };
        Logger.d("2");
        Observable<Drawable> ob2 = ob1.map(f1);
        Logger.d("3");
        Action1<Drawable> action1 = new Action1<Drawable>() {
            @Override
            public void call(Drawable drawable) {
                System.out.println("action start");
                img.setImageDrawable(drawable);
                Toast.makeText(ActivityRxTransformation.this, "load image success", Toast.LENGTH_SHORT).show();
                System.out.println("action end");
            }
        };
        Logger.d("4");
        ob2.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        ob2.subscribe(action1);
        Logger.d("5");
    }

    private void loadImage() {
        Observable.just("http://lber19535.github.io/img/logo.png").map(new Func1<String, Drawable>() {
            @Override
            public Drawable call(String s) {
                BitmapDrawable drawable = null;
                try {
                    URL url = new URL(s);
                    URLConnection connection = url.openConnection();
                    drawable = new BitmapDrawable(getResources(), connection.getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return drawable;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Drawable>() {
            @Override
            public void call(Drawable drawable) {
                img.setImageDrawable(drawable);
                Toast.makeText(ActivityRxTransformation.this, "load image success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * flat mapping
     * <p/>
     * 1 to many
     * many to many
     *
     * @param v
     */
    public void onShowStudentCourse(View v) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student student = new Student(NameUtils.getRandomFirstName(), CourseUtils.getRandomCourse(), CourseUtils.getRandomCourse());
            students.add(student);
        }

        Observable.from(students.toArray(new Student[]{})).flatMap(new Func1<Student, Observable<String>>() {
            @Override
            public Observable<String> call(Student student) {
                Log.d(TAG, student.toString());
                return Observable.from(student.getCourseList());
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(ActivityRxTransformation.this, "completed, pls check the log with ActivityRxTransformation tag", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
            }
        });

    }

    class Student {
        private String name;
        private List<String> courseList = new ArrayList<>();

        public Student(String name, String... courses) {
            this.name = name;
            courseList.addAll(Arrays.asList(courses));
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<String> courseList) {
            this.courseList = courseList;
        }

        @Override
        public String toString() {
            return "name:" + name + " course: " + Arrays.toString(courseList.toArray());
        }
    }
}
