package kr.pe.sheep_transform.lpu237_adr;

import android.app.Application;


public class Lpu237Application extends Application
{

    @Override
    public void onCreate() {
        super.onCreate();
        ManagerDevice.getInstance().load( this );
    }

    @Override
    public void onTerminate() {
        ManagerDevice.getInstance().unload();
        super.onTerminate();
    }
}
