package kr.pe.sheep_transform.lpu237_adr.lib.rom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

import kr.pe.sheep_transform.lpu237_adr.lib.rom.RomErrorCodeFirmwareIndex;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.RomResult;
import kr.pe.sheep_transform.lpu237_adr.lib.util.FwVersion;
import kr.pe.sheep_transform.lpu237_adr.lib.util.IntByteConvert;
import kr.pe.sheep_transform.lpu237_adr.lib.util.Tools;


public class Rom {

    static public String get_error_description_firmware_index_setting( int n_error_code ){
        String s_description="";
        switch (n_error_code){
            case RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_name:
                s_description = "firmware index none matched name";
                break;
            case RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_version:
                s_description = "firmware index none matched version";
                break;
            case RomErrorCodeFirmwareIndex.error_firmware_index_none_device_name:
                s_description = "firmware index none device name";
                break;
            case RomErrorCodeFirmwareIndex.error_firmware_index_none_device_version:
                s_description = "firmware index none device version";
                break;
            case RomErrorCodeFirmwareIndex.error_firmware_index_format_device_version:
                s_description = "firmware index format device version";
                break;
            case RomErrorCodeFirmwareIndex.error_firmware_index_none_file_header:
                s_description = "firmware index none file header";
                break;
            default:
                s_description = "invalid error code";
                break;
        }//end switch
        return s_description;
    }

    //condition mask
    private final static int mask_condition_no = 0;
    private final static int mask_condition_eq = 1;
    private final static int mask_condition_neq = 2;
    private final static int mask_condition_gt = 4;
    private final static int mask_condition_lt = 8;
    //
    static public class Firmware{
        public static final int size_info = 175;//firmware information size
        private int m_n_size = 0;//firmware size
        private int m_n_offset = -1;//starting offset.
        private byte[] m_s_version = new byte[4];
        private byte[] m_s_model_name = new byte[31];
        private int m_n_update_condition = mask_condition_no;
        private byte[] m_s_hash = new byte[32];//only header version 1.1 greater then equal.
        //
        public Firmware(){

        }
        public Firmware( byte[] rawStream, int n_start_pos ){
            set( rawStream ,n_start_pos);
        }

        public boolean set( byte[] rawStream, int n_start_pos ){
            boolean b_result =false;
            do{
                if( rawStream == null )
                    continue;
                if (n_start_pos < 0 )
                    continue;
                if( rawStream.length <= n_start_pos )
                    continue;
                //
                if( (rawStream.length-n_start_pos) < m_n_size )
                    continue;
                //
                byte[] s_dword = new byte[4];
                int n_offset = n_start_pos;
                System.arraycopy(rawStream,n_offset,s_dword,0,s_dword.length);

                m_n_size = IntByteConvert.byteToInt(s_dword, ByteOrder.LITTLE_ENDIAN );
                if( m_n_size <= 0 )
                    continue;
                n_offset += s_dword.length;

                System.arraycopy(rawStream,n_offset,s_dword,0,s_dword.length);
                m_n_offset = IntByteConvert.byteToInt(s_dword,ByteOrder.LITTLE_ENDIAN);
                n_offset += s_dword.length;

                System.arraycopy(rawStream,n_offset,m_s_version,0,m_s_version.length);
                n_offset += m_s_version.length;

                System.arraycopy(rawStream,n_offset,m_s_model_name,0,m_s_model_name.length);
                n_offset += m_s_model_name.length;
                //
                System.arraycopy(rawStream,n_offset,s_dword,0,s_dword.length);
                m_n_update_condition = IntByteConvert.byteToInt(s_dword,ByteOrder.LITTLE_ENDIAN);
                n_offset += s_dword.length;
                //
                System.arraycopy(rawStream,n_offset,m_s_hash,0,m_s_hash.length);

                b_result = true;
            }while(false);
            return b_result;
        }

