package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

//import junit.runner.Version;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashSet;

import kr.pe.sheep_transform.lpu237_adr.ManagerDevice;
import kr.pe.sheep_transform.lpu237_adr.lib.hid.HidDevice;
import kr.pe.sheep_transform.lpu237_adr.lib.util.FwVersion;
import kr.pe.sheep_transform.lpu237_adr.lib.util.IntByteConvert;
import kr.pe.sheep_transform.lpu237_adr.lib.util.KeyboardConst;
import kr.pe.sheep_transform.lpu237_adr.lib.util.KeyboardMap;
import kr.pe.sheep_transform.lpu237_adr.lib.util.Tools;

public class Lpu237 extends HidDevice
{
    //
    private HashSet<Integer> m_set_changed = new HashSet<>();// data set is Lpu237ChangedParameter's member

    static public int[] convert_ibutton_mode_description_string_to_type_number(String[] s_type){
        int[] n_type = null;
        if(s_type==null){
            return n_type;
        }

        //check type
        for(int i=0; i<s_type.length; i++){
            switch (s_type[i]){
                case Lpu237iButtonTypeDescriptionString.Zeros:
                case Lpu237iButtonTypeDescriptionString.Zeros7:
                case Lpu237iButtonTypeDescriptionString.F12:
                case Lpu237iButtonTypeDescriptionString.Addmit:
                case Lpu237iButtonTypeDescriptionString.None:
                    break;
                default:
                    return n_type;
            }//end switch
        }//end for

        n_type = new int[s_type.length];
        for(int i=0; i<s_type.length; i++){
            switch (s_type[i]){
                case Lpu237iButtonTypeDescriptionString.Zeros:
                    n_type[i] = Lpu237iButtonType.Zeros;
                    break;
                case Lpu237iButtonTypeDescriptionString.Zeros7:
                    n_type[i] = Lpu237iButtonType.Zeros7;
                    break;
                case Lpu237iButtonTypeDescriptionString.F12:
                    n_type[i] = Lpu237iButtonType.F12;
                    break;
                case Lpu237iButtonTypeDescriptionString.Addmit:
                    n_type[i] = Lpu237iButtonType.Addmit;
                    break;
                case Lpu237iButtonTypeDescriptionString.None:
                    n_type[i] = Lpu237iButtonType.None;
                    break;
                default:
                    break;
            }//end switch
        }//end for
        return n_type;
    }
    static public String[] convert_ibutton_mode_type_number_to_description_string(int[] n_type){
        String[] s_type = null;
        if(n_type==null){
            return s_type;
        }

        //check type
        for(int i=0; i<n_type.length; i++){
            switch (n_type[i]){
            case Lpu237iButtonType.Zeros:
            case Lpu237iButtonType.Zeros7:
            case Lpu237iButtonType.F12:
            case Lpu237iButtonType.Addmit:
            case Lpu237iButtonType.None:
                break;
            default:
                return s_type;
            }//end switch
        }//end for

        s_type = new String[n_type.length];
        for(int i=0; i<n_type.length; i++){
            switch (n_type[i]){
                case Lpu237iButtonType.Zeros:
                    s_type[i] = Lpu237iButtonTypeDescriptionString.Zeros;
                    break;
                case Lpu237iButtonType.Zeros7:
                    s_type[i] = Lpu237iButtonTypeDescriptionString.Zeros7;
                    break;
                case Lpu237iButtonType.F12:
                    s_type[i] = Lpu237iButtonTypeDescriptionString.F12;
                    break;
                case Lpu237iButtonType.Addmit:
                    s_type[i] = Lpu237iButtonTypeDescriptionString.Addmit;
                    break;
                case Lpu237iButtonType.None:
                    s_type[i] = Lpu237iButtonTypeDescriptionString.None;
                    break;
                default:
                    break;
            }//end switch
        }//end for
        return s_type;
    }
    static public String[] convert_interface_type_number_to_string(int[] n_inf){
        String[] s_inf = null;

        if(n_inf==null){
            return s_inf;
        }

        //checks vaild
        for(int i=0; i<n_inf.length; i++){
            switch (n_inf[i]){
                case Lpu237Interface.usbKeyboard:
                case Lpu237Interface.usbVendorHid:
                case Lpu237Interface.uart:
                case Lpu237Interface.usbVCom:
                    break;
                default:
                    return s_inf;
            }//end switch
        }//end for

        s_inf = new String[n_inf.length];
        //converts
        for(int i=0; i<n_inf.length; i++){
            switch (n_inf[i]){
                case Lpu237Interface.usbKeyboard:
                    s_inf[i] = Lpu237InterfaceString.sUsbKeyboard;
                    break;
                case Lpu237Interface.usbVendorHid:
                    s_inf[i] = Lpu237InterfaceString.sUsbHid;
                    break;
                case Lpu237Interface.uart:
                    s_inf[i] = Lpu237InterfaceString.sRS232;
                    break;
                case Lpu237Interface.usbVCom:
                    s_inf[i] = Lpu237InterfaceString.sUsbVCom;
                    break;
                default:
                    break;
            }//end switch
        }//end for

        return s_inf;
    }
    static public int[] convert_interface_type_string_to_number(String[] s_inf){
        int[] n_inf = null;

        boolean b_valid = true;
        if(s_inf==null){
            return n_inf;
        }

        //checks vaild
        for(int i=0; i<s_inf.length; i++){
            switch (s_inf[i]){
                case Lpu237InterfaceString.sUsbKeyboard:
                case Lpu237InterfaceString.sUsbHid:
                case Lpu237InterfaceString.sRS232:
                case Lpu237InterfaceString.sUsbVCom:
                    break;
                default:
                    return n_inf;
            }//end switch
        }//end for

        n_inf = new int[s_inf.length];
        //converts
        for(int i=0; i<s_inf.length; i++){
            switch (s_inf[i]){
                case Lpu237InterfaceString.sUsbKeyboard:
                    n_inf[i] = Lpu237Interface.usbKeyboard;
                    break;
                case Lpu237InterfaceString.sUsbHid:
                    n_inf[i] = Lpu237Interface.usbVendorHid;
                    break;
                case Lpu237InterfaceString.sRS232:
                    n_inf[i] = Lpu237Interface.uart;
                    break;
                case Lpu237InterfaceString.sUsbVCom:
                    n_inf[i] = Lpu237Interface.usbVCom;
                    break;
                default:
                    break;
            }//end switch
        }//end for

        return n_inf;
    }
    public boolean is_changed(){
        return !m_set_changed.isEmpty();
    }
    private boolean _is_changed( int n_change ) {
        boolean b_changed = m_set_changed.contains(n_change);
        return b_changed;
    }
    private void _set_change( int n_change ){
        if( !m_set_changed.contains(n_change) )
            m_set_changed.add(n_change);
    }
    @Override
    public int get_vid() {
        return Lpu237Const.USB_VID;
    }

    @Override
    public int get_pid() {
        return Lpu237Const.USB_PID;
    }

    /**
     * get USB interface number
     * @return the interface number of LPU237 device.
     */
    @Override
    public int get_interface_number() {
        return Lpu237Const.USB_INF;
    }

    @Override
    public int get_in_report_size() {
        return Lpu237Const.SIZE_REPORT_IN;
    }

    @Override
    public int get_out_report_size() {
        return Lpu237Const.SIZE_REPORT_OUT;
    }

    public String getDescription()
    {
        String s_description = m_parameters.getDescription();
        return s_description;
    }

    ////////////////////////////////
    public boolean get_decoder_mmd1000(){
        return m_parameters.get_decoder_mmd1000();
    }
    public String getDecoder(){
        if( getName().compareTo("himalia") == 0){
            return "MH1902T";
        }
        else {
            if (get_decoder_mmd1000())
                return "MMD1100";
            else
                return "DeltaAsic";
        }
    }

    public int get_ibutton_type(){
        return m_parameters.get_ibutton_type();
    }
    public void set_ibutton_type( int n_type ){
        if( m_parameters.set_ibutton_type(n_type) )
            _set_change( Lpu237ChangedParameter.BlankField);
    }
    public String getiButtonType(){
        String s_type = "unknown";

        switch (get_ibutton_type()){
            case Lpu237iButtonType.Zeros:
                s_type = "Zeros";
                break;
            case Lpu237iButtonType.Zeros7:
                s_type = "Zeros7Times";
                break;
            case Lpu237iButtonType.F12:
                s_type = "F12";
                break;
            case Lpu237iButtonType.Addmit:
                s_type = "Addmit";
                break;
            case Lpu237iButtonType.None:
                s_type = "None";
                break;
            default:
                break;
        }//end switch
        return s_type;
    }

    public String[] getAvailableAlliButtonTypesDescription(){
        return Lpu237.convert_ibutton_mode_type_number_to_description_string(
                get_available_all_ibutton_types()
        );
    }
    public int[] get_available_all_ibutton_types(){
        int[] n_type = null;
        String s_n = getName();
        String s_v = getVersionSystem();
        do {
            if(Lpu237Tools.is_support_ibutton_send_setting_16zeros_f12_7zeros_on_remove_in_kb_mode(s_n,s_v)){
                n_type = new int[]{
                        Lpu237iButtonType.Zeros,
                        Lpu237iButtonType.Zeros7,
                        Lpu237iButtonType.F12,
                        Lpu237iButtonType.Addmit,
                        Lpu237iButtonType.None // this case : when i-button is removed,  msr sends user-defined remove-values.
                };
                continue;
            }
            if(Lpu237Tools.is_support_ibutton_code_stick(s_n,s_v)){
                n_type = new int[]{
                        Lpu237iButtonType.Zeros,
                        Lpu237iButtonType.Zeros7,
                        Lpu237iButtonType.F12,
                        Lpu237iButtonType.Addmit,
                        Lpu237iButtonType.None // this case : when i-button is removed,  msr doesn't send anything
                };
                continue;
            }
            if(Lpu237Tools.is_support_ibutton_send_none_16zeros_f12_7zeros_on_remove_in_kb_mode(s_n,s_v)){
                n_type = new int[]{
                        Lpu237iButtonType.Zeros,
                        Lpu237iButtonType.Zeros7,
                        Lpu237iButtonType.F12,
                        Lpu237iButtonType.None
                };
                continue;
            }
            if(Lpu237Tools.is_support_ibutton_send_none_16zeros_f12_on_remove_in_kb_mode(s_n,s_v)){
                n_type = new int[]{
                        Lpu237iButtonType.Zeros,
                        Lpu237iButtonType.F12,
                        Lpu237iButtonType.None
                };
                continue;
            }
            if(Lpu237Tools.is_support_ibutton_send_only_f12_on_remove_in_kb_mode(s_n,s_v)){
                n_type = new int[]{
                        Lpu237iButtonType.F12
                };
                continue;
            }

            if (Lpu237Tools.is_support_ibutton_send_only_16zeros_on_remove_in_kb_mode(s_n,s_v)) {
                n_type = new int[]{
                        Lpu237iButtonType.Zeros
                };
            }

        }while (false);
        return n_type;
    }
    public String getDeviceType(){
        String s_type = "unknown";

        switch (get_device_type()){
            case Lpu237DeviceType.Standard:
                s_type = "standard";
                break;
            case Lpu237DeviceType.Compact:
                s_type = "compact";
                break;
            case Lpu237DeviceType.IbuttonOlny:
                s_type = "i-button only";
                break;
            default:
                break;
        }//end switch
        return s_type;
    }
    public int get_device_type(){
        int n_type = Lpu237DeviceType.Standard;

        if( m_parameters.get_is_ibutton_only_type() )
            n_type = Lpu237DeviceType.IbuttonOlny;
        else{
            if( !m_parameters.get_is_standard_type() )
                n_type = Lpu237DeviceType.Compact;
        }
        return n_type;
    }

