package ma.sdop.imagelist.mvvm.network.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import ma.sdop.imagelist.mvvm.network.ApiType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parkjoosung on 2017. 4. 11..
 */

public class RetrofitFactory {
    private static RetrofitFactory instance;

    private RetrofitFactory() { }

    public Retrofit getAdapter(ApiType type) {

        return getInstagramAdapter();
    }

    private Retrofit getInstagramAdapter() {
        return new Retrofit.Builder()
                .baseUrl("https://www.instagram.com/")
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitFactory getInstance() {
        if ( instance == null ) {
            synchronized (RetrofitFactory.class) {
                instance = new RetrofitFactory();
            }
        }
        return instance;
    }


}
