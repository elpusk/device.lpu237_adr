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
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

/**
 * manage Lpu237 & Hidbootloader
 * Used Singleton Design pattern
 * Use getInstance() method for getting instance.
 */
public class ManagerDevice
{
    private static class Singleton {
        private static final ManagerDevice instance = new ManagerDevice();
    }

    public static ManagerDevice getInstance () {
        return Singleton.instance;
    }

    private UsbManager m_usbManager = null;

    private HashMap<String, UsbDevice> m_lpu237Devices = new HashMap<>();//not managed
    private HashMap<String, UsbDevice> m_hidbootDevices = new HashMap<>();//not managed
    private Object m_lock_device_list = new Object();
    private String m_s_cur_lpu237 = "";    //key of m_map_sel_lpu237
    private Lpu237 m_cur_lpu237 = null;
    private String m_s_cur_hidbootloader = "";    //the key of m_map_sel_hidbootloader
    private Lpu237 m_cur_hidbootloader = null;
    private Lpu237.Parameters m_parameter_for_recover = new Lpu237.Parameters();

    private Boolean m_b_waits_attach_bootloader = false;


    /////////////////////////////////////////////////////////////////////////////////
    // methods.

    private ManagerDevice(){
    }

    /**
     * Export!!!!!!!
     * initialize single instance.
     * @param application
     * @param cb
     * @return true - success
     */
    public boolean load(Application application,MgmtCallback cb ){
        boolean b_result = false;

        do{
            if(application == null){
                continue;
            }
            if(cb == null){
                continue;
            }
            if(m_usbManager != null ){
                b_result = true;
                continue;//already loaded
            }
            //
            m_application = application;
            m_cb =cb;
            m_usbManager = (UsbManager) m_application.getSystemService(Context.USB_SERVICE);
            if(m_usbManager == null){
                m_application = null;
                m_cb = null;
                continue;
            }
            b_result = true;
        }while(false);

        return b_result;
    }

    /**
     * Export!!!!!!!
     * uninitialize single instance.
     * @return true - success
     */
    public boolean unload(){
        boolean b_result = false;
        do{
            if( m_usbManager == null ) {
                b_result = true;
                continue;
            }
            //
            if( size_lpu237() > 0 ){
                lpu237_close();
            }

            m_usbManager = null;
            m_application = null;
            m_cb = null;
            b_result = true;
        }while(false);

        return b_result;
    }

    /**
     * update current lpu237 paths, bootloader . m_lpu237Devices & m_hidbootDevices
     * @return none
     */
    private void _update_device_list(){
        do{
            if(m_usbManager == null){
                continue;
            }

            m_lpu237Devices.clear();
            m_hidbootDevices.clear();

            HashMap<String, UsbDevice> usbDevices = m_usbManager.getDeviceList();
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                UsbDevice device = entry.getValue();

                if( (device.getVendorId() == Lpu237Const.USB_VID) && (device.getProductId() == Lpu237Const.USB_PID )) {
                    m_lpu237Devices.put(entry.getKey(),entry.getValue());
                }
                else if( (device.getVendorId() == HidBootLoaderInfo.USB_VID) && (device.getProductId() == HidBootLoaderInfo.USB_PID )) {
                    m_hidbootDevices.put(entry.getKey(),entry.getValue());
                }
            }//end for

        }while (false);
    }

    /**
     * Export!!!!!!!
     * get current lpu237 path array
     * @return lpu237 path string array
     */
    public String[] get_lpu237_list(){
        this._update_device_list();
        String[] s = new String[m_lpu237Devices.size()];
        if(!m_lpu237Devices.isEmpty()) {
            m_lpu237Devices.keySet().toArray(s);
        }
        return s;
    }

    /**
     * get current hidbootloader path array
     * @return hidbootloader path string array
     */
    public String[] get_HidBootloader_list(){
        this._update_device_list();
        String[] s = new String[m_hidbootDevices.size()];
        if(!m_hidbootDevices.isEmpty()) {
            m_hidbootDevices.keySet().toArray(s);
        }
        return s;
    }

    public boolean open_lpu237(String s_path){
        boolean b_result = false;

        do{
            if(s_path == null){
                continue;
            }
            if(s_path.isEmpty()){
                continue;
            }
            synchronized (m_lock_device_list) {
                if(m_cur_lpu237 != null ){
                    if(m_cur_lpu237.is_opened()){
                        continue;//already opened
                    }
                }
                // current lpu123 not selected or not opened
                if( !m_lpu237Devices.containsKey(s_path) ){
                    continue;
                }
                m_cur_lpu237 = new Lpu237(m_usbManager,m_lpu237Devices.get(s_path));
                if(m_cur_lpu237 ==null){
                    continue;
                }
                if( !m_cur_lpu237.open() ){
                    continue;
                }
                b_result = true;
            }
        }while(false);
        return b_result;
    }
    public boolean close_lpu237(){
        boolean b_result = false;

        do{
            synchronized (m_lock_device_list) {
                if(m_cur_lpu237 == null){
                    continue;
                }
                if(!m_cur_lpu237.is_opened()){
                    continue;
                }
                m_cur_lpu237.close();
                m_s_cur_lpu237 = "";
                m_cur_lpu237 = null;
                b_result = true;
            }
        }while(false);
        return b_result;
    }

    public boolean select_lpu237(int n_index){
        boolean b_result = false;
        do{
            synchronized (m_lock_device_list) {
                if(m_map_sel_lpu237.size()>n_index){
                    m_map_sel_lpu237.entrySet().toArray()[n_index]
                    continue;
                }
                if (m_list_devices == null)
                    continue;
                if (n_index >= m_list_devices.size())
                    continue;
                if (n_index < 0)
                    continue;
                m_n_cur_lpu237 = n_index;
            }

            b_result = true;
        }while(false);
        return b_result;
    }
    /////////////////////////////////////////////////////////////////
    //
    public void addCallbackParameter(Object c){
        if(m_cb != null){
            m_cb.addUserPara(c);
        }
    }
    public Boolean is_waits_attach_bootloader(){
        return m_b_waits_attach_bootloader;
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
    private Application m_application = null;
    private MgmtCallback m_cb = null;

}



