package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailList {


    @SerializedName("SRFID")
    @Expose
    private String mSRFID;

    @SerializedName("ICMRID")
    @Expose
    private String mICMRID;

    @SerializedName("NameOfPatient")
    @Expose
    private String mNameOfPatient;

    @SerializedName("SwabCollectedOn")
    @Expose
    private String mSwabCollectedOn;

    @SerializedName("NameOfTestingLab")
    @Expose
    private String mNameOfTestingLab;

    @SerializedName("StatePNUMBER")
    @Expose
    private String mStatePNUMBER;

        @SerializedName("POSITIVECONFIMRATIONDATE")
    @Expose
    private String mPOSITIVECONFIMRATIONDATE;

    @SerializedName("DistrictPNUMBER")
    @Expose
    private String mDistrictPNUMBER;

    @SerializedName("Gender")
    @Expose
    private String mGender;

    @SerializedName("Age")
    @Expose
    private String mAge;

    @SerializedName("Result")
    @Expose
    private String mResult;

    @SerializedName("imreBase64Data")
    @Expose
    private String mimreBase64Data;

    public String getmSRFID() {
        return mSRFID;
    }

    public void setmSRFID(String mSRFID) {
        this.mSRFID = mSRFID;
    }

    public String getmICMRID() {
        return mICMRID;
    }

    public void setmICMRID(String mICMRID) {
        this.mICMRID = mICMRID;
    }

    public String getmNameOfPatient() {
        return mNameOfPatient;
    }

    public void setmNameOfPatient(String mNameOfPatient) {
        this.mNameOfPatient = mNameOfPatient;
    }

    public String getmSwabCollectedOn() {
        return mSwabCollectedOn;
    }

    public void setmSwabCollectedOn(String mSwabCollectedOn) {
        this.mSwabCollectedOn = mSwabCollectedOn;
    }

    public String getmNameOfTestingLab() {
        return mNameOfTestingLab;
    }

    public void setmNameOfTestingLab(String mNameOfTestingLab) {
        this.mNameOfTestingLab = mNameOfTestingLab;
    }

    public String getmStatePNUMBER() {
        return mStatePNUMBER;
    }

    public void setmStatePNUMBER(String mStatePNUMBER) {
        this.mStatePNUMBER = mStatePNUMBER;
    }

    public String getmPOSITIVECONFIMRATIONDATE() {
        return mPOSITIVECONFIMRATIONDATE;
    }

    public void setmPOSITIVECONFIMRATIONDATE(String mPOSITIVECONFIMRATIONDATE) {
        this.mPOSITIVECONFIMRATIONDATE = mPOSITIVECONFIMRATIONDATE;
    }

    public String getmDistrictPNUMBER() {
        return mDistrictPNUMBER;
    }

    public void setmDistrictPNUMBER(String mDistrictPNUMBER) {
        this.mDistrictPNUMBER = mDistrictPNUMBER;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmResult() {
        return mResult;
    }

    public void setmResult(String mResult) {
        this.mResult = mResult;
    }

    public String getMimreBase64Data() {
        return mimreBase64Data;
    }

    public void setMimreBase64Data(String mimreBase64Data) {
        this.mimreBase64Data = mimreBase64Data;
    }
}
