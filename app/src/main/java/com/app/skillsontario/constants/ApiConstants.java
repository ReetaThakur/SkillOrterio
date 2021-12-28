package com.app.skillsontario.constants;


import com.app.skillsontario.apiConnection.ApiClient;
import com.app.skillsontario.apiConnection.ApiInterface;

public interface ApiConstants {

    ApiInterface API_INTERFACE = ApiClient.getClient().create(ApiInterface.class);

    int LOGIN = 100;
    int SIGNUP = 101;
    int SEND_OTP = 102;
    int VERIFY_OTP = 103;
    int FORGOT_PASSWORD = 104;
    int FORGOT_USERNAME = 105;
    int SAVE_INFO_FOR_TAX = 106;
    int CHECK_EMAIL = 108;
    int CHECK_USERNAME = 109;
    int PDF_1 = 110;
    int PDF_2 = 111;
    int PDF_3 = 112;
    int DELETE_ALL_SLIP = 113;
    int COUNTRY_LIST = 114;
    int PROVINCE_LIST = 115;
    int CHECK_SIN = 116;
    int DUPLICATE_CHECK_SIN = 117;
    int ESTIMATED_REFUND =118;
    int PLAN =119;
    int TRANSACTION_HISTORY =120;
    int FINAL_SUBMISSION =121;

}
