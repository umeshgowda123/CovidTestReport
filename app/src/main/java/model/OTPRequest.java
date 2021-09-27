package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPRequest {
    @SerializedName("pMobileNumber")
    @Expose
    private String mMobileNumber;

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }
}
