package kr.pe.sheep_transform.lpu237_adr.lib.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

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
                s_key += byteToHex(c_key);
            }
        }
        else{
            if( MapCodeToString.CvtUnshiftCodeToString.containsKey(c_key) ){
                s_key += MapCodeToString.CvtUnshiftCodeToString.get(c_key);
            }
            else{
                s_key += byteToHex(c_key);
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

}
