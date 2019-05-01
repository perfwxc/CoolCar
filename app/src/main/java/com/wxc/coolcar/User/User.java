package com.wxc.coolcar.User;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/23.
 */

public class User implements Serializable {
    private String WD;      //
    private String XT;
    private String XYH;
    private String XYL;
    private String TIME;
    private String WZ;
    private String WD1;
    private String XT1;
    private String XYH1;
    private String XYL1;
    private String WZ1;
    private String Name;
    private String Address;
    private String Type;
    private String Num;
    private String Help;
    private String ide;
    private String T1;
    private String T2;
    private String XY;
    private String H2S;


    private int imageid;
    private int id;


    public User(String WD,int imageid, int id) {
        this.WD = WD;
        this.imageid = imageid;
        this.id=id;
    }

    public String getWD() {
        return WD;
    }

    public String getIde() {
        return ide;
    }

    public String getXT() {
        return XT;
    }

    public String getXYH() {
        return XYH;
    }

    public String getXYL() {
        return XYL;
    }

    public String getWZ() {
        return WZ;
    }

    public String getWD1() {
        return WD1;
    }

    public String getXT1() {return XT1;}

    public String getXYH1() {
        return XYH1;
    }

    public String getXYL1() {
        return XYL1;
    }

    public String getXY() { return XY; }

    public String getTIME() {
        return TIME;
    }

    public String getH2S() {
        return H2S;
    }

    public String getWZ1() { return WZ1; }

    public int getImageId() {
        return imageid;
    }

    public int getId() {
        return id;
    }

    public String getName() {return Name;}

    public String getAddress() {
        return Address;
    }

    public String getType() {
        return Type;
    }

    public String getHelp() {
        return Help;
    }

    public String getNum() { return Num; }

    public String getT1() { return T1; }

    public String getT2() { return T2; }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public void setXT(String XT) {
        this.XT = XT;
    }

    public void setXYL(String XYL) {
        this.XYL = XYL;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public void setXYH(String XYH) {
        this.XYH = XYH;
    }

    public void setWZ(String WZ) { this.WZ = WZ; }


    public void setNum(String Num) {
        this.Num = Num;
    }


    public void setH2S( String a ) { H2S = a; }
}