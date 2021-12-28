package com.app.skillsontario.apiConnection;


import com.app.skillsontario.baseClasses.BaseResponseModel;
import com.app.skillsontario.models.CareerDetailModel;
import com.app.skillsontario.models.EventsModal;
import com.app.skillsontario.models.FeedbackModal;
import com.app.skillsontario.models.HomeModal;
import com.app.skillsontario.models.InterestModal;
import com.app.skillsontario.models.NewsModal;
import com.app.skillsontario.models.NotificationModal;
import com.app.skillsontario.models.PartnerModal;
import com.app.skillsontario.models.RegistrationModal;
import com.app.skillsontario.models.ResourceModal;
import com.app.skillsontario.models.ScholarModel;
import com.app.skillsontario.models.ScholarShipModal;
import com.app.skillsontario.models.careerListModel.CareerListDetails;
import com.app.skillsontario.models.quizModel.QuizResultModel;
import com.app.skillsontario.models.quizModel.ResultModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


public interface ApiInterface {

    //***********************Login/Logout APIS********************/

    @POST("user/register")
    Call<BaseResponseModel<RegistrationModal>> registerUser(@Body HashMap<String, Object> body);

    @POST("user/login")
    Call<BaseResponseModel<RegistrationModal>> loginUser(@Body HashMap<String, Object> body);


    @PATCH("user/reset-password")
    Call<BaseResponseModel> forgotPassword(@Body HashMap<String, Object> body);

    @PATCH("user/change-password")
    Call<BaseResponseModel> changePassword(@Body HashMap<String, Object> body);

    @PATCH("user/logout")
    Call<BaseResponseModel> logout(@Body HashMap<String, Object> body);

    @PATCH("user/update")
    Call<BaseResponseModel<RegistrationModal>> updateProfile(@Body HashMap<String, Object> body);

    @GET("job/get-option")
    Call<BaseResponseModel<ArrayList<InterestModal>>> jobOption();

    @POST("user/home")
    Call<BaseResponseModel<ArrayList<HomeModal>>> getHomeData(@Body HashMap<String, Object> body);

    //***********************Notification APIS********************/
    @PATCH("user/update")
    Call<BaseResponseModel> updateUser(@Body HashMap<String, Object> body);

    //**********************Images APIS***************/
    @POST("lot/remove-image")
    Call<BaseResponseModel> deleteImages(@Body HashMap<String, Object> body);

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<EventsModal>>> getevent(@Body HashMap<String, Object> body);

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<ScholarModel>>> geteventListScholar(@Body HashMap<String, Object> body);


    @POST("event/list")
    Call<BaseResponseModel<ArrayList<NewsModal>>> getNews(@Body HashMap<String, Object> body);

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<PartnerModal>>> getPartner(@Body HashMap<String, Object> body);

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<ResourceModal>>> getresource(@Body HashMap<String, Object> body);

    @POST("event/list")
    Call<BaseResponseModel<ArrayList<ScholarShipModal>>> getScholarShip(@Body HashMap<String, Object> body);

    @Multipart
    @POST("upload/image")
    Call<BaseResponseModel> uploadFile(@PartMap Map<String, Object> params, @Part MultipartBody.Part image);


    @POST("career/list")
    Call<BaseResponseModel<ArrayList<CareerListDetails>>> getCareerList(@Body HashMap<String, Object> body);

    @POST("user/feedback")
    Call<BaseResponseModel> getFeedBack(@Body HashMap<String, Object> body);

   /* @POST("career/add-book-mark")
    Call<BaseResponseModel> addCareerBookmark(@Body HashMap<String, Object> body);
*/

    @HTTP(method = "DELETE", path = "career/delete-book-mark", hasBody = true)
    Call<BaseResponseModel> deleteCareerBookmark(@Body HashMap<String, Object> body);

   /* @PATCH("user/update")
    Call<BaseResponseModel> updateUser(@Body HashMap<String, Object> body);
*/

    //***********************Quiz List APIS********************/
    @POST("quiz/list")
    Call<BaseResponseModel<ArrayList<ResultModel>>> getQuizListQuestion(@Body HashMap<String, Object> body);

    @POST("quiz/result")
    Call<BaseResponseModel<ArrayList<QuizResultModel>>> getQuizResult(@Body HashMap<String, Object> body);

    @POST("quiz/result/list")
    Call<BaseResponseModel<ArrayList<CareerListDetails>>> getQuizResultList(@Body HashMap<String, Object> body);

   /* @POST("career/list")
    Call<BaseResponseModel<ArrayList<CareerListDetails>>> getCareerList(@Body HashMap<String, Object> body);
*/

    @POST("career/add-book-mark")
    Call<BaseResponseModel<CareerDetailModel>> addCareerBookmark(@Body HashMap<String, Object> body);


    /* @HTTP(method = "DELETE", path = "career/delete-book-mark", hasBody = true)
     Call<BaseResponseModel> deleteCareerBookmark(@Body HashMap<String, Object> body);
 */
//user/notification notify/list
    @POST("notify/list")
    Call<BaseResponseModel<ArrayList<NotificationModal>>> getNitification(@Body HashMap<String, Object> body);

    @POST("notify/read")
    Call<BaseResponseModel<ArrayList<NotificationModal>>> readNitification(@Body HashMap<String, Object> body);

    @POST("career/list-book-mark")
    Call<BaseResponseModel<ArrayList<CareerListDetails>>> getBookMarkList(@Body HashMap<String, Object> body);

    @POST("user/feedback")
    Call<BaseResponseModel<FeedbackModal>> Sendfeedback(@Body HashMap<String, Object> body);

}