    public String getUid(){
        byte[] s_id = get_uid();
        return Tools.byteArrayToHex(s_id);

    }
    public byte[] get_uid(){
        return m_parameters.get_uid();
    }

    public byte[] get_name(){
        return m_parameters.get_name();
    }
    public String getName(){
        String s_name = "";
        for(byte c: get_name()){
            if( c != ' ' && c != 0 ){
                s_name += (char)c;
            }
        }
        return s_name;
    }

    public void set_enable_track( int n_track, boolean b_enable ){
        if( m_parameters.set_enable_track( n_track, b_enable ) ) {
            switch( n_track ){
                case 0: _set_change(Lpu237ChangedParameter.EnableTrack1); break;
                case 1: _set_change(Lpu237ChangedParameter.EnableTrack2); break;
                case 2: _set_change(Lpu237ChangedParameter.EnableTrack3); break;
            }//end switch
        }
    }
    public boolean get_enable_track( int n_track ){
        return m_parameters.get_enable_track(n_track);
    }
    public String getEnableTrack( int n_track ){
        if( get_enable_track(n_track))
            return "Enable";
        else
            return "Disable";
    }

    public FwVersion get_version_system(){
        return m_parameters.get_version_system();
    }
    public String getVersionSystem(){
        FwVersion v = get_version_system();
        return v.toString();
    }

    public FwVersion get_version_structure(){
        return m_parameters.get_version_structure();
    }
    public String getVersionStructure(){
        return get_version_structure().toString();
    }

    public void set_interface( int n_interface ){
        if( m_parameters.set_interface(n_interface) )
            _set_change(Lpu237ChangedParameter.DeviceInterface);
    }
    public int get_interface(){
        return m_parameters.get_interface();
    }
    public int[] get_available_all_interfaces(){
        int[] n_inf = null;
        String s_n = getName();
        boolean b_support_vcom = false;
        if(Lpu237Tools.is_device_name_of_lpu238(s_n)){
            b_support_vcom = true;
        }

        switch(ManagerDevice.getInstance().lpu237_get_device_type()){
            case Lpu237DeviceType.Compact:
                if(b_support_vcom){
                    n_inf = new int[]{Lpu237Interface.usbVendorHid,Lpu237Interface.usbVCom};
                }
                else {
                    n_inf = new int[]{Lpu237Interface.usbKeyboard, Lpu237Interface.usbVendorHid};
                }
                break;
            case Lpu237DeviceType.IbuttonOlny:
            default://may be Lpu237DeviceType.Standard
                if(b_support_vcom){
                    n_inf = new int[]{Lpu237Interface.usbVendorHid, Lpu237Interface.uart,Lpu237Interface.usbVCom};
                }
                else {
                    n_inf = new int[]{Lpu237Interface.usbKeyboard, Lpu237Interface.usbVendorHid, Lpu237Interface.uart};
                }
                break;
        }//end switch
        return  n_inf;
    }
    public String[] getAvailableAllInterfaces(){
        return Lpu237.convert_interface_type_number_to_string(
            this.get_available_all_interfaces()
        );
    }

    public String getInterface(){
        String s_interface = "";
        switch( get_interface() ){
            case Lpu237Interface.usbKeyboard:
                s_interface = Lpu237InterfaceString.sUsbKeyboard;
                break;
            case Lpu237Interface.usbVendorHid:
                s_interface = Lpu237InterfaceString.sUsbHid;
                break;
            case Lpu237Interface.uart:
                s_interface = Lpu237InterfaceString.sRS232;
                break;
            case Lpu237Interface.usbVCom:
                s_interface = Lpu237InterfaceString.sUsbVCom;
                break;
            default:
                break;
        }//end switch
        return s_interface;
    }

    public void set_language_index( int n_language ){
        if( m_parameters.set_language_index( n_language) )
            _set_change(Lpu237ChangedParameter.DeviceLanguage);
    }
    public int get_language_index(){
        return m_parameters.get_language_index();
    }
    public String getLanguageIndex(){
        String s_lang = "";

        switch( get_language_index()){
            case Lpu237LanguageIndex.english:
                s_lang = "USA English";
                break;
            case Lpu237LanguageIndex.spanish:
                s_lang = "Spanish";
                break;
            case Lpu237LanguageIndex.danish:
                s_lang = "Danish";
                break;
            case Lpu237LanguageIndex.french:
                s_lang = "French";
                break;
            case Lpu237LanguageIndex.german:
                s_lang = "German";
                break;
            case Lpu237LanguageIndex.italian:
                s_lang = "Italian";
                break;
            case Lpu237LanguageIndex.norwegian:
                s_lang = "Norwegian";
                break;
            case Lpu237LanguageIndex.swedish:
                s_lang = "Swedish";
                break;
            case Lpu237LanguageIndex.israel:
                s_lang = "Hebrew";
                break;
            case Lpu237LanguageIndex.turkey:
                s_lang = "Turkiye";
                break;
            default:
                s_lang = "USA English";
                break;
        }//end switch
        //
        return s_lang;
    }

    public void set_buzzer_frequency( int n_frequency ){
        if( m_parameters.set_buzzer_frequency(n_frequency) )
            _set_change(Lpu237ChangedParameter.BuzzerFrequency);
    }
    public int get_buzzer_frequency(){
        return m_parameters.get_buzzer_frequency();
    }
    public String getBuzzerFrequency(){
        String s_data = "ON";
        if( Parameters.DEFAULT_FREQUENCY_BUZZER > get_buzzer_frequency() )
            s_data = "OFF";
        //
        return s_data;
    }

    public void set_global_prefix( Lpu237Tags tag ){
        if( m_parameters.set_global_prefix(tag) )
            _set_change(Lpu237ChangedParameter.GlobalPrefix);
    }
    public Lpu237Tags get_global_prefix(){
        return m_parameters.get_global_prefix();
    }
    public String getGlobalPrefix(){
        Lpu237Tags tag = get_global_prefix();
        return tag.toString();
    }

    public void set_global_postfix( Lpu237Tags tag ){
        if( m_parameters.set_global_postfix(tag) )
            _set_change(Lpu237ChangedParameter.GlobalPostfix);
    }
    public Lpu237Tags get_global_postfix(){
        return m_parameters.get_global_postfix();
    }
    public String getGlobalPostfix(){
        return get_global_postfix().toString();
    }

    public void set_private_prefix(int n_track, Lpu237Tags tag){
        if( m_parameters.set_private_prefix(n_track,tag) ) {
            switch( n_track ){
                case 0: _set_change(Lpu237ChangedParameter.PrivatePrefixTrack1); break;
                case 1: _set_change(Lpu237ChangedParameter.PrivatePrefixTrack2); break;
                case 2: _set_change(Lpu237ChangedParameter.PrivatePrefixTrack3); break;
            }//end switch
        }
    }
    public Lpu237Tags get_private_prefix(int n_track ){
        return m_parameters.get_private_prefix(n_track);
    }
    public String getPrivatePrefix( int n_track ){
        return get_private_prefix(n_track).toString();
    }

    public void set_private_postfix(int n_track, Lpu237Tags tag ){
        if( m_parameters.set_private_postfix(n_track,tag) ) {
            switch( n_track ){
                case 0: _set_change(Lpu237ChangedParameter.PrivatePostfixTrack1); break;
                case 1: _set_change(Lpu237ChangedParameter.PrivatePostfixTrack2); break;
                case 2: _set_change(Lpu237ChangedParameter.PrivatePostfixTrack3); break;
            }//end switch
        }
    }
    public Lpu237Tags get_private_postfix(int n_track ){
        return m_parameters.get_private_postfix(n_track);
    }
    public String getPrivatePostfix( int n_track ){
        return get_private_postfix(n_track).toString();
    }

    public void set_ibutton_tag_prefix( Lpu237Tags tag ){
        if( m_parameters.set_ibutton_tag_prefix(tag) )
            _set_change(Lpu237ChangedParameter.iButtonTagPrefix);
    }
    public Lpu237Tags get_ibutton_tag_prefix(){
        return m_parameters.get_ibutton_tag_prefix();
    }
    public String getIbuttonTagPrefix(){
        return get_ibutton_tag_prefix().toString();
    }

    public void set_ibutton_tag_postfix( Lpu237Tags tag ){
        if( m_parameters.set_ibutton_tag_postfix(tag) )
            _set_change(Lpu237ChangedParameter.iButtonTagPostfix);
    }
    public Lpu237Tags get_ibutton_tag_postfix(){
        return m_parameters.get_ibutton_tag_postfix();
    }
    public String getIbuttonTagPostfix(){
        return get_ibutton_tag_postfix().toString();
    }
    //
    public void set_ibutton_remove( Lpu237Tags tag ){
        if( m_parameters.set_ibutton_remove(tag) )
            _set_change(Lpu237ChangedParameter.iButtonRemove);
    }
    public Lpu237Tags get_ibutton_remove(){
        return m_parameters.get_ibutton_remove();
    }
    public String getIbuttonRemove(){
        return get_ibutton_remove().toString();
    }
//
    public void set_ibutton_remove_tag_prefix( Lpu237Tags tag ){
        if( m_parameters.set_ibutton_remove_tag_prefix(tag) )
            _set_change(Lpu237ChangedParameter.iButtonRemoveTagPrefix);
    }
    public Lpu237Tags get_ibutton_remove_tag_prefix(){
        return m_parameters.get_ibutton_remove_tag_prefix();
    }
    public String getIbuttonRemoveTagPrefix(){
        return get_ibutton_remove_tag_prefix().toString();
    }

    public void set_ibutton_remove_tag_postfix( Lpu237Tags tag ){
        if( m_parameters.set_ibutton_remove_tag_postfix(tag) )
            _set_change(Lpu237ChangedParameter.iButtonRemoveTagPostfix);
    }
    public Lpu237Tags get_ibutton_remove_tag_postfix(){
        return m_parameters.get_ibutton_remove_tag_postfix();
    }
    public String getIbuttonRemoveTagPostfix(){
        return get_ibutton_remove_tag_postfix().toString();
    }

    public void set_uart_prefix( Lpu237Tags tag ){
        if( m_parameters.set_uart_prefix(tag) )
            _set_change(Lpu237ChangedParameter.UartPrefix);
    }
    public Lpu237Tags get_uart_prefix(){
        return m_parameters.get_uart_prefix();
    }
    public String getUartPrefix(){
        return get_uart_prefix().toString();
    }

    public void set_uart_postfix( Lpu237Tags tag ){
        if( m_parameters.set_uart_postfix( tag ) )
            _set_change(Lpu237ChangedParameter.UartPostfix);
    }
    public Lpu237Tags get_uart_postfix(){
        return m_parameters.get_uart_postfix();
    }
    public String getUartPostfix(){
        return get_uart_postfix().toString();
    }