        public int get_size(){
            return m_n_size;
        }
        public int get_offset(){
            return m_n_offset;
        }
        public byte[] get_version(){
            return m_s_version;
        }
        public byte[] get_name(){
            return m_s_model_name;
        }
        public String get_name_by_string(){
            return Tools.getStringFromByteArray(get_name());
        }
        public int get_condition(){
            return m_n_update_condition;
        }
        public byte[] get_hash(){
            return m_s_hash;
        }


    }

    static class Header{
        public static final int m_n_size = 8015;//header size
        private byte[] m_s_version = new byte[4];
        private byte[] m_s_rfu = new byte[128];
        //private int m_n_number_of_firmware = 0;
        //private Firmware[] m_firmwares = new Firmware[45];
        private Firmware[] m_firmwares = null;
        //
        public Header(){

        }
        public Header(byte[] rawStream,int n_start_pos ){
            set( rawStream,n_start_pos );
        }

        public boolean set( byte[] rawStream,int n_start_pos ){
            boolean b_result =false;
            do{
                if( rawStream == null )
                    continue;
                //
                if (n_start_pos < 0 )
                    continue;
                if( rawStream.length <= n_start_pos )
                    continue;
                //
                if( (rawStream.length-n_start_pos) < m_n_size )
                    continue;
                //
                byte[] s_dword = new byte[4];
                int n_offset = n_start_pos;
                System.arraycopy(rawStream,n_offset,s_dword,0,s_dword.length);

                int n_size = IntByteConvert.byteToInt(s_dword, ByteOrder.LITTLE_ENDIAN );
                if( n_size != m_n_size )
                    continue;
                n_offset += s_dword.length;

                System.arraycopy(rawStream,n_offset,m_s_version,0,m_s_version.length);
                n_offset += m_s_version.length;
                //
                n_offset += m_s_rfu.length;
                //
                System.arraycopy(rawStream,n_offset,s_dword,0,s_dword.length);
                int n_number_of_firmware = IntByteConvert.byteToInt(s_dword, ByteOrder.LITTLE_ENDIAN );
                n_offset += s_dword.length;
                //
                if( n_number_of_firmware > 0 ) {
                    m_firmwares = new Firmware[n_number_of_firmware];
                    for (int i = 0; i < n_number_of_firmware; i++) {
                        m_firmwares[i] = new Firmware( rawStream, n_offset );
                        n_offset += Firmware.size_info;
                    }//end for
                }
                else
                    m_firmwares = null;

                //
                b_result = true;
            }while (false);
            return b_result;
        }

        public int get_size(){
            return m_n_size;
        }
        public byte[] get_version(){
            return m_s_version;
        }
        public int get_number_of_firmware(){
            if( m_firmwares == null )
                return 0;
            return m_firmwares.length;
        }

        public Firmware get_firmware( int n_index ){
            Firmware selected = null;

            do{
                if( n_index < 0 )
                    continue;
                if( m_firmwares == null )
                    continue;
                if( n_index >= m_firmwares.length )
                    continue;
                selected = m_firmwares[n_index];
            }while (false);
            return selected;
        }
    }

    private File m_file_rom = null;
    private int m_n_cur_firmware_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_name;
    private Header m_header = null;
    private Firmware m_firmware = null;
    private String m_s_error_message = "";
    private boolean m_b_valied_format = false;

