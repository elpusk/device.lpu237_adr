package kr.pe.sheep_transform.lpu237_adr;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Build;
import android.os.storage.StorageManager;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;

import static java.util.Arrays.copyOf;

import androidx.activity.result.ActivityResultLauncher;
import android.webkit.MimeTypeMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

interface DebugDefine{
    boolean WhenNoDeviceFileSelect = false;
}

interface RequestCode{
    int LOADFROM_EXTERNAL_STORAGE = 2;
    int OPEN_ROM_FILE = 3;
}

interface ManagerIntentAction{

    String USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    String USB_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    String UPDATE_LIST_NO_DEVICE = "kr.pe.sheep_transform.lpu237_adr.UPDATE_LIST_NO_DEVICE";

    String LPU237_PERMISSION = "kr.pe.sheep_transform.lpu237_adr.LPU237_PERMISSION";
    String GET_INFO_FOR_LIST = "kr.pe.sheep_transform.lpu237_adr.GET_INFO_FOR_LIST";
    String UPDATE_UID = "kr.pe.sheep_transform.lpu237_adr.UPDATE_UID";
    String GET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.GET_PARAMETERS";
    String SET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.SET_PARAMETERS";

    String BOOTLOADER_PERMISSION = "kr.pe.sheep_transform.lpu237_adr.BOOTLOADER_PERMISSION";
    String START_BOOTLOADER = "kr.pe.sheep_transform.lpu237_adr.START_BOOTLOADER";
    String SECTOR_INFO = "kr.pe.sheep_transform.lpu237_adr.SECTOR_INFO";
    String ERASE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.ERASE_SECTOR";
    String ERASE_COMPLETE = "kr.pe.sheep_transform.lpu237_adr.ERASE_COMPLETE";
    String WRITE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.WRITE_SECTOR";
    String WRITE_COMPLETE = "kr.pe.sheep_transform.lpu237_adr.WRITE_COMPLETE";
    String START_APP = "kr.pe.sheep_transform.lpu237_adr.START_APP";
    String RECOVER_PARAMETER = "kr.pe.sheep_transform.lpu237_adr.RECOVER_PARAMETER";

    String ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST";

    String ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS";
    String ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS";
    String ACTIVITY_MAIN_START_BOOT = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_MAIN_START_BOOT";

    String ACTIVITY_UPDATE_START_BOOT = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_START_BOOT";
    String ACTIVITY_UPDATE_SECTOR_INFO = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_SECTOR_INFO";
    String ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR";
    String ACTIVITY_UPDATE_DETAIL_ERASE_INFO = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_DETAIL_ERASE_INFO";
    String ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE";
    String ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR";
    String ACTIVITY_UPDATE_DETAIL_WRITE_INFO = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_DETAIL_WRITE_INFO";
    String ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE";
    String ACTIVITY_UPDATE_START_APP = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_START_APP";
    String ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS";
    String ACTIVITY_UPDATE_RECOVER_PARAMETER = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_RECOVER_PARAMETER";

    String GENERAL_TERMINATE_APP = "kr.pe.sheep_transform.lpu237_adr.GENERAL_TERMINATE_APP";
    //
    int INT_ALL_ACTION = 0xFFFFFFFF;
    int INT_UNKNOWN = 0;
    int INT_USB_ATTACHED =  0x01000001;
    int INT_USB_DETACHED =  0x01000002;
    int INT_UPDATE_LIST_NO_DEVICE =   0x01000004;
    int INT_ALL_USB =    0x010000FF;

    int INT_LPU237_PERMISSION =     0x02000000;
    int INT_GET_INFO_FOR_LIST =     0x02000001;
    int INT_UPDATE_UID =            0x02000002;
    int INT_GET_PARAMETERS =        0x02000004;
    int INT_SET_PARAMETERS =        0x02000008;
    int INT_ALL_LPU237 =         0x020000FF;

    int INT_BOOTLOADER_PERMISSION =     0x04000000;
    int INT_START_BOOTLOADER =          0x04000001;
    int INT_SECTOR_INFO =            0x04000002;
    int INT_ERASE_SECTOR =            0x04000004;
    int INT_ERASE_COMPLETE =          0x04000008;
    int INT_WRITE_SECTOR =            0x04000010;
    int INT_WRITE_COMPLETE =            0x04000020;
    int INT_START_APP =                 0x04000040;
    int INT_RECOVER_PARAMETER =         0x04000080;
    int INT_ALL_BOOTLOADER =         0x040000FF;

