package com.wxc.coolcar.Main;


import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

public class PowerLED {
    boolean m_isOn;
    Camera m_Camera;
    /**
     * 闪光初始化
     */
    public PowerLED(Context context) {
        m_isOn = false;
        /**
         * 开启相机
         */
        try {
            m_Camera = Camera.open();
        }
        catch (Exception e){
            Log.d("TAG", "Error is " + e.getMessage());
            Toast.makeText(context, "请在设置中打开应用相机权限",
                    Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 打开闪光灯
     */
    public void turnOn() {
        if (!m_isOn) {
            m_isOn = true;
            try {
                Camera.Parameters mParameters;
                mParameters = m_Camera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启闪光灯
                m_Camera.setParameters(mParameters);//设置相机参数
            } catch (Exception ex) {
            }
        }
    }
    /**
     * 关闭闪光
     */

    public void turnOff() {
        if (m_isOn) {
            m_isOn = false;
            try {
                Camera.Parameters mParameters;
                mParameters = m_Camera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭闪光灯
                m_Camera.setParameters(mParameters);//设置相机参数
            } catch (Exception ex) {
            }
        }
    }
    /**
     * 释放相机资源
     */
    public void Destroy() {
        if (m_Camera != null){
            m_Camera.release();//释放相机资源
            m_Camera = null;//清空相机
        }
    }
}