    public boolean is_loaded_rom_header(){
        return m_b_valied_format;
    }
    public int load_rom_header(File rom_file ){
        int n_result = RomResult.result_error;
        do{
            if( rom_file == null ){
                n_result = RomResult.result_error_invalied_parameter;
                continue;
            }
            //
            boolean b_valied_format = true;
            byte[] s_header = new byte[Header.m_n_size] ;

            FileInputStream fileInputStream = null;
            try {
                int n_offset = 0;
                fileInputStream = new FileInputStream(rom_file);
                //

                int n_partial = 0;
                byte[] s_partial = new byte[1024];
                int n_remainder = s_header.length;

                do {
                    if( n_remainder >= s_partial.length )
                        n_partial = s_partial.length;
                    else
                        n_partial = n_remainder;
                    //
                    n_partial = fileInputStream.read(s_partial, 0, n_partial);
                    if (n_partial > 0) {
                        System.arraycopy(s_partial, 0, s_header, n_offset, n_partial);
                        n_offset += n_partial;
                        n_remainder -= n_partial;
                    }
                    else{//the end of file
                        b_valied_format = false;
                        break;//exit while
                    }
                }while (n_remainder > 0);

                if(n_remainder<=0) {
                    if (n_offset < Header.m_n_size) {
                        n_result = RomResult.result_error_shorter_then_expected;
                        continue;
                    }

                    m_header = new Header(s_header, 0);
                }
            }
            catch (Exception e) {
                e.printStackTrace() ;
                continue;
            }
            finally {
                try{
                    if( fileInputStream != null )
                        fileInputStream.close();
                } catch(IOException io) {}
            }
            //
            if(b_valied_format) {
                m_file_rom = rom_file;
                n_result = RomResult.result_success;
            }
            else{
                n_result = RomResult.result_error_unknown_format;
            }
        }while(false);
        set_error_message(n_result);

        if(n_result == RomResult.result_success){
            m_b_valied_format = true;
        }
        else{
            m_b_valied_format = false;
        }
        return n_result;
    }

    public int get_the_number_of_firmware(){
        int n_number = 0;
        do{
            if( m_header == null )
                continue;
            n_number = m_header.get_number_of_firmware();

        }while(false);
        return n_number;
    }
    public String get_device_name_of_firmware( int n_index ){
        String s_name = "";
        do{
            if( n_index < 0 )
                continue;
            if( get_the_number_of_firmware() <= n_index )
                continue;
            //
            Firmware firmware = m_header.get_firmware(n_index);
            if( firmware == null )
                continue;
            s_name = firmware.get_name_by_string();

        }while(false);
        return s_name;
    }
    public int set_updatable_firmware_index( String s_dev_fw_model_name, int n_major, int n_minor, int n_fix, int n_build){
        return set_updatable_firmware_index( s_dev_fw_model_name
                ,new byte[]{(byte)n_major,(byte)n_minor,(byte)n_fix,(byte)n_build}
        );
    }

    public int set_updatable_firmware_index( String s_dev_fw_model_name, byte[] s_dev_fw_version ){
        int n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_name;

        do{
            if( s_dev_fw_model_name == null ) {
                n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_device_name;
                continue;
            }
            if( s_dev_fw_model_name.isEmpty() ) {
                n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_device_name;
                continue;
            }
            if( s_dev_fw_version == null ) {
                n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_device_version;
                continue;
            }
            if( s_dev_fw_version.length != 4 ) {
                n_index = RomErrorCodeFirmwareIndex.error_firmware_index_format_device_version;
                continue;
            }
            if( m_header == null ) {
                n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_file_header;
                continue;
            }
            //
            String s_packed_dev_fw_model_name = new String(s_dev_fw_model_name.trim());

            FwVersion version_dev = new FwVersion( s_dev_fw_version );
            String s_name = "";
            String s_packed_s_name = new String(s_name);

            for( int i = 0; i<m_header.get_number_of_firmware(); i++ ){
                Firmware fw = m_header.get_firmware(i);
                s_name = fw.get_name_by_string();
                s_packed_s_name = s_name.trim();

                if( s_packed_dev_fw_model_name.equals( s_packed_s_name )) {
                    n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_version;

                    FwVersion version_file = new FwVersion(fw.get_version());

                    if( fw.get_condition() == mask_condition_no ){
                        n_index = i;
                        break;//exit for
                    }
                    if( (fw.get_condition() & mask_condition_eq) == mask_condition_eq ){
                        if( version_file.equal(version_dev) ){
                            n_index = i;
                            break;//exit for
                        }
                    }
                    if( (fw.get_condition() & mask_condition_neq) == mask_condition_neq ){
                        if( !version_file.equal(version_dev) ){
                            n_index = i;
                            break;//exit for
                        }
                    }
                    if( (fw.get_condition() & mask_condition_gt) == mask_condition_gt ){
                        if( version_file.greater(version_dev) ){
                            n_index = i;
                            break;//exit for
                        }
                    }
                    if( (fw.get_condition() & mask_condition_lt) == mask_condition_lt ){
                        if( version_file.less(version_dev) ){
                            n_index = i;
                            break;//exit for
                        }
                    }
                }
                else{
                    n_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_name;
                }
            }//end for
            //
            m_n_cur_firmware_index = n_index;
        }while (false);
        return n_index;
    }