    public void set_global_send_condition( boolean b_all_no_error ){
        if( m_parameters.set_global_send_condition( b_all_no_error) )
            _set_change(Lpu237ChangedParameter.GlobalSendCondition);
    }
    public boolean get_global_send_condition(){
        return m_parameters.get_global_send_condition();
    }
    public String getGlobalSendCondition(){
        String s_data = "";
        if( get_global_send_condition() ){
            s_data = "No Error in all tracks";
        }
        else
            s_data = "One or more tracks are normal";
        return s_data;
    }

    public void set_reading_direction( byte c_dir ){
        if( m_parameters.set_reading_direction( c_dir) )
            _set_change(Lpu237ChangedParameter.MsrReadingDirection);
    }
    public byte get_reading_direction(){
        return m_parameters.get_reading_direction();
    }
    public String getReadingDirection(){
        String s_data = "";
        switch (get_reading_direction()){
            case Lpu237Direction.forwardDirection:
                s_data = "Forward Direction";
                break;
            case Lpu237Direction.backwardDirection:
                s_data = "Backward Direction";
                break;
            default:
                s_data = "BiDirection";
                break;
        }
        return s_data;
    }

    public void set_track_order( byte[] order ){
        if( m_parameters.set_track_order( order) )
            _set_change(Lpu237ChangedParameter.MsrTrackOrder);
    }
    public byte[] get_track_order(){
        return m_parameters.get_track_order();
    }
    public String getTrackOrder(){
        String s_data = "";
        byte[] order = get_track_order();
        if(order.length == 3){
            s_data = String.valueOf(order[0]) +
                    String.valueOf(order[1]) +
                    String.valueOf(order[2]);
        }
        return s_data;
    }

    public void set_ibutton_end(int n_pos){
        if(m_parameters.set_ibutton_end(n_pos)){
            _set_change(Lpu237ChangedParameter.BlankField);
        }
    }
    public int get_ibutton_end(){
        return m_parameters.get_ibutton_end();
    }
    public String getIButtonEnd(){
        return String.valueOf(get_ibutton_end());
    }
    public void set_ibutton_start(int n_pos){
        if(m_parameters.set_ibutton_start(n_pos)){
            _set_change(Lpu237ChangedParameter.BlankField);
        }
    }
    public int get_ibutton_start(){
        return m_parameters.get_ibutton_start();
    }
    public String getIButtonStart(){
        return String.valueOf(get_ibutton_start());
    }
    public void set_mmd1100_reset_interval(int n_reset){
        if(m_parameters.set_mmd1100_reset_interval(n_reset)){
            _set_change(Lpu237ChangedParameter.BlankField);
        }
    }
    public int get_mmd1100_reset_interval(){
        return m_parameters.get_mmd1100_reset_interval();
    }
    public  String getMmd1100ResetInterval(){
        String s_data = "";
        switch(get_mmd1100_reset_interval()){
            case 0:s_data = "0(default, 03:22)";break;
            case 16:s_data = "16(06:43)";break;
            case 32:s_data = "32(13:27)";break;
            case 48:s_data = "48(20:10)";break;
            case 64:s_data = "64(26:53)";break;
            case 80:s_data = "80(33:36)";break;
            case 96:s_data = "96(40:19)";break;
            case 112:s_data = "112(47:03)";break;
            case 128:s_data = "128(53:46)";break;
            case 144:s_data = "144(01:00:29)";break;
            case 160:s_data = "160(01:07:12)";break;
            case 176:s_data = "176(01:13:55)";break;
            case 192:s_data = "192(01:20:39)";break;
            case 208:s_data = "208(01:27:22)";break;
            case 224:s_data = "224(01:34:05)";break;
            case 240:s_data = "240(disable)";break;
            default:break;
        }//end switch
        return s_data;
    }
    public  void set_any_good_indicate_success(boolean b_any){
        if( m_parameters.set_any_good_indicate_success( b_any) )
            _set_change(Lpu237ChangedParameter.BlankField);
    }
    public boolean get_any_good_indicate_success(){
        return m_parameters.get_any_good_indicate_success();
    }
    public String getAnyGoodIndicateSuccess(){
        String s_data = "";
        if( get_any_good_indicate_success()){
            s_data = "One or more tracks are normal";
        }
        else{
            s_data = "No Error in all tracks";
        }
        return s_data;
    }

    ////////////////////////////////

    private Lpu237()
    {
        super();
    }

    public  Lpu237(UsbManager usbManager, UsbDevice usbDevice){
        super(usbManager,usbDevice);
    }

    private boolean _df_io( byte c_cmd, byte c_sub, byte[] s_data, InPacket packet ){
        boolean b_result = false;

        do{
            int n_tx = 0;

            if(s_data == null)
                n_tx = Lpu237Const.SIZE_REQ_HEAD;
            else if( s_data.length > 0  )
                n_tx = Lpu237Const.SIZE_REQ_HEAD + s_data.length;
            //

            // send request.
            byte[] s_tx = new byte[n_tx];
            s_tx[0] = c_cmd;
            s_tx[1] = c_sub;
            s_tx[2] = (byte)(s_tx.length- Lpu237Const.SIZE_REQ_HEAD);
            if( n_tx > Lpu237Const.SIZE_REQ_HEAD )
                System.arraycopy(s_data,0, s_tx, Lpu237Const.SIZE_REQ_HEAD, s_tx.length- Lpu237Const.SIZE_REQ_HEAD);

            byte[] s_out_packet = new byte[Lpu237Const.SIZE_REPORT_OUT];
            int n_out_packet = 0;
            int n_offset = 0;
            boolean b_error = false;

            do {
                n_tx = (s_tx.length - n_offset) - s_out_packet.length;
                if (n_tx >= 0) {
                    n_tx = s_out_packet.length;
                } else {
                    n_tx = (s_tx.length - n_offset);
                }
                System.arraycopy(s_tx, n_offset, s_out_packet, 0, n_tx);
                n_out_packet = write(s_out_packet);
                if( n_out_packet <= 0 ){
                    b_error = true;
                    break;
                }
                n_offset += n_tx;
            }while( n_offset < s_tx.length);

            if( b_error)
                continue;

            // get response.
            int n_in_report = 0;
            byte[] s_in_report = new byte[Lpu237Const.SIZE_REPORT_IN];
            s_in_report[0] = 0;

            n_in_report = read(s_in_report);
            if( n_in_report <= 0 ) {
                continue;
            }

            if( s_in_report[0] != Lpu237Response.prefix )
                continue;
            int n_length = (int)s_in_report[2];
            if( n_length < 0)
                n_length = 0;
            //
            byte[] s_rx = new byte[Lpu237Const.SIZE_REQ_HEAD+n_length];
            n_offset = 0;
            int n_rx = s_rx.length - n_offset;
            if( n_rx > s_in_report.length )
                n_rx = s_in_report.length;

            do{
                System.arraycopy(s_in_report,0,s_rx,n_offset,n_rx);
                n_offset += n_rx;
                if( n_offset >= s_rx.length )
                    continue;
                //
                n_in_report = read(s_in_report);
                if( n_in_report <= 0 ) {
                    b_error = true;
                    continue;
                }
                n_rx = s_rx.length - n_offset;
                if( n_rx > s_in_report.length )
                    n_rx = s_in_report.length;

            }while( n_offset < s_rx.length );

            if( b_error )
                continue;
            //
            if( packet == null )
                packet = new InPacket(s_rx);
            else{
                packet.set(s_rx);
            }
            if( !packet.isPrefx())
                continue;
            if( !packet.isSuccess())
                continue;

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _df_get( int n_offset, int n_size,InPacket packet) {
        boolean b_result = false;

        do{
            byte[] s_offset = IntByteConvert.intTobyte( n_offset, ByteOrder.LITTLE_ENDIAN );
            byte[] s_size = IntByteConvert.intTobyte( n_size, ByteOrder.LITTLE_ENDIAN );
            byte[] s_data = new byte[s_offset.length+s_size.length];
            System.arraycopy(s_offset,0,s_data,0,s_offset.length);
            System.arraycopy(s_size,0,s_data, s_offset.length, s_size.length);

            if( !_df_io(Lpu237Request.cmdConfig, Lpu237RequestSub.subConfigGet,s_data,packet) )
                continue;
            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _df_set( int n_offset, int n_size, byte[] s_data, InPacket packet){
        boolean b_result = false;

        do{
            byte[] s_offset = IntByteConvert.intTobyte( n_offset, ByteOrder.LITTLE_ENDIAN );
            byte[] s_size = IntByteConvert.intTobyte( n_size, ByteOrder.LITTLE_ENDIAN );
            int n_data_field = s_offset.length+s_size.length;
            if( s_data != null)
                n_data_field += s_data.length;

            byte[] s_data_field = new byte[n_data_field];
            System.arraycopy(s_offset,0,s_data_field, 0, s_offset.length);
            System.arraycopy(s_size,0,s_data_field, s_offset.length, s_size.length);
            if( s_data != null)
                System.arraycopy(s_data,0,s_data_field, s_offset.length+s_size.length,s_data.length);
            //
            if( !_df_io(Lpu237Request.cmdConfig,Lpu237RequestSub.subConfigSet,s_data_field,packet) )
                continue;
            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _df_set_keymap_table(){
        boolean b_result = false;

        do{
            FwVersion v = m_parameters.get_version_system();
            FwVersion v3401 = new FwVersion(3,4,0,1);
            if( v.less(v3401) ){
                b_result = true;
                continue;
            }
            //usb map
            int n_map = m_parameters.get_language_index();
            int n_offset = Lpu237Address.HidKeymapAddressOffset;
            int n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            byte[] s_data = new byte[n_size];
            System.arraycopy(KeyboardMap.gASCToHIDKeyMap,n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2
            ,s_data,0,n_size);
            //
            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            n_offset = Lpu237Address.HidKeymapAddressOffset+KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            System.arraycopy(KeyboardMap.gASCToHIDKeyMap,
                    n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2 + KeyboardConst.FOR_CVT_MAX_ASCII_CODE
                    ,s_data,0,n_size);
            //
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            //PS2 map
            n_offset = Lpu237Address.Ps2KeymapAddressOffset;
            n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            System.arraycopy(KeyboardMap.gASCToPS2KeyMap,n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2
                    ,s_data,0,n_size);
            //
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            n_offset = Lpu237Address.Ps2KeymapAddressOffset+KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            System.arraycopy(KeyboardMap.gASCToPS2KeyMap,
                    n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2 + KeyboardConst.FOR_CVT_MAX_ASCII_CODE
                    ,s_data,0,n_size);
            //
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while (false);
        return b_result;
    }
    public boolean df_get_parameter()
    {
        boolean b_result = false;
        boolean b_leave_config = false;

        String s_n ="";
        String s_v ="";

        do{
            if( !df_enter_config() )
                continue;
            b_leave_config = true;

            df_get_decoder_mmd1000();

            if( !df_get_name() )
                continue;
            if( !df_get_version_system() )
                continue;
            if( !df_get_version_structure() )
                continue;

            s_n = this.getName();
            s_v = this.getVersionSystem();

            if( !df_get_blanks() )
                continue;
            if( !df_get_type() )
                continue;
            if( !df_get_ibutton_only_type())
                continue;
            if( !df_get_uid() )
                continue;
            if( !df_get_enable_tracK(0))
                continue;
            if( !df_get_enable_tracK(1))
                continue;
            if( !df_get_enable_tracK(2))
                continue;
            if(!df_get_interface())
                continue;
            if(!df_get_language_index())
                continue;
            if(!df_get_buzzer_frequency())
                continue;
            if(!df_get_boot_run_time())
                continue;
            if(!df_get_global_prefix())
                continue;
            if(!df_get_global_postfix())
                continue;
            if(!df_get_private_prefix(0))
                continue;
            if(!df_get_private_postfix(0))
                continue;
            if(!df_get_private_prefix(1))
                continue;
            if(!df_get_private_postfix(1))
                continue;
            if(!df_get_private_prefix(2))
                continue;
            if(!df_get_private_postfix(2))
                continue;
            if(!df_get_ibutton_tag_prefix())
                continue;
            if(!df_get_ibutton_tag_postfix())
                continue;
            if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ) {
                //for system structure v4.0
                if (!df_get_ibutton_remove())
                    continue;
                if (!df_get_ibutton_remove_tag_prefix())
                    continue;
                if (!df_get_ibutton_remove_tag_postfix())
                    continue;
            }
            if(!df_get_uart_prefix())
                continue;
            if(!df_get_uart_postfix())
                continue;
            if( Lpu237Tools.is_support_msr_global_tag_send_condition(s_n,s_v) ) {
                if (!df_get_global_send_condition())
                    continue;
            }
            if(!df_get_reading_direction())
                continue;
            if( Lpu237Tools.is_support_msr_send_order(s_n,s_v) ) {
                if (!df_get_track_order())
                    continue;
            }
            //
            m_set_changed.clear();
            b_result = true;
        }while (false);

        if( b_leave_config )
            b_result = df_leave_config();
        //
        return b_result;
    }

    public boolean df_run_bootloader()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdGotoBootLoader,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_run_bootloader");
        return b_result;
    }

    public boolean df_apply()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdApply,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_apply");
        return b_result;
    }

    public boolean df_enter_config()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdEnterCS,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_enter_config");
        return b_result;
    }
    public boolean df_leave_config()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdLeaveCS,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_leave_config");

        return b_result;
    }

    public boolean df_enter_opos()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdEnterOps,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_enter_opos");

        return b_result;
    }
    public boolean df_leave_opos()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdLeaveOps,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_leave_opos");

        return b_result;
    }

