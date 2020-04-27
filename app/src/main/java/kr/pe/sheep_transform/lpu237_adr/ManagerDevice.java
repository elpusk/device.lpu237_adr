package kr.pe.sheep_transform.lpu237_adr;

import android.app.Application;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.hardware.usb.*;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

class ManagerDevice implements Runnable
{

    private static class Singleton {
        private static final ManagerDevice instance = new ManagerDevice();
    }

    public static ManagerDevice getInstance () {
        return Singleton.instance;
    }

    private class SystemMode{
        private boolean m_b_bootloader = false;//the current bootloader is running
        private boolean m_b_startup_with_bootloader = false;//when app start up, bootloader is connected.

        private String m_s_device_system_name = "";
        private FwVersion m_version_device = null;
        private File m_file_firmware = null;

        public boolean is_bootloader(){
            return m_b_bootloader;
        }
        public boolean is_start_up_bootloader(){
            return m_b_startup_with_bootloader;
        }

        public void enable_bootloader(String s_device_system_name,FwVersion version_device, File file_firmware){
            m_b_bootloader = true;
            m_s_device_system_name = s_device_system_name;
            m_version_device = version_device;
            m_file_firmware = file_firmware;
        }
        public void disable_bootloader(){
            m_b_bootloader = m_b_startup_with_bootloader = false;
            m_s_device_system_name = "";
            m_version_device = null;
            m_file_firmware = null;
        }

        public void enable_startup_bootloader(){
            m_b_bootloader = m_b_startup_with_bootloader = true;
            m_s_device_system_name = "";
            m_version_device = null;
            m_file_firmware = null;
        }
        public String get_device_system_name(){
            return m_s_device_system_name;
        }
        public FwVersion get_device_version(){
            return m_version_device;
        }
        public File get_firmware_file(){
            return m_file_firmware;
        }
    }

    private final int TIMEOUT_REMOVE_MMSEC = 5000;
    private final int TIMEOUT_CONNECT_MMSEC = 5000;

    private TimerTask m_timer_task_waits_removed = null;
    private TimerTask m_timer_task_waits_connected = null;

    private Timer m_timer_connect = new Timer();
    private Timer m_timer_remove = new Timer();

    private final int MAX_REQUEST = 50;
    public class Request{

        private TypeRequest m_request = TypeRequest.Request_none;
        private Context m_context = null;
        private File m_file_firmware = null;
        private FwVersion m_version = null;
        private String m_s_data = "";

        public Request( TypeRequest request, Context context )
        {
            m_request = request;
            m_context = context;
        }

        public Request( TypeRequest request, Context context,File file_firmware,String s_data, FwVersion version )
        {
            m_request = request;
            m_context = context;
            m_file_firmware = file_firmware;
            m_s_data = s_data;
            m_version = version;
        }

        public TypeRequest getRequest()
        {
            return m_request;
        }

        public Context getContext() {
            return m_context;
        }

        public File getFile(){  return m_file_firmware; }

        public String getStringData(){
            return m_s_data;
        }
        public FwVersion getVersion(){
            return m_version;
        }
    }

    private final int MAX_RESPONSE = 50;
    public class Response{
        private TypeRequest m_request = TypeRequest.Request_none;
        private TypeRequestResult m_result = TypeRequestResult.RequestResult_ing;
        private Context m_context = null;
        private File m_file_firmware = null;
        //
        public Response( TypeRequest request, Context context, TypeRequestResult result ){
            m_request = request;
            m_context = context;
            m_result = result;
        }
        public Response( TypeRequest request, Context context, TypeRequestResult result, File file ){
            m_request = request;
            m_context = context;
            m_result = result;
            m_file_firmware = file;
        }

        public TypeRequest getRequest()
        {
            return m_request;
        }

        public TypeRequestResult getResult() {
            return m_result;
        }

        public Context getContext() {
            return m_context;
        }

        public File getFile(){  return m_file_firmware; }

        public void setResult( TypeRequestResult result ){
            m_result = result;
        }
    }
    private Integer m_index_response=0;
    private HashMap<Integer,Response> m_map_response = new HashMap<>(MAX_RESPONSE);
    private Object m_locker_response_map = new Object();

    private SystemMode m_system_mode = new SystemMode();

    private BlockingQueue<ManagerDevice.Request> m_blockingQueue = new LinkedBlockingDeque<ManagerDevice.Request>(MAX_REQUEST);
    private Thread m_thread;
    private AtomicBoolean m_working;
    private UsbManager m_usbManager;

    private Object m_lock_lpu237_permission = new Object();
    private Object m_lock_device_list = new Object();
    private ArrayList<Lpu237> m_list_devices;
    private ArrayList<HidBootLoader> m_list_bootloader;
    private int m_n_cur_lpu237 = -1;    //index of m_list_devices
    private int m_n_cur_boorloader = -1;    //the index of m_list_bootloader

    private Lpu237.Parameters m_parameter_for_recover = new Lpu237.Parameters();

    private StartUpActivity m_startup_activiy = null;
    private MainActivity m_main_activity = null;
    private UpdateActivity m_update_activity = null;

    private AtomicBoolean m_b_resume_startup_activiy;
    private AtomicBoolean m_b_resume_main_activiy;
    private AtomicBoolean m_b_stop_update_activiy;
    private String m_s_deferred_fw_ok_message = "";

    private Boolean m_b_waits_attach_bootloader = false;

    public Boolean is_waits_attach_bootloader(){
        return m_b_waits_attach_bootloader;
    }
    public void resume_startup_activity( boolean b_resume ){
        m_b_resume_startup_activiy.set(b_resume);
    }
    public void resume_main_activity( boolean b_resume ){
        m_b_resume_main_activiy.set(b_resume);
    }
    public void stop_update_activity( boolean b_stop ){
        if( b_stop )
            Log.i("stop_update_activity","update_activity : stop");
        else
            Log.i("stop_update_activity","update_activity : run");
        m_b_stop_update_activiy.set(b_stop);
    }
    public boolean is_resume_startup_activity(){
        return m_b_resume_startup_activiy.get();
    }
    public boolean is_resume_main_activity(){
        return m_b_resume_main_activiy.get();
    }
    public boolean is_stop_update_activity(){
        return m_b_stop_update_activiy.get();
    }

    public void set_startup_activiy( StartUpActivity act ){
        m_startup_activiy = act;
    }
    public void set_main_activity( MainActivity act ){
        m_main_activity = act;
    }
    public void set_update_activity( UpdateActivity act ){
        m_update_activity = act;
    }

    public StartUpActivity get_startup_activity(){ return m_startup_activiy; }
    public MainActivity get_main_activity(){ return m_main_activity; }
    public UpdateActivity get_update_activity(){ return m_update_activity; }


    public boolean is_startup_with_bootloader(){
        return m_system_mode.is_start_up_bootloader();
    }

