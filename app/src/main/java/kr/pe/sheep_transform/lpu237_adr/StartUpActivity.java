package kr.pe.sheep_transform.lpu237_adr;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.usb.UsbDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import android.util.Log;

import kr.pe.sheep_transform.lpu237_adr.databinding.ActivityStartUpBinding;

public class StartUpActivity extends AppCompatActivity
{
    private ActivityStartUpBinding m_start_up_Binding;
    private DeviceRecyclerAdapter m_adapter;
    private BroadcastReceiver m_recevier = null;

    /////////////////////////////
    // control area
    private Button m_button_exit;
    private Button m_button_apply;

    private DialogInterface.OnClickListener m_listener_dlg_exit_yes = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is Yes.
            dialog.dismiss();
            ManagerDevice.getInstance().unload();
            //
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    };
    private DialogInterface.OnClickListener m_listener_dlg_exit_no = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is No.
            dialog.dismiss();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_exit_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
        }
    };


    private DialogInterface.OnClickListener m_listener_dlg_connect_yes = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is No.
            Dialog dlg  = (Dialog) dialog;
            Context context = dlg.getContext();

            dialog.dismiss();

            Toast.makeText(
                    context,
                    " Please Waits.\n Loading the device data." ,
                    Toast.LENGTH_SHORT).show();
            //
            ManagerDevice.getInstance().select_lpu237(0);
            Tools.start_main_activity(context);
        }
    };
    private DialogInterface.OnClickListener m_listener_dlg_connect_no = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is No.
            dialog.dismiss();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_connect_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
        }
    };

    private DialogInterface.OnClickListener m_listener_dlg_connect_no_device_ok = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is No.
            dialog.dismiss();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_connect_no_device_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
        }
    };


    @Override
    protected void onDestroy() {
        unregisterReceiver();
        ManagerDevice.getInstance().unload();
        super.onDestroy();

        System.runFinalization();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        m_start_up_Binding = DataBindingUtil.setContentView(this,R.layout.activity_start_up);
        m_adapter = new DeviceRecyclerAdapter(this);
        m_start_up_Binding.idRecyclerviewDevice.setAdapter(m_adapter);
        m_start_up_Binding.idRecyclerviewDevice.setLayoutManager(new LinearLayoutManager(m_adapter.context));

        registerReceiver();
        ManagerDevice.getInstance().set_startup_activiy(this);
        if( !ManagerDevice.getInstance().push_requst(TypeRequest.Request_update_list,this) ){
            Tools.showOkDialogForErrorTerminate(this,"FN00","ERROR",getResources().getString(R.string.msg_dialog_error_terminate));
        }

        String s_app_version = Tools.get_app_version_name(this);
        TextView view_title = (TextView)findViewById(R.id.id_textview_title);
        s_app_version = view_title.getText() + " - " + s_app_version;
        view_title.setText( s_app_version );

        m_button_apply = (Button)findViewById(R.id.id_button_apply_connect);
        //m_button_apply.setEnabled(false);
        m_button_apply.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                if( ManagerDevice.getInstance().size_lpu237() > 0 ){
                    Tools.showYesNoDialog(
                            v.getContext()
                            ,"Connection"
                            ,"Do you want to connect the device."
                            ,m_listener_dlg_connect_yes
                            ,m_listener_dlg_connect_no
                            ,m_listener_dlg_connect_cancel
                    );
                }
                else{
                    Tools.showOkDialog(
                            v.getContext()
                            ,"Connection"
                            ,"No connected device."
                            ,m_listener_dlg_connect_no_device_ok
                            ,m_listener_dlg_connect_no_device_cancel
                    );
                }
            }

        });

        m_button_exit = (Button)findViewById(R.id.id_button_exit);

        m_button_exit.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Tools.showYesNoDialog(
                        v.getContext()
                        ,"Exit Application"
                        ,"Do you wan to exit ?"
                        ,m_listener_dlg_exit_yes
                        ,m_listener_dlg_exit_no
                        ,m_listener_dlg_exit_cancel
                );
            }

        });

        registerReceiver();
        Log.i("onCreate","StartUpActivity : onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver();
        ManagerDevice.getInstance().resume_startup_activity(true);
        Log.i("onResume","StartUpActivity : onResume");
    }

    @Override
    protected void onPause() {
        ManagerDevice.getInstance().resume_startup_activity(false);
        super.onPause();
        //unregisterReceiver();
        Log.i("onPause","StartUpActivity : onPause");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        ManagerDevice.getInstance().unload();
        //
        finishAffinity();
        System.runFinalization();
        System.exit(0);
    }

    private void registerReceiver(){
        do{
            if( m_recevier != null )
                continue;
            this.m_recevier = new StartUpBroadcastReceiver(this);
            this.registerReceiver(
                    this.m_recevier
                    , Tools.getIntentFilter(
                            ManagerIntentAction.INT_ALL_ACTIVITY_STARTUP | ManagerIntentAction.INT_GENERAL_TERMINATE_APP
                    )
            );
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

    class StartUpBroadcastReceiver extends BroadcastReceiver{
        public StartUpBroadcastReceiver() {
            super();
        }
        public StartUpBroadcastReceiver(AppCompatActivity activity) {
            super();
            m_activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            do{
                if( m_activity == null )
                    continue;
                //
                ManagerDevice.Response response = null;

                switch(Tools.getActionIntFromActionString(intent.getAction())){
                    case ManagerIntentAction.INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST:
                        m_adapter.notifyDataSetChanged();
                        /*
                        if( ManagerDevice.getInstance().size_lpu237() == 1 ){
                            //auto start.
                            ManagerDevice.getInstance().select_lpu237(0);
                            Tools.start_main_activity(m_activity);
                        }
                        */
                        break;
                    case ManagerIntentAction.INT_GENERAL_TERMINATE_APP:
                        ManagerDevice.getInstance().unload();
                        //
                        finishAffinity();
                        System.runFinalization();
                        System.exit(0);
                        break;
                    default:
                        //Log.i("StartUp:onReceive", intent.getAction());
                        break;
                }//end switch
            }while(false);

        }

        private AppCompatActivity m_activity;
    }
}