    private boolean df_get_blanks(){
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.cBlank;
            n_size = Lpu237SystemStructureSize.cBlank;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_ibutton_type(packet.get_ibutton_type());

            if(packet.get_all_track_is_success_indication()) {
                m_parameters.set_any_good_indicate_success(false);
            }
            else{
                m_parameters.set_any_good_indicate_success(true);
            }

            m_parameters.set_mmd1100_reset_interval(packet.get_mmd1100_reset_interval());

            //this is dragon _______________
            int n_pos_start = packet.get_ibutton_position_start();
            int n_pos_end = packet.get_ibutton_position_end();
            if(n_pos_start == 0 && n_pos_end ==0){
                n_pos_end = 15;
            }
            else if(n_pos_start == 0 && n_pos_end ==15){
                n_pos_end = 0;
            }
            m_parameters.set_ibutton_start(n_pos_start);
            m_parameters.set_ibutton_end(n_pos_end);

            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_blanks");

        return b_result;
    }
    public boolean df_get_type(){
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdHwIsStandard,(byte)0,null,packet))
                continue;
            m_parameters.set_is_standard_type(packet.isPositive());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_type");

        return b_result;
    }

    public boolean df_get_decoder_mmd1000(){
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdHwIsMMD1000,(byte)0,null,packet))
                continue;
            if( packet.isPositive() )
                m_parameters.set_decoder_mmd1000(true);
            else if( packet.isSuccess() )
                m_parameters.set_decoder_mmd1000(false);
            else {
                m_parameters.set_decoder_mmd1000(false);
                continue;
            }
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_decoder_mmd1000");

        return b_result;
    }

    public boolean df_get_ibutton_only_type()
    {
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdHwIsOnlyiButton,(byte)0,null,packet))
                continue;
            m_parameters.set_is_ibutton_only_type(packet.isPositive());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_only_type");

        return b_result;
    }

    public boolean df_get_uid()
    {
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdReadUID,(byte)0,null,packet))
                continue;
            m_parameters.set_uid( packet.s_data );
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_uid");

        return b_result;
    }

    public boolean df_get_name()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.sName;
            n_size = Lpu237SystemStructureSize.sName;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_name(packet.get_name());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_name");

        return b_result;
    }

    private boolean df_get_enable_tracK( int n_track )
    {
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr0_cEnableTack;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr1_cEnableTack;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr2_cEnableTack;
            }

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_enable_track( n_track, packet.getEnable() );
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_enable_track " + String.valueOf(n_track));

        return b_result;
    }

    public boolean df_get_version_system()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.sSysVer;
            n_size = Lpu237SystemStructureSize.sSysVer;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_version_system(packet.get_version());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_version_system");

        return b_result;
    }

    public boolean df_get_version_structure()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.sStructVer;
            n_size = Lpu237SystemStructureSize.sStructVer;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_version_structure(packet.get_version());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_version_structure");

        return b_result;
    }

    private boolean df_get_interface()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.Interface;
            n_size = Lpu237SystemStructureSize.Interface;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_interface((int)(packet.get_byte()));
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_interface");

        return b_result;
    }

    private boolean df_get_language_index()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_Keymap_nMappingIndex;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_Keymap_nMappingIndex;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_language_index(packet.get_int());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_language_index");

        return b_result;
    }

    private boolean df_get_buzzer_frequency()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.nBuzzerFrequency;
            n_size = Lpu237SystemStructureSize.nBuzzerFrequency;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_buzzer_frequency(packet.get_int()/10);
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_buzzer_frequency");

        return b_result;
    }

    private boolean df_get_boot_run_time()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.nBootRunTime;
            n_size = Lpu237SystemStructureSize.nBootRunTime;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_boot_run_time(packet.get_int()*10);
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_boot_run_time");

        return b_result;
    }

    private boolean df_get_global_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_global_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_global_prefix");

        return b_result;
    }

    private boolean df_get_global_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_global_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_global_postfix");

        return b_result;
    }

    private boolean df_get_private_prefix( int n_track )
    {
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePrefix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_cSize
                + Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_sTag;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePrefix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_sTag;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePrefix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_sTag;
            }

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_private_prefix( n_track, packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_private_prefix " + String.valueOf(n_track));

        return b_result;
    }

    private boolean df_get_private_postfix( int n_track )
    {
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_sTag;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_sTag;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_sTag;
            }

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_private_postfix( n_track, packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_private_postfix " + String.valueOf(n_track));

        return b_result;
    }

    private boolean df_get_ibutton_tag_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_tag_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_tag_prefix");

        return b_result;
    }

    private boolean df_get_ibutton_tag_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_tag_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_tag_postfix");

        return b_result;
    }

    private boolean df_get_ibutton_remove()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.RemoveItemTag_cSize;
            n_size = Lpu237SystemStructureSize.RemoveItemTag_cSize
                    + Lpu237SystemStructureSize.RemoveItemTag_cTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_remove(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_remove");

        return b_result;
    }

    private boolean df_get_ibutton_remove_tag_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButtonRemove_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_remove_tag_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_remove__tag_prefix");

        return b_result;
    }

    private boolean df_get_ibutton_remove_tag_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButtonRemove_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_remove_tag_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_remove_tag_postfix");

        return b_result;
    }

    private boolean df_get_uart_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_uart_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_uart_prefix");

        return b_result;

    }

    private boolean df_get_uart_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_uart_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_uart_postfix");

        return b_result;
    }

    private boolean df_get_global_send_condition()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_nGlobalTagCondition;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_nGlobalTagCondition;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_global_send_condition(packet.get_condition());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_global_send_condition");

        return b_result;
    }
    private boolean df_get_reading_direction()
    {
        boolean b_result = false;
        int n_track = 0;
        do{
            //
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoMsr0_cRDirect;
            n_size = Lpu237SystemStructureSize.InfoMsr0_cRDirect;

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_reading_direction( packet.get_byte_from_array(0,3));
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_reading_direction ");

        return b_result;
    }

    private boolean df_get_track_order()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_nOrderObject0;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_nOrderObject0 * 3;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_track_order(packet.get_track_order_by_byte_array());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_track_order");

        return b_result;
    }

    public boolean df_set_parameter()
    {
        boolean b_result = false;
        boolean b_leave_config = false;

        String s_n = this.getName();
        String s_v = this.getVersionSystem();

        do{
            if( !df_enter_config() )
                continue;
            b_leave_config = true;

            if( _is_changed( Lpu237ChangedParameter.BlankField)) {
                if (!df_set_blanks())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.EnableTrack1)) {
                if (!df_set_enable_tracK(0))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.EnableTrack2)) {
                if (!df_set_enable_tracK(1))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.EnableTrack3)) {
                if (!df_set_enable_tracK(2))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.DeviceInterface)) {
                if (!df_set_interface())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.DeviceLanguage)) {
                if (!df_set_language_index())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.BuzzerFrequency)) {
                if (!df_set_buzzer_frequency())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.GlobalPrefix)) {
                if (!df_set_global_prefix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.GlobalPostfix)) {
                if (!df_set_global_postfix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePrefixTrack1)) {
                if (!df_set_private_prefix(0))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePostfixTrack1)) {
                if (!df_set_private_postfix(0))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePrefixTrack2)) {
                if (!df_set_private_prefix(1))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePostfixTrack2)) {
                if (!df_set_private_postfix(1))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePrefixTrack3)) {
                if (!df_set_private_prefix(2))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePostfixTrack3)) {
                if (!df_set_private_postfix(2))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.iButtonTagPrefix)) {
                if (!df_set_ibutton_tag_prefix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.iButtonTagPostfix)) {
                if (!df_set_ibutton_tag_postfix())
                    continue;
            }

            if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ) {
                //for system structure v4.0
                if (_is_changed(Lpu237ChangedParameter.iButtonRemove)) {
                    if (!df_set_ibutton_remove())
                        continue;
                }
                if (_is_changed(Lpu237ChangedParameter.iButtonRemoveTagPrefix)) {
                    if (!df_set_ibutton_remove_tag_prefix())
                        continue;
                }
                if (_is_changed(Lpu237ChangedParameter.iButtonRemoveTagPostfix)) {
                    if (!df_set_ibutton_remove_tag_postfix())
                        continue;
                }
            }
            if( _is_changed( Lpu237ChangedParameter.UartPrefix)) {
                if (!df_set_uart_prefix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.UartPostfix)) {
                if (!df_set_uart_postfix())
                    continue;
            }

            if( Lpu237Tools.is_support_msr_global_tag_send_condition(s_n,s_v) ) {
                if (_is_changed(Lpu237ChangedParameter.GlobalSendCondition)) {
                    if (!df_set_global_send_condition())
                        continue;
                }
            }
            if (_is_changed(Lpu237ChangedParameter.MsrReadingDirection)) {
                if (!df_set_reading_direction())
                    continue;
            }
            if( Lpu237Tools.is_support_msr_send_order(s_n,s_v) ) {
                if (_is_changed(Lpu237ChangedParameter.MsrTrackOrder)) {
                    if (!df_set_track_order())
                        continue;
                }
            }
            if( !m_set_changed.isEmpty() )
                if( !df_apply() )
                    continue;
            //
            m_set_changed.clear();
            b_result = true;
        }while (false);

        if( b_leave_config )
            b_result = df_leave_config();
        //
        if( !b_result )
            Log.i("Lpu237","error : df_set_parameter");

        return b_result;
    }

    private boolean df_set_blanks(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.cBlank;
            n_size = Lpu237SystemStructureSize.cBlank;
            byte[] s_data = new byte[n_size];
            Arrays.fill(s_data,(byte)0);//clear

            int[] n_blank = {0,0,0,0};
            int n_op = 0;

            switch (m_parameters.get_ibutton_type()){
                case Lpu237iButtonType.Zeros7:
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS7&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    //this code need for firmware mis-coding. but maintains compatibility
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    break;
                case Lpu237iButtonType.F12:
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_F12&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    //this code need for firmware mis-coding. but maintains compatibility
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    break;
                case Lpu237iButtonType.Addmit:
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ADDMIT&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    //this code need for firmware mis-coding. but maintains compatibility
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    break;
                case Lpu237iButtonType.None:
                    n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS&0xFF;//for unsigned operation.
                    n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] |= n_op;
                    break;
                case Lpu237iButtonType.Zeros:
                default:
                    break;
            }//end switch

            //success indication condition.
            n_op = Lpu237Const.MASK_IN_BLANK_OF_SUCCESS_INDICATE&0xFF;//for unsigned operation.
            if( m_parameters.get_any_good_indicate_success() ){
                n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_SUCCESS_INDICATE] |= n_op;
            }
            else{
                n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_SUCCESS_INDICATE]  &= ~n_op;
            }

            //mmd1100 reset interval
            n_op = Lpu237Const.MASK_IN_BLANK_OF_MMD1100_RESET_INTERVAL&0xFF;//for unsigned operation.
            int n_mmd1100_reset_interval = m_parameters.get_mmd1100_reset_interval();
            n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_MMD1100_RESET_INTERVAL] &= ~n_op;//reset
            n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_MMD1100_RESET_INTERVAL] |= n_mmd1100_reset_interval;//set

            //ibutton position
            int n_op_start = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_POSITION_START&0xFF;//for unsigned operation.
            int n_pos_start = m_parameters.get_ibutton_start();

            int n_op_end = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_POSITION_END&0xFF;//for unsigned operation.
            int n_pos_end = m_parameters.get_ibutton_end();

            //this is dragon _______________
            if(n_pos_start == 0 && n_pos_end ==15){
                n_pos_end = 0;
            }
            else if(n_pos_start == 0 && n_pos_end ==0){
                n_pos_end = 15;
            }

            n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_POSITION] &= ~n_op_start;
            n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_POSITION] |= (n_pos_start <<= 4);

            n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_POSITION] &= ~n_op_end;
            n_blank[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_POSITION] |= n_pos_end;

            for(int i=0; i<s_data.length; i++){
                s_data[i] = (byte)n_blank[i];
            }//end for
            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_blanks");

        return b_result;
    }

    private boolean df_set_enable_tracK( int n_track ){
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2)
                continue;
            boolean b_enable = m_parameters.get_enable_track(n_track);

            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr0_cEnableTack;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr1_cEnableTack;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr2_cEnableTack;
            }

            byte[] s_data = new byte[n_size];
            if( b_enable )
                s_data[0] = 1;
            else
                s_data[0] = 0;

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_enable_track " + String.valueOf(n_track));

        return b_result;
    }
    private boolean df_set_interface(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.Interface;
            n_size = Lpu237SystemStructureSize.Interface;
            byte[] s_data = new byte[n_size];

            int n_inf = m_parameters.get_interface();
            s_data[0] = (byte)n_inf;

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_interface");

        return b_result;
    }

    private boolean df_set_language_index(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_Keymap_nMappingIndex;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_Keymap_nMappingIndex;
            int n_language = m_parameters.get_language_index();
            byte[] s_data = IntByteConvert.intTobyte(n_language,ByteOrder.LITTLE_ENDIAN);

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            n_offset = Lpu237SystemStructureOffset.InfoMsr0_Keymap0_nMappingIndex;
            n_size = Lpu237SystemStructureSize.InfoMsr0_Keymap0_nMappingIndex;
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            n_offset = Lpu237SystemStructureOffset.InfoMsr1_Keymap0_nMappingIndex;
            n_size = Lpu237SystemStructureSize.InfoMsr1_Keymap0_nMappingIndex;
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            n_offset = Lpu237SystemStructureOffset.InfoMsr2_Keymap0_nMappingIndex;
            n_size = Lpu237SystemStructureSize.InfoMsr2_Keymap0_nMappingIndex;
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            if( !_df_set_keymap_table() )
                continue;;
            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_language_index");

        return b_result;

    }

    private boolean df_set_buzzer_frequency(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.nBuzzerFrequency;
            n_size = Lpu237SystemStructureSize.nBuzzerFrequency;

            //this cod eis dragon
            int n_data = m_parameters.get_buzzer_frequency();
            if( n_data <= 500 ){
                n_data = 500;
            }
            if(m_parameters.get_decoder_mmd1000()){
                if(n_data==2500){
                    n_data = 1600;
                }
            }
            if( m_parameters.get_version_system().get_major() >4 && n_data == 1600 ){
                n_data = 2500;
            }

            byte[] s_data = IntByteConvert.intTobyte(n_data*10,ByteOrder.LITTLE_ENDIAN);

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_buzzer_frequency");

        return b_result;
    }

    private boolean df_set_global_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_cSize
            + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_sTag;

            Lpu237Tags tag = m_parameters.get_global_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_global_prefix");

        return b_result;
    }

    private boolean df_set_global_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_sTag;

            Lpu237Tags tag = m_parameters.get_global_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_global_postfix");

        return b_result;
    }

    private boolean df_set_private_prefix(int n_track){
         boolean b_result = false;

         do{
             if( n_track < 0 || n_track > 2 )
                 continue;
             //
             int n_offset =0, n_size = 0;
             if( n_track == 0){
                 n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePrefix0_cSize;
                 n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_cSize
                         + Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_sTag;
             }
             else if( n_track == 1 ){
                 n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePrefix0_cSize;
                 n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_cSize
                         + Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_sTag;
             }
             else if( n_track == 2 ){
                 n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePrefix0_cSize;
                 n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_cSize
                         + Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_sTag;
             }

             Lpu237Tags tag = m_parameters.get_private_prefix(n_track);
             byte[] s_data = tag.get_tag_stream_with_length();

             InPacket packet = new InPacket();
             if( !_df_set( n_offset, n_size, s_data, packet) )
                 continue;
             b_result = true;
         }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_private_prefix " + String.valueOf(n_track));

        return b_result;
    }

    private boolean df_set_private_postfix(int n_track){
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_sTag;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_sTag;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_sTag;
            }

            Lpu237Tags tag = m_parameters.get_private_postfix(n_track);
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_private_postfix " + String.valueOf(n_track));

        return b_result;

    }

    private boolean df_set_ibutton_tag_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_sTag;

            Lpu237Tags tag = m_parameters.get_ibutton_tag_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_tag_prefix");

        return b_result;
    }

    private boolean df_set_ibutton_tag_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_sTag;

            Lpu237Tags tag = m_parameters.get_ibutton_tag_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_tag_postfix");

        return b_result;
    }

    private boolean df_set_ibutton_remove(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.RemoveItemTag_cSize;
            n_size = Lpu237SystemStructureSize.RemoveItemTag_cSize
                    + Lpu237SystemStructureSize.RemoveItemTag_cTag;

            Lpu237Tags tag = m_parameters.get_ibutton_remove();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_remove");

        return b_result;
    }

    private boolean df_set_ibutton_remove_tag_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButtonRemove_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPrefix_sTag;

            Lpu237Tags tag = m_parameters.get_ibutton_remove_tag_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_remove_tag_prefix");

        return b_result;
    }

    private boolean df_set_ibutton_remove_tag_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButtonRemove_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoiButtonRemove_GlobalPostfix_sTag;

            Lpu237Tags tag = m_parameters.get_ibutton_remove_tag_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_remove_tag_postfix");

        return b_result;
    }
    private boolean df_set_uart_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPrefix_sTag;

            Lpu237Tags tag = m_parameters.get_uart_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_uart_prefix");

        return b_result;
    }

    private boolean df_set_uart_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPostfix_sTag;

            Lpu237Tags tag = m_parameters.get_uart_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_uart_postfix");

        return b_result;

    }

    private boolean df_set_global_send_condition(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_nGlobalTagCondition;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_nGlobalTagCondition;

            int n_data = 0;
            if( m_parameters.get_global_send_condition() )
                n_data = 1;
            byte[] s_data = IntByteConvert.intTobyte(n_data,ByteOrder.LITTLE_ENDIAN);

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_global_send_condition");

        return b_result;
    }

    private boolean df_set_reading_direction(){
        boolean b_result = true;

        do{
            int n_track = 0;
            byte c_dir = m_parameters.get_reading_direction();

            int n_offset =0, n_size = 0;
            for(n_track=0; n_track<3; n_track++ ) {
                if (n_track == 0) {
                    n_offset = Lpu237SystemStructureOffset.InfoMsr0_cRDirect;
                    n_size = Lpu237SystemStructureSize.InfoMsr0_cRDirect;
                } else if (n_track == 1) {
                    n_offset = Lpu237SystemStructureOffset.InfoMsr1_cRDirect;
                    n_size = Lpu237SystemStructureSize.InfoMsr1_cRDirect;
                } else if (n_track == 2) {
                    n_offset = Lpu237SystemStructureOffset.InfoMsr2_cRDirect;
                    n_size = Lpu237SystemStructureSize.InfoMsr2_cRDirect;
                }

                byte[] s_data = new byte[n_size];
                Arrays.fill(s_data, c_dir);
                //
                InPacket packet = new InPacket();
                if( !_df_set( n_offset, n_size, s_data, packet) ) {
                    b_result = false;
                    break;// exit for
                }
            }//end for track

        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_reading_direction ");

        return b_result;
    }

    private boolean df_set_track_order(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_nOrderObject0;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_nOrderObject0;
            n_size *= 3;//sizeof(unsigned long)*3

            byte[] c_order = m_parameters.get_track_order();
            int[] n_order = {(int)c_order[0],(int)c_order[1],(int)c_order[2]};
            byte[] s_data = new byte[Integer.BYTES*n_order.length];
            byte[][] item = new byte[n_order.length][Integer.BYTES];
            int n_len = 0;
            for(int i=0; i<n_order.length; i++ ) {
                item[i] = IntByteConvert.intTobyte(n_order[i], ByteOrder.LITTLE_ENDIAN);
                System.arraycopy(item[i],0,s_data,n_len,item.length);
                n_len += item[i].length;
            }//end for i

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_track_order");

        return b_result;
    }
    //////////////////////////////

    public boolean get_parameter( Parameters dst ){
        boolean b_result =false;

        do{
            if( dst == null )
                continue;
            //
            m_parameters.copy_all_parameter_to_( dst );
            //
            b_result = true;
        }while(false);
        return b_result;
    }

    public boolean set_parameter( Parameters src ){
        boolean b_result =false;

        do{
            if( src == null )
                continue;
            //
            m_parameters.set_changable_parameter(src);
            //
            _set_change(Lpu237ChangedParameter.EnableTrack1);
            _set_change(Lpu237ChangedParameter.EnableTrack2);
            _set_change(Lpu237ChangedParameter.EnableTrack3);
            _set_change(Lpu237ChangedParameter.DeviceInterface);
            _set_change(Lpu237ChangedParameter.DeviceLanguage);
            _set_change(Lpu237ChangedParameter.BuzzerFrequency);
            _set_change(Lpu237ChangedParameter.GlobalPrefix);
            _set_change(Lpu237ChangedParameter.GlobalPostfix);
            _set_change(Lpu237ChangedParameter.PrivatePrefixTrack1);
            _set_change(Lpu237ChangedParameter.PrivatePostfixTrack1);
            _set_change(Lpu237ChangedParameter.PrivatePrefixTrack2);
            _set_change(Lpu237ChangedParameter.PrivatePostfixTrack2);
            _set_change(Lpu237ChangedParameter.PrivatePrefixTrack3);
            _set_change(Lpu237ChangedParameter.PrivatePostfixTrack3);
            _set_change(Lpu237ChangedParameter.iButtonTagPrefix);
            _set_change(Lpu237ChangedParameter.iButtonTagPostfix);
            _set_change(Lpu237ChangedParameter.iButtonRemove);
            _set_change(Lpu237ChangedParameter.iButtonRemoveTagPrefix);
            _set_change(Lpu237ChangedParameter.iButtonRemoveTagPostfix);
            _set_change(Lpu237ChangedParameter.UartPrefix);
            _set_change(Lpu237ChangedParameter.UartPostfix);
            _set_change(Lpu237ChangedParameter.GlobalSendCondition);
            _set_change(Lpu237ChangedParameter.BlankField);
            _set_change(Lpu237ChangedParameter.MsrReadingDirection);
            _set_change(Lpu237ChangedParameter.MsrTrackOrder);
            //
            b_result = true;
        }while(false);
        return b_result;
    }

    //////////////////////////////
    static class InPacket{
        public int m_n_language_index = 0;//default is english language index.
        public byte c_prefix=0;
        public byte c_result=0;
        public byte c_length=0;
        public byte[] s_data=null;

        public void set_lagnuage_index( int n_language_index ){
            m_n_language_index = n_language_index;
        }

        boolean getEnable()
        {
            boolean b_enable = false;

            do{
                if( c_length != 1 )
                    continue;
                if( s_data == null )
                    continue;
                if( s_data[0] == 0 )
                    continue;
                b_enable = true;
            }while (false);
            return b_enable;
        }

        FwVersion get_version()
        {
            FwVersion v = null;

            do{
                if( c_length != FwVersion.SIZE_VERSION )
                    continue;
                //
                v = new FwVersion( s_data );
            }while (false);
            return v;
        }

        byte[] get_name()
        {
            byte[] s_name = null;

            do{
                if( c_length != Parameters.SIZE_NAME )
                    continue;
                //
                s_name = new byte[Parameters.SIZE_NAME];
                System.arraycopy(s_data,0,s_name,0,Parameters.SIZE_NAME);
            }while(false);
            return s_name;
        }
        byte getLength()
        {
            return c_length;
        }

        byte get_byte_from_array( int n_offset, int n_array ){
            byte c_result = 0;
            do{
                if( c_length != n_array )
                    continue;
                c_result = s_data[n_offset];
            }while(false);
            return c_result;
        }
        byte get_byte()
        {
            byte c_result = 0;
            do{
                if( c_length != 1 )
                    continue;
                c_result = s_data[0];
            }while(false);
            return c_result;
        }
        int get_int()
        {
            int n_result = 0;
            do{
                if( c_length != 4 )
                    continue;
                n_result = IntByteConvert.byteToInt( s_data,ByteOrder.LITTLE_ENDIAN);
            }while(false);
            return n_result;
        }

        byte[] get_track_order_by_byte_array()
        {
            byte[] order={0,0,0};
            do{
                if(c_length != 4*3){
                    continue;
                }

                byte[] ul={0,0,0,0};
                for(int i=0; i<order.length; i++){
                    for(int j=0; j<4; j++ ){
                        ul[j] = s_data[i*4+j];
                    }//end for i
                    order[i] = (byte)IntByteConvert.byteToInt(ul,ByteOrder.LITTLE_ENDIAN);
                }//end for j

            }while (false);
            return order;
        }
        Lpu237Tags get_tag()
        {
            Lpu237Tags tag = new Lpu237Tags();

            do{
                boolean[] b_valid = {false,false};

                int n_len = (int)c_length;
                if( n_len == (Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_cSize
                        + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_sTag ) ) {
                    b_valid[0] = true;
                }
                else if( n_len == (Lpu237SystemStructureSize.RemoveItemTag_cSize
                        + Lpu237SystemStructureSize.RemoveItemTag_cTag ) ) {
                    b_valid[1] = true;
                    tag.resize(Lpu237Tags.NUMBER_IBUTTON_REMOVE_TAG);
                }
                if(!(b_valid[0]|b_valid[1])){
                    continue;
                }

                n_len = (int)s_data[0];

                if( n_len > 0 ) {
                    byte[] s_tag = new byte[n_len];
                    //ascii to hidkey from index 1.
                    int n_map_start_offset = 0;

                    for( int i = 0; i<n_len/2; i++ ){
                        if( s_data[1+2*i] == (byte)0xff ){
                            //ascii code
                            n_map_start_offset = m_n_language_index*KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2;
                            n_map_start_offset += s_data[1+2*i+1];
                            s_tag[2*i] = KeyboardMap.gASCToHIDKeyMap[2*n_map_start_offset+0];
                            s_tag[2*i+1] = KeyboardMap.gASCToHIDKeyMap[2*n_map_start_offset+1];
                        }
                        else{
                            //hid code
                            s_tag[2*i] = s_data[1+2*i];
                            s_tag[2*i+1] = s_data[1+2*i+1];
                        }
                    }//end for
                    tag.set_tag(s_tag);
                }
                else
                    tag.set_tag((byte[])null);

            }while (false);
            return tag;
        }

        /**
         * get Global tag send condition
         * */
        boolean get_condition()
        {
            boolean b_enable = false;

            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.ContainerInfoMsrObj_nGlobalTagCondition )
                    continue;
                if( s_data == null )
                    continue;
                int n_condition =IntByteConvert.byteToInt( s_data, ByteOrder.LITTLE_ENDIAN);
                if( n_condition == 0 )
                    continue;
                b_enable = true;
            }while (false);
            return b_enable;
        }

        boolean get_all_track_is_success_indication(){
            boolean b_all = true;//system default.

            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.cBlank )
                    continue;
                if( s_data == null )
                    continue;
                int[] n = {
                        s_data[Lpu237Const.OFFSET_IN_BLANK_OF_SUCCESS_INDICATE]&0xFF,//for unsigned operation.
                        Lpu237Const.MASK_IN_BLANK_OF_SUCCESS_INDICATE&0xFF//for unsigned operation.
                };
                int n_blank1 = n[0]&n[1];
                if(n_blank1 == 0){
                    continue;
                }
                b_all = false;
            }while(false);
            return b_all;
        }

        /**
         *
         * @return 0~240, (return value) % 16 == 0,
         * 0 -  use default interval,
         * 240 - disable reset.
         */
        int get_mmd1100_reset_interval(){
            int n_reset = 0;

            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.cBlank )
                    continue;
                if( s_data == null )
                    continue;
                byte c = s_data[Lpu237Const.OFFSET_IN_BLANK_OF_MMD1100_RESET_INTERVAL];
                c &= ((byte) Lpu237Const.MASK_IN_BLANK_OF_MMD1100_RESET_INTERVAL);

                // 비트 마스킹을 사용하여 unsigned 값을 얻음
                n_reset = c&0xFF;

            }while (false);
            return n_reset;
        }

        int  get_ibutton_position_start(){
            int n_pos = 0;
            int n_op = 0;
            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.cBlank )
                    continue;
                if( s_data == null )
                    continue;
                n_pos = s_data[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_POSITION]&0xFF;//for unsigned operation.
                n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_POSITION_START&0xFF;//for unsigned operation.
                n_pos = n_pos & n_op;
                n_pos >>= 4;
            }while(false);
            return n_pos;
        }
        int  get_ibutton_position_end(){
            int n_pos = 0;
            int n_op = 0;
            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.cBlank )
                    continue;
                if( s_data == null )
                    continue;
                n_pos = s_data[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_POSITION]&0xFF;//for unsigned operation.
                n_op = Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_POSITION_END&0xFF;//for unsigned operation.
                n_pos = n_pos & n_op;
            }while(false);
            return n_pos;
        }

        int get_ibutton_type(){
            int n_type = Lpu237iButtonType.Zeros;

            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.cBlank )
                    continue;
                if( s_data == null )
                    continue;
                byte c_f12 = (byte)(s_data[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_F12);
                if( c_f12 != 0 ){
                    n_type = Lpu237iButtonType.F12;
                    continue;
                }

                byte c_zers7 = (byte)(s_data[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS7);
                if( c_zers7 != 0 ) {
                    n_type = Lpu237iButtonType.Zeros7;
                    continue;
                }

                byte c_addmit = (byte)(s_data[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ADDMIT);
                if( c_addmit != 0 ) {
                    n_type = Lpu237iButtonType.Addmit;
                    continue;
                }

                byte c_none = (byte)(s_data[Lpu237Const.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Const.MASK_IN_BLANK_OF_IBUTTON_ZEROS);
                if( c_none !=0 ){
                    n_type = Lpu237iButtonType.None;
                    continue;
                }

                n_type = Lpu237iButtonType.Zeros;

            }while (false);
            return n_type;
        }

        boolean isPrefx()
        {
            if( c_prefix == Lpu237Response.prefix )
                return true;
            else
                return false;
        }

        boolean isSuccess()
        {
            if( c_result == Lpu237Response.resultGood || c_result == Lpu237Response.resultGoodNegative )
                return true;
            else
                return false;
        }

        boolean isPositive()
        {
            if( c_result == Lpu237Response.resultGood )
                return true;
            else
                return false;
        }

        boolean isNegative()
        {
            if( c_result == Lpu237Response.resultGoodNegative )
                return true;
            else
                return false;
        }

        public void set( final InPacket packet ){
            if( packet == null ){
                c_result = c_length = c_prefix = 0;
                if( s_data != null ){
                    s_data = null;
                }
            }
            else{
                c_prefix = packet.c_prefix;
                c_result = packet.c_result;
                c_length = packet.c_length;
                if( packet.s_data == null )
                    s_data = null;
                else{
                    s_data = new byte[packet.s_data.length];
                    System.arraycopy(packet.s_data,0,s_data,0,s_data.length);
                }
            }
        }

        public void set( byte[] rawStream ){
            do {
                c_result = c_length = c_prefix = 0;
                s_data = null;

                if (rawStream.length < 3)
                    continue;
                //
                c_prefix = rawStream[0];
                c_result = rawStream[1];
                c_length = rawStream[2];

                int n_length = (int)c_length;
                if( n_length <= 0)
                    continue;
                //
                s_data = new byte[n_length];
                System.arraycopy(rawStream,3, s_data,0, n_length);
            }while(false);
        }

        public InPacket( byte[] rawStream ){
            do {
                if (rawStream.length < 3)
                    continue;
                //
                c_prefix = rawStream[0];
                c_result = rawStream[1];
                c_length = rawStream[2];

                int n_length = (int)c_length;
                if( n_length <= 0)
                    continue;
                //
                s_data = new byte[n_length];
                System.arraycopy(rawStream,3, s_data,0, n_length);
            }while(false);
        }

        public InPacket( ByteBuffer rawStream ){
            do {
                if (rawStream.capacity() < 3)
                    continue;
                c_prefix = rawStream.get(0);
                c_result = rawStream.get(1);
                c_length = rawStream.get(2);

                int n_length = (int)c_length;
                if( n_length <= 0)
                    continue;
                //
                s_data = new byte[n_length];
                rawStream.get(s_data,3, n_length);
            }while(false);
        }

        public InPacket(){

        }
    }

    /**
     * YES0514
     * add -
     * m_is_any_good_indicate_success
     * m_n_mmd1100_reset_interval
     * m_n_ibutton_start, m_n_ibutton_end
     * m_tag_ibutton_remove
     * m_tag_ibutton_remove_tag_prefix, m_tag_ibutton_remove_tag_postfix
     *
     * changed -
     * m_tag_ibutton_prefix -> m_tag_ibutton_tag_prefix
     * m_tag_ibutton_postfix -> m_tag_ibutton_tag_postfix
     */
    static public class Parameters{
        static final int SIZE_UID=16;
        static final int SIZE_NAME=16;
        static final int SIZE_SERIAL_NUMBER=8;
        public static final int DEFAULT_FREQUENCY_BUZZER = 2500;

        private Object m_locker = new Object();

        private byte[] m_uid = new byte[SIZE_UID];
        private byte[] m_name = new byte[SIZE_NAME];
        private boolean m_is_standard_type = false;
        private boolean m_is_ibutton_only_type = false;
        private boolean[] m_enable_track = {false,false,false};
        private FwVersion m_version_system = new FwVersion();
        private FwVersion m_version_structure = new FwVersion();
        private int m_n_interface = (int)Lpu237Interface.usbKeyboard;
        private int m_n_language_index = (int)Lpu237LanguageIndex.english;
        private int m_n_buzzer_frequency = DEFAULT_FREQUENCY_BUZZER;
        private int m_n_boot_run_time = 0;
        private Lpu237Tags m_tag_global_prefix = new Lpu237Tags();
        private Lpu237Tags m_tag_global_postfix = new Lpu237Tags();
        private Lpu237Tags[] m_tag_private_prefix = new Lpu237Tags[Lpu237Const.NUMBER_ISO_TRACK];
        private Lpu237Tags[] m_tag_private_postfix = new Lpu237Tags[Lpu237Const.NUMBER_ISO_TRACK];
        private Lpu237Tags m_tag_ibutton_tag_prefix = new Lpu237Tags();
        private Lpu237Tags m_tag_ibutton_tag_postfix = new Lpu237Tags();
        private Lpu237Tags m_tag_ibutton_remove = new Lpu237Tags(20);//tag number is 20
        private Lpu237Tags m_tag_ibutton_remove_tag_prefix = new Lpu237Tags();
        private Lpu237Tags m_tag_ibutton_remove_tag_postfix = new Lpu237Tags();
        private Lpu237Tags m_tag_uart_prefix = new Lpu237Tags();
        private Lpu237Tags m_tag_uart_postfix = new Lpu237Tags();
        private boolean m_is_all_no_false = true;//global tag send condition.

        //cBlank[1]' 0 bit set - If any track is normal reading done, indicates success.
        private boolean m_is_any_good_indicate_success = false;

        //cBlank[2]' 0 bit set - send F12 key for releasing i-button
        //cBlank[2]' 1 bit set - disable send '0000000000000000' for releasing i-button
        //cBlank[2]' 2 bit set - send 7 times zero for releasing i-button.
        //cBlank[2]' 3 bit set - send Addmitt type for contact and discontact i-button.
        private int m_n_ibutton_type = Lpu237iButtonType.Zeros;//by blank member of structure.

        //add v.5.16
        //cBlank[1]' 4 bit ~ 7 bit - MMD1100 reset interval.(Tr)
        //Tr = (n+1)Tw = (n+1)*700msec
        // n = cBlank[1] & 0xF0;(0~240), n%0x10 == 0
        // if n is 0, use default interval
        // if n is 240, disable reset.
        // max interval Tr-max = (224+1)*700m = 157.5sec - 2 min 37.5sec
        private int m_n_mmd1100_reset_interval = 0;

        //add lpu237-v5.22, v3.23 and lpu238 v1.1
        //cBlank[0]' high nibble i-button send data starting zero base index 0~7
        //cBlank[0]' low nibble i-button send data ending zero base index 0~7
        //therefor the value of cBlank[0]' high nibble cannot be greater then it of cBlank[0]' low nibble
        private int m_n_ibutton_start = 0;
        private int m_n_ibutton_end = 0;
        private boolean m_b_decoder_is_mmd1000 = false;

        private byte m_c_reading_direction = Lpu237Direction.biDirection;

        private byte[] m_track_order = {0,1,2};

        //
        String m_s_description = "";

        public Parameters(){
            for( int i = 0; i< m_tag_private_prefix.length; i++ ){
                m_tag_private_prefix[i] = new Lpu237Tags();
                m_tag_private_postfix[i] = new Lpu237Tags();
            }//end for
        }

        public void copy_all_parameter_to_( Parameters dst ){
            synchronized (m_locker) {
                do{
                    if( dst == null )
                        continue;
                    //
                    System.arraycopy(m_uid,0,dst.m_uid,0,m_uid.length);
                    System.arraycopy(m_name,0,dst.m_name,0,m_name.length);
                    dst.m_is_standard_type = m_is_standard_type;
                    dst.m_is_ibutton_only_type = m_is_ibutton_only_type;

                    int i = 0;
                    for(i=0; i<3; i++ ){
                        dst.m_track_order[i] = m_track_order[i];
                        dst.m_enable_track[i] = m_enable_track[i];
                        m_tag_private_prefix[i].copy_to(dst.m_tag_private_prefix[i]);
                        m_tag_private_postfix[i].copy_to(dst.m_tag_private_postfix[i]);
                    }//end for
                    dst.m_version_system.set_version(m_version_system.get_version());
                    dst.m_version_structure.set_version(m_version_structure.get_version());
                    dst.m_n_interface = m_n_interface;
                    dst.m_n_language_index = m_n_language_index;
                    dst.m_n_buzzer_frequency = m_n_buzzer_frequency;
                    dst.m_n_boot_run_time = m_n_boot_run_time;

                    m_tag_global_prefix.copy_to(dst.m_tag_global_prefix);
                    m_tag_global_postfix.copy_to(dst.m_tag_global_postfix);

                    m_tag_ibutton_tag_prefix.copy_to(dst.m_tag_ibutton_tag_prefix);
                    m_tag_ibutton_tag_postfix.copy_to(dst.m_tag_ibutton_tag_postfix);

                    m_tag_ibutton_remove.copy_to(dst.m_tag_ibutton_remove);

                    m_tag_ibutton_remove_tag_prefix.copy_to(dst.m_tag_ibutton_remove_tag_prefix);
                    m_tag_ibutton_remove_tag_postfix.copy_to(dst.m_tag_ibutton_remove_tag_postfix);

                    m_tag_uart_prefix.copy_to(dst.m_tag_uart_prefix);
                    m_tag_uart_postfix.copy_to(dst.m_tag_uart_postfix);

                    dst.m_is_all_no_false = m_is_all_no_false;
                    dst.m_n_ibutton_type = m_n_ibutton_type;
                    dst.m_b_decoder_is_mmd1000 = m_b_decoder_is_mmd1000;

                    dst.m_n_mmd1100_reset_interval = m_n_mmd1100_reset_interval;
                    dst.m_n_ibutton_start = m_n_ibutton_start;
                    dst.m_n_ibutton_end = m_n_ibutton_end;

                    dst.m_is_any_good_indicate_success = m_is_any_good_indicate_success;

                    dst.m_c_reading_direction = m_c_reading_direction;
                }while (false);
            }
        }

        public void set_all_parameter( Parameters src ) {
            set_changable_parameter(src);

            // here is unchangeable item only.
            synchronized (m_locker) {
                System.arraycopy(src.m_uid,0,m_uid,0,m_uid.length);
                System.arraycopy(src.m_name,0,m_name,0,m_name.length);
                m_is_standard_type = src.m_is_standard_type;
                m_is_ibutton_only_type = src.m_is_ibutton_only_type;

                m_version_system.set_version(src.m_version_system.get_version());
                m_version_structure.set_version(src.m_version_structure.get_version());
                m_n_boot_run_time = src.m_n_boot_run_time;

                m_b_decoder_is_mmd1000 = src.m_b_decoder_is_mmd1000;
            }

        }
        public void set_changable_parameter( Parameters src ){
            synchronized (m_locker){
                int i = 0;
                for(i=0;i<3;i++){
                    m_track_order[i] = src.m_track_order[i];
                    m_enable_track[i] = src.m_enable_track[i];
                }//end for
                //
                m_n_interface = src.m_n_interface;
                m_n_language_index = src.m_n_language_index;
                m_n_buzzer_frequency = src.m_n_buzzer_frequency;

                src.m_tag_global_prefix.copy_to(this.m_tag_global_prefix);
                src.m_tag_global_postfix.copy_to(this.m_tag_global_postfix);

                for( i = 0; i<3; i++ ){
                    src.m_tag_private_prefix[i].copy_to(this.m_tag_private_prefix[i]);
                    src.m_tag_private_postfix[i].copy_to(this.m_tag_private_postfix[i]);
                }

                src.m_tag_ibutton_tag_prefix.copy_to(this.m_tag_ibutton_tag_prefix);
                src.m_tag_ibutton_tag_postfix.copy_to(this.m_tag_ibutton_tag_postfix);

                src.m_tag_ibutton_remove.copy_to(this.m_tag_ibutton_remove);

                src.m_tag_ibutton_remove_tag_prefix.copy_to(this.m_tag_ibutton_remove_tag_prefix);
                src.m_tag_ibutton_remove_tag_postfix.copy_to(this.m_tag_ibutton_remove_tag_postfix);

                src.m_tag_uart_prefix.copy_to(this.m_tag_uart_prefix);
                src.m_tag_uart_postfix.copy_to(this.m_tag_uart_postfix);

                m_is_all_no_false = src.m_is_all_no_false;
                m_n_ibutton_type = src.m_n_ibutton_type;

                m_n_mmd1100_reset_interval = src.m_n_mmd1100_reset_interval;
                m_n_ibutton_start = src.m_n_ibutton_start;
                m_n_ibutton_end = src.m_n_ibutton_end;

                m_is_any_good_indicate_success = src.m_is_any_good_indicate_success;
                m_c_reading_direction = src.m_c_reading_direction;
            }
        }

        public boolean set_reading_direction(byte dir){
            boolean b_changed = false;

            synchronized (m_locker){
                if(m_c_reading_direction != dir){
                    m_c_reading_direction = dir;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public boolean set_track_order(byte[] order){
            boolean b_changed = false;

            synchronized (m_locker){
                if(order.length == 3){
                    for(int i=0; i<3; i++){
                        if(m_track_order[i] != order[i]){
                            m_track_order[i] = order[i];
                            b_changed = true;
                        }
                    }//end for
                }
            }
            return b_changed;
        }
        public boolean set_any_good_indicate_success(boolean b_any_good_indicate_success){
            boolean b_changed = false;

            synchronized (m_locker){
                if(m_is_any_good_indicate_success!=b_any_good_indicate_success){
                    m_is_any_good_indicate_success = b_any_good_indicate_success;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public  boolean set_mmd1100_reset_interval(int n_mmd1100_reset_interval){
            boolean b_changed = false;

            synchronized (m_locker){
                if(m_n_mmd1100_reset_interval != n_mmd1100_reset_interval) {
                    m_n_mmd1100_reset_interval = n_mmd1100_reset_interval;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_start( int n_ibutton_start){
            boolean b_changed = false;

            synchronized (m_locker){
                if( m_n_ibutton_start != n_ibutton_start) {
                    m_n_ibutton_start = n_ibutton_start;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_end( int n_ibutton_end){
            boolean b_changed = false;

            synchronized (m_locker){
                if(m_n_ibutton_end != n_ibutton_end) {
                    m_n_ibutton_end = n_ibutton_end;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public void set_decoder_mmd1000( boolean b_mmd1000 ){
            synchronized (m_locker){
                m_b_decoder_is_mmd1000 = b_mmd1000;
            }
        }

        public void set_is_standard_type( boolean is_standard_type ){
            synchronized (m_locker) {
                m_is_standard_type = is_standard_type;
            }
        }

        public void set_is_ibutton_only_type( boolean is_ibutton_only_type ){
            synchronized (m_locker){
                m_is_ibutton_only_type = is_ibutton_only_type;
            }
        }

        public void set_uid( byte[] s_id ){
            synchronized (m_locker) {
                do{
                    Arrays.fill( m_uid, (byte) 0);
                    if( s_id == null )
                        continue;
                    int n_length = m_uid.length;
                    if( m_uid.length > s_id.length )
                        n_length = s_id.length;
                    //
                    for(int i = 0; i<n_length; i++ ){
                        m_uid[i] = s_id[i];
                    }
                }while (false);
            }
        }

        public void set_name( byte[] s_name ){
            synchronized (m_locker) {
                do{
                    Arrays.fill( m_name, (byte) 0);
                    if( s_name == null )
                        continue;
                    int n_length = m_name.length;
                    if( m_name.length > s_name.length )
                        n_length = s_name.length;
                    //
                    for(int i = 0; i<n_length; i++ ){
                        m_name[i] = s_name[i];
                    }
                }while (false);
            }

        }
        public boolean set_enable_track(int n_track, boolean b_enable) {
            boolean b_changed = false;
            synchronized (m_locker) {
                do {
                    if (n_track < 0 || n_track > 2)
                        continue;
                    if( m_enable_track[n_track] != b_enable ) {
                        m_enable_track[n_track] = b_enable;
                        b_changed = true;
                    }
                } while (false);
            }
            return b_changed;
        }

        public void set_version_system( FwVersion version ) {
            synchronized (m_locker) {
                m_version_system = version;
            }
        }

        public void set_version_structure( FwVersion version ){
            synchronized (m_locker) {
                m_version_structure = version;
            }
        }

        public boolean set_interface( int n_interface ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_interface != n_interface ) {
                    m_n_interface = n_interface;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_type( int n_type ){
            boolean b_changed = false;
            do {
                if (n_type < Lpu237iButtonType.Zeros || n_type > Lpu237iButtonType.None)
                    continue;
                synchronized (m_locker) {
                    if( m_n_ibutton_type != n_type ) {
                        m_n_ibutton_type = n_type;
                        b_changed = true;
                    }
                }

            }while (false);
            return b_changed;
        }
        public boolean set_language_index( int n_index ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_language_index != n_index) {
                    m_n_language_index = n_index;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_buzzer_frequency( int n_buzzer ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_buzzer_frequency != n_buzzer ) {
                    m_n_buzzer_frequency = n_buzzer;
                    b_changed  = true;
                }
            }
            return b_changed;
        }
        public boolean set_boot_run_time( int n_time ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_boot_run_time != n_time) {
                    m_n_boot_run_time = n_time;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_global_prefix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_global_prefix.equal(tag) ){
                    m_tag_global_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_global_postfix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_global_postfix.equal(tag)) {
                    m_tag_global_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_private_prefix( int n_track, Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if (n_track >= 0 && n_track < 3) {
                    if( !m_tag_private_prefix[n_track].equal(tag)) {
                        m_tag_private_prefix[n_track] = tag;
                        b_changed = true;
                    }
                }
            }
            return b_changed;
        }
        public boolean set_private_postfix( int n_track, Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if (n_track >=0 && n_track < 3) {
                    if( !m_tag_private_postfix[n_track].equal(tag)) {
                        m_tag_private_postfix[n_track] = tag;
                        b_changed = true;
                    }
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_tag_prefix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_tag_prefix.equal(tag)) {
                    m_tag_ibutton_tag_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_tag_postfix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_tag_postfix.equal(tag)) {
                    m_tag_ibutton_tag_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public boolean set_ibutton_remove( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_remove.equal(tag)) {
                    m_tag_ibutton_remove = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public boolean set_ibutton_remove_tag_prefix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_remove_tag_prefix.equal(tag)) {
                    m_tag_ibutton_remove_tag_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_remove_tag_postfix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_remove_tag_postfix.equal(tag)) {
                    m_tag_ibutton_remove_tag_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public boolean set_uart_postfix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_uart_postfix.equal(tag)) {
                    m_tag_uart_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_uart_prefix( Lpu237Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_uart_prefix.equal(tag) ) {
                    m_tag_uart_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_global_send_condition( boolean b_condition ){
            boolean b_changed = false;
            synchronized (m_locker){
                if( m_is_all_no_false != b_condition ) {
                    m_is_all_no_false = b_condition;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public byte[] get_track_order(){
            synchronized (m_locker){
                return m_track_order;
            }
        }
        public byte get_reading_direction(){
            synchronized (m_locker){
                return m_c_reading_direction;
            }
        }
        public boolean get_any_good_indicate_success(){
            synchronized (m_locker){
                return m_is_any_good_indicate_success;
            }
        }
        public int get_mmd1100_reset_interval(){
            synchronized (m_locker){
                return m_n_mmd1100_reset_interval;
            }
        }
        public  int get_ibutton_start(){
            synchronized (m_locker){
                return m_n_ibutton_start;
            }
        }
        public int get_ibutton_end(){
            synchronized (m_locker){
                return m_n_ibutton_end;
            }
        }
        public boolean get_decoder_mmd1000(){
            synchronized (m_locker){
                return m_b_decoder_is_mmd1000;
            }
        }
        public int get_ibutton_type(){
            synchronized (m_locker){
                return m_n_ibutton_type;
            }
        }
        public  boolean get_is_standard_type(){
            synchronized (m_locker) {
                return m_is_standard_type;
            }
        }
        public  boolean get_is_ibutton_only_type(){
            synchronized (m_locker){
                return m_is_ibutton_only_type;
            }
        }
        public byte[] get_uid(){
            synchronized (m_locker) {
                return m_uid;
            }
        }
        public byte[] get_name(){
            synchronized (m_locker){
                return m_name;
            }
        }
        public boolean get_enable_track( int n_track ){
            boolean b_enable = true;
            synchronized (m_locker){
                do{
                    if( n_track < 0 || n_track >(Lpu237Const.NUMBER_ISO_TRACK-1))
                        continue;
                    b_enable = m_enable_track[n_track];
                }while (false);
            }
            return b_enable;
        }
        public FwVersion get_version_system(){
            synchronized (m_locker){
                return m_version_system;
            }
        }
        public FwVersion get_version_structure(){
            synchronized (m_locker){
                return m_version_structure;
            }
        }
        public int get_interface(){
            synchronized (m_locker){
                return m_n_interface;
            }
        }
        public int get_language_index(){
            synchronized (m_locker){
                return m_n_language_index;
            }
        }
        public int get_buzzer_frequency(){
            synchronized (m_locker){
                return m_n_buzzer_frequency;
            }
        }
        public int get_boot_run_time(){
            synchronized (m_locker){
                return m_n_boot_run_time;
            }
        }
        public Lpu237Tags get_global_prefix(){
            synchronized (m_locker){
                return m_tag_global_prefix;
            }
        }
        public Lpu237Tags get_global_postfix(){
            synchronized (m_locker){
                return m_tag_global_postfix;
            }
        }
        public Lpu237Tags get_private_prefix(int n_track ){
            Lpu237Tags tag = null;

            synchronized (m_locker){
                do{
                    if( n_track < 0 || n_track >(Lpu237Const.NUMBER_ISO_TRACK-1))
                        continue;
                    tag = m_tag_private_prefix[n_track];
                }while(false);
            }
            return tag;
        }
        public Lpu237Tags get_private_postfix(int n_track ){
            Lpu237Tags tag = null;

            synchronized (m_locker){
                do{
                    if( n_track < 0 || n_track >(Lpu237Const.NUMBER_ISO_TRACK-1))
                        continue;
                    tag = m_tag_private_postfix[n_track];
                }while(false);
            }
            return tag;
        }
        public Lpu237Tags get_ibutton_tag_prefix(){
            synchronized (m_locker){
                return m_tag_ibutton_tag_prefix;
            }
        }
        public Lpu237Tags get_ibutton_tag_postfix(){
            synchronized (m_locker){
                return m_tag_ibutton_tag_postfix;
            }
        }
        public Lpu237Tags get_ibutton_remove(){
            synchronized (m_locker){
                return m_tag_ibutton_remove;
            }
        }
        public Lpu237Tags get_ibutton_remove_tag_prefix(){
            synchronized (m_locker){
                return m_tag_ibutton_remove_tag_prefix;
            }
        }
        public Lpu237Tags get_ibutton_remove_tag_postfix(){
            synchronized (m_locker){
                return m_tag_ibutton_remove_tag_postfix;
            }
        }

        public Lpu237Tags get_uart_prefix(){
            synchronized (m_locker){
                return m_tag_uart_prefix;
            }
        }
        public Lpu237Tags get_uart_postfix(){
            synchronized (m_locker){
                return m_tag_uart_postfix;
            }
        }
        public boolean get_global_send_condition(){
            synchronized (m_locker){
                return m_is_all_no_false;
            }
        }
        public String getDescription()
        {
            synchronized (m_locker){
                if( m_is_ibutton_only_type){
                    m_s_description = new String("i-Button Reader Model");
                }
                else {
                    if (m_is_standard_type)
                        m_s_description = new String("Standard Model");
                    else
                        m_s_description = new String("Compact Model");
                }

                m_s_description += " - ";
                m_s_description += Tools.byteArrayToHex(m_uid);
                return m_s_description;
            }
        }

    }

    //
    private Parameters m_parameters = new Parameters();
}
