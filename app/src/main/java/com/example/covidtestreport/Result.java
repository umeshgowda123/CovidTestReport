package com.example.covidtestreport;

public class Result
{
    private String SRF_id;
    private String name;
    private String DateOfTest;
    private String Result;
    private String age;
    private String name_of_lab;
    private String icmr;
    private String gender;
    private String address;
    private String sample_date;
    private String result_date;
    private String type_of_test;
    private String state_P_code;

    public Result(String SRF_id, String name, String dateOfTest, String result,String age,String name_of_lab,String icmr,String gender,String address,String sample_date,String result_date,String type_of_test,String state_P_code)
    {
        this.SRF_id = SRF_id;
        this.name = name;
        this.DateOfTest = dateOfTest;
        this.Result = result;
        this.age = age;
        this.name_of_lab = name_of_lab;
        this.icmr = icmr;
        this.gender= gender;
        this.address = address;
        this.sample_date = sample_date;
        this.result_date = result_date;
        this.type_of_test = type_of_test;
        this.state_P_code = state_P_code;

    }

    public String getName_of_lab() {
        return name_of_lab;
    }

    public void setName_of_lab(String name_of_lab) {
        this.name_of_lab = name_of_lab;
    }

    public String getIcmr() {
        return icmr;
    }

    public void setIcmr(String icmr) {
        this.icmr = icmr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSample_date() {
        return sample_date;
    }

    public void setSample_date(String sample_date) {
        this.sample_date = sample_date;
    }

    public String getResult_date() {
        return result_date;
    }

    public void setResult_date(String result_date) {
        this.result_date = result_date;
    }

    public String getType_of_test() {
        return type_of_test;
    }

    public void setType_of_test(String type_of_test) {
        this.type_of_test = type_of_test;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSRF_id() {
        return SRF_id;
    }

    public void setSRF_id(String SRF_id) {
        this.SRF_id = SRF_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfTest() {
        return DateOfTest;
    }

    public void setDateOfTest(String dateOfTest) {
        this.DateOfTest = dateOfTest;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        this. Result = result;
    }

    public String getState_P_code() {
        return state_P_code;
    }

    public void setState_P_code(String state_P_code) {
        this.state_P_code = state_P_code;
    }
}