    int INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST = 0x08000000;
    int INT_ALL_ACTIVITY_STARTUP              = 0x080000FF;

    int INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS = 0x10000000;
    int INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS = 0x10000001;
    int INT_ACTIVITY_MAIN_START_BOOT              = 0x10000002;
    int INT_ALL_ACTIVITY_MAIN                     = 0x100000FF;

    int INT_ACTIVITY_UPDATE_START_BOOT              = 0x20000001;
    int INT_ACTIVITY_UPDATE_SECTOR_INFO             = 0x20000002;
    int INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR   = 0x20000004;
    int INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO       = 0x20000008;
    int INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE = 0x20000010;
    int INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR   = 0x20000020;
    int INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO       = 0x20000040;
    int INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE = 0x20000080;
    int INT_ACTIVITY_UPDATE_START_APP               = 0x20000100;
    int INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS = 0x20000200;
    int INT_ACTIVITY_UPDATE_RECOVER_PARAMETER       = 0x20000400;
    int INT_ALL_ACTIVITY_UPDATE                     = 0x2000FFFF;

    int INT_GENERAL_TERMINATE_APP         = 0x40000000;
    int INT_ALL_GENERAL                 = 0x400000FF;

    //extra data index of intent.
    String EXTRA_NAME_RESPONSE_INDEX = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_INDEX";
    String EXTRA_NAME_RESPONSE_SECTOR_INDEX = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_SECTOR_INDEX";
    String EXTRA_NAME_RESPONSE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_SECTOR";
    String EXTRA_NAME_RESPONSE_SECTOR_CHAIN = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_SECTOR_CHAIN";
    String EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY";
    String EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY";

}

