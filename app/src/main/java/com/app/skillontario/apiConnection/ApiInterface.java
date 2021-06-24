package com.app.skillontario.apiConnection;


import com.app.skillontario.baseClasses.BaseResponseModel;
import com.app.skillontario.models.EventsModal;
import com.app.skillontario.models.NewsModal;
import com.app.skillontario.models.RegistrationModal;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.models.careerListModel.CareerListOutput;

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


public interface ApiInterface {

    //***********************Login/Logout APIS********************/

    @POST("user/register")
    Call<BaseResponseModel<RegistrationModal>> registerUser(@Body HashMap<String, Object> body);

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

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<EventsModal>>> getevent(@Body HashMap<String, Object> body);

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<NewsModal>>> getNews(@Body HashMap<String, Object> body);

    @Multipart
    @POST("upload/image")
    Call<BaseResponseModel> uploadFile(@PartMap Map<String, Object> params, @Part MultipartBody.Part image);


    @POST("career/list")
    Call<BaseResponseModel<ArrayList<CareerListDetails>>> getCareerList(@Body HashMap<String, Object> body);
}
