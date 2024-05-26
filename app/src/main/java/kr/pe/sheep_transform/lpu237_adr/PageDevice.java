package kr.pe.sheep_transform.lpu237_adr;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;//android.support.v4.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;//android.support.v7.app.AppCompatActivity;
import androidx.core.content.ContextCompat;//android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.Manifest;

import java.io.File;

interface UpdateStep{
    int Reset = 0;
    int StartBootload = 1;
    int Removed = 2;
    int Connected = 3;
    int EraseFirmware = 4;
    int WriteSector = 5;
    int RunApp = 12;
    int RecoverParameter = 13;
    //
    int MAX_UPDATE_STEP = 13;//start bootloader, removed, connected, erase, write x 7, run app, recover
}

public class PageDevice implements Button.OnClickListener, FileDialog.FileSelectedListener {
    private AppCompatActivity m_activity = null;

    private TextView m_textview_info;
    private Button m_button_update;
    private String m_s_info = "";
    //private ActivityResultLauncher<Intent> m_filePickerLauncher;

    public PageDevice(AppCompatActivity activity ) {
        do {
            if( activity==null)
                continue;
            m_activity = activity;

        }while (false);
    }

    public void ini(){
        if(DebugDefine.WhenNoDeviceFileSelect) {
            return;
        }
        m_textview_info = (TextView) m_activity.findViewById(R.id.id_textview_info);
        m_button_update = (Button)m_activity.findViewById(R.id.id_button_update_firmware);
        //
        m_button_update.setOnClickListener(this);
        //////
        /*
        ActivityResultContracts.StartActivityForResult arc = new  ActivityResultContracts.StartActivityForResult();
        m_filePickerLauncher = ((ActivityResultCaller)m_activity).registerForActivityResult(
                arc,
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                            Uri uri = result.getData().getData();
                            //readRomFile(uri);
                        }
                    }
                }
        );
         */

    }

    public String get_info_string(){
        return m_s_info;
    }

    @Override public void onClick(View view){
        // firmware update button

        do {
            if (!is_external_storage_writable())
                return;

            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            if (ContextCompat.checkSelfPermission(m_activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        m_activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RequestCode.LOADFROM_EXTERNAL_STORAGE
                );
            } else {
                select_firmware();
            }
        }while(false);
    }

    public void select_firmware(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 현재 API 레벨이 29 (안드로이드 10) 이상인 경우 실행할 코드
            Tools.selectFirmwareGreaterThenEqualApi29(m_activity);
        }
        else{
            Tools.selectFirmwareLessApi29(m_activity, this);
        }
    }

    /**
     * this function will be called when a file is selected for updating firmware.
     * @param file - the selected firmware
     */
    public void fileSelected(File file) {
        do {
            Log.d(getClass().getName(), "selected file " + file.toString());

            int n_error = ManagerDevice.getInstance().check_firmware(
                    file
                    , ManagerDevice.getInstance().lpu237_getName()
                    , ManagerDevice.getInstance().lpu237_get_version_system());
            if( n_error < 0 ){
                //Log.i("fileSelected", "error : check_firmware");
                String s_error = Rom.get_error_description_firmware_index_setting(n_error);
                s_error = m_activity.getResources().getString(R.string.msg_dialog_error_invalied_firmware) + "("+s_error+")";
                Tools.showOkDialogForError(m_activity,"FU01","ERROR",s_error);
                continue;
            }

            if (ManagerDevice.getInstance().push_requst(
                    TypeRequest.Request_start_bootloader
                    , m_activity.getBaseContext()
                    , file
                    , ManagerDevice.getInstance().lpu237_getName()
                    , ManagerDevice.getInstance().lpu237_get_version_system())) {
                Log.i("fileSelected", "success : startBootLoader");
            }
            else {
                //Log.i("fileSelected", "error : startBootLoader");
                Tools.showOkDialogForError(m_activity,"FU01","ERROR",m_activity.getResources().getString(R.string.msg_dialog_error));
            }
        }while (false);

    }

    /* Checks if external storage is available for read and write */
    private boolean is_external_storage_writable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void display_device_information( int n_index  ){
        String s_v = ManagerDevice.getInstance().lpu237_getVersionSystem();
        String s_n = ManagerDevice.getInstance().lpu237_getName();

        String s_info = "@System version : ";
        s_info += ManagerDevice.getInstance().lpu237_getVersionSystem();
        s_info += ".\n";

        s_info += "[=]Connected device information\n";

        s_info += "[=]Device Type : This device is ";
        s_info += ManagerDevice.getInstance().lpu237_getDeviceType();
        s_info += " model.\n";

        s_info += "[=]Device Name : ";
        s_info += ManagerDevice.getInstance().lpu237_getName();
        s_info += ".\n";

        s_info += "[=]Device UID : ";
        s_info += ManagerDevice.getInstance().lpu237_getUid();
        s_info += ".\n";

        s_info += "[=]Decoder : ";
        s_info += ManagerDevice.getInstance().lpu237_getDecoder();
        s_info += ".\n";

        if(Lpu237Tools.is_support_mmd1100_reset(s_n,s_v)) {
            s_info += "[=]MMD1100 reset interval : ";
            s_info += ManagerDevice.getInstance().lpu237_getMmd1100ResetInterval();
            s_info += ".\n";
        }

        s_info += "[=]Device Interface : ";
        s_info += ManagerDevice.getInstance().lpu237_getInterface();
        s_info += " mode.\n";

        s_info += "[=]Device Language : ";
        s_info += ManagerDevice.getInstance().lpu237_getLanguageIndex();
        s_info += ".\n";

        s_info += "[=]Device Buzzer Frequency : ";
        s_info += ManagerDevice.getInstance().lpu237_getBuzzerFrequency();
        s_info += ".\n";

        s_info += "[=]ISO1 track status : ";
        s_info += ManagerDevice.getInstance().lpu237_getEnableTrack(0);
        s_info += ".\n";

        s_info += "[=]ISO2 track status : ";
        s_info += ManagerDevice.getInstance().lpu237_getEnableTrack(1);
        s_info += ".\n";

        s_info += "[=]ISO3 track status : ";
        s_info += ManagerDevice.getInstance().lpu237_getEnableTrack(2);
        s_info += ".\n";
        //
        m_s_info = new String(s_info);

        m_textview_info.setText(s_info);
    }
}
