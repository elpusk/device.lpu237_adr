package kr.pe.sheep_transform.lpu237_adr.lib.mgmt;

import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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

import kr.pe.sheep_transform.lpu237_adr.HidBootCallbackImpl;
import kr.pe.sheep_transform.lpu237_adr.MainActivity;
import kr.pe.sheep_transform.lpu237_adr.ManagerIntentAction;
import kr.pe.sheep_transform.lpu237_adr.R;
import kr.pe.sheep_transform.lpu237_adr.StartUpActivity;
//import kr.pe.sheep_transform.lpu237_adr.Tools;
import kr.pe.sheep_transform.lpu237_adr.UpdateActivity;

import kr.pe.sheep_transform.lpu237_adr.lib.hidboot.HidBootLoader;
import kr.pe.sheep_transform.lpu237_adr.lib.hidboot.HidBootLoaderInfo;
import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237;
import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237Const;
import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237Direction;
import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237Tags;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.Rom;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.RomErrorCodeFirmwareIndex;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.RomResult;
import kr.pe.sheep_transform.lpu237_adr.lib.util.FwVersion;
import kr.pe.sheep_transform.lpu237_adr.lib.util.KeyboardConst;

public class ManagerDevice implements Runnable
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

        private MgmtTypeRequest m_request = MgmtTypeRequest.Request_none;
        private Context m_context = null;
        private File m_file_firmware = null;
        private FwVersion m_version = null;
        private String m_s_data = "";

        public Request(MgmtTypeRequest request, Context context )
        {
            m_request = request;
            m_context = context;
        }

        public Request(MgmtTypeRequest request, Context context, File file_firmware, String s_data, FwVersion version )
        {
            m_request = request;
            m_context = context;
            m_file_firmware = file_firmware;
            m_s_data = s_data;
            m_version = version;
        }

        public MgmtTypeRequest getRequest()
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
        private MgmtTypeRequest m_request = MgmtTypeRequest.Request_none;
        private MgmtTypeRequestResult m_result = MgmtTypeRequestResult.RequestResult_ing;
        private Context m_context = null;
        private File m_file_firmware = null;
        //
        public Response(MgmtTypeRequest request, Context context, MgmtTypeRequestResult result ){
            m_request = request;
            m_context = context;
            m_result = result;
        }
        public Response(MgmtTypeRequest request, Context context, MgmtTypeRequestResult result, File file ){
            m_request = request;
            m_context = context;
            m_result = result;
            m_file_firmware = file;
        }

        public MgmtTypeRequest getRequest()
        {
            return m_request;
        }

        public MgmtTypeRequestResult getResult() {
            return m_result;
        }

        public Context getContext() {
            return m_context;
        }

        public File getFile(){  return m_file_firmware; }

        public void setResult( MgmtTypeRequestResult result ){
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

    private Boolean m_b_waits_attach_bootloader = false;

    public void addCallbackParameter(Object c){
        if(m_cb != null){
            m_cb.addUserPara(c);
        }
    }
    /**
     *
     * @param rom_file using rom file
     * @param s_dev_name target device name
     * @param dev_version target device version
     * @return negative error code, zero or positive rom has a updatable firmware.
     */
    public int check_firmware(File rom_file,String s_dev_name, FwVersion dev_version){
        int n_fw_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_file_header;

        do{
            if( rom_file == null ) {
                continue;
            }
            if( s_dev_name == null ) {
                n_fw_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_device_name;
                continue;
            }
            if( dev_version == null ) {
                n_fw_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_device_version;
                continue;
            }

            Rom rom = new Rom();
            if( rom.load_rom_header(rom_file) != RomResult.result_success ) {
                continue;
            }

            n_fw_index = rom.set_updatable_firmware_index(s_dev_name,dev_version);
        }while(false);
        return n_fw_index;
    }
    public Boolean is_waits_attach_bootloader(){
        return m_b_waits_attach_bootloader;
    }


    public boolean is_startup_with_bootloader(){
        return m_system_mode.is_start_up_bootloader();
    }

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

    public boolean load(Application application,MgmtCallback cb ){
        boolean b_result = false;

        do{
            m_application = application;
            m_cb =cb;
            //
            m_usbManager = (UsbManager) m_application.getSystemService(m_application.USB_SERVICE);

            m_working = new AtomicBoolean(true);
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
                m_blockingQueue.put( new Request(MgmtTypeRequest.Request_kill, m_application));
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            //

            if( size_lpu237() > 0 ){
                lpu237_close();
            }

            m_application = null;

            b_result = true;
        }while(false);

        return b_result;
    }

    public boolean push_requst(MgmtTypeRequest req, Context context, File file_fw, String s_data, FwVersion version )
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
    public boolean push_requst(MgmtTypeRequest req, Context context  )
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

    public Integer setResponse(MgmtTypeRequest request, Context context, MgmtTypeRequestResult result ){
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


    //don't call this function in worker thread.
    private void _broadcast_terminate_app(){
        //
        Intent intent = new Intent(ManagerIntentAction.GENERAL_TERMINATE_APP);
        if( m_application != null )
            m_application.sendBroadcast(intent);
        //
        unload();
    }

    public void select_lpu237( int n_index ){
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

    public boolean set_rom_file(int n_index_bootloader, File rom_file,String s_dev_name, FwVersion dev_version ){
        boolean b_result = false;
        synchronized (m_lock_device_list) {
            do {
                if( m_list_bootloader == null )
                    continue;
                if( m_list_bootloader.size()<= n_index_bootloader )
                    continue;
                //
                b_result = m_list_bootloader.get(n_index_bootloader).set_rom_file(rom_file, s_dev_name, dev_version);
            }while (false);
        }
        return b_result;
    }

    /**
     *
     * @return string error description in processing "set_rom_file(int n_index_bootloader, File rom_file,String s_dev_name, FwVersion dev_version )"
     */
    public String get_error_description_firmware_index_setting(int n_index_bootloader){
        String s_description="";
        synchronized (m_lock_device_list) {
            do {
                if( m_list_bootloader == null ) {
                    s_description = "none bootloader";
                    continue;
                }
                if( m_list_bootloader.size()<= n_index_bootloader ) {
                    s_description = "invalid bootloader index["+Integer.toString(n_index_bootloader)+"/"+Integer.toString(m_list_bootloader.size())+"]";
                    continue;
                }
                s_description = Rom.get_error_description_firmware_index_setting(m_list_bootloader.get(n_index_bootloader).get_fw_index());
            } while (false);
        }
        return s_description;
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
    public String lpu237_getDecoder(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getDecoder();
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
    public String[] lpu237_getAvailableAlliButtonTypesDescription(){
        String[] s_data = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getAvailableAlliButtonTypesDescription();
            } while (false);
        }
        return s_data;
    }
    public  String[] lpu237_getAvailableAllInterfaces(){
        String[] s_data = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getAvailableAllInterfaces();
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

    public  String lpu237_getMmd1100ResetInterval(){
        String s_data = "";
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                s_data = m_list_devices.get(m_n_cur_lpu237).getMmd1100ResetInterval();
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
    public byte lpu237_get_reading_direction(){
        byte c_data = Lpu237Direction.biDirection;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237 <0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                c_data = m_list_devices.get(m_n_cur_lpu237).get_reading_direction();
            }while (false);
        }
        return c_data;
    }
    public byte[] lpu237_get_track_order(){
        byte[] order = {0,1,2};
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237 <0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                order = m_list_devices.get(m_n_cur_lpu237).get_track_order();
            }while (false);
        }
        return order;
    }

    public boolean lpu237_get_any_good_indicate_success(){
        boolean b_data = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237 <0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_data = m_list_devices.get(m_n_cur_lpu237).get_any_good_indicate_success();
            }while (false);
        }
        return b_data;
    }

    public int lpu237_get_mmd1100_reset_interval(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_mmd1100_reset_interval();
            } while (false);
        }
        return n_data;
    }

    public int lpu237_get_ibutton_start(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_ibutton_start();
            } while (false);
        }
        return n_data;
    }

    public int lpu237_get_ibutton_end(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                n_data = m_list_devices.get(m_n_cur_lpu237).get_ibutton_end();
            } while (false);
        }
        return n_data;
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

    public Lpu237Tags lpu237_get_global_prefix(){
        Lpu237Tags tag = null;
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
    public Lpu237Tags lpu237_get_global_postfix(){
        Lpu237Tags tag = null;
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
    public Lpu237Tags lpu237_get_private_prefix( int n_track ){
        Lpu237Tags tag = null;
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
    public Lpu237Tags lpu237_get_private_postfix( int n_track ){
        Lpu237Tags tag = null;
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
    public Lpu237Tags lpu237_get_ibutton_tag_prefix(){
        Lpu237Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_tag_prefix();
            } while (false);
        }
        return tag;
    }

    public Lpu237Tags lpu237_get_ibutton_tag_postfix(){
        Lpu237Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_tag_postfix();
            } while (false);
        }
        return tag;
    }

    public Lpu237Tags lpu237_get_ibutton_remove(){
        Lpu237Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_remove();
            } while (false);
        }
        return tag;
    }

    public Lpu237Tags lpu237_get_ibutton_remove_tag_prefix(){
        Lpu237Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_remove_tag_prefix();
            } while (false);
        }
        return tag;
    }
    public Lpu237Tags lpu237_get_ibutton_remove_tag_postfix(){
        Lpu237Tags tag = null;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                tag = m_list_devices.get(m_n_cur_lpu237).get_ibutton_remove_tag_postfix();
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
    public void lpu237_set_global_prefix( Lpu237Tags tag ){
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
    public void lpu237_set_global_postfix( Lpu237Tags tag ){
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
    public void lpu237_set_private_prefix( int n_track,Lpu237Tags tag){
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
    public void lpu237_set_private_postfix( int n_track,Lpu237Tags tag ){
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
    public void lpu237_set_ibutton_tag_prefix( Lpu237Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_tag_prefix(tag);
            } while (false);
        }
    }
    public void lpu237_set_ibutton_tag_postfix( Lpu237Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_tag_postfix(tag);
            } while (false);
        }
    }
    public void lpu237_set_ibutton_remove( Lpu237Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_remove(tag);
            } while (false);
        }
    }
    public void lpu237_set_ibutton_remove_tag_prefix( Lpu237Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_remove_tag_prefix(tag);
            } while (false);
        }
    }
    public void lpu237_set_ibutton_remove_tag_postfix( Lpu237Tags tag ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_remove_tag_postfix(tag);
            } while (false);
        }
    }

    public void lpu237_set_uart_prefix( Lpu237Tags tag ){
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
    public void lpu237_set_uart_postfix( Lpu237Tags tag ){
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
    public void lpu237_set_any_good_indicate_success( boolean b_any ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_any_good_indicate_success(b_any);
            } while (false);
        }
    }

    public  void lpu237_set_ibutton_end(int n_pos){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_end(n_pos);
            } while (false);
        }
    }

    public  void lpu237_set_ibutton_start(int n_pos){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_ibutton_start(n_pos);
            } while (false);
        }
    }
    public void lpu237_set_mmd1100_reset_interval( int n_reset ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_mmd1100_reset_interval(n_reset);
            } while (false);
        }
    }

    public void lpu237_set_reading_direction( byte c_dir ){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_reading_direction(c_dir);
            } while (false);
        }
    }

    public void lpu237_set_track_order( byte[] order){
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_lpu237 < 0)
                    continue;
                if( m_list_devices == null )
                    continue;
                //
                m_list_devices.get(m_n_cur_lpu237).set_track_order(order);
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
                Lpu237Tags [] tags_pre = new Lpu237Tags[3];
                Lpu237Tags [] tags_post = new Lpu237Tags[3];

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
                Lpu237Tags [] tags_pre = new Lpu237Tags[3];
                Lpu237Tags [] tags_post = new Lpu237Tags[3];

                for( n_track = 0; n_track<3; n_track++ ){
                    tags_pre[n_track] = new Lpu237Tags();
                    tags_post[n_track] = new Lpu237Tags();
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

    boolean lpu237_df_get_ibutton_only_type(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_ibutton_only_type();
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
    boolean lpu237_df_get_version_structure(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_version_structure();
            }while(false);
        }

        return b_result;
    }

    boolean lpu237_df_get_version_system(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_version_system();
            }while(false);
        }

        return b_result;
    }

    boolean lpu237_df_get_name(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_lpu237<0 )
                    continue;
                if( m_list_devices == null )
                    continue;
                b_result = m_list_devices.get(m_n_cur_lpu237).df_get_name();
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

    /**
     * get sector info. if gotten the sector info, order array will be adjusted.
     * @return true - communication success
     */
    boolean bootloader_df_get_sector_info(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).df_get_sector_info();
            }while(false);
        }
        return b_result;
    }
    boolean bootloader_df_erase_one_sector(Context context){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).df_erase_one_sector(context,m_working);
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

    public int bootloader_get_current_write_sector(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_boorloader < 0)
                    continue;
                if( m_list_bootloader == null )
                    continue;
                n_data = m_list_bootloader.get(m_n_cur_boorloader).get_current_write_sector();
            } while (false);
        }
        return n_data;
    }
    public int bootloader_get_current_erase_sector(){
        int n_data = -1;
        synchronized (m_lock_device_list) {
            do {
                if (m_n_cur_boorloader < 0)
                    continue;
                if( m_list_bootloader == null )
                    continue;
                n_data = m_list_bootloader.get(m_n_cur_boorloader).get_current_erase_sector();
            } while (false);
        }
        return n_data;
    }

    boolean bootloader_is_write_complete(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).is_write_complete();
            }while(false);
        }

        return b_result;
    }
    boolean bootloader_is_erase_complete(){
        boolean b_result = false;
        synchronized (m_lock_device_list){
            do{
                if( m_n_cur_boorloader<0 )
                    continue;
                if( m_list_bootloader == null )
                    continue;
                b_result = m_list_bootloader.get(m_n_cur_boorloader).is_erase_complete();
            }while(false);
        }

        return b_result;
    }



    ////////////////////////////
    private Application m_application;
    private MgmtCallback m_cb = null;

    private boolean _result_from_worker_is_success(Intent intent ){
        boolean b_result = false;
        do{
            if( intent == null )
                continue;
            ManagerDevice.Response response = ManagerDevice.getInstance().getResponse(
                    intent.getIntExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_INDEX, -1)
            );
            if (response.getResult() != MgmtTypeRequestResult.RequestResult_success)
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
            , MgmtTypeRequestResult result, int n_secor_index ){
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
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;

        do{
            HashMap<String, UsbDevice> usbDevices = m_usbManager.getDeviceList();
            if( usbDevices.isEmpty() ){
                continue;
            }

            boolean b_need_permission = true;

            PendingIntent pendingIntent = null;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                UsbDevice device = entry.getValue();

                if( (device.getVendorId() == Lpu237Const.USB_VID) && (device.getProductId() == Lpu237Const.USB_PID )) {
                    if( m_system_mode.is_start_up_bootloader() ){
                        if( m_usbManager.hasPermission(device)) {
                            _add_to_list(new Lpu237(m_usbManager, device));//add device
                            select_lpu237(0);

                            b_need_permission = false;
                            m_system_mode.disable_bootloader();
                            if(m_cb != null){
                                m_cb.cbDetectLpu237AfterFwUpdateInBLStartModeNoNeedPermission(new ArrayList<>());
                            }
                            //showFwDownloadOk("Parameters is default.(by bootloader)");
                        }
                    }
                    if(b_need_permission) {
                        if(m_cb != null){
                            m_cb.cbDetectLpu237NeedPermission(request.getContext(),m_usbManager,device);
                        }
                    }
                    result = MgmtTypeRequestResult.RequestResult_success;
                    b_result = true;
                    break;//exit for - only one device supports
                }
                else if( (device.getVendorId() == HidBootLoaderInfo.USB_VID) && (device.getProductId() == HidBootLoaderInfo.USB_PID )) {
                    if(m_cb != null){
                        m_cb.cbDetectHidbootLoaderNeedPermission(request.getContext(),m_usbManager,device);
                    }
                    result = MgmtTypeRequestResult.RequestResult_success;
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

        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;

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

            if( !lpu237_df_get_name() ){
                Log.i("run","error : lpu237_df_get_name");
                continue;
            }
            if( !lpu237_df_get_version_system() ){
                Log.i("run","error : lpu237_df_get_version_system");
                continue;
            }
            if( !lpu237_df_get_ibutton_only_type()) {
                Log.i("run","error : lpu237_df_get_ibutton_only_type");
                continue;
            }

            if( !lpu237_df_get_uid() ){
                Log.i("run","error : df_get_uid");
                continue;
            }
            if( !lpu237_df_get_type() ){
                Log.i("run","error : df_get_type");
                continue;
            }

            result = MgmtTypeRequestResult.RequestResult_success;
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
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;

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

            result = MgmtTypeRequestResult.RequestResult_success;
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
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do{
            if( !lpu237_open() ){
                continue;
            }
            b_need_close = true;

            if( !lpu237_df_get_parameter() ){
                continue;
            }

            result = MgmtTypeRequestResult.RequestResult_success;
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
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do{
            if( !lpu237_open() ){
                continue;
            }
            b_need_close = true;

            if( !lpu237_df_set_parameter() ){
                continue;
            }

            result = MgmtTypeRequestResult.RequestResult_success;
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

        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
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

            result = MgmtTypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        _result_broadcast_from_worker( ManagerIntentAction.START_BOOTLOADER,request, result,-1 );

        return b_result;
    }

    /**
     * get sector information from device.( for himalia )
     * @param request
     */
    private boolean _run_get_sector_info(ManagerDevice.Request request){
        boolean b_result = false;
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do{
            if( !bootloader_df_get_sector_info() ){
                continue;
            }
            //
            result = MgmtTypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        _result_broadcast_from_worker(ManagerIntentAction.SECTOR_INFO, request, result, -1);
        return b_result;
    }
    private boolean _run_firmware_erase(ManagerDevice.Request request)
    {
        boolean b_result = false;
        String s_action = ManagerIntentAction.ERASE_SECTOR;
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do {
            if( !bootloader_df_erase_one_sector(request.getContext()) ){
                continue;
            }
            //
            if( bootloader_is_erase_complete() )
                s_action = ManagerIntentAction.ERASE_COMPLETE;

            result = MgmtTypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        int n_sector = bootloader_get_current_erase_sector();

        if( m_working.get() ) {
            _result_broadcast_from_worker(s_action, request, result, n_sector);
        }
        return b_result;
    }

    private boolean _run_firmware_write(ManagerDevice.Request request)
    {
        boolean b_result = false;
        String s_action = ManagerIntentAction.WRITE_SECTOR;
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do{
            if( !bootloader_df_write_one_sector(request.getContext()) ){
                continue;
            }

            if( bootloader_is_write_complete() )
                s_action = ManagerIntentAction.WRITE_COMPLETE;
            //
            result = MgmtTypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        int n_sector = bootloader_get_current_write_sector();

        if( m_working.get() )
            _result_broadcast_from_worker( s_action,request, result, n_sector );

        return b_result;
    }

    private boolean _run_run_app(ManagerDevice.Request request)
    {
        boolean b_result = false;
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do{
            if( !bootloader_df_run_app() ){
                continue;
            }

            result = MgmtTypeRequestResult.RequestResult_success;
            b_result = true;
        }while(false);

        _result_broadcast_from_worker( ManagerIntentAction.START_APP,request, result,-1 );

        return b_result;
    }

    private boolean _run_recover_parameter(ManagerDevice.Request request)
    {
        boolean b_result = false;
        boolean b_need_close = false;
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_error;
        do{
            if( !lpu237_open() ){
                continue;
            }

            b_need_close = true;

            if( !lpu237_recover_parameter() )
                continue;

            if( !lpu237_df_set_parameter() )
                continue;
            result = MgmtTypeRequestResult.RequestResult_success;
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
        MgmtTypeRequestResult result = MgmtTypeRequestResult.RequestResult_ing;
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
                    case Request_firmware_sector_info:
                        b_result = _run_get_sector_info(request);
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



