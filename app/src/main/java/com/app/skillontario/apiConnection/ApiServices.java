package com.app.skillontario.apiConnection;


import com.app.skillontario.baseClasses.BaseResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;


public interface ApiServices {

    //***********************Login/Logout APIS********************/

    //@POST("user/signin")
   // Call<BaseResponseModel<LoginResponse>> login(@Body HashMap<String, Object> body);

    @POST("user/reset-password")
    Call<BaseResponseModel> forgotPassword(@Body HashMap<String, Object> body);

    @PATCH("user/change-password")
    Call<BaseResponseModel> changePassword(@Body HashMap<String, Object> body);

    @PATCH("user/logout")
    Call<BaseResponseModel> logout(@Body HashMap<String, Object> body);



    //***********************Notification APIS********************/




    //**********************Images APIS***************/
    @POST("lot/remove-image")
    Call<BaseResponseModel> deleteImages(@Body HashMap<String, Object> body);

    @Multipart
    @POST("upload/image")
    Call<BaseResponseModel> uploadFile(@PartMap Map<String, Object> params, @Part MultipartBody.Part image);

}