    public int set_updatable_firmware_index( String s_dev_fw_model_name, FwVersion dev_fw_version ){
        int n_index = RomErrorCodeFirmwareIndex.error_firmware_index_format_device_version;
        do{
            if( dev_fw_version == null )
                continue;
            n_index = set_updatable_firmware_index( s_dev_fw_model_name, dev_fw_version.get_version());
        }while (false);
        return n_index;

    }

    public int get_firmware_size(){
        int n_size = -1;

        do{
            if(m_firmware == null){
                continue;
            }

            n_size = m_firmware.get_size();
        }while (false);
        return n_size;
    }
    public int set_firmware( int n_index ){
        int n_result = RomResult.result_error;

        do{
            if( m_header == null ) {
                n_result = RomResult.result_error_invalied_parameter;
                continue;
            }
            if( n_index < 0 ) {
                n_result = RomResult.result_error_invalied_parameter;
                continue;
            }
            if( n_index >= m_header.get_number_of_firmware() ) {
                n_result = RomResult.result_error_invalied_parameter;
                continue;
            }
            //
            m_firmware = m_header.get_firmware( n_index );
            //
            n_result = RomResult.result_success;
        }while(false);
        set_error_message(n_result);
        return n_result;
    }

    public int read_binary_of_firmware( byte[] s_out_buffer, int n_start_pos ){
        int n_read = -1;

        do{
            if( m_file_rom == null )
                continue;
            if( m_header == null )
                continue;
            if( m_firmware == null )
                continue;
            if( n_start_pos < 0 )
                continue;
            if( s_out_buffer == null )
                continue;

            int n_remainder = m_firmware.get_size() - n_start_pos;
            if( n_remainder <= 0 ){
                n_read = 0;
                continue;
            }

            int n_wanted = s_out_buffer.length;
            if( n_remainder < n_wanted )
                n_wanted = n_remainder;
            //
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(m_file_rom);
                //
                fileInputStream.skip(m_firmware.get_offset()+n_start_pos );//set starting position of firmware data.
                n_read = fileInputStream.read(s_out_buffer, 0, n_wanted);
            }
            catch (Exception e) {
                e.printStackTrace() ;
                n_read = -1;
                continue;
            }
            finally {
                try{
                    if( fileInputStream != null )
                        fileInputStream.close();
                } catch(IOException io) {}
            }
            //
        }while(false);
        return n_read;
    }

    public String get_last_error_message(){
        return m_s_error_message;
    }

    public Firmware get_fw(){
        return m_firmware;
    }

    private void set_error_message( int n_result ){
        switch(n_result){
            case RomResult.result_success:
                m_s_error_message = "";
                break;
            case RomResult.result_error:
                m_s_error_message = "system error";
                break;
            case RomResult.result_error_not_found:
                m_s_error_message = "not found";
                break;
            case RomResult.result_error_invalied_parameter:
                m_s_error_message = "invalied parameter";
                break;
            case RomResult.result_error_shorter_then_expected:
                m_s_error_message = "the given data is shorter then expected it";
                break;
            case RomResult.result_error_not_loaded_dll:
                m_s_error_message = "not load module";
                break;
            case RomResult.result_error_not_open_file:
                m_s_error_message = "not opened file";
                break;
            case RomResult.result_error_greater_then_expected:
                m_s_error_message = "the given data is greater then expected it";
                break;
            case RomResult.result_error_over_capacity:
                m_s_error_message = "over capacity";
                break;
            case RomResult.result_error_unknown_format:
                m_s_error_message = "unknown format(not rom)";
                break;
            default:
                m_s_error_message = "unknown error";
                break;
        }//end switch
    }
}
