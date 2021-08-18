package com.app.skillontario.apiConnection;


import android.text.TextUtils;
import android.util.Log;

import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.utils.MySharedPreference;
import com.app.skillorterio.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.skillontario.constants.SharedPrefsConstants.LANGUAGE_API;
import static com.app.skillontario.constants.SharedPrefsConstants.USER_TOKEN;


public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addNetworkInterceptor(addHeaders())
                .addInterceptor(interceptor)
                .addInterceptor(new ForbiddenInterceptor("3xaUser!@3#", "9raPass@3!)#@done"))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RequestBody getRequestValue(JSONObject requestObject) {
        try {
            return RequestBody.create(new JSONObject(String.valueOf(requestObject)).toString(),
                    MediaType.parse("application/json; charset=utf-8"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = null;
        if (value != null)
            body = RequestBody.create(value, MediaType.parse("text/plain"));
        return body;
    }

    private static class ForbiddenInterceptor implements Interceptor {
        private String credentials;

        public ForbiddenInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }

    private static Interceptor addHeaders() {

        return chain -> {
            String accessToken = "";
            String language = "";
            Request.Builder request = chain.request().newBuilder();
            if (MySharedPreference.getInstance().getBooleanData(SharedPrefsConstants.IS_HEADER)) {
                if (!TextUtils.isEmpty(MySharedPreference.getInstance().getStringData(USER_TOKEN))) {
                    accessToken = MySharedPreference.getInstance().getStringData(USER_TOKEN);
                    request.addHeader("x-access-token", accessToken);
                    Log.d("Access token "," "+accessToken);
                }
            }
            language = MySharedPreference.getInstance().getStringData(LANGUAGE_API);
            if (TextUtils.isEmpty(language)) {
                language = "eng";
            }

            request.addHeader("lang", language);

            return chain.proceed(request.build());
        };
    }
}
