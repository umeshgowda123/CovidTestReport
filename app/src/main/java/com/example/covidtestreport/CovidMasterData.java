package com.example.covidtestreport;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity
    public class CovidMasterData {

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "SRFID")
        private String SRFID;

        @ColumnInfo(name = "ICMRID")
        private String ICMRID;

        @ColumnInfo(name = "NameOfPatient")
        private String NameOfPatient;

        @ColumnInfo(name = "SwabCollectedOn")
        private String SwabCollectedOn;

        @ColumnInfo(name = "NameOfTestingLab")
        private String NameOfTestingLab;

        @ColumnInfo(name = "StatePNUMBER")
        private String StatePNUMBER;

         @ColumnInfo(name = "POSITIVECONFIMRATIONDATE")
        private String POSITIVECONFIMRATIONDATE;

        @ColumnInfo(name = "DistrictPNUMBER")
        private String DistrictPNUMBER;

        @ColumnInfo(name = "Gender")
        private String Gender;

        @ColumnInfo(name = "Age")
        private String Age;

         @ColumnInfo(name = "Result")
        private String Result;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSRFID() {
            return SRFID;
        }

        public void setSRFID(String SRFID) {
            this.SRFID = SRFID;
        }

        public String getICMRID() {
            return ICMRID;
        }

        public void setICMRID(String ICMRID) {
            this.ICMRID = ICMRID;
        }

        public String getNameOfPatient() {
            return NameOfPatient;
        }

        public void setNameOfPatient(String nameOfPatient) {
            NameOfPatient = nameOfPatient;
        }

        public String getSwabCollectedOn() {
            return SwabCollectedOn;
        }

        public void setSwabCollectedOn(String swabCollectedOn) {
            SwabCollectedOn = swabCollectedOn;
        }

        public String getNameOfTestingLab() {
            return NameOfTestingLab;
        }

        public void setNameOfTestingLab(String nameOfTestingLab) {
            NameOfTestingLab = nameOfTestingLab;
        }

        public String getStatePNUMBER() {
            return StatePNUMBER;
        }

        public void setStatePNUMBER(String statePNUMBER) {
            StatePNUMBER = statePNUMBER;
        }

        public String getPOSITIVECONFIMRATIONDATE() {
            return POSITIVECONFIMRATIONDATE;
        }

        public void setPOSITIVECONFIMRATIONDATE(String POSITIVECONFIMRATIONDATE) {
            this.POSITIVECONFIMRATIONDATE = POSITIVECONFIMRATIONDATE;
        }

        public String getDistrictPNUMBER() {
            return DistrictPNUMBER;
        }

        public void setDistrictPNUMBER(String districtPNUMBER) {
            DistrictPNUMBER = districtPNUMBER;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String result) {
            Result = result;
        }
    }
