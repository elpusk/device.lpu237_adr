package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

import kr.pe.sheep_transform.lpu237_adr.lib.util.FwVersion;

public class Lpu237Tools {
    static public boolean is_support_hid_boot(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_device_name_of_lpu237_with_lpc1343(s_sys_name);
    }
    static public boolean is_support_keymap_download(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,4,0,1))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,2,1,4))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_device_id(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,7,0,4))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,2,1,4))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_uart_prepostfix(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,3,0,2))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,2,1,4))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_magtek(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(1,0,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,0,0,4))){
                continue;
            }
            if( v.less(new FwVersion(5,15,0,0))){
                b_support = true;//ganymede
                continue;
            }
            if( v.less(new FwVersion(5,18,0,0))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_wiznova_without_reset(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(5,0,0,0))){
                continue;
            }
            if( v.greater(new FwVersion(5,12,0,0))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }

    static public boolean is_support_msr_wiznova_with_reset(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(5,16,0,0))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_magtek_and_wiznova(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                continue;
            }

            if(!Lpu237Tools.is_support_msr_magtek(s_sys_name,s_sys_version)){
                continue;
            }

            b_support = Lpu237Tools.is_support_msr_wiznova_without_reset(s_sys_name,s_sys_version);
            b_support |= Lpu237Tools.is_support_msr_wiznova_with_reset(s_sys_name,s_sys_version);
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_hid_vendor(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,9,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,5,0,0))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_iso2_max_38(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,10,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,2,0,0))){
                continue;
            }
            b_support = true;
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_global_tag(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,11,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,3,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_combination(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,21,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,13,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_success_indicate_condition(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_support_msr_combination(s_sys_name,s_sys_version);
    }
    static public boolean is_support_msr_send_only_iso2_when_1_or_3_equal(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,21,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,14,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_woori_stock_passbook(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_support_msr_send_only_iso2_when_1_or_3_equal(s_sys_name,s_sys_version);
    }
    static public boolean is_support_msr_card_direction(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,21,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,17,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_global_tag_send_condition(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,11,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,3,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_read(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,3,0,2))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,2,1,4))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_prepostfix_in_kb_mode(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_support_ibutton_read(s_sys_name,s_sys_version);
    }
    static public boolean is_support_ibutton_send_only_16zeros_on_remove_in_kb_mode(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,4,0,1))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(4,2,1,4))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_hid_vendor(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,12,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,4,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_send_only_f12_on_remove_in_kb_mode(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,13,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,5,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_send_none_16zeros_f12_on_remove_in_kb_mode(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,14,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,6,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_send_none_16zeros_f12_7zeros_on_remove_in_kb_mode(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,16,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,8,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_code_stick(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,17,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,9,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_send_setting_16zeros_f12_7zeros_on_remove_in_kb_mode(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,22,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,21,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_ibutton_prepostfix_on_remove(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_support_ibutton_send_setting_16zeros_f12_7zeros_on_remove_in_kb_mode(s_sys_name,s_sys_version);
    }
    static public boolean is_support_ibutton_remove(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_support_ibutton_send_setting_16zeros_f12_7zeros_on_remove_in_kb_mode(s_sys_name,s_sys_version);
    }

    static public boolean is_support_change_ibutton_range(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,23,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,22,0,0))){
                continue;
            }
            if( v.greater_then_equal(new FwVersion(10,0,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_mmd1100_iso_mode(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less_then_equal(new FwVersion(5,22,0,0))){
                continue;
            }
            if(v.get_build() != 1){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }
    static public boolean is_support_mmd1100_reset(String s_sys_name,String s_sys_version){
        return is_support_msr_wiznova_with_reset(s_sys_name,s_sys_version);
    }
    static public boolean is_support_usb_vcom(String s_sys_name,String s_sys_version){
        return Lpu237Tools.is_device_name_of_lpu238(s_sys_name);
    }
    static public boolean is_support_ps2(String s_sys_name,String s_sys_version){
        boolean b_support = true;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = false;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = false;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,10,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = false;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,2,0,0))){
                continue;
            }

            b_support = false;
        }while (false);
        return b_support;
    }
    static public boolean is_support_msr_send_order(String s_sys_name,String s_sys_version){
        boolean b_support = false;
        do{
            if( Lpu237Tools.is_device_name_of_lpu238(s_sys_name)){
                b_support = true;
                continue;
            }
            if( Lpu237Tools.is_device_name_of_lpu237_with_mh1902t(s_sys_name)){
                b_support = true;
                continue;
            }

            FwVersion v = new FwVersion(s_sys_version);
            if( v.less(new FwVersion(3,21,0,0))){
                continue;
            }
            if( v.less(new FwVersion(4,0,0,0))){
                b_support = true;//callisto
                continue;
            }
            if( v.less(new FwVersion(5,19,0,0))){
                continue;
            }

            b_support = true;

        }while (false);
        return b_support;
    }

    static public String[] get_device_name_of_lpc1343(){
        return new String[]{"callisto","ganymede","europa"};
    }
    static public boolean is_device_name_of_lpc1343(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpc1343();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_mh1902t(){
        return new String[]{"himalia","elara"};
    }
    static public boolean is_device_name_of_mh1902t(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_mh1902t();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_lpu237(){
        return new String[]{"callisto","ganymede","himalia"};
    }
    static public boolean is_device_name_of_lpu237(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpu237();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_lpu238(){
        return new String[]{"europa","elara"};
    }
    static public boolean is_device_name_of_lpu238(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpu238();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_lpu237_with_lpc1343(){
        return new String[]{"callisto","ganymede"};
    }
    static public boolean is_device_name_of_lpu237_with_lpc1343(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpu237_with_lpc1343();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_lpu238_with_lpc1343(){
        return new String[]{"europa"};
    }
    static public boolean is_device_name_of_lpu238_with_lpc1343(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpu238_with_lpc1343();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_lpu237_with_mh1902t(){
        return new String[]{"himalia"};
    }
    static public boolean is_device_name_of_lpu237_with_mh1902t(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpu237_with_mh1902t();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }
    static public String[] get_device_name_of_lpu238_with_mh1902t(){
        return new String[]{"elara"};
    }
    static public boolean is_device_name_of_lpu238_with_mh1902t(String s_sys_name){
        String[] n = Lpu237Tools.get_device_name_of_lpu238_with_mh1902t();
        return Lpu237Tools._is_found_name_in_array(n,s_sys_name);
    }

    static private boolean _is_found_name_in_array(String[] n,String s_name){
        boolean b_found = false;
        if(n==null){
            return b_found;
        }
        for( int i=0; i<n.length; i++ ){
            if( s_name.compareTo(n[i])==0){
                b_found = true;
                break;
            }
        }//end for
        return b_found;
    }

}//Lpu237Tools
