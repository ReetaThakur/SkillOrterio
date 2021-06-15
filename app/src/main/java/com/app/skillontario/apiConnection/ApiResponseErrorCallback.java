package com.app.skillontario.apiConnection;

public interface ApiResponseErrorCallback {

    void getApiResponse(Object responseObject, int flag);
    void getApiError(Throwable t, int flag);
}