public class Tools {
    static public String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(final byte b: a)
            sb.append(String.format("%02x ", b&0xff));
        return sb.toString();
    }
    static public String byteToHex(byte a){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02x ", a&0xff));
        return sb.toString();
    }

    static public String   StringOfHidKey( byte c_modifier, byte c_key ){
        String s_key = "";

        if( (c_modifier & KeyboardConst.HIDKEY_MOD_L_CTL) == KeyboardConst.HIDKEY_MOD_L_CTL ){
            s_key += "CTRL + ";
        }
        if( (c_modifier & KeyboardConst.HIDKEY_MOD_L_ALT) == KeyboardConst.HIDKEY_MOD_L_ALT ){
            s_key += "ALT + ";
        }
        if( (c_modifier & KeyboardConst.HIDKEY_MOD_L_SFT) == KeyboardConst.HIDKEY_MOD_L_SFT ){

            if( MapCodeToString.CvtShiftCodeToString.containsKey(c_key) ){
                if( MapCodeToString.CvtUnshiftCodeToString.containsKey(c_key) ){
                    String s_unshift_key = MapCodeToString.CvtUnshiftCodeToString.get(c_key);
                    String s_shift_key = MapCodeToString.CvtShiftCodeToString.get(c_key);
                    if( !s_shift_key.equals(s_unshift_key)){
                        s_key += "(SHIFT) + ";
                        s_key += MapCodeToString.CvtShiftCodeToString.get(c_key);
                    }
                    else{
                        s_key += "SHIFT + ";
                        s_key += MapCodeToString.CvtShiftCodeToString.get(c_key);
                    }
                }
                else {
                    s_key += "SHIFT + ";
                    s_key += MapCodeToString.CvtShiftCodeToString.get(c_key);
                }
            }
            else{
                s_key += "SHIFT + ";
                s_key += Tools.byteToHex(c_key);
            }
        }
        else{
            if( MapCodeToString.CvtUnshiftCodeToString.containsKey(c_key) ){
                s_key += MapCodeToString.CvtUnshiftCodeToString.get(c_key);
            }
            else{
                s_key += Tools.byteToHex(c_key);
            }
        }
        return s_key;
    }

    static public String getStringFromByteArray( byte[] s_array ){
        String s_data = "";

        do {
            try {
                if (s_array == null)
                    continue;
                int n_len = 0;
                for(byte c:s_array){
                    if( c == 0x00)
                        break;
                    else
                        n_len++;
                }
                if( n_len == 0 )
                    continue;
                byte[] s_array_out = Arrays.copyOf(s_array,n_len);
                s_data = new String(s_array_out, "UTF-8");  // Best way to decode using "UTF-8"
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                s_data = "";
            }
        }while (false);
        return s_data;
    }

    static public boolean is_all_zeros( byte[] s_data ){
        boolean b_zero = false;

        do{
            if( s_data == null )
                continue;

            b_zero = true;

            for( byte c : s_data){
                if( c != 0 ){
                    b_zero = false;
                    break;
                }
            }//end for
        }while(false);
        return b_zero;
    }

    static public IntentFilter getIntentFilter( int n_actions ) {
        IntentFilter filter = new IntentFilter();
        if( (n_actions & ManagerIntentAction.INT_USB_ATTACHED) != 0 )
            filter.addAction(ManagerIntentAction.USB_ATTACHED);
        if( (n_actions & ManagerIntentAction.INT_USB_DETACHED) != 0 )
            filter.addAction(ManagerIntentAction.USB_DETACHED);
        if( (n_actions & ManagerIntentAction.INT_UPDATE_LIST_NO_DEVICE) != 0 )
            filter.addAction(ManagerIntentAction.UPDATE_LIST_NO_DEVICE);

        if( (n_actions & ManagerIntentAction.INT_LPU237_PERMISSION) != 0 )
            filter.addAction(ManagerIntentAction.LPU237_PERMISSION);
        if( (n_actions & ManagerIntentAction.INT_GET_INFO_FOR_LIST) != 0 )
            filter.addAction(ManagerIntentAction.GET_INFO_FOR_LIST);
        if( (n_actions & ManagerIntentAction.INT_UPDATE_UID) != 0 )
            filter.addAction(ManagerIntentAction.UPDATE_UID);
        if( (n_actions & ManagerIntentAction.INT_GET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.GET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_SET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.SET_PARAMETERS);

        if( (n_actions & ManagerIntentAction.INT_BOOTLOADER_PERMISSION) != 0 )
            filter.addAction(ManagerIntentAction.BOOTLOADER_PERMISSION);
        if( (n_actions & ManagerIntentAction.INT_START_BOOTLOADER) != 0 )
            filter.addAction(ManagerIntentAction.START_BOOTLOADER);

        if( (n_actions & ManagerIntentAction.INT_SECTOR_INFO) != 0 )
            filter.addAction(ManagerIntentAction.SECTOR_INFO);
        if( (n_actions & ManagerIntentAction.INT_ERASE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.ERASE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_ERASE_COMPLETE) != 0 )
            filter.addAction(ManagerIntentAction.ERASE_COMPLETE);
        if( (n_actions & ManagerIntentAction.INT_WRITE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.WRITE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_WRITE_COMPLETE) != 0 )
            filter.addAction(ManagerIntentAction.WRITE_COMPLETE);
        if( (n_actions & ManagerIntentAction.INT_START_APP) != 0 )
            filter.addAction(ManagerIntentAction.START_APP);
        if( (n_actions & ManagerIntentAction.INT_RECOVER_PARAMETER) != 0 )
            filter.addAction(ManagerIntentAction.RECOVER_PARAMETER);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_MAIN_START_BOOT) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_MAIN_START_BOOT);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_START_BOOT) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_START_BOOT);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_SECTOR_INFO) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_SECTOR_INFO);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_ERASE_INFO);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_START_APP) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_START_APP);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_RECOVER_PARAMETER) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_RECOVER_PARAMETER);

        if( (n_actions & ManagerIntentAction.INT_GENERAL_TERMINATE_APP) != 0 )
            filter.addAction(ManagerIntentAction.GENERAL_TERMINATE_APP);
        return filter;
    }

    static public int getActionIntFromActionString( String s_action ){
        int n_action = ManagerIntentAction.INT_UNKNOWN;

        do{
            if( s_action == null )
                continue;
            if( s_action.equals( ManagerIntentAction.LPU237_PERMISSION )){
                n_action = ManagerIntentAction.INT_LPU237_PERMISSION;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.USB_ATTACHED)){
                n_action = ManagerIntentAction.INT_USB_ATTACHED;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.USB_DETACHED)){
                n_action = ManagerIntentAction.INT_USB_DETACHED;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.UPDATE_LIST_NO_DEVICE)){
                n_action = ManagerIntentAction.INT_UPDATE_LIST_NO_DEVICE;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.GET_INFO_FOR_LIST)){
                n_action = ManagerIntentAction.INT_GET_INFO_FOR_LIST;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.BOOTLOADER_PERMISSION)){
                n_action = ManagerIntentAction.INT_BOOTLOADER_PERMISSION;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.START_BOOTLOADER)){
                n_action = ManagerIntentAction.INT_START_BOOTLOADER;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.SECTOR_INFO)){
                n_action = ManagerIntentAction.INT_SECTOR_INFO;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ERASE_SECTOR)){
                n_action = ManagerIntentAction.INT_ERASE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ERASE_COMPLETE)){
                n_action = ManagerIntentAction.INT_ERASE_COMPLETE;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.UPDATE_UID)){
                n_action = ManagerIntentAction.INT_UPDATE_UID;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.GET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_GET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.SET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_SET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.WRITE_SECTOR)){
                n_action = ManagerIntentAction.INT_WRITE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.WRITE_COMPLETE)){
                n_action = ManagerIntentAction.INT_WRITE_COMPLETE;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.START_APP)){
                n_action = ManagerIntentAction.INT_START_APP;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.RECOVER_PARAMETER)) {
                n_action = ManagerIntentAction.INT_RECOVER_PARAMETER;
                continue;
            }


            if( s_action.equals( ManagerIntentAction.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST)){
                n_action = ManagerIntentAction.INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_MAIN_START_BOOT)){
                n_action = ManagerIntentAction.INT_ACTIVITY_MAIN_START_BOOT;
                continue;
            }
            //
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_START_BOOT)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_START_BOOT;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_ERASE_INFO)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_START_APP)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_START_APP;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_RECOVER_PARAMETER)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_RECOVER_PARAMETER;
                continue;
            }
            //
            if( s_action.equals( ManagerIntentAction.GENERAL_TERMINATE_APP)){
                n_action = ManagerIntentAction.INT_GENERAL_TERMINATE_APP;
                continue;
            }
        }while(false);
        return n_action;
    }

    static public AlertDialog showYesNoDialog(
            Context context
            ,String s_title
            ,String s_message
            ,DialogInterface.OnClickListener listener_yes
            ,DialogInterface.OnClickListener listener_no
            ,DialogInterface.OnCancelListener listener_cancel
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title);
        builder.setMessage(s_message);
        builder.setPositiveButton("YES", listener_yes);
        builder.setNegativeButton("NO",listener_no);
        builder.setOnCancelListener(listener_cancel);
        return builder.show();
    }
    static public void showOkDialog(
            Context context
            ,String s_title
            ,String s_message
            ,DialogInterface.OnClickListener listener_ok
            ,DialogInterface.OnCancelListener listener_cancel
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title);
        builder.setMessage(s_message);
        builder.setNeutralButton("OK",listener_ok);
        builder.setOnCancelListener(listener_cancel);
        builder.show();
    }

    static private DialogInterface.OnClickListener m_listener_dlg_ok_error_terminate = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is OK.
            dialog.dismiss();

            //terminate app
            ManagerDevice.getInstance().unload();

            if( ManagerDevice.getInstance().get_update_activity() != null ) {
                ManagerDevice.getInstance().get_update_activity().finishAffinity();
                ManagerDevice.getInstance().set_update_activity(null);
            }

            if( ManagerDevice.getInstance().get_main_activity() != null ) {
                ManagerDevice.getInstance().get_main_activity().finishAffinity();
                ManagerDevice.getInstance().set_main_activity(null);
            }
            if( ManagerDevice.getInstance().get_startup_activity() != null ) {
                ManagerDevice.getInstance().get_startup_activity().finishAffinity();
                ManagerDevice.getInstance().set_startup_activiy(null);
            }
            System.runFinalization();
            System.exit(0);

        }
    };
    static private DialogInterface.OnCancelListener m_listener_dlg_cancel_error_terminate = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();

            //terminate app
            ManagerDevice.getInstance().unload();
            if( ManagerDevice.getInstance().get_update_activity() != null ) {
                ManagerDevice.getInstance().get_update_activity().finishAffinity();
                ManagerDevice.getInstance().set_update_activity(null);
            }
            if( ManagerDevice.getInstance().get_main_activity() != null ) {
                ManagerDevice.getInstance().get_main_activity().finishAffinity();
                ManagerDevice.getInstance().set_main_activity(null);
            }
            if( ManagerDevice.getInstance().get_startup_activity() != null ) {
                ManagerDevice.getInstance().get_startup_activity().finishAffinity();
                ManagerDevice.getInstance().set_startup_activiy(null);
            }
            System.runFinalization();
            System.exit(0);
        }
    };

    static public void showOkDialogForErrorTerminate(
            Context context
            ,String s_flow
            ,String s_title
            ,String s_message
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title+" - "+s_flow);
        builder.setMessage(s_message);
        builder.setNeutralButton("OK",Tools.m_listener_dlg_ok_error_terminate);
        builder.setOnCancelListener(Tools.m_listener_dlg_cancel_error_terminate);
        builder.show();
    }

    static private DialogInterface.OnClickListener m_listener_dlg_ok_error = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is OK.
            dialog.dismiss();
        }
    };
    static private DialogInterface.OnCancelListener m_listener_dlg_cancel_error = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
        }
    };

    static public void showOkDialogForError(
            Context context
            ,String s_flow
            ,String s_title
            ,String s_message
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title+" - "+s_flow);
        builder.setMessage(s_message);
        builder.setNeutralButton("OK",Tools.m_listener_dlg_ok_error);
        builder.setOnCancelListener(Tools.m_listener_dlg_cancel_error);
        builder.show();
    }

    static public void selectFirmwareGreaterThenEqualApi29(Activity activity){
        //open file picker
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType("application/rom");
        intent.setType("*/*");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        String pickerInitialUri = "Download";
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(activity, intent, RequestCode.OPEN_ROM_FILE, null);

    }
    static public void selectFirmwareLessApi29(Activity activity,FileDialog.FileSelectedListener listener){
        do{
            // 현재 API 레벨이 29 미만인 경우 실행할 코드
            String s_legacyPath = System.getenv("EXTERNAL_STORAGE");
            String s_secondaryPath = System.getenv("SECONDARY_STORAGE");

            File mPath = new File(Environment.getExternalStorageDirectory() + "//");
            //File mPath = new File(s_legacyPath + "//");

            //FileDialog fileDialog = new FileDialog(activity, mPath, null);
            FileDialog fileDialog = new FileDialog(activity, mPath, ".rom");
            fileDialog.addFileListener(listener);

            fileDialog.showDialog();

        }while(false);
    }
    static public void selectFirmwareLessApi29_with_cancel(
            Activity activity
            ,FileDialog.FileSelectedListener listener
            ,DialogInterface.OnCancelListener listener_cancel
    )
    {
        do{
            String s_legacyPath = System.getenv("EXTERNAL_STORAGE");
            String s_secondaryPath = System.getenv("SECONDARY_STORAGE");

            File mPath = new File(Environment.getExternalStorageDirectory() + "//");
            //File mPath = new File(s_legacyPath + "//");
            FileDialog fileDialog = new FileDialog(activity, mPath, ".rom");
            //FileDialog fileDialog = new FileDialog(activity, mPath, null);
            fileDialog.addFileListener(listener);

            fileDialog.showDialog(listener_cancel);

        }while(false);
    }

    static public void start_main_activity( Context context ){
        if( context != null ) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
    static public void start_update_activity( Context context ){
        if( context != null ) {
            Intent intent = new Intent(context, UpdateActivity.class);
            context.startActivity(intent);
        }
    }

    static public int get_app_version_code(Context context){
        PackageInfo packageInfo = null;

        //PackageInfo 초기화
        try{
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return -1;
        }

        return packageInfo.versionCode;
    }

    static public String get_app_version_name(Context context){
        PackageInfo packageInfo = null;         //패키지에 대한 전반적인 정보

        //PackageInfo 초기화
        try{
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "";
        }

        return packageInfo.versionName;
    }

    public static File fileFromContentUri(Activity act, Uri contentUri) {
        Context context = (Context)act;
        Cursor returnCursor =   act.getContentResolver().query(contentUri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        //int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String s_org_file_name = returnCursor.getString(nameIndex);
        String fileExtension = s_org_file_name.substring(s_org_file_name.toString().lastIndexOf("."));
        String fileName = "temporary_file" + (fileExtension != null ? fileExtension : "");
        File tempFile = null;

        if(fileExtension.toLowerCase().compareTo(".rom")!=0){
            return tempFile;
        }
        try {
            tempFile = new File(context.getCacheDir(), fileName);
            if(tempFile.exists()){
                tempFile.delete();
            }
            tempFile.createNewFile();

            FileOutputStream oStream = new FileOutputStream(tempFile);
            InputStream inputStream = context.getContentResolver().openInputStream(contentUri);

            if (inputStream != null) {
                _copy(inputStream, oStream);
            }

            oStream.flush();
            oStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempFile;
    }

    private static void _copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }
}

interface FramePage{
    int PageNone = -1;
    int PageDevice = 0;
    int PageCommon = 1;
    int PageGlobal = 2;
    int PageTrack1 = 3;
    int PageTrack2 = 4;
    int PageTrack3 = 5;
    int PageiButtonTag = 6;
    int PageiButtonRemove = 7;
    int PageiButtonRemoveTag = 8;
    int PageTotal = PageiButtonRemoveTag+1;
}
