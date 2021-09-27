package api;

import io.reactivex.Observable;
import model.OTPRequest;
import model.OtpResponse;
import model.OtpVerRes;
import model.TokenRes;
import model.ValidOTPRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("api/Values/FN_SEND_OTP")
    Call<OtpResponse> FnSendOtp(
            @Body OTPRequest otpRequest
    );

    @POST("api/Values/FN_VALIDATE_OTP")
    Call<OtpVerRes> FnValidateOtp(
        //    @Body "pMobileNumber" String pMobileNumber,
            @Body ValidOTPRequest validOTPRequest
    );
    @FormUrlEncoded
    @POST("token")
    Call<TokenRes> getToken(@Field("username") String email, @Field("password") String password, @Field("grant_type") String granttype);


}