    private DialogInterface.OnClickListener m_listener_dlg_fw_download_yes = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // when bootloader is found at starting app. and display yes, no dialog to select download firmware
            // this listener is Yes.
            dialog.dismiss();
            //
            m_system_mode.enable_startup_bootloader();
            Tools.start_update_activity(m_startup_activiy);
        }
    };

    private DialogInterface.OnClickListener m_listener_dlg_fw_download_no = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // when bootloader is found at starting app. and display yes, no dialog to select download firmware
            // this listener is No.
            dialog.dismiss();
            Tools.showOkDialogForErrorTerminate(m_startup_activiy,"FS00","Notice",m_startup_activiy.getResources().getString(R.string.msg_dialog_need_download_terminate));
            //Toast.makeText(m_startup_activiy, "ERROR : Please Restart App.", Toast.LENGTH_LONG).show();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_fw_download_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when bootloader is found at starting app. and display yes, no dialog to select download firmware
            // this listener is No.
            dialog.dismiss();
            Tools.showOkDialogForErrorTerminate(m_startup_activiy,"FS00","Notice",m_startup_activiy.getResources().getString(R.string.msg_dialog_need_download_terminate));
            //Toast.makeText(m_startup_activiy, "ERROR : Please Restart App.", Toast.LENGTH_LONG).show();
        }
    };


    private DialogInterface.OnClickListener m_listener_dlg_fw_download_ok = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // when firmware update ok.
            //dialog.dismiss();
            //
            if( m_update_activity != null ){
                m_update_activity.finish();
                m_update_activity = null;
            }
            if( !push_requst( TypeRequest.Request_get_info_for_list,m_startup_activiy) ){
                Tools.showOkDialogForErrorTerminate(m_startup_activiy,"FU21","ERROR",m_startup_activiy.getResources().getString(R.string.msg_dialog_error_terminate));
            }
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_fw_download_ok_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
            //
            if( m_update_activity != null ){
                m_update_activity.finish();
                m_update_activity = null;
            }
            push_requst( TypeRequest.Request_get_info_for_list,m_startup_activiy);
            //Tools.start_main_activity(m_startup_activiy);
        }
    };

    /////////////////////////////////////////////////////////////////////////////////
    // methods.

    private ManagerDevice(){
        m_list_devices = new ArrayList<>();
        m_list_bootloader = new ArrayList<>();
        //
        m_timer_task_waits_removed = new TimerTask() {
            @Override
            public void run() {
                //announce timeout of remove-waits
                //nothing to do.
            }
        };
        m_timer_task_waits_connected = new TimerTask() {
            @Override
            public void run() {
                synchronized (m_lock_lpu237_permission){
                    if( m_timer_connect != null ){
                        /*
                        if (!push_requst(TypeRequest.Request_update_list, m_update_activity)) {
                            Toast.makeText(m_application, "m_timer_task_waits_connected : error", Toast.LENGTH_SHORT).show();
                        }
                        */
                    }
                }
            }
        };
    }

    public boolean load(Application application ){
        boolean b_result = false;

        do{
            m_application = application;
            //
            m_application.registerReceiver(m_usbReceiver, Tools.getIntentFilter(
                    ManagerIntentAction.INT_ALL_USB |
                            ManagerIntentAction.INT_ALL_LPU237 |
                            ManagerIntentAction.INT_ALL_BOOTLOADER
            ));

            m_usbManager = (UsbManager) m_application.getSystemService(m_application.USB_SERVICE);

            m_working = new AtomicBoolean(true);
            m_b_resume_startup_activiy  = new AtomicBoolean(false);
            m_b_resume_main_activiy  = new AtomicBoolean(false);
            m_b_stop_update_activiy  = new AtomicBoolean(false);

            m_thread = new Thread(ManagerDevice.this);
            m_thread.start();
            b_result = true;
        }while(false);

        return b_result;
    }

    public boolean unload(){
        boolean b_result = false;
        do{
            if( m_application == null ) {
                b_result = true;
                continue;
            }
            m_working.set(false);//kill thread.
            try {
                m_blockingQueue.put( new Request(TypeRequest.Request_kill, m_application));
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            //
            m_application.unregisterReceiver(m_usbReceiver);

            if( size_lpu237() > 0 ){
                lpu237_close();
            }

            m_application = null;

            b_result = true;
        }while(false);

        return b_result;
    }

    public boolean push_requst( TypeRequest req, Context context, File file_fw,String s_data, FwVersion version )
    {
        boolean b_result = false;
        do{
            if( m_application == null ) {
                continue;
            }
            try {
                m_blockingQueue.put( new Request(req, context, file_fw,s_data,version));
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            //
            b_result = true;
        }while(false);

        return b_result;
    }
    public boolean push_requst( TypeRequest req, Context context  )
    {
        return push_requst(req,context,null,"",null);
    }

    private void _start_waits_removed_timer(Context context){
        if( m_timer_remove == null){
            m_timer_remove = new Timer();
        }
        m_timer_remove.schedule(m_timer_task_waits_removed,TIMEOUT_REMOVE_MMSEC);
    }
    private void _stop_waits_removed_timer(){
        if( m_timer_remove != null) {
            m_timer_remove.cancel();
            m_timer_remove.purge();
            m_timer_remove = null;
        }
    }

    private void _start_waits_connected_timer(Context context){
        synchronized (m_lock_lpu237_permission) {
            /*
            if (m_timer_connect == null) {
                m_timer_connect = new Timer();
            }
            m_timer_connect.schedule(m_timer_task_waits_connected, TIMEOUT_CONNECT_MMSEC);
            */
        }
    }
    private void _stop_waits_connected_timer(){
        synchronized (m_lock_lpu237_permission) {
            /*
            if (m_timer_connect != null) {
                m_timer_connect.cancel();
                m_timer_connect.purge();
                m_timer_connect = null;
            }
            */
        }
    }

    public Integer setResponse( TypeRequest request, Context context, TypeRequestResult result ){
        Integer index = -1;

        synchronized (m_locker_response_map){
            index = m_index_response;
            m_map_response.put(index,new Response( request, context, result ) );
            m_index_response++;
        }
        return index;
    }

    public Response getResponse( Integer index ){
        Response response = null;
        do {
            synchronized (m_locker_response_map) {
                if( !m_map_response.containsKey(index) )
                    continue;
                response = m_map_response.get(index);
                m_map_response.remove(index);
            }
        }while(false);
        return response;
    }

    public void showFwDownloadOk() {
        do {
            if( m_s_deferred_fw_ok_message == null )
                continue;
            if( m_s_deferred_fw_ok_message.isEmpty() )
                continue;
            if (m_update_activity == null )
                continue;
            if( m_b_stop_update_activiy.get())
                continue;
            String s_deferred_fw_ok_message = m_s_deferred_fw_ok_message;
            Tools.showOkDialog(
                    m_update_activity
                    , "Update-Firmware."
                    , "The Firmware Update OK.\r\n" + s_deferred_fw_ok_message
                    , m_listener_dlg_fw_download_ok
                    , m_listener_dlg_fw_download_ok_cancel
            );
            m_s_deferred_fw_ok_message="";
        }while(false);
    }

    private void showFwDownloadOk( String s_additional_message ) {
        if (m_update_activity != null && !m_b_stop_update_activiy.get() ) {
            m_s_deferred_fw_ok_message="";
            Tools.showOkDialog(
                    m_update_activity
                    , "Update-Firmware."
                    , "The Firmware Update OK.\r\n"+s_additional_message
                    , m_listener_dlg_fw_download_ok
                    , m_listener_dlg_fw_download_ok_cancel
            );
        }
        else{
            m_s_deferred_fw_ok_message = s_additional_message;
        }
    }

    //don't call this function in worker thread.
    //call only receiver.
    private void _result_broadcast_to_activity(
            boolean b_main_activity
            ,String s_toast_message
            ,Context context
            , String s_action
            , Boolean b_result
            , int n_data){

        if( context == null )
            context = m_application;

        boolean b_resume = true;
        if( b_main_activity )
            b_resume = is_resume_main_activity();
        else
            b_resume = is_resume_startup_activity();

        b_resume = true;

        if( b_resume ) {
            Intent intent = new Intent(s_action);
            intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY, b_result);
            intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY, n_data);
            context.sendBroadcast(intent);
        }
        else{
            if( b_result )
                Toast.makeText(m_application, "SUCCESS : "+s_toast_message, Toast.LENGTH_LONG).show();
            else{
                Toast.makeText(m_application, "ERROR : "+s_toast_message, Toast.LENGTH_LONG).show();
            }
        }
    }
    private void _result_broadcast_to_activity(
            Context context
            , String s_action
            , Boolean b_result
            , int n_data){

        if( context == null )
            context = m_application;

        Intent intent = new Intent(s_action);
        intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY, b_result);
        intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY, n_data);
        context.sendBroadcast(intent);
    }
    //don't call this function in worker thread.
    private void _broadcast_terminate_app(){
        //
        Intent intent = new Intent(ManagerIntentAction.GENERAL_TERMINATE_APP);
        if( m_application != null )
            m_application.sendBroadcast(intent);
        //
        unload();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // receiver sub-function

    private boolean _callback_normal_usb_attached(Context context, Intent intent){
        boolean b_result = false;
        do{
            UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if( device == null )
                continue;
            if( device.getVendorId() != Lpu237Info.USB_VID )
                continue;
            //
            b_result = true;
        }while(false);
        return b_result;
    }
    private boolean _callback_boot_usb_attached(Context context, Intent intent){
        boolean b_result = false;
        do{
            UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if( device == null )
                continue;
            if( device.getVendorId() != Lpu237Info.USB_VID )
                continue;

            if( device.getProductId() == Lpu237Info.USB_PID ) {
                if (!push_requst(TypeRequest.Request_update_list, context)) {
                    //Toast.makeText(m_application, "_callback_usb_attached : error", Toast.LENGTH_SHORT).show();
                    Tools.showOkDialogForErrorTerminate(m_update_activity,"FU14","ERROR",m_update_activity.getResources().getString(R.string.msg_dialog_error_reboot));
                    continue;
                }
                b_result = true;
                continue;
            }

            if( device.getProductId() == HidBootLoaderInfo.USB_PID ) {
                if (!push_requst(TypeRequest.Request_update_list, context)) {
                    //Toast.makeText(m_application, "updateBootloaderList : error", Toast.LENGTH_SHORT).show();
                    Tools.showOkDialogForErrorTerminate(m_main_activity,"FU03","ERROR",m_main_activity.getResources().getString(R.string.msg_dialog_error_reboot));
                    continue;
                }
            }

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_normal_detached(Context context, Intent intent){
        boolean b_result = false;
        do{
            Toast.makeText(m_application, "Removed Usb device.......", Toast.LENGTH_SHORT).show();
            b_result = true;
        }while(false);
        return b_result;
    }
    private boolean _callback_boot_detached(Context context, Intent intent){
        boolean b_result = false;
        do{
            Toast.makeText(m_application, "Removed Usb device.", Toast.LENGTH_SHORT).show();
            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_update_list_no_device(Context context, Intent intent){
        boolean b_result = false;
        do{
            //this intent always error
            if (!_result_from_worker_is_success( intent )) {
                //Toast.makeText(m_application, "ERROR : No device. Please Restart App.", Toast.LENGTH_LONG).show();//_broadcast_terminate_app();
                Context available_context = null;

                if( m_update_activity != null ) {
                    available_context = m_update_activity;
                    Tools.showOkDialogForErrorTerminate(available_context,"FU15","ERROR",available_context.getResources().getString(R.string.msg_dialog_error_reboot));
                }
                else if( m_main_activity != null ) {
                    available_context = m_main_activity;
                    Tools.showOkDialogForErrorTerminate(available_context,"FU04","ERROR",available_context.getResources().getString(R.string.msg_dialog_error_reboot));
                }
                else if( m_startup_activiy != null ) {
                    available_context = m_startup_activiy;
                    Tools.showOkDialogForErrorTerminate(available_context,"FN01","ERROR",available_context.getResources().getString(R.string.msg_dialog_no_device_terminate));
                }
                else
                    available_context = context;
            }

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_normal_lpu237_permission(Context context, Intent intent){
        boolean b_result = false;
        do{
            UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
            if (granted){
                _add_to_list(new Lpu237(m_usbManager, device));//add device
                select_lpu237(0);
                //
                if(!push_requst( TypeRequest.Request_get_info_for_list,context) )
                    Tools.showOkDialogForErrorTerminate(m_startup_activiy,"FN02","ERROR",m_startup_activiy.getResources().getString(R.string.msg_dialog_error_terminate));
            }
            else{ // User not accepted our USB connection. Send an Intent to the Main Activity
                Toast.makeText(m_application, "ERROR : You must accept that your device is accessed.", Toast.LENGTH_LONG).show();
                //retry permission.
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(m_application, 0, new Intent(ManagerIntentAction.LPU237_PERMISSION), 0);
                m_usbManager.requestPermission(device, pendingIntent);
            }

            b_result = true;
        }while(false);
        return b_result;
    }
    private boolean _callback_boot_lpu237_permission(Context context, Intent intent){
        boolean b_result = false;
        _stop_waits_connected_timer();
        do{
            UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
            if (granted){
                _add_to_list(new Lpu237(m_usbManager, device));//add device
                select_lpu237(0);
                //
                if( m_system_mode.is_start_up_bootloader() ){
                    m_system_mode.disable_bootloader();

                    showFwDownloadOk("Parameters is default.");
                }
                else {
                    if( !push_requst(TypeRequest.Request_get_parameters, m_update_activity) ){
                        Tools.showOkDialogForErrorTerminate(m_update_activity,"FU16","ERROR",m_update_activity.getResources().getString(R.string.msg_dialog_error_terminate));
                    }
                }
            }
            else{ // User not accepted our USB connection. Send an Intent to the Main Activity
                Toast.makeText(m_application, "ERROR : You must accept that your device is accessed.", Toast.LENGTH_LONG).show();
                //retry permission.
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(m_application, 0, new Intent(ManagerIntentAction.LPU237_PERMISSION), 0);
                m_usbManager.requestPermission(device, pendingIntent);
            }

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_normal_get_info_for_list(Context context, Intent intent){
        boolean b_result = false;
        do{
            if (!_result_from_worker_is_success( intent )) {
                //Toast.makeText(m_application, "ERROR : can not the basic information from device.", Toast.LENGTH_SHORT).show();
                Tools.showOkDialogForErrorTerminate(m_startup_activiy,"FN04","ERROR",m_startup_activiy.getResources().getString(R.string.msg_dialog_error_terminate));
            }
            else{
                //send intent to StartupActivity.
                Intent intent_display = new Intent(ManagerIntentAction.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST);
                context.sendBroadcast(intent_display);
            }
            b_result = true;
        }while(false);
        return b_result;
    }
    private boolean _callback_boot_get_info_for_list(Context context, Intent intent){
        boolean b_result = false;
        do{
            if (!_result_from_worker_is_success( intent )) {
                Toast.makeText(m_application, "ERROR : _callback_boot_get_info_for_list", Toast.LENGTH_SHORT).show();
                continue;
            }
            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_normal_get_parameters(Context context, Intent intent){
        boolean b_result = false;
        do{
            ManagerDevice.Response response = _response_from_worker_is_success( intent );
            if( response.getResult() != TypeRequestResult.RequestResult_success ){
                continue;
            }

            b_result = true;
        }while(false);
        //send intent to MainActivity.
        _result_broadcast_to_activity( context,ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS,b_result,0);
        return b_result;
    }
    private boolean _callback_boot_get_parameters(Context context, Intent intent){
        boolean b_result = false;
        do{
            ManagerDevice.Response response = _response_from_worker_is_success( intent );
            if( response.getResult() != TypeRequestResult.RequestResult_success ){
                //send intent to UpdateActivity.
                _result_broadcast_to_activity(context,ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS,false,0);
            }
            else{
                //send intent to UpdateActivity.
                _result_broadcast_to_activity(context,ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS,true,0);
                if( !ManagerDevice.getInstance().push_requst(TypeRequest.Request_recover_parameters, context) ){
                    Tools.showOkDialogForErrorTerminate(m_update_activity,"FU18","ERROR",m_update_activity.getResources().getString(R.string.msg_dialog_error_terminate));
                }
            }

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_normal_set_parameters(Context context, Intent intent){
        boolean b_result = false;
        do{
            if (_result_from_worker_is_success( intent )) {
                b_result = true;
            }
        }while(false);
        _result_broadcast_to_activity( context,ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS,b_result,0);
        return b_result;
    }

    private boolean _callback_started_bootloader(Context context, Intent intent){
        boolean b_result = false;
        do{
            if (_result_from_worker_is_success( intent )) {
                m_b_waits_attach_bootloader = true;
                b_result = true;
                continue;
            }
            //
            Tools.showOkDialogForError(m_main_activity,"FU02","ERROR",m_main_activity.getResources().getString(R.string.msg_dialog_error));
        }while(false);
        _result_broadcast_to_activity( true,"Starts bootloader.",context,ManagerIntentAction.ACTIVITY_MAIN_START_BOOT,b_result,0);
        return b_result;
    }
    private boolean _callback_normal_bootloader_permission(Context context, Intent intent){
        boolean b_result = false;
        do{
            UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            Boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
            if (granted){
                HidBootLoader bootloader = new HidBootLoader(m_usbManager, device);

                _add_to_list(bootloader);
                select_bootloader(0);

                Tools.showYesNoDialog(
                        m_startup_activiy
                        ,"Download firmware"
                        ,"Do you wan to download a firmware?"
                        ,m_listener_dlg_fw_download_yes
                        ,m_listener_dlg_fw_download_no
                        ,m_listener_dlg_fw_download_cancel
                );
            }
            else{ // User not accepted our USB connection. Send an Intent to the Main Activity
                Toast.makeText(m_application, "ERROR : You must accept that your device is accessed.", Toast.LENGTH_SHORT).show();
                //retry permission.
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(m_application, 0, new Intent(ManagerIntentAction.BOOTLOADER_PERMISSION), 0);
                m_usbManager.requestPermission(device, pendingIntent);
            }

            b_result = true;
        }while(false);
        return b_result;
    }
    private boolean _callback_boot_bootloader_permission(Context context, Intent intent){
        boolean b_result = false;
        do{
            UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            Boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
            if (granted){
                clear_list_lpu237();

                if( m_main_activity != null ) {
                    m_b_waits_attach_bootloader = false;
                    m_main_activity.finish();
                    m_main_activity = null;
                }
                Tools.start_update_activity(m_startup_activiy);

                HidBootLoader bootloader = new HidBootLoader(m_usbManager, device);
                _add_to_list(bootloader);
                select_bootloader(0);

                set_rom_file(
                        0
                        ,m_system_mode.get_firmware_file()
                        ,m_system_mode.get_device_system_name()
                        ,m_system_mode.get_device_version()
                );
            }
            else{ // User not accepted our USB connection. Send an Intent to the Main Activity
                Toast.makeText(m_application, "ERROR : You must accept that your device is accessed.", Toast.LENGTH_SHORT).show();
                //retry permission.
                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(m_application, 0, new Intent(ManagerIntentAction.BOOTLOADER_PERMISSION), 0);
                m_usbManager.requestPermission(device, pendingIntent);
            }

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _callback_boot_erase_firmware(Context context, Intent intent){
        boolean b_result = false;
        do{
            // erase result. here.
            if (_result_from_worker_is_success( intent )) {
                if( !push_requst(TypeRequest.Request_firmware_write,context ) ){
                    Tools.showOkDialogForErrorTerminate(m_update_activity,"FU08","ERROR",m_update_activity.getResources().getString(R.string.msg_dialog_error_terminate_as));
                }
                b_result = true;
            }
        }while(false);
        _result_broadcast_to_activity(true,"Erase the Firmware.",context, ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE, b_result, 0);
        return b_result;
    }

    private boolean _callback_boot_write_sector(Context context, Intent intent){
        boolean b_result = false;
        int n_sector = -1;
        do{
            //write sector
            if( intent == null )
                continue;
            ManagerDevice.Response response = ManagerDevice.getInstance().getResponse(
                    intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INDEX, -1)
            );
            n_sector = intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR_INDEX,-1);

            if (response.getResult() != TypeRequestResult.RequestResult_success) {
                continue;
            }
            if( !push_requst(TypeRequest.Request_firmware_write,context ) ){
                Tools.showOkDialogForErrorTerminate(m_update_activity,"FU10","ERROR",m_update_activity.getResources().getString(R.string.msg_dialog_error_reboot));
            }
            b_result = true;
        }while(false);
        _result_broadcast_to_activity( true,"Write a sector"+String.valueOf(n_sector)+".",context,ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR,b_result,n_sector);
        return b_result;
    }
    private boolean _callback_boot_write_complete(Context context, Intent intent){
        boolean b_result = false;
        do{
            if (_result_from_worker_is_success( intent )) {
                //always true........ here

                if( !push_requst(TypeRequest.Request_run_app,context ) ){
                    Tools.showOkDialogForErrorTerminate(m_update_activity,"FU11","ERROR",m_update_activity.getResources().getString(R.string.msg_dialog_error_reboot));
                }
                b_result = true;
            }
        }while(false);
        _result_broadcast_to_activity( true, "Write the firmware",context,ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE,b_result,0);
        return b_result;
    }
    private boolean _callback_boot_started_app(Context context, Intent intent){
        boolean b_result = false;
        do{
            if (_result_from_worker_is_success( intent )) {
                clear_list_bootloader();
                b_result = true;
            }
        }while(false);
        _result_broadcast_to_activity( true,"Starts App.",context,ManagerIntentAction.ACTIVITY_UPDATE_START_APP,b_result,0);
        return b_result;
    }
    private boolean _callback_boot_recover_parameter(Context context, Intent intent){
        boolean b_result = false;
        do{
            m_system_mode.disable_bootloader();

            if (_result_from_worker_is_success( intent )) {
                showFwDownloadOk("Recover Parameters OK.");
                b_result = true;
            }
            else{
                showFwDownloadOk("Recover Parameters Error.");
            }
        }while(false);
        _result_broadcast_to_activity( true,"Recover the parameters.",context,ManagerIntentAction.ACTIVITY_UPDATE_RECOVER_PARAMETER,b_result,0);
        return b_result;
    }

    void select_lpu237( int n_index ){
        do{
            synchronized (m_lock_device_list) {
                if (m_list_devices == null)
                    continue;
                if (n_index >= m_list_devices.size())
                    continue;
                if (n_index < 0)
                    continue;
                m_n_cur_lpu237 = n_index;
            }
        }while(false);
    }
    void select_bootloader( int n_index ){
        do{
            synchronized (m_lock_device_list) {
                if (m_list_bootloader == null)
                    continue;
                if (n_index >= m_list_bootloader.size())
                    continue;
                if (n_index < 0)
                    continue;
                m_n_cur_boorloader = n_index;
            }
        }while(false);
    }

    public int size_bootloader(){
        synchronized (m_lock_device_list) {
            if( m_list_bootloader == null )
                return 0;
            return m_list_bootloader.size();
        }
    }
    public int size_lpu237(){
        synchronized (m_lock_device_list) {
            if( m_list_devices == null )
                return 0;
            return m_list_devices.size();
        }
    }

    public void set_rom_file(int n_index_bootloader, File rom_file,String s_dev_name, FwVersion dev_version ){
        synchronized (m_lock_device_list) {
            do {
                if( m_list_bootloader == null )
                    continue;
                if( m_list_bootloader.size()<= n_index_bootloader )
                    continue;
                //
                m_list_bootloader.get(n_index_bootloader).set_rom_file(rom_file, s_dev_name, dev_version);
            }while (false);
        }

    }
    public void set_rom_file(int n_index_bootloader, File rom_file,int n_index_fw ){
        synchronized (m_lock_device_list) {
            do {
                if( m_list_bootloader == null )
                    continue;
                if( m_list_bootloader.size()<= n_index_bootloader )
                    continue;
                //
                m_list_bootloader.get(n_index_bootloader).set_rom_file(rom_file,n_index_fw);
            }while (false);
        }

    }
    private void _add_to_list(Lpu237 dev ){
        synchronized (m_lock_device_list) {
            if (m_list_devices != null) {
                m_list_devices.add(dev);
            }
        }
    }
    private void _add_to_list( HidBootLoader dev ){
        synchronized (m_lock_device_list) {
            if (m_list_bootloader != null) {
                m_list_bootloader.add(dev);
            }
        }
    }

    private void clear_list_lpu237(){
        synchronized (m_lock_device_list) {
            if (m_list_devices != null) {
                m_list_devices.clear();
            }
        }
    }
    private void clear_list_bootloader(){
        synchronized (m_lock_device_list) {
            if (m_list_bootloader != null) {
                m_list_bootloader.clear();
            }
        }
    }

    public boolean lpu237_is_changed(){
        boolean b_changed = false;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices==null)
                    continue;
                //
                b_changed = m_list_devices.get(m_n_cur_lpu237).is_changed();
            } while (false);
        }
        return b_changed;
    }
    public String lpu237_getDescription(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getDescription();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getVersionSystem(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getVersionSystem();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getDeviceType(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getDeviceType();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getName(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getName();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getUid(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getUid();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getDecoderMmd1000(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getDecoderMmd1000();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getInterface(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getInterface();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getLanguageIndex(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getLanguageIndex();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getBuzzerFrequency(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getBuzzerFrequency();
            } while (false);
        }
        return s_data;
    }
    public String lpu237_getEnableTrack(int n_track){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getEnableTrack(n_track);
            } while (false);
        }
        return s_data;
    }
    //
    public int lpu237_get_device_type(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_device_type();
            } while (false);
        }
        return n_data;
    }
    public int lpu237_get_buzzer_frequency(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_buzzer_frequency();
            } while (false);
        }
        return n_data;
    }
    public int lpu237_get_interface(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_interface();
            } while (false);
        }
        return n_data;
    }
    public int lpu237_get_language_index(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_language_index();
            } while (false);
        }
        return n_data;
    }
    public boolean lpu237_get_enable_track( int n_track ){
        boolean b_data = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237 <0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_data = m_list_devices.get(m_n_cur_lpu237).get_enable_track(n_track);
            }while (false);
        }
        return b_data;
    }
    public boolean lpu237_get_global_send_condition(){
        boolean b_data = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237 <0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_data = m_list_devices.get(m_n_cur_lpu237).get_global_send_condition();
            }while (false);
        }
        return b_data;
    }
    public int lpu237_get_ibutton_type(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_ibutton_type();
            } while (false);
        }
        return n_data;
    }

    public Lpu237.Tags lpu237_get_global_prefix(){
        Lpu237.Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_global_prefix();
            } while (false);
        }
        return tag;
    }
    public Lpu237.Tags lpu237_get_global_postfix(){
        Lpu237.Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_global_postfix();
            } while (false);
        }
        return tag;
    }
    public Lpu237.Tags lpu237_get_private_prefix( int n_track ){
        Lpu237.Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_private_prefix(n_track);
            } while (false);
        }
        return tag;
    }
    public Lpu237.Tags lpu237_get_private_postfix( int n_track ){
        Lpu237.Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_private_postfix(n_track);
            } while (false);
        }
        return tag;
    }
    public Lpu237.Tags lpu237_get_ibutton_prefix(){
        Lpu237.Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_prefix();
            } while (false);
        }
        return tag;
    }
    public Lpu237.Tags lpu237_get_ibutton_postfix(){
        Lpu237.Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_postfix();
            } while (false);
        }
        return tag;
    }

    public FwVersion lpu237_get_version_system(){
        FwVersion v = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                v = m_list_devices.get(m_n_cur_lpu237).get_version_system();
            } while (false);
        }
        return v;
    }

    //////////////////////////
    public void lpu237_set_ibutton_type( int n_type ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_type(n_type);
            } while (false);
        }
    }
    public void lpu237_set_enable_track( int n_track, boolean b_enable ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_enable_track(n_track, b_enable);
            } while (false);
        }
    }
    public void lpu237_set_interface( int n_interface ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_interface(n_interface);
            } while (false);
        }
    }
    public void lpu237_set_language_index( int n_language ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_language_index(n_language);
            } while (false);
        }
    }
    public void lpu237_set_buzzer_frequency( int n_frequency ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_buzzer_frequency(n_frequency);
            } while (false);
        }
    }
    public void lpu237_set_global_prefix( Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_global_prefix(tag);
            } while (false);
        }
    }
    public void lpu237_set_global_postfix( Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_global_postfix(tag);
            } while (false);
        }
    }
    public void lpu237_set_private_prefix( int n_track,Lpu237.Tags tag){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_private_prefix(n_track, tag);
            } while (false);
        }
    }
    public void lpu237_set_private_postfix( int n_track,Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_private_postfix(n_track, tag);
            } while (false);
        }
    }
    public void lpu237_set_ibutton_prefix( Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_prefix(tag);
            } while (false);
        }
    }
    public void lpu237_set_ibutton_postfix( Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_postfix(tag);
            } while (false);
        }
    }
    public void lpu237_set_uart_prefix( Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_uart_prefix(tag);
            } while (false);
        }
    }
    public void lpu237_set_uart_postfix( Lpu237.Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_uart_postfix(tag);
            } while (false);
        }
    }
    public void lpu237_set_global_send_condition( boolean b_all_no_error ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_global_send_condition(b_all_no_error);
            } while (false);
        }
    }

    public boolean lpu237_set_Opos_sentinel(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if(m_n_cur_lpu237 < 0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                int n_track = 0;
                Lpu237.Tags [] tags_pre = new Lpu237.Tags[3];
                Lpu237.Tags [] tags_post = new Lpu237.Tags[3];

                for( n_track = 0; n_track<3; n_track++ ){
                    tags_pre[n_track] = m_list_devices.get(m_n_cur_lpu237).get_private_prefix(n_track);
                    tags_post[n_track] = m_list_devices.get(m_n_cur_lpu237).get_private_postfix(n_track);
                }//end for

                boolean b_error = false;
                for( n_track =0 ; n_track < 3; n_track ++ ){
                    if( tags_pre[n_track] == null ){
                        b_error = true;
                        break;//exit for
                    }
                    if( tags_pre[n_track].is_full() ){
                        b_error = true;
                        break;//exit for
                    }
                    if( tags_post[n_track] == null ){
                        b_error = true;
                        break;//exit for
                    }
                    if( tags_post[n_track].is_full() ){
                        b_error = true;
                        break;//exit for
                    }
                }//end for
                //
                if( b_error )
                    continue;
                //
                n_track = 0;
                tags_pre[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC);
                tags_post[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM);
                n_track++;
                tags_pre[n_track].push_back(KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SEMI__COL);
                tags_post[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM);
                n_track++;
                tags_pre[n_track].push_back(KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SEMI__COL);
                tags_post[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM);
                //
                for( n_track = 0; n_track<3; n_track++ ){
                    m_list_devices.get(m_n_cur_lpu237).set_private_prefix(n_track,tags_pre[n_track]);
                    m_list_devices.get(m_n_cur_lpu237).set_private_postfix(n_track,tags_post[n_track]);
                }//end for

                b_result= true;
            }while(false);
        }

        return b_result;
    }
    public boolean lpu237_set_pre_posfix_type1(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if(m_n_cur_lpu237 < 0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                int n_track = 0;
                Lpu237.Tags [] tags_pre = new Lpu237.Tags[3];
                Lpu237.Tags [] tags_post = new Lpu237.Tags[3];

                for( n_track = 0; n_track<3; n_track++ ){
                    tags_pre[n_track] = new Lpu237.Tags();
                    tags_post[n_track] = new Lpu237.Tags();
                }//end for

                n_track = 0;
                tags_pre[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC);
                tags_post[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM);
                n_track++;
                tags_pre[n_track].push_back(KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SEMI__COL);
                tags_post[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM);
                n_track++;
                tags_pre[n_track].push_back(KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SEMI__COL);
                tags_post[n_track].push_back(KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM);
                //
                for( n_track = 0; n_track<3; n_track++ ){
                    m_list_devices.get(m_n_cur_lpu237).set_private_prefix(n_track,tags_pre[n_track]);
                    m_list_devices.get(m_n_cur_lpu237).set_private_postfix(n_track,tags_post[n_track]);
                }//end for

                b_result= true;
            }while(false);
        }

        return b_result;    }
    /////////////////////////
    boolean lpu237_open(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).open();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_close(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).close();
            }while(false);
        }

        return b_result;
    }

    boolean lpu237_save_parameter(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).get_parameter(m_parameter_for_recover);
            }while(false);
        }

        return b_result;
    }

    boolean lpu237_recover_parameter(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).set_parameter(m_parameter_for_recover);
            }while(false);
        }

        return b_result;
    }


    boolean lpu237_df_enter_config(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_enter_config();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_df_leave_config(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_leave_config();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_df_get_uid(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_uid();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_df_get_type(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_type();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_df_get_parameter(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_parameter();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_df_set_parameter(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_set_parameter();
            }while(false);
        }

        return b_result;
    }
    boolean lpu237_df_run_bootloader(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_run_bootloader();
            }while(false);
        }

        return b_result;
    }

    boolean bootloader_open(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).open();
            }while(false);
        }

        return b_result;
    }
    boolean bootloader_close(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).close();
            }while(false);
        }

        return b_result;
    }
    boolean bootloader_df_run_app(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).df_run_app();
            }while(false);
        }

        return b_result;
    }
    boolean bootloader_df_erase_all(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).df_erase_all();
            }while(false);
        }

        return b_result;
    }
    boolean bootloader_df_write_one_sector(Context context){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).df_write_one_sector(context,m_working);
            }while(false);
        }

        return b_result;
    }
    public int bootloader_get_current_sector_index(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_boorloader < 0)
                    continue;
                if( m_list_bootloader == null )
                    continue;
                n_data = m_list_bootloader.get(m_n_cur_boorloader).get_current_sector_index();
            } while (false);
        }
        return n_data;
    }
    boolean bootloader_is_send_complete(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).is_send_complete();
            }while(false);
        }

        return b_result;
    }



    ////////////////////////////
    private Application m_application;
    private final BroadcastReceiver m_usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean b_resut =false;

            if(m_system_mode.is_bootloader()){
                //bootloader mode
                switch (Tools.getActionIntFromActionString(intent.getAction())) {
                    case ManagerIntentAction.INT_USB_ATTACHED:
                        b_resut = _callback_boot_usb_attached(context, intent);
                        break;
                    case ManagerIntentAction.INT_USB_DETACHED:
                        b_resut = _callback_boot_detached(context, intent);
                        break;
                    case ManagerIntentAction.INT_UPDATE_LIST_NO_DEVICE:
                        b_resut = _callback_update_list_no_device(context, intent);
                        break;
                    case ManagerIntentAction.INT_LPU237_PERMISSION:
                        b_resut = _callback_boot_lpu237_permission(context, intent);
                        break;
                    case ManagerIntentAction.INT_GET_INFO_FOR_LIST:
                        b_resut = _callback_boot_get_info_for_list(context, intent);
                        break;
                    case ManagerIntentAction.INT_GET_PARAMETERS:
                        b_resut = _callback_boot_get_parameters(context, intent);
                        break;
                    case ManagerIntentAction.INT_START_BOOTLOADER:
                        b_resut = _callback_started_bootloader(context, intent);
                        break;
                    case ManagerIntentAction.INT_BOOTLOADER_PERMISSION:
                        b_resut = _callback_boot_bootloader_permission(context, intent);
                        break;
                    case ManagerIntentAction.INT_ERASE_FIRMWARE:
                        b_resut = _callback_boot_erase_firmware(context, intent);
                        break;
                    case ManagerIntentAction.INT_WRITE_SECTOR:
                        b_resut = _callback_boot_write_sector(context, intent);
                        break;
                    case ManagerIntentAction.INT_WRITE_COMPLETE:
                        b_resut = _callback_boot_write_complete(context, intent);
                        break;
                    case ManagerIntentAction.INT_START_APP:
                        b_resut = _callback_boot_started_app(context, intent);
                        break;
                    case ManagerIntentAction.INT_RECOVER_PARAMETER:
                        b_resut = _callback_boot_recover_parameter(context, intent);
                        break;
                    default:
                        b_resut = true;
                        //Log.i("mgmt:BonReceive", intent.getAction());
                        break;
                }//end switch
            }
            else {//normal mode
                switch (Tools.getActionIntFromActionString(intent.getAction())) {
                    case ManagerIntentAction.INT_USB_ATTACHED:
                        b_resut = _callback_normal_usb_attached(context, intent);
                        break;
                    case ManagerIntentAction.INT_USB_DETACHED:
                        b_resut = _callback_normal_detached(context, intent);
                        break;
                    case ManagerIntentAction.INT_UPDATE_LIST_NO_DEVICE:
                        b_resut = _callback_update_list_no_device(context, intent);
                        break;
                    case ManagerIntentAction.INT_LPU237_PERMISSION:
                        b_resut = _callback_normal_lpu237_permission(context, intent);
                        break;
                    case ManagerIntentAction.INT_GET_INFO_FOR_LIST:
                        b_resut = _callback_normal_get_info_for_list(context, intent);
                        break;
                    case ManagerIntentAction.INT_GET_PARAMETERS:
                        b_resut = _callback_normal_get_parameters(context, intent);
                        break;
                    case ManagerIntentAction.INT_SET_PARAMETERS:
                        b_resut = _callback_normal_set_parameters(context, intent);
                        break;
                    case ManagerIntentAction.INT_START_BOOTLOADER:
                        b_resut = _callback_started_bootloader(context, intent);
                        break;
                    case ManagerIntentAction.INT_BOOTLOADER_PERMISSION:
                        b_resut = _callback_normal_bootloader_permission(context, intent);
                        break;
                    default:
                        b_resut = true;
                        //Log.i("mgmt:NonReceive", intent.getAction());
                        break;
                }//end switch
            }

            if( !b_resut ){
                Log.i("mgmt:onReceive - error", intent.getAction());
            }
        }
    };

    private boolean _result_from_worker_is_success(Intent intent ){
        boolean b_result = false;
        do{
            if( intent == null )
                continue;
            ManagerDevice.Response response = ManagerDevice.getInstance().getResponse(
                    intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INDEX, -1)
            );
            if (response.getResult() != TypeRequestResult.RequestResult_success)
                continue;
            //
            b_result = true;
        }while(false);
        return b_result;
    }

    private ManagerDevice.Response _response_from_worker_is_success(Intent intent ){
        ManagerDevice.Response response = null;
        do{
            if( intent == null )
                continue;
            response = ManagerDevice.getInstance().getResponse(
                    intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INDEX, -1)
            );
        }while(false);
        return response;
    }

    private void _result_broadcast_from_worker(
            String s_action, ManagerDevice.Request request
            , TypeRequestResult result, int n_secor_index ){
        Integer index_result = setResponse( request.getRequest(), request.getContext(), result);
        Intent intent = null;

        Context context = request.getContext();
        if( context == null )
            context = m_application;

        intent = new Intent(s_action );
        intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INDEX, index_result.intValue());
        intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR_INDEX, n_secor_index);
        context.sendBroadcast(intent);
    }

    private boolean _run_update_list(ManagerDevice.Request request)
    {
        boolean b_result = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;

        do{
            HashMap<String, UsbDevice> usbDevices = m_usbManager.getDeviceList();
            if( usbDevices.isEmpty() ){
                continue;
            }

            PendingIntent pendingIntent = null;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                UsbDevice device = entry.getValue();

                if( (device.getVendorId() == Lpu237Info.USB_VID) && (device.getProductId() == Lpu237Info.USB_PID )) {
                    pendingIntent =
                            PendingIntent.getBroadcast(
                                    request.getContext()//m_application
                                    , 0
                                    , new Intent(ManagerIntentAction.LPU237_PERMISSION)
                                    , 0);
                    m_usbManager.requestPermission(device, pendingIntent);
                    result = TypeRequestResult.RequestResult_success;
                    b_result = true;
                    break;//exit for - only one device supports
                }
                else if( (device.getVendorId() == HidBootLoaderInfo.USB_VID) && (device.getProductId() == HidBootLoaderInfo.USB_PID )) {
                    pendingIntent =
                            PendingIntent.getBroadcast(
                                    request.getContext()//m_application
                                    , 0
                                    , new Intent(ManagerIntentAction.BOOTLOADER_PERMISSION)
                                    , 0);
                    m_usbManager.requestPermission(device, pendingIntent);
                    result = TypeRequestResult.RequestResult_success;
                    b_result = true;
                    break;//exit for - only one device supports
                }
            }//end for

        }while(false);

        if( !b_result ) {
            //error case only
            _result_broadcast_from_worker(ManagerIntentAction.UPDATE_LIST_NO_DEVICE, request, result, -1);
        }
        return b_result;
    }

    private boolean _run_get_info_for_list(ManagerDevice.Request request)
    {
        boolean b_result = false;
        boolean b_need_close = false;
        boolean b_need_leave_config = false;

        TypeRequestResult result = TypeRequestResult.RequestResult_error;

        do{
            if( !lpu237_open() ){
                Log.i("run","error : open");
                continue;
            }

            b_need_close = true;

            if( !lpu237_df_enter_config() ){
                Log.i("run","error : df_enter_config");
                continue;
            }
            b_need_leave_config = true;

            if( !lpu237_df_get_uid() ){
                Log.i("run","error : df_get_uid");
                continue;
            }
            if( !lpu237_df_get_type() ){
                Log.i("run","error : df_get_type");
                continue;
            }

            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        if( b_need_leave_config ) {
            if (!lpu237_df_leave_config()) {
                Log.i("run", "error : df_leave_config");
            }
        }
        if( b_need_close )
            lpu237_close();
        //
        _result_broadcast_from_worker( ManagerIntentAction.GET_INFO_FOR_LIST,request, result,-1 );
        return b_result;
    }

    private boolean _run_get_uid(ManagerDevice.Request request)
    {
        boolean b_result = false;
        boolean b_need_close = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;

        do{
            if( !lpu237_open() ){
                Log.i("run","error : open");
                continue;
            }

            b_need_close = true;

            if( !lpu237_df_get_uid() ){
                Log.i("run","error : df_get_uid");
                continue;
            }

            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        if( b_need_close )
            lpu237_close();
        //
        _result_broadcast_from_worker( ManagerIntentAction.UPDATE_UID,request, result,-1 );
        return b_result;
    }

    private boolean _run_get_parameters(ManagerDevice.Request request)
    {
        boolean b_result = false;
        boolean b_need_close = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        do{
            if( !lpu237_open() ){
                continue;
            }
            b_need_close = true;

            if( !lpu237_df_get_parameter() ){
                continue;
            }

            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        if( b_need_close )
            lpu237_close();
        //
        _result_broadcast_from_worker( ManagerIntentAction.GET_PARAMETERS,request, result,-1 );

        return b_result;
    }

    private boolean _run_set_parameters(ManagerDevice.Request request)
    {
        boolean b_result = false;
        boolean b_need_close = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        do{
            if( !lpu237_open() ){
                continue;
            }
            b_need_close = true;

            if( !lpu237_df_set_parameter() ){
                continue;
            }

            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        if( b_need_close )
            lpu237_close();
        //
        _result_broadcast_from_worker( ManagerIntentAction.SET_PARAMETERS,request, result,-1 );

        return b_result;
    }

    private boolean _run_start_bootloader(ManagerDevice.Request request)
    {
        boolean b_result = false;

        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        do {
            //start bootloader
            if (request.getFile() == null)
                continue;
            //
            if (!lpu237_open()) {
                continue;
            }
            if( !lpu237_save_parameter() ){
                Log.i("run","error : lpu237_save_parameter");
                continue;
            }
            if( !lpu237_df_enter_config() ){
                Log.i("run","error : df_enter_config");
                continue;
            }

            if (!lpu237_df_run_bootloader()){
                lpu237_df_leave_config();
                lpu237_close();
                continue;
            }

            //success start bootloader.
            lpu237_close();

            m_system_mode.enable_bootloader( request.getStringData(),request.getVersion(), request.getFile());

            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        _result_broadcast_from_worker( ManagerIntentAction.START_BOOTLOADER,request, result,-1 );

        return b_result;
    }

    private boolean _run_firmware_erase(ManagerDevice.Request request)
    {
        boolean b_result = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        do {
            if( !bootloader_df_erase_all() ){
                continue;
            }
            //
            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        _result_broadcast_from_worker( ManagerIntentAction.ERASE_FIRMWARE,request, result,-1 );

        return b_result;
    }

    private boolean _run_firmware_write(ManagerDevice.Request request)
    {
        boolean b_result = false;
        String s_action = ManagerIntentAction.WRITE_SECTOR;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        int n_sector_index = -1;
        do{
            n_sector_index = bootloader_get_current_sector_index();

            if( !bootloader_df_write_one_sector(request.getContext()) ){
                continue;
            }

            if( bootloader_is_send_complete() )
                s_action = ManagerIntentAction.WRITE_COMPLETE;
            //
            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        int n_sector = -1;
        if( n_sector_index >=0 && n_sector_index< HidBootLoaderSectorOrder.order.length)
            n_sector = HidBootLoaderSectorOrder.order[n_sector_index];
        if( m_working.get() )
            _result_broadcast_from_worker( s_action,request, result, n_sector );

        return b_result;
    }

    private boolean _run_run_app(ManagerDevice.Request request)
    {
        boolean b_result = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        do{
            if( !bootloader_df_run_app() ){
                continue;
            }

            _start_waits_connected_timer(m_update_activity);
            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        _result_broadcast_from_worker( ManagerIntentAction.START_APP,request, result,-1 );

        return b_result;
    }

    private boolean _run_recover_parameter(ManagerDevice.Request request)
    {
        boolean b_result = false;
        boolean b_need_close = false;
        TypeRequestResult result = TypeRequestResult.RequestResult_error;
        do{
            if( !lpu237_open() ){
                continue;
            }

            b_need_close = true;

            if( !lpu237_recover_parameter() )
                continue;

            if( !lpu237_df_set_parameter() )
                continue;
            result = TypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        if( b_need_close )
            lpu237_close();
        //
        _result_broadcast_from_worker( ManagerIntentAction.RECOVER_PARAMETER,request, result,-1 );

        return b_result;
    }

    @Override
    public void run() {

        boolean b_result =false;
        TypeRequestResult result = TypeRequestResult.RequestResult_ing;
        Intent intent = null;

        while(m_working.get()){
            try {
                ManagerDevice.Request request = m_blockingQueue.take();
                switch( request.getRequest() ){
                    case Request_kill:
                        b_result = true;
                        continue;
                    case Request_update_list:
                        b_result = _run_update_list(request);
                        break;
                    case Request_get_info_for_list:
                        b_result = _run_get_info_for_list(request);
                        break;
                    case Request_get_uid:
                        b_result = _run_get_uid(request);
                        break;
                    case Request_get_parameters:
                        b_result = _run_get_parameters(request);
                        break;
                    case Request_set_parameters:
                        b_result = _run_set_parameters(request);
                        break;
                    case Request_start_bootloader:
                        b_result = _run_start_bootloader(request);
                        break;
                    case Request_firmware_erase:
                        b_result = _run_firmware_erase(request);
                        break;
                    case Request_firmware_write:
                        b_result = _run_firmware_write(request);
                        break;
                    case Request_run_app:
                        b_result = _run_run_app(request);
                        break;
                    case Request_recover_parameters:
                        b_result = _run_recover_parameter(request);
                        break;
                    default:
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.i("manager", "run:doing");
        }//
        Log.i("manager","run:exit");

    }
}

enum TypeRequest{
    Request_none,
    Request_kill,
    Request_update_list,
    Request_get_info_for_list,
    Request_get_uid,
    Request_get_parameters,
    Request_set_parameters,
    Request_start_bootloader,
    Request_firmware_erase,
    Request_firmware_write,
    Request_run_app,
    Request_recover_parameters
}

enum TypeRequestResult{
    RequestResult_ing,
    RequestResult_success,
    RequestResult_error
}