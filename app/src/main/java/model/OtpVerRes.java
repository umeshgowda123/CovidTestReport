package model;

import com.example.covidtestreport.CovidMasterData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpVerRes {

    @SerializedName("MESSAGE")
    @Expose
    private String mMessage;

    @SerializedName("RESPONCE_CODE")
    @Expose
    private String mRESPONCE_CODE;

    @SerializedName("STATUS")
    @Expose
    private String mSTATUS;

    @SerializedName("RESPONCE_OTP")
    @Expose
    private String mRESPONCE_OTP;

    @SerializedName("lstReportDetailsSorted_Return")
    @Expose
    List<CovidMasterData> covidMasterData;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmRESPONCE_CODE() {
        return mRESPONCE_CODE;
    }

    public void setmRESPONCE_CODE(String mRESPONCE_CODE) {
        this.mRESPONCE_CODE = mRESPONCE_CODE;
    }

    public String getmSTATUS() {
        return mSTATUS;
    }

    public void setmSTATUS(String mSTATUS) {
        this.mSTATUS = mSTATUS;
    }

    public String getmRESPONCE_OTP() {
        return mRESPONCE_OTP;
    }

    public void setmRESPONCE_OTP(String mRESPONCE_OTP) {
        this.mRESPONCE_OTP = mRESPONCE_OTP;
    }

    public List<CovidMasterData> getCovidMasterData() {
        return covidMasterData;
    }

    public void setCovidMasterData(List<CovidMasterData> covidMasterData) {
        this.covidMasterData = covidMasterData;
    }
}
