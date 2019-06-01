package com.wxc.coolcar.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/23.
 */

public class User implements Serializable {
    private String userTemperature;         //人体温度
    private String heartRate;               //心率
    private String highBloodPressure;       //高血压
    private String lowBloodPressure;        //低血压
    private String SaO2;                    //血氧浓度
    private String H2S;                     //H2S浓度
    private String Lux;                     //光照强度
    private String environmentTemperature;  //环境温度
    private String ambientHumidity;         //环境湿度
    private String smokeDensity;            //烟雾浓度
    private String CODensity;               //CO浓度
    private String alcoholConcentration;    //酒精浓度
    private String Name;                    //用户名
    private String Address;                 //用户地址
    private String Type;                    //用户车型
    private String telephone;               //电话号码
    private String emergencyPhone;          //应急电话
    private String ide;                     //编号
    private String TIME;                    //测量时间
    private String timeStamp1;              //时间戳第一部分
    private String timeStamp2;              //时间戳第二部分
    private int imageId;
    private int id;

    public User(String userTemperature, int imageId, int id) {
        this.userTemperature = userTemperature;
        this.imageId = imageId;
        this.id = id;
    }

    public String getUserTemperature() {
        return userTemperature;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getHighBloodPressure() {
        return highBloodPressure;
    }

    public void setHighBloodPressure(String highBloodPressure) {
        this.highBloodPressure = highBloodPressure;
    }

    public String getLowBloodPressure() {
        return lowBloodPressure;
    }

    public void setLowBloodPressure(String lowBloodPressure) {
        this.lowBloodPressure = lowBloodPressure;
    }

    public String getLux() {
        return Lux;
    }

    public void setLux(String lux) {
        this.Lux = lux;
    }

    public String getEnvironmentTemperature() {
        return environmentTemperature;
    }

    public void setEnvironmentTemperature(String environmentTemperature) {
        this.environmentTemperature = environmentTemperature;
    }

    public String getAmbientHumidity() {
        return ambientHumidity;
    }

    public void setAmbientHumidity(String ambientHumidity) {
        this.ambientHumidity = ambientHumidity;
    }

    public String getSmokeDensity() {
        return smokeDensity;
    }

    public void setSmokeDensity(String smokeDensity) {
        this.smokeDensity = smokeDensity;
    }

    public String getCODensity() {
        return CODensity;
    }

    public void setCODensity(String CODensity) {
        this.CODensity = CODensity;
    }

    public String getSaO2() {
        return SaO2;
    }

    public void setSaO2(String saO2) {
        SaO2 = saO2;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getH2S() {
        return H2S;
    }

    public void setH2S(String H2S) {
        this.H2S = H2S;
    }

    public String getAlcoholConcentration() {
        return alcoholConcentration;
    }

    public void setAlcoholConcentration(String alcoholConcentration) {
        this.alcoholConcentration = alcoholConcentration;
    }

    public int getImageId() {
        return imageId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String Num) {
        this.telephone = Num;
    }

    public String getTimeStamp1() {
        return timeStamp1;
    }

    public void setTimeStamp1(String timeStamp1) {
        this.timeStamp1 = timeStamp1;
    }

    public String getTimeStamp2() {
        return timeStamp2;
    }

    public void setTimeStamp2(String timeStamp2) {
        this.timeStamp2 = timeStamp2;
    }

    public void setUserTemperature(String userTemperature) {
        this.userTemperature = userTemperature;
    }
}