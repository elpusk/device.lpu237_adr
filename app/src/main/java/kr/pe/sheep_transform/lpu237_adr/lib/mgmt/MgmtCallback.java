package kr.pe.sheep_transform.lpu237_adr.lib.mgmt;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.util.List;

public interface MgmtCallback {
    void addUserPara(Object c);
    void cbDetectLpu237AfterFwUpdateInBLStartModeNoNeedPermission(List<Object> users);

    void cbDetectLpu237NeedPermission(Context c, UsbManager m, UsbDevice d );
    void cbDetectHidbootLoaderNeedPermission(Context c, UsbManager m, UsbDevice d );
}
