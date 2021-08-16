package com.app.skillontario.apiConnection;

import android.annotation.SuppressLint;
import android.provider.Settings;
import android.text.TextUtils;

import com.app.skillontario.baseClasses.AppController;
import com.app.skillontario.constants.SharedPrefsConstants;
import com.app.skillontario.models.CareerModal;
import com.app.skillontario.models.SignUpModel;
import com.app.skillontario.models.careerListModel.CareerListDetails;
import com.app.skillontario.requestmodal.ChangePassModal;
import com.app.skillontario.requestmodal.GetEventRequest;
import com.app.skillontario.requestmodal.UpdateProfileModal;
import com.app.skillontario.utils.MySharedPreference;

import java.util.ArrayList;
import java.util.HashMap;

import static com.app.skillontario.SignIn.SignInActivity.fcm;
import static com.app.skillontario.constants.AppConstants.FIREBASE_TOKEN;
import static com.app.skillontario.constants.SharedPrefsConstants.USER_ID;


public class RequestBodyGenerator {

    @SuppressLint("HardwareIds")
    public static HashMap<String, Object> loginUser(String email, String password) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("email", email.toLowerCase().trim());
        object.put("password", password);
        object.put("device_id", Settings.Secure.getString(AppController.context.getContentResolver(), Settings.Secure.ANDROID_ID));
        object.put("firebase_token", MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN));

        return object;
    }

    public static HashMap<String, Object> registerUser(SignUpModel signUpModel, String divece, String userType) {
        HashMap<String, Object> object = new HashMap<>();

        object.put("email", signUpModel.getEmail().toLowerCase().trim());
        object.put("password", signUpModel.getPassword());
        object.put("fname", "");
        object.put("lname", "");
        object.put("gender", "");
        object.put("school", "");
        object.put("city", "");
        object.put("province", "");
        object.put("dob", "");
        object.put("deviceType", "Android");
        object.put("deviceId", divece);
        object.put("userType", userType);
        object.put("notifyStatus", "1");
        object.put("terms", "1");

        if (MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN) != null) {
            if (MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN).equalsIgnoreCase("")) {
                object.put("fcmToken", fcm);
            } else
                object.put("fcmToken", MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN));
        } else {
            object.put("fcmToken", fcm);
        }

       // object.put("fcmToken", MySharedPreference.getInstance().getStringData(FIREBASE_TOKEN));
        object.put("status", "");

        String lang = MySharedPreference.getInstance().getStringData(SharedPrefsConstants.LANGUAGE_API);
        if (TextUtils.isEmpty(lang)) {
            lang = "eng";
        }
        object.put("lang", lang);

        return object;
    }

    /* public static HashMap<String, Object> setCareerList(String UserId,int pageNo) {
         HashMap<String, Object> object = new HashMap<>();

         object.put("cId", "");
         object.put("search", searchString);
         object.put("page", String.valueOf(pageNo));
         object.put("pageLimit", "");
         object.put("userId", UserId);
         object.put("filter", "");


         return object;
     }
 */
    public static HashMap<String, Object> setBookmark(CareerListDetails careerListDetails, String userID, String careerId) {
        HashMap<String, Object> request = new HashMap<>();
        HashMap<String, Object> subRequest = new HashMap<>();

        ArrayList<HashMap<String, Object>> listn = new ArrayList<>();
        HashMap<String, Object> updateList = new HashMap<>();


        updateList.put("_id", careerListDetails.getId());
        updateList.put("status", careerListDetails.getStatus());
        updateList.put("jobSectorId", careerListDetails.getJobSectorId());
        updateList.put("jobProfileId", careerListDetails.getJobProfileId());
        updateList.put("fee", careerListDetails.getFee());
        updateList.put("image", careerListDetails.getImage());
        updateList.put("jobDesc", careerListDetails.getJobDesc());
        updateList.put("jobResp", careerListDetails.getJobResp());
        updateList.put("jobArea", careerListDetails.getJobArea());
        updateList.put("advice", careerListDetails.getAdvice());
        updateList.put("eduReq", careerListDetails.getEduReq());
        updateList.put("traReq", careerListDetails.getTraReq());
        updateList.put("expeReq", careerListDetails.getExpeReq());
        updateList.put("createdAt", careerListDetails.getCreatedAt());
        updateList.put("updatedAt", careerListDetails.getUpdatedAt());
        updateList.put("jobSector", careerListDetails.getJobSector());
        updateList.put("jobProfile", careerListDetails.getJobProfile());
        updateList.put("JobEducatId", careerListDetails.getJobEducatId());
        updateList.put("JobEducat", careerListDetails.getJobEducat());
        listn.add(updateList);
        request.put("userId", userID);
        request.put("careerId", careerId);
        request.put("careerDetails", listn);


        return request;
    }


    public static HashMap<String, Object> forgotPassword(String email) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("email", email);
        return object;
    }

    public static HashMap<String, Object> userID() {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        return object;
    }

    public static HashMap<String, Object> changePassword(String password, String oldPassword) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("currPassword", oldPassword);
        object.put("newPassword", password);

        return object;
    }

    public static HashMap<String, Object> getCatalogue(String catalogueId, int page) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("catalogueId", catalogueId);
        object.put("page", page);
        return object;
    }

    public static HashMap<String, Object> uploadImage(int catalogueId, int lotId) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("catalogueId", catalogueId);
        object.put("lotId", lotId);
        return object;
    }

    public static HashMap<String, Object> getMiscList(int page) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("page", page);
        return object;
    }

    public static HashMap<String, Object> getList(String pickup, String condition, String keyword) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("conditionId", condition);
        object.put("pickupId", pickup);
        object.put("keywordId", keyword);
        object.put("page", "1");
        return object;
    }

    public static HashMap<String, Object> createCatalogue(String auctionId, String catalogueName, String cataloguersName, String email) {

        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", MySharedPreference.getInstance().getStringData(USER_ID));
        object.put("auctionId", auctionId);
        object.put("catalogueName", catalogueName);
        object.put("cataloguersName", cataloguersName);
        object.put("streetNumber", "");
        object.put("streetName", "");
        object.put("email", email);

        return object;
    }

    public static HashMap<String, Object> getEvent(GetEventRequest getEventRequest) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("eType", getEventRequest.geteType());
        object.put("eventId", getEventRequest.getEventId());
        object.put("page", getEventRequest.getPage());
        object.put("pageLimit", getEventRequest.getPageLimit());
        object.put("search", getEventRequest.getSearch());
        return object;
    }

    public static HashMap<String, Object> updateProfile(UpdateProfileModal updateProfileModal) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", updateProfileModal.getId());
        object.put("fname", updateProfileModal.getFname().trim());
        object.put("lname", updateProfileModal.getLname().trim());
        object.put("gender", updateProfileModal.getGender());
        object.put("email", updateProfileModal.getEmail().toLowerCase().trim());
        object.put("school", updateProfileModal.getSchool());
        object.put("dob", updateProfileModal.getDob());
        object.put("city", updateProfileModal.getCity().trim());
        object.put("country", updateProfileModal.getCountry().trim());
        return object;

    }

    public static HashMap<String, Object> ChangePass(ChangePassModal updateProfileModal) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", updateProfileModal.getUserId());
        object.put("oldPass", updateProfileModal.getOldPass());
        object.put("newPass", updateProfileModal.getNewPass());

        return object;
    }

    public static HashMap<String, Object> Logout(String User_id) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", User_id);
        return object;
    }

    public static HashMap<String, Object> setCareerFilter(int pageNo, String User_id, String sector, String education, String redf, String search) {
        HashMap<String, Object> object = new HashMap<>();

        HashMap<String, Object> hashMapfilter = new HashMap<>();

        ArrayList<String> arraysector = new ArrayList<>();
        if (!sector.equalsIgnoreCase("")) {
            String[] parts = sector.split(",");
            for (int i = 0; i < parts.length; i++) {
                arraysector.add(parts[i]);
            }
        }

        HashMap<String, Object> hashMapsector = new HashMap<>();
        hashMapsector.put("sector", arraysector);

        ArrayList<String> arrayListeducation = new ArrayList<>();
        HashMap<String, Object> hashMapeducation = new HashMap<>();
        if (!education.equalsIgnoreCase("")) {
            String[] parts = education.split(",");
            for (int i = 0; i < parts.length; i++) {
                arrayListeducation.add(parts[i]);
            }
        }
        hashMapeducation.put("education", arrayListeducation);


        HashMap<String, String> redflag = new HashMap<>();
        redflag.put("redFlag", redf);

        hashMapfilter.put("sector", arraysector);
        hashMapfilter.put("education", arrayListeducation);
        hashMapfilter.put("redFlag", redf);

        HashMap<String, Object> subRequest = new HashMap<>();
        ArrayList<Object> finalarr = new ArrayList<>();
        finalarr.add(hashMapfilter);
        object.put("cId", "");
        object.put("search", search);
        object.put("page", String.valueOf(pageNo));
        object.put("pageLimit", "");
        object.put("userId", User_id);
        object.put("filter", finalarr);
        return object;
    }

    public static HashMap<String, Object> setCareerList(String userId, int pageNo, String searchString) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("cId", "");
        object.put("search", searchString);
        object.put("page", String.valueOf(pageNo));
        object.put("pageLimit", "");
        object.put("userId", userId);
        object.put("filter", "");
        return object;
    }

    public static HashMap<String, Object> setCareerDetailData(String userId, String carrerId) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("cId", carrerId);
        object.put("search", "");
        object.put("page", "1");
        object.put("pageLimit", "");
        object.put("userId", userId);
        object.put("filter", "");
        return object;
    }

    public static HashMap<String, Object> setBookmarkadd(CareerModal careerListDetails, String userID, String careerId) {
        HashMap<String, Object> request = new HashMap<>();
        HashMap<String, Object> subRequest = new HashMap<>();

        ArrayList<HashMap<String, Object>> listn = new ArrayList<>();
        HashMap<String, Object> updateList = new HashMap<>();


        updateList.put("_id", careerListDetails.getId());
        updateList.put("status", careerListDetails.getStatus());
        updateList.put("jobSectorId", careerListDetails.getJobSectorId());
        updateList.put("jobProfileId", careerListDetails.getJobProfileId());
        updateList.put("fee", careerListDetails.getFee());
        updateList.put("image", careerListDetails.getImage());
        updateList.put("jobDesc", careerListDetails.getJobDesc());
        updateList.put("jobResp", careerListDetails.getJobResp());
        updateList.put("jobArea", careerListDetails.getJobArea());
        updateList.put("advice", careerListDetails.getAdvice());
        updateList.put("eduReq", careerListDetails.getEduReq());
        updateList.put("traReq", careerListDetails.getTraReq());
        updateList.put("expeReq", careerListDetails.getExpeReq());
        updateList.put("createdAt", careerListDetails.getCreatedAt());
        updateList.put("updatedAt", careerListDetails.getUpdatedAt());
        updateList.put("jobSector", careerListDetails.getJobSector());
        updateList.put("jobProfile", careerListDetails.getJobProfile());
        updateList.put("JobEducatId", careerListDetails.getJobEducatId());
        updateList.put("JobEducat", careerListDetails.getJobEducat());
        listn.add(updateList);
        request.put("userId", userID);
        request.put("careerId", careerId);
        request.put("careerDetails", listn);

        return request;
    }

    public static HashMap<String, Object> getBookMark(String UserId, int page, int total_count) {
        HashMap<String, Object> object = new HashMap<>();
        object.put("userId", UserId);
        object.put("page", String.valueOf(page));
        object.put("pageLimit", String.valueOf(total_count));
        return object;
    }
}
