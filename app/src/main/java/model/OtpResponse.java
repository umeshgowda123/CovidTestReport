package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("MESSAGE")
    @Expose
    private String mMessage;

    @SerializedName("RESPONCE_CODE")
    @Expose
    private String mResponseCode;

    @SerializedName("STATUS")
    @Expose
    private String mStatus;

    @SerializedName("RESPONCE_OTP")
    @Expose
    private String mResponseOTP;

    @SerializedName("lstReportDetailsSorted_Return")
    @Expose
    private String DetailList;

    public String getDetailList() {
        return DetailList;
    }

    public void setDetailList(String detailList) {
        DetailList = detailList;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(String mResponseCode) {
        this.mResponseCode = mResponseCode;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getResponseOTP() {
        return mResponseOTP;
    }

    public void setResponseOTP(String mResponseOTP) {
        this.mResponseOTP = mResponseOTP;
    }
}
