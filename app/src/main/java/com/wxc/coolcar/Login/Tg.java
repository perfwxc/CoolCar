package com.wxc.coolcar.Login;

/**
 * Created by Chenge666 on 2018/11/19.
 */

public class Tg {

    /**
     * ext :
     * extend :
     * params : ["验证码","1234","4"]
     * sig : ecab4881ee80ad3d76bb1da68387428ca752eb885e52621a3129dcf4d9bc4fd4
     * sign : 腾讯云
     * tel : {"mobile":"13788888888","nationcode":"86"}
     * time : 1457336869
     * tpl_id : 19
     */

    //private String ext;
    //private String extend;
    private String sig;
    //private String sign;
    private TelBean tel;
    private int time;
    private int tpl_id;
    private String[] params;

    //public String getExt() {
    //return ext;
    //}

    //public void setExt(String ext) {
    //this.ext = ext;
    //}

    //public String getExtend() {
    //return extend;
    //}

    //public void setExtend(String extend) {
    //this.extend = extend;
    //}

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    //public String getSign() {
    //return sign;
    //}

    //public void setSign(String sign) {
    //this.sign = sign;
    //}

    public TelBean getTel() {
        return tel;
    }

    public void setTel(TelBean tel) {
        this.tel = tel;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTpl_id() {
        return tpl_id;
    }

    public void setTpl_id(int tpl_id) {
        this.tpl_id = tpl_id;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public static class TelBean {
        /**
         * mobile : 13788888888
         * nationcode : 86
         */

        private String mobile;
        private String nationcode;
        TelBean(String mobile,String nationcode){
            this.mobile = mobile;
            this.nationcode = nationcode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNationcode() {
            return nationcode;
        }

        public void setNationcode(String nationcode) {
            this.nationcode = nationcode;
        }
    }
}
