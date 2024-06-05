package kr.pe.sheep_transform.lpu237_adr;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.*;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import kr.pe.sheep_transform.lpu237_adr.lib.mgmt.MgmtCallback;

public class MgmtCallbackImpl implements MgmtCallback {
    private List<Object> m_list_user_para = new ArrayList<>();
    public MgmtCallbackImpl(){

    }

    public void addUserPara(Object c){
        m_list_user_para.add(c);
    }

    public List<Object> getM_list_user_para() {
        return m_list_user_para;
    }

    public void cbDetectLpu237AfterFwUpdateInBLStartModeNoNeedPermission(List<Object> users){
        do{
            if(m_list_user_para.isEmpty()){
                continue;
            }
            if(!(m_list_user_para.get(0) instanceof UpdateActivity)){
                continue;
            }
            UpdateActivity act = (UpdateActivity)m_list_user_para.get(0);
            if(act == null){
                continue;
            }
            act.finish();//close
            m_list_user_para.remove(0);
        }while(false);
    }

    public void set_list_user_para(List<Object> m_list_user_para) {
        this.m_list_user_para = m_list_user_para;
    }

    public void cbLpu237NeedPermission(Context c, UsbManager m, UsbDevice d ){
        do{

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(
                            c
                            , 0
                            , new Intent(ManagerIntentAction.LPU237_PERMISSION)
                            , PendingIntent.FLAG_MUTABLE
                    );
            m.requestPermission(d, pendingIntent);

        }while(false);
    }
    public void cbHidbootLoaderNeedPermission(Context c,UsbManager m,UsbDevice d ){
        do{

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(
                            c
                            , 0
                            , new Intent(ManagerIntentAction.BOOTLOADER_PERMISSION)
                            , PendingIntent.FLAG_MUTABLE
                    );
            m.requestPermission(d, pendingIntent);

        }while(false);
    }

}
