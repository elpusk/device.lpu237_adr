package kr.pe.sheep_transform.lpu237_adr;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;//android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;//android.support.v7.app.AppCompatActivity
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class UpdateActivity extends AppCompatActivity implements FileDialog.FileSelectedListener {

    private BroadcastReceiver m_recevier = null;
    private TextView m_textview_info;

    private TextView m_textview_start_boot;
    private TextView m_textview_erase;
    private TextView m_textview_write;
    private TextView m_textview_start_app;
    private TextView m_textview_recover;
    private File m_fw_file = null;
    private Bundle m_outState = null;
    private AlertDialog m_dlg_exit = null;

    private TextView [] m_textview_step = new TextView[5];

    private DialogInterface.OnClickListener m_listener_dlg_stop_yes = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is Yes.
            m_dlg_exit = null;
            dialog.dismiss();
            //UpdateActivity.super.onBackPressed();
            ManagerDevice.getInstance().unload();
            //
            if( ManagerDevice.getInstance().get_update_activity() != null )
                ManagerDevice.getInstance().get_update_activity().finishAffinity();
            if( ManagerDevice.getInstance().get_startup_activity() != null ) {
                ManagerDevice.getInstance().get_startup_activity().finishAffinity();
            }
            System.runFinalization();
            System.exit(0);

        }
    };
    private DialogInterface.OnClickListener m_listener_dlg_stop_no = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is No.
            m_dlg_exit = null;
            dialog.dismiss();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_stop_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            m_dlg_exit = null;
            dialog.dismiss();
        }
    };

    private DialogInterface.OnCancelListener m_listener_file_select_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // file selection is canceled,
            dialog.dismiss();
            Tools.showOkDialogForErrorTerminate(UpdateActivity.this,"FS02","Notice",UpdateActivity.this.getResources().getString(R.string.msg_dialog_need_download_terminate));
        }
    };

    private DialogInterface.OnCancelListener m_listener_fw_select_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // file selection is canceled,
            dialog.dismiss();
            Tools.showOkDialogForErrorTerminate(UpdateActivity.this,"FS04","Notice",UpdateActivity.this.getResources().getString(R.string.msg_dialog_need_download_terminate));
        }
    };


    /////////////////////////////
    // control area
    private Button m_button_exit;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        m_outState = outState;
        outState.putString("m_textview_info", m_textview_info.getText().toString());
        outState.putString("m_textview_start_boot", m_textview_start_boot.getText().toString());
        outState.putString("m_textview_erase", m_textview_erase.getText().toString());
        outState.putString("m_textview_write", m_textview_write.getText().toString());
        outState.putString("m_textview_start_app", m_textview_start_app.getText().toString());
        outState.putString("m_textview_recover", m_textview_recover.getText().toString());
        //

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if( savedInstanceState != null ) {
            m_textview_info.setText(savedInstanceState.getString("m_textview_info"));
            m_textview_start_boot.setText(savedInstanceState.getString("m_textview_start_boot"));
            m_textview_erase.setText(savedInstanceState.getString("m_textview_erase"));
            m_textview_write.setText(savedInstanceState.getString("m_textview_write"));
            m_textview_start_app.setText(savedInstanceState.getString("m_textview_start_app"));
            m_textview_recover.setText(savedInstanceState.getString("m_textview_recover"));
        }

        super.onRestoreInstanceState(savedInstanceState);
        m_outState = null;
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RequestCode.OPEN_ROM_FILE) {
            Uri uri = data.getData();
            m_fw_file = Tools.fileFromContentUri(this,uri);
            if(m_fw_file != null) {
                showFwSelectDialog();//select fw in rom file
            }
            else{
                String s_error = "Invalid file.(" + uri.toString() + ")";
                Tools.showOkDialogForError(this,"FU01","ERROR",s_error);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //
        String s_app_version = Tools.get_app_version_name(this);
        TextView view_title = (TextView)findViewById(R.id.id_textview_title);
        s_app_version = view_title.getText() + " - " + s_app_version;
        view_title.setText( s_app_version );

        m_button_exit = (Button)findViewById(R.id.id_button_exit);

        m_button_exit.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                m_dlg_exit = Tools.showYesNoDialog(
                        UpdateActivity.this
                        ,"Warning!"
                        ,"Do you wan to stop the updating firmware?\r\n" +
                                "If this process is stopped, the device cannot be used." +
                                "and App is terminated."
                        ,m_listener_dlg_stop_yes
                        ,m_listener_dlg_stop_no
                        ,m_listener_dlg_stop_cancel
                );
            }

        });


        m_textview_info = (TextView) findViewById(R.id.id_textview_info);
        m_textview_start_boot = findViewById(R.id.id_textview_step0);//start boot
        m_textview_erase = findViewById(R.id.id_textview_step1);//erase
        m_textview_write = findViewById(R.id.id_textview_step2);//write
        m_textview_start_app = findViewById(R.id.id_textview_step3);//start app
        m_textview_recover = findViewById(R.id.id_textview_step4);//recover
        //
        m_textview_start_boot.setText(" SUCCESS : Starts Bootloader.");
        ManagerDevice.getInstance().set_update_activity(this);

        // recovering the instance state
        if (savedInstanceState == null) {
            if (ManagerDevice.getInstance().is_startup_with_bootloader()) {
                m_textview_info.setText("Select a Rom file for updating.");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // 현재 API 레벨이 29 (안드로이드 10) 이상인 경우 실행할 코드
                    Tools.selectFirmwareGreaterThenEqualApi29(this);
                }
                else {
                    Tools.selectFirmwareLessApi29_with_cancel(this, this, m_listener_file_select_cancel);
                }
            } else {
                m_textview_info.setText("Please Waits!  Getting the sector info of system.");
                if (!ManagerDevice.getInstance().push_requst(TypeRequest.Request_firmware_sector_info, this)) {
                    Tools.showOkDialogForErrorTerminate(this, "FU06", "ERROR", this.getResources().getString(R.string.msg_dialog_error_reboot));
                }
            }
        }
        registerReceiver();
        Log.i("onCreate","UpdateActivity : onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ManagerDevice.getInstance().stop_update_activity(false);
        ManagerDevice.getInstance().showFwDownloadOk();
        Log.i("onResume","UpdateActivity : onResume");
    }

    @Override
    protected void onPause() {
        if( m_dlg_exit != null ) {
            m_dlg_exit.dismiss();
            m_dlg_exit = null;
        }
        super.onPause();
        Log.i("onPause","UpdateActivity : onPause");
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
        ManagerDevice.getInstance().set_update_activity(null);
        ManagerDevice.getInstance().stop_update_activity(true);
        Log.i("onDestroy","UpdateActivity : onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ManagerDevice.getInstance().stop_update_activity(true);
        Log.i("onStop","UpdateActivity : onStop");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if( m_dlg_exit != null ) {
            m_dlg_exit.dismiss();
            m_dlg_exit = null;
        }

        m_dlg_exit = Tools.showYesNoDialog(
                this
                ,"Warning!"
                ,"Do you wan to stop the updating firmware?\r\n" +
                        "If this process is stopped, the device cannot be used." +
                        "and App is terminated."
                ,m_listener_dlg_stop_yes
                ,m_listener_dlg_stop_no
                ,m_listener_dlg_stop_cancel
        );

    }

    private void registerReceiver(){
        do{
            if( m_recevier != null )
                continue;
            //
            this.m_recevier = new UpdateActivity.DeviceBroadcastReceiver(this);

            this.registerReceiver(this.m_recevier, Tools.getIntentFilter(
                    ManagerIntentAction.INT_ALL_ACTIVITY_UPDATE | ManagerIntentAction.INT_GENERAL_TERMINATE_APP
            ));
        }while(false);
    }

    private void unregisterReceiver(){
        do{
            if( m_recevier == null )
                continue;
            this.unregisterReceiver(m_recevier);
            m_recevier = null;
        }while(false);
    }

    private boolean showFwSelectDialog(){
        boolean b_result = false;
        do {
            if( m_fw_file == null )
                continue;
            //
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this
                    , android.R.layout.select_dialog_singlechoice
            );
            //
            Rom rom = new Rom();
            if (rom.load_rom_header(m_fw_file) != RomResult.result_success)
                continue;
            //
            int n_number_fw = rom.get_the_number_of_firmware();
            if( n_number_fw <=0 )
                continue;
            for( int i=0; i<n_number_fw; i++ ){
                adapter.add(rom.get_device_name_of_firmware(i));
            }//end for

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setAdapter(
                    adapter
                    , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String s_name = adapter.getItem(which);
                            int n_index = which;
                            //
                            dialog.dismiss();

                            ManagerDevice.getInstance().set_rom_file(0,m_fw_file,n_index);
                            m_textview_info.setText("Please Waits!  Getting the sector info of system.");
                            if (ManagerDevice.getInstance().push_requst(TypeRequest.Request_firmware_sector_info,UpdateActivity.this)) {
                                Log.i("fileSelected", "success : Request_firmware_sector_info");
                            } else {
                                Log.i("fileSelected", "error : Request_firmware_sector_info");
                                Tools.showOkDialogForErrorTerminate(UpdateActivity.this,"FS04","ERROR",UpdateActivity.this.getResources().getString(R.string.msg_dialog_error_reboot));
                            }
                        }
                    }
            );
            builder.setOnCancelListener( m_listener_fw_select_cancel);
            builder.show();
            //
            b_result = true;
        }while(false);
        return b_result;
    }

    /**
     * this routine is called when a file in selected.( When the app is starting,
     * it detects a hidbootloader and try download a firmware for recovering device.)
     * @param file - the selected file
     */
    public void fileSelected(File file) {
        do {
            //when selected firmware. your action.
            Log.d(getClass().getName(), "selected file " + file.toString());

            m_fw_file = file;

            if( !showFwSelectDialog() ){
                Tools.showOkDialogForErrorTerminate(this,"FS03","Notice",this.getResources().getString(R.string.msg_dialog_need_download_terminate));
                continue;
            }
        }while (false);

    }

    class DeviceBroadcastReceiver extends BroadcastReceiver {
        private AppCompatActivity m_activity;

        public DeviceBroadcastReceiver() {
            super();
        }

        public DeviceBroadcastReceiver(AppCompatActivity activity) {
            super();
            this.m_activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            do {
                if (m_activity == null)
                    continue;
                ManagerDevice.Response response = null;

                switch (Tools.getActionIntFromActionString(intent.getAction())) {
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_START_BOOT:
                        _callback_start_boot(context, intent);
                        break;
                    //bootloader case
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_SECTOR_INFO:
                        _callback_sector_info(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR:
                        _callback_complete_erase_sector(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO:
                        _callback_erase_info(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE:
                        _callback_complete_erase_firmware(context, intent);
                        break;

                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR:
                        _callback_complete_write_sector(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO:
                        _callback_write_info(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE:
                        _callback_complete_write_firmware(context, intent);
                        break;

                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_START_APP:
                        _callback_start_app(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS:
                        _callback_get_parameters(context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_UPDATE_RECOVER_PARAMETER:
                        _callback_recover_parameter(context, intent);
                        break;
                    case ManagerIntentAction.INT_GENERAL_TERMINATE_APP:
                        ManagerDevice.getInstance().set_update_activity(null);
                        finish();
                    default:
                        Log.i("UpdateAct::onReceive", intent.getAction());
                        break;
                }//end switch
                //
            } while (false);

        }
        private boolean _callback_start_boot(Context context, Intent intent){
            boolean b_result =false;
            String s_start;

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    s_start = " ERROR : Starts Bootloader.";
                    //Toast.makeText(getApplicationContext(), "ERROR : Starts Bootloader.", Toast.LENGTH_SHORT).show();
                }
                else {
                    s_start = " SUCCESS : Starts Bootloader.";
                }
                m_textview_start_boot.setText(s_start);
                if( m_outState != null ){
                    m_outState.putString("m_textview_start_boot", s_start);
                }
            }while(false);
            return b_result;
        }

        private boolean _callback_sector_info(Context context, Intent intent){
            boolean b_result =false;
            String s_info="";
            String s_erase="";

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                int n_sector = intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY,-1);

                if( !b_result ){
                    s_info = "Please Reboot your system. - FU07.";
                    s_erase = " ERROR : Get Sectors Info."
                            +String.valueOf(n_sector)
                            +".";
                    //Toast.makeText(getApplicationContext(), "ERROR : Erase firmware.", Toast.LENGTH_SHORT).show();
                    continue;
                }
                //one sector erase ok.
                if( n_sector == -1 ){
                    s_erase = " SUCCESS : Erase a sector "
                            +String.valueOf(n_sector)
                            +".";
                    continue;
                }
                s_info = "Please Waits! Starting Erase-sector "+String.valueOf(n_sector+1)+".";
                s_erase = " SUCCESS : Get Sectors Info "+String.valueOf(n_sector)+".";

                m_textview_info.setText(s_info);
                m_textview_erase.setText(s_erase);
                if( m_outState != null ){
                    m_outState.putString("m_textview_info", s_info);
                    m_outState.putString("m_textview_erase", s_erase);
                }
            }while(false);

            if( !s_info.isEmpty() )
                m_textview_info.setText(s_info);
            if( !s_erase.isEmpty() )
                m_textview_erase.setText(s_erase);
            if( m_outState != null ){
                if( !s_info.isEmpty() )
                    m_outState.putString("m_textview_info", s_info);
                if( !s_erase.isEmpty() )
                    m_outState.putString("m_textview_erase", s_erase);
            }

            return b_result;
        }
        ////////////////////////////
        // erase
        private boolean _callback_complete_erase_sector(Context context, Intent intent){
            boolean b_result =false;
            String s_info="";
            String s_erase="";

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                int n_sector = intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY,-1);

                if( !b_result ){
                    s_info = "Please Reboot your system. - FU07.";
                    s_erase = " ERROR : Erase firmware."
                            +String.valueOf(n_sector)
                            +".";
                    //Toast.makeText(getApplicationContext(), "ERROR : Erase firmware.", Toast.LENGTH_SHORT).show();
                    continue;
                }
                //one sector erase ok.
                if( n_sector == -1 ){
                    s_erase = " SUCCESS : Erase a sector "
                            +String.valueOf(n_sector)
                            +".";
                    continue;
                }
                s_info = "Please Waits! Erasing the sector "+String.valueOf(n_sector+1)+".";
                s_erase = " SUCCESS : Erase the sector "+String.valueOf(n_sector)+".";

                m_textview_info.setText(s_info);
                m_textview_erase.setText(s_erase);
                if( m_outState != null ){
                    m_outState.putString("m_textview_info", s_info);
                    m_outState.putString("m_textview_erase", s_erase);
                }
            }while(false);

            if( !s_info.isEmpty() )
                m_textview_info.setText(s_info);
            if( !s_erase.isEmpty() )
                m_textview_erase.setText(s_erase);
            if( m_outState != null ){
                if( !s_info.isEmpty() )
                    m_outState.putString("m_textview_info", s_info);
                if( !s_erase.isEmpty() )
                    m_outState.putString("m_textview_erase", s_erase);
            }

            return b_result;
        }
        private boolean _callback_erase_info(Context context, Intent intent){
            boolean b_result =true;
            return b_result;
        }
        private boolean _callback_complete_erase_firmware(Context context, Intent intent){
            boolean b_result =false;
            String s_info="";
            String s_erase="";

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                //b_result is always true.
                if( !b_result ){
                    s_erase = " ERROR : Complete Erase firmware.";
                    //Toast.makeText(getApplicationContext(), "ERROR : complete firmware.", Toast.LENGTH_LONG).show();
                    continue;
                }
                s_info = "Please Waits! Starts Writing Sector.";
                s_erase = " SUCCESS : Erase a Firmware.";
            }while(false);

            if( !s_info.isEmpty() )
                m_textview_info.setText(s_info);
            if( !s_erase.isEmpty() )
                m_textview_erase.setText(s_erase);
            if( m_outState != null ){
                if( !s_info.isEmpty() )
                    m_outState.putString("m_textview_info", s_info);
                if( !s_erase.isEmpty() )
                    m_outState.putString("m_textview_erase", s_erase);
            }

            return b_result;
        }


        ////////////////////////////
        // write
        private boolean _callback_complete_write_sector(Context context, Intent intent){
            boolean b_result =false;
            String s_info="";
            String s_write="";

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                int n_sector = intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY,-1);

                if( !b_result ){
                    s_info = "Please Reboot your system. - FU09.";
                    s_write = " ERROR : Write a sector."
                            +String.valueOf(n_sector)
                            +".";
                    //Toast.makeText(getApplicationContext(), "ERROR : write sector.", Toast.LENGTH_LONG).show();
                    continue;
                }
                //one sector write ok.
                if( n_sector == -1 ){
                    s_write = " SUCCESS : Write a sector "
                            +String.valueOf(n_sector)
                            +".";
                    continue;
                }
                s_info = "Please Waits! Writing the sector";
                s_write = " SUCCESS : Write the sector "+String.valueOf(n_sector)+".";
            }while(false);

            if( !s_info.isEmpty() )
                m_textview_info.setText(s_info);
            if( !s_write.isEmpty() )
                m_textview_write.setText(s_write);
            if( m_outState != null ){
                if( !s_info.isEmpty() )
                    m_outState.putString("m_textview_info", s_info);
                if( !s_write.isEmpty() )
                    m_outState.putString("m_textview_write", s_write);
            }
            return b_result;
        }
        private boolean _callback_write_info(Context context, Intent intent){
            boolean b_result =false;
            String s_write="";

            do{
                int n_sector = intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR,-1);
                if( n_sector == -1 ){
                    s_write = " SUCCESS : Write the sector "+String.valueOf(n_sector)+".";
                    continue;
                }
                short w_chain = intent.getShortExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR_CHAIN,(short)-1);
                if( w_chain % 2 == 0) {
                    s_write =
                            " SUCCESS : Write a sector "
                                    + String.valueOf(n_sector)
                                    + ": Chain "
                                    + String.valueOf(w_chain)
                                    + "/75 .";
                }
                else{
                    s_write =
                            " SUCCESS : Write a sector "
                                    + String.valueOf(n_sector)
                                    + ": Chain "
                                    + String.valueOf(w_chain)
                                    + "/75 .......";
                }
            }while(false);

            m_textview_write.setText(s_write);
            if( m_outState != null ){
                m_outState.putString("m_textview_write", s_write);
            }

            return b_result;
        }
        private boolean _callback_complete_write_firmware(Context context, Intent intent){
            boolean b_result =false;
            String s_info="";
            String s_write="";

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                //b_result is always true.
                if( !b_result ){
                    s_write = " ERROR : Complete firmware.";
                    //Toast.makeText(getApplicationContext(), "ERROR : complete firmware.", Toast.LENGTH_LONG).show();
                    continue;
                }
                s_info = "Please Waits! Starts the updated App.";
                s_write = " SUCCESS : Write a Firmware.";
            }while(false);

            if( !s_info.isEmpty() )
                m_textview_info.setText(s_info);
            if( !s_write.isEmpty() )
                m_textview_write.setText(s_write);
            if( m_outState != null ){
                if( !s_info.isEmpty() )
                    m_outState.putString("m_textview_info", s_info);
                if( !s_write.isEmpty() )
                    m_outState.putString("m_textview_write", s_write);
            }

            return b_result;
        }
        private boolean _callback_start_app(Context context, Intent intent){
            boolean b_result =false;
            String s_info;
            String s_start_app;

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    s_info = "Please Reboot your system. - FU12.";
                    s_start_app = " ERROR : Run App.";
                    //Toast.makeText(getApplicationContext(), "ERROR : run app.", Toast.LENGTH_LONG).show();
                    continue;
                }
                s_info = "Please Waits! gets the parameter of system.";
                s_start_app = " SUCCESS : Run App.";

            }while(false);

            m_textview_info.setText(s_info);
            m_textview_start_app.setText(s_start_app);

            if( m_outState != null ){
                m_outState.putString("m_textview_info", s_info);
                m_outState.putString("m_textview_start_app", s_start_app);
            }
            return b_result;
        }
        private boolean _callback_get_parameters(Context context, Intent intent){
            boolean b_result =false;
            String s_info;
            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    s_info = "Please Restart App. - FU17.";

                    //Toast.makeText(getApplicationContext(), "ERROR : run app.", Toast.LENGTH_LONG).show();
                    continue;
                }
                s_info = "Please Waits! Recover the parameter of system.";
                //m_textview_start_app.setText(" SUCCESS : Run App.");

            }while(false);

            m_textview_info.setText(s_info);
            if( m_outState != null ){
                m_outState.putString("m_textview_info", s_info);
            }

            return b_result;
        }
        private boolean _callback_recover_parameter(Context context, Intent intent){
            boolean b_result =false;
            String s_info;
            String s_recover;

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    s_info = "Please Restart App. - FU17.";
                    s_recover = " ERROR : Recover Parameters.";
                    //Toast.makeText(getApplicationContext(), "ERROR : recover system parameters.", Toast.LENGTH_LONG).show();
                    continue;
                }
                s_info = "Good Job. Update compete.";
                s_recover = " SUCCESS : Recover Parameters.";
                //Toast.makeText(getApplicationContext(), "SUCCESS UPDATE-FIRMWARE(Recover OK.).", Toast.LENGTH_LONG).show();
            }while(false);

            m_textview_info.setText(s_info);
            m_textview_recover.setText(s_recover);
            if( m_outState != null ){
                m_outState.putString("m_textview_info", s_info);
                m_outState.putString("m_textview_recover", s_recover);
            }

            return b_result;
        }

    }

}
