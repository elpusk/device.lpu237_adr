package kr.pe.sheep_transform.lpu237_adr;

import android.app.Application;

import kr.pe.sheep_transform.lpu237_adr.lib.mgmt.ManagerDevice;


public class Lpu237Application extends Application
{

    @Override
    public void onCreate() {
        super.onCreate();
        ManagerDevice.getInstance().load( this,new MgmtCallbackImpl() );
        Manager.getInstance().load(this);
    }

    @Override
    public void onTerminate() {
        ManagerDevice.getInstance().unload();
        Manager.getInstance().unload();
        super.onTerminate();
    }
}
