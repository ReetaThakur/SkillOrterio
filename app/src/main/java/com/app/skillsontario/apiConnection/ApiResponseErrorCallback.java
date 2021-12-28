package com.app.skillsontario.apiConnection;

public interface ApiResponseErrorCallback {

    void getApiResponse(Object responseObject, int flag);

    void getApiError(Throwable t, int flag);

  //  void getApiError500(String msg, int flag);
}