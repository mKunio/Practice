package mlearn.sabachina.com.cn.retrofit;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * retrofit 底层还是依赖的OkHttp
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.190/ecp/mvc/service/cmn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUserBiz iUserBiz = retrofit.create(IUserBiz.class);
        Call<ResponseBody> users = iUserBiz.getUsers("mainEcpService/doService.json", "C9423F05B4DCB53B2CEA81BADCDC5D56D5590D10F9484C5A-4519F028E6858AB6AD273A5CF6E10C41");
        users.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String resposes = null;
                try {
                    resposes = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Gson gson = new GsonBuilder().
                System.out.println(resposes + "");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
//        MultipartBody
    }

    //    public interface IUserBiz {
//        @GET("mainEcpService/doService.json?ck=C9423F05B4DCB53B2CEA81BADCDC5D56D5590D10F9484C5A-4519F028E6858AB6AD273A5CF6E10C41")
//        Call<ResponseBody> getUsers();
//    }
    public interface IUserBiz {
        @GET
        Call<ResponseBody> getUsers(@Url String url, @Query("ck") String ck);
    }
//        TextView textView = (TextView) findViewById(R.id.edit_comment);
//        Person person = new Person();
//        person.setAge("23");
//        person.setName("张三");
//        person.setSex("男");
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(person);
//        textView.setText(jsonString);
}

//class Person {
//    private String name;
//    private String sex;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSex() {
//        return sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    private String age;
//}
