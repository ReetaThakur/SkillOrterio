package com.app.skillsontario.apiConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class ConnectivityAwareUrlClient implements Interceptor {
    private final Context mContext;

    /**
     * @param context
     */
    public ConnectivityAwareUrlClient(Context context) {
        mContext = context;
    }

    /**
     * @param context
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    /**
     * @param chain
     */
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (!isOnline(mContext)) {
            throw new NoConnectivityException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    /**
     *
     */
    public static class NoConnectivityException extends IOException {
        /**
         *
         */
        @Override
        public String getMessage() {
            return "No internet connection.";
        }

    }
}