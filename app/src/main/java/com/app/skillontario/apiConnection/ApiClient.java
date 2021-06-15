package com.app.skillontario.apiConnection;


import android.text.TextUtils;
import android.util.Log;

import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.skillontario.constants.AppConstants.AUTH_TOKEN;


public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(addHeaders())
                .addInterceptor(new BasicAuthInterceptor("3xaUser!@3#", "9raPass@3!)#@done"))
                .readTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(0,loggingInterceptor);

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Add custom headers
     * @return Header Interceptor
     */
    private static Interceptor addHeaders() {
        return chain -> {
            String accessToken;
            Request.Builder request = chain.request().newBuilder();
            if (!TextUtils.isEmpty(MySharedPreference.getInstance().getStringData(AUTH_TOKEN))) {
                accessToken = MySharedPreference.getInstance().getStringData(AUTH_TOKEN);
                Log.e("accessToken", accessToken);
                request.addHeader("x-access-token", accessToken);
            }
            return chain.proceed(request.build());
        };
    }

    public static RequestBody getRequestValue(JSONObject requestObject){
        try {
            return RequestBody.create(new JSONObject(String.valueOf(requestObject)).toString(),
                    MediaType.parse("application/json; charset=utf-8"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body=null;
        if(value!=null)
            body= RequestBody.create(value, MediaType.parse("text/plain"));
        return body;
    }
}
