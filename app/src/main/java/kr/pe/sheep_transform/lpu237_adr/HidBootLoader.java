package kr.pe.sheep_transform.lpu237_adr;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.File;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

interface HidBootLoaderRequest{
    byte cmdUnknown = 0;
    byte cmdWrite = 10;
    byte cmdRead = 20;
    byte cmdErase = 30;
    byte cmdRunApp = 40;
}

interface  HidBootLoaderResponse{
    byte resultSuccess = 0;
    byte resultError = (byte)0xFF;
}

interface HidBootLoaderInfo{
    int USB_VID = 0x134b;
    int USB_PID = 0x0243;
}
interface HidBootLoaderSectorOrder{
    int[] order = new int[]{
            2,3,4,5,6,7,1
    };
}
public class HidBootLoader extends HidDevice{

    private Rom m_rom = new Rom();
    private final int SIZE_SECTOR = 4096;
    private int m_n_cur_sector_index = 0;
    private int m_n_fw_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_name;

    public int get_fw_index(){
        return m_n_fw_index;
    }

    public boolean is_send_complete(){
        if( m_n_cur_sector_index >= HidBootLoaderSectorOrder.order.length)
            return true;
        else
            return false;
    }
    public int get_current_sector_index(){
        return m_n_cur_sector_index;
    }
    @Override
    public int get_vid() {
        return 0x134b;
    }

    @Override
    public int get_pid() {
        return 0x0243;
    }

    @Override
    public int get_interface_number() {
        return 0;
    }

    @Override
    public int get_in_report_size() {
        return 64;
    }

    @Override
    public int get_out_report_size() {
        return 64;
    }

    interface OutPacketOffset{
        int cCmd = 0;
        int sTag = 1;
        int wPara = 1;
        int dwPara = 1;
        int wChain = 6;
        int wLen = 8;
        int sData = 10;
    }
    interface OutPacketSize{
        int cCmd = 1;
        int sTag = 5;
        int wPara = 2;
        int dwPara = 4;
        int wChain = 2;
        int wLen = 2;
    }

    static class OutPacket{
        public final static int SizeHeader = 10;

        private byte[] m_s_raw_data;

        private void _allocate_raw_data( int n_min_size, byte[] s_data ){
            do{
                if( m_s_raw_data != null )
                    continue;
                //
                int n_data = 0;
                if( s_data != null )
                    n_data = s_data.length;

                int n_raw = n_min_size;
                if( n_min_size < (SizeHeader+n_data) ){
                    n_raw = (SizeHeader+n_data) / n_min_size;
                    if( ((SizeHeader+n_data) % n_min_size) != 0 )
                        n_raw++;

                    n_raw = n_min_size * n_raw;
                }

                m_s_raw_data = new byte[n_raw];

            }while(false);
        }

        public OutPacket( int n_min_size, byte c_cmd ){
            _allocate_raw_data( n_min_size, null );
            m_s_raw_data[OutPacketOffset.cCmd] = c_cmd;
        }

        public OutPacket( int n_min_size, byte c_cmd,byte[] s_tag, short w_chain, byte[] s_data ){
            _allocate_raw_data( n_min_size, s_data );
            //
            m_s_raw_data[OutPacketOffset.cCmd]  = c_cmd;
            if( s_tag != null ){
                if( s_tag.length <OutPacketSize.sTag )
                    System.arraycopy( s_data,0,m_s_raw_data,OutPacketOffset.sTag,s_tag.length);
                else
                    System.arraycopy( s_data,0,m_s_raw_data,OutPacketOffset.sTag,OutPacketSize.sTag);
            }

            System.arraycopy(
                    IntByteConvert.shortTobyte(w_chain, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.wChain
                    ,OutPacketSize.wChain);
            if( s_data != null ){
                System.arraycopy(
                        s_data
                        ,0
                        ,m_s_raw_data
                        ,OutPacketOffset.sData
                        ,s_data.length);
            }
        }

        public OutPacket( int n_min_size, byte c_cmd,short w_para, short w_chain, byte[] s_data ){
            _allocate_raw_data( n_min_size, s_data );
            //
            m_s_raw_data[OutPacketOffset.cCmd]  = c_cmd;

            System.arraycopy(
                    IntByteConvert.shortTobyte(w_para, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.wPara
                    ,OutPacketSize.wPara);

            System.arraycopy(
                    IntByteConvert.shortTobyte(w_chain, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.wChain
                    ,OutPacketSize.wChain);
            if( s_data != null ){
                System.arraycopy(
                        s_data
                        ,0
                        ,m_s_raw_data
                        ,OutPacketOffset.sData
                        ,s_data.length);
            }
        }

        public OutPacket( int n_min_size, byte c_cmd,int dw_para, short w_chain, byte[] s_data ){
            _allocate_raw_data( n_min_size, s_data );
            //
            m_s_raw_data[OutPacketOffset.cCmd]  = c_cmd;

            System.arraycopy(
                    IntByteConvert.intTobyte(dw_para, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.dwPara
                    ,OutPacketSize.dwPara);

            System.arraycopy(
                    IntByteConvert.shortTobyte(w_chain, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.wChain
                    ,OutPacketSize.wChain);
            if( s_data != null ){
                System.arraycopy(
                        s_data
                        ,0
                        ,m_s_raw_data
                        ,OutPacketOffset.sData
                        ,s_data.length);
            }
        }

        public OutPacket( int n_min_size, byte c_cmd,int dw_para, short w_len, short w_chain, byte[] s_data, int n_data ){
            _allocate_raw_data( n_min_size, s_data );
            //
            m_s_raw_data[OutPacketOffset.cCmd]  = c_cmd;

            System.arraycopy(
                    IntByteConvert.intTobyte(dw_para, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.dwPara
                    ,OutPacketSize.dwPara);

            System.arraycopy(
                    IntByteConvert.shortTobyte(w_len, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.wLen
                    ,OutPacketSize.wLen);

            System.arraycopy(
                    IntByteConvert.shortTobyte(w_chain, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,OutPacketOffset.wChain
                    ,OutPacketSize.wChain);
            if( s_data != null ){
                if(n_data <= s_data.length) {
                    System.arraycopy(
                            s_data
                            , 0
                            , m_s_raw_data
                            , OutPacketOffset.sData
                            , n_data);
                }
            }
        }

        public byte[] get_raw_packet(){
            return m_s_raw_data;
        }

        public byte get_cmd(){
            if( m_s_raw_data != null ){
                return m_s_raw_data[0];
            }
            else
                return HidBootLoaderRequest.cmdUnknown;
        }

        public byte[] get_data_field( int n_start_pos, int n_size ){
            byte[] s_data = null;

            do{
                if( m_s_raw_data == null )
                    continue;
                if( m_s_raw_data.length <= SizeHeader )
                    continue;
                int n_data = m_s_raw_data.length - SizeHeader;
                if( n_data <= n_start_pos )
                    continue;
                //
                int n_start_offset = SizeHeader + n_start_pos;
                int n_copy = n_size;
                if( (n_start_offset+n_size) >= m_s_raw_data.length ){
                    n_copy = m_s_raw_data.length - n_start_offset;
                }

                s_data = new byte[n_copy];
                System.arraycopy(m_s_raw_data,n_start_offset,s_data,0,s_data.length);
            }while(false);
            return s_data;
        }

        public short get_wpara(){
            short w_para = 0;

            do{
                if( m_s_raw_data == null )
                    continue;
                //
                byte[] s_wpara = new byte[2];
                System.arraycopy(m_s_raw_data,OutPacketOffset.wPara,s_wpara,0,s_wpara.length);
                w_para = IntByteConvert.byteToShort(s_wpara,ByteOrder.LITTLE_ENDIAN);
            }while(false);
            return w_para;
        }

        public int get_dwpara(){
            int dw_para = 0;

            do{
                if( m_s_raw_data == null )
                    continue;
                //
                byte[] s_dwpara = new byte[4];
                System.arraycopy(m_s_raw_data,OutPacketOffset.dwPara,s_dwpara,0,s_dwpara.length);
                dw_para = IntByteConvert.byteToInt(s_dwpara,ByteOrder.LITTLE_ENDIAN);
            }while(false);
            return dw_para;
        }

        public short get_chain(){
            short w_chain = 0;
            do{
                if( m_s_raw_data == null )
                    continue;
                //
                byte[] s_wchain = new byte[2];
                System.arraycopy(m_s_raw_data,OutPacketOffset.wChain,s_wchain,0,s_wchain.length);
                w_chain = IntByteConvert.byteToShort(s_wchain,ByteOrder.LITTLE_ENDIAN);
            }while(false);
            return w_chain;
        }
    }

    interface InPacketOffset{
        int cReplay = 0;
        int cResult = 1;
        int sTag = 2;
        int wChain = 6;
        int wLen = 8;
        int sData = 10;
    }
    interface InPacketSize{
        int cReplay = 1;
        int cResult = 1;
        int sTag = 4;
        int wChain = 2;
        int wLen = 2;
    }

    static class InPacket{
        public final static int SizeHeader = 10;

        private byte[] m_s_raw_data = null;

        private void _allocate_raw_data( int n_min_size, byte[] s_data ){
            do{
                if( m_s_raw_data != null )
                    continue;
                //
                int n_data = 0;
                if( s_data != null )
                    n_data = s_data.length;

                int n_raw = n_min_size;
                if( n_min_size < (SizeHeader+n_data) ){
                    n_raw = (SizeHeader+n_data) / n_min_size;
                    if( ((SizeHeader+n_data) % n_min_size) != 0 )
                        n_raw++;

                    n_raw = n_min_size * n_raw;
                }

                m_s_raw_data = new byte[n_raw];

            }while(false);
        }

        public InPacket(int n_min_size){
            _allocate_raw_data( n_min_size, null );
        }
        public InPacket( int n_min_size, byte c_replay ){
            _allocate_raw_data( n_min_size, null );
            m_s_raw_data[InPacketOffset.cReplay] = c_replay;
        }
        public InPacket( int n_min_size, byte c_replay, byte c_result ){
            this( n_min_size, c_replay );
            m_s_raw_data[InPacketOffset.cResult] = c_result;
        }
        public InPacket( int n_min_size, byte c_replay, byte c_result, byte[] s_tag ){
            this( n_min_size, c_replay, c_result );
            if( s_tag != null ){
                int n_tag = InPacketSize.sTag;
                if( s_tag.length < n_tag )
                    n_tag = s_tag.length;
                System.arraycopy(s_tag,0,m_s_raw_data,InPacketOffset.sTag,n_tag);
            }
        }
        public InPacket( int n_min_size, byte c_replay, byte c_result, byte[] s_tag, short w_chain, byte[] s_data ){
            short w_len = 0;
            if( s_data != null ){
                w_len = (short)s_data.length;
            }
            _allocate_raw_data( n_min_size, s_data );
            m_s_raw_data[InPacketOffset.cReplay] = c_replay;
            m_s_raw_data[InPacketOffset.cResult] = c_result;
            if( s_tag != null ){
                int n_tag = InPacketSize.sTag;
                if( s_tag.length < n_tag )
                    n_tag = s_tag.length;
                System.arraycopy(s_tag,0,m_s_raw_data,InPacketOffset.sTag,n_tag);
            }
            System.arraycopy(
                    IntByteConvert.shortTobyte(w_chain, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,InPacketOffset.wChain
                    ,InPacketSize.wChain);
            System.arraycopy(
                    IntByteConvert.shortTobyte(w_len, ByteOrder.LITTLE_ENDIAN)
                    ,0
                    ,m_s_raw_data
                    ,InPacketOffset.wLen
                    ,InPacketSize.wLen);
            if( s_data != null ){
                System.arraycopy(s_data,0,m_s_raw_data,InPacketOffset.sData,s_data.length);
            }
        }

        public byte[] get_raw_packet(){
            return m_s_raw_data;
        }

        public short get_chain(){
            short w_chain = 0;
            do{
                if( m_s_raw_data == null )
                    continue;
                //
                byte[] s_wchain = new byte[2];
                System.arraycopy(m_s_raw_data,InPacketOffset.wChain,s_wchain,0,s_wchain.length);
                w_chain = IntByteConvert.byteToShort(s_wchain,ByteOrder.LITTLE_ENDIAN);
            }while(false);
            return w_chain;
        }

        public byte[] get_data_field( int n_start_pos, int n_size ){
            byte[] s_data = null;

            do{
                if( m_s_raw_data == null )
                    continue;
                if( m_s_raw_data.length <= SizeHeader )
                    continue;
                int n_data = m_s_raw_data.length - SizeHeader;
                if( n_data <= n_start_pos )
                    continue;
                //
                int n_start_offset = SizeHeader + n_start_pos;
                int n_copy = n_size;
                if( (n_start_offset+n_size) >= m_s_raw_data.length ){
                    n_copy = m_s_raw_data.length - n_start_offset;
                }

                s_data = new byte[n_copy];
                System.arraycopy(m_s_raw_data,n_start_offset,s_data,0,s_data.length);
            }while(false);
            return s_data;
        }

        public boolean is_all_zeros(){
            boolean b_result = false;
            do{
                if( m_s_raw_data == null )
                    continue;

                b_result = true;

                for( byte c : m_s_raw_data ){
                    if( c != 0 ){
                        b_result = false;
                        break;
                    }
                }//end for

            }while(false);
            return b_result;
        }

        public boolean is_success(){
            boolean b_result = false;

            do{
                if( m_s_raw_data == null )
                    continue;
                if( m_s_raw_data[InPacketOffset.cResult] != HidBootLoaderResponse.resultSuccess)
                    continue;

                b_result = true;
            }while(false);
            return b_result;
        }
    }//the end of InPacket class

    interface HidBootStatus{
        int StatusIdle = 10;
        int StatusOutReport = 20;
        int StatusJob = 30;
        int StatusInReport = 40;
    }

    static class HidBootIo{
        private int m_n_status = HidBootStatus.StatusIdle;
        private OutPacket m_out;
        private short m_w_offset; //the current buffer's offset.
        //

    }

    private HidBootLoader(){
        super();
    }
    public  HidBootLoader(UsbManager usbManager, UsbDevice usbDevice){
        super(usbManager,usbDevice);
    }

    public boolean df_run_app(){
        boolean b_result = false;
        boolean b_close = false;

        do{
            if( !this.open() )
                continue;
            b_close = true;

            OutPacket out = new OutPacket(this.get_out_report_size(),HidBootLoaderRequest.cmdRunApp);

            int n_offset = 0;
            byte[] s_tx = out.get_raw_packet();
            byte[] s_packet = new byte[this.get_out_report_size()];
            int n_loop = s_tx.length / this.get_out_report_size();

            int n_tx = 0;
            b_result = true;

            for( int i =0 ; i< n_loop; i++ ){
                System.arraycopy(s_tx,n_offset,s_packet,0,s_packet.length);
                n_tx = this.write(s_packet);
                if( n_tx != s_packet.length ) {
                    b_result = false;
                    break;
                }
            }//end for

        }while(false);
        if( b_close ){
            this.close();
        }

        return b_result;
    }

    private int _read_with_passing_zeros_response( InPacket in_p ){
        int n_rx = 0;
        int n_retry = 5;

        do {
            n_rx = read(in_p.get_raw_packet());
            if( n_rx == 0 )
                continue;
            if( n_rx != get_in_report_size() ){
                break;
            }
            if( !in_p.is_all_zeros() )
                break;
            n_retry--;
            if( n_retry <= 0 ){
                n_rx = 0;
                Log.d("error","over retry counter");
                break;
            }
        }while (true);

        return n_rx;
    }

    public boolean df_write_one_sector(Context context, AtomicBoolean b_parent_run){
        boolean b_result = false;
        boolean b_close = false;

        do{
            if( m_n_fw_index < 0 )
                continue;
            if( m_n_cur_sector_index >= HidBootLoaderSectorOrder.order.length )
                continue;

            int n_sector = HidBootLoaderSectorOrder.order[m_n_cur_sector_index];
            byte[] s_data = new byte[get_out_report_size()-OutPacket.SizeHeader];
            byte[] s_data_last = new byte[SIZE_SECTOR%s_data.length];
            byte[] ps_data = null;

            int n_read = 0;
            b_result = false;
            short w_chain = 0;
            int n_tx = 0;
            int n_rx = 0;
            boolean b_run = true;
            int n_remainder = SIZE_SECTOR;
            int n_offset_fw_data = (n_sector-1)*SIZE_SECTOR;

            if( !this.open() ){
                continue;
            }
            b_close = true;

            if( !b_parent_run.get())
                continue;//break process

            do {
                if( n_remainder > s_data_last.length )
                    ps_data = s_data;
                else
                    ps_data = s_data_last;
                //
                Arrays.fill(ps_data,(byte)0xff);//clear read buffer.
                //read firmware data.
                n_read = m_rom.read_binary_of_firmware(ps_data, n_offset_fw_data);
                if( n_read < 0 ) {
                    break;//exit while with error
                }
                if( n_read == 0 ){
                    b_result = true;
                    b_run = false;
                    m_n_cur_sector_index++;
                    continue;//exit while
                }

                n_offset_fw_data += n_read;

                // build spilt packet.
                OutPacket out_p = new OutPacket(
                        this.get_out_report_size()
                        ,HidBootLoaderRequest.cmdWrite
                        ,n_sector
                        ,(short)SIZE_SECTOR
                        ,w_chain
                        ,ps_data
                        ,ps_data.length);

                //send data.
                /*
                Log.i("df_write_one_sector"
                        ,"secter="+String.valueOf(n_sector)
                        + ", chain = "+String.valueOf((int)w_chain)
                        + ", data size = "+String.valueOf(n_read)
                        + ", tx size = "+String.valueOf(ps_data.length)
                );
                */
                if( context != null){
                    //send intent to updateActivitiy.
                    Intent intent = new Intent(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO);
                    intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR, n_sector);
                    intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR_CHAIN, w_chain);
                    context.sendBroadcast(intent);
                }

                w_chain++;

                n_tx = this.write(out_p.get_raw_packet());
                if( n_tx != get_out_report_size() ){
                    break;//exit while with error
                }

                n_remainder -= ps_data.length;
                if( n_remainder <= 0 || n_read < s_data.length ){
                    b_result = true;
                    b_run = false;
                    m_n_cur_sector_index++;
                }
                if( !b_parent_run.get()) {
                    b_result = false;
                    break;//break process
                }

            }while( b_run );

            if( !b_result  )
                continue;


            while( n_remainder > 0){
                //send 0xFF(zeros)
                n_read = s_data.length;
                if( n_remainder < n_read ) {
                    n_read = n_remainder;
                    ps_data = new byte[n_read];
                }
                else{
                    ps_data = s_data;
                }
                Arrays.fill(ps_data,(byte)0xff);//clear read buffer.

                // build spilt packet.
                OutPacket out_p = new OutPacket(
                        this.get_out_report_size()
                        ,HidBootLoaderRequest.cmdWrite
                        ,n_sector
                        ,(short)SIZE_SECTOR
                        ,w_chain
                        ,ps_data
                        ,n_read);

                //send data.
                /*
                Log.i("df_write_one_sector"
                        ,"FF : secter="+String.valueOf(n_sector)
                                + ", chain = "+String.valueOf((int)w_chain)
                                + ", data size = "+String.valueOf(n_read)
                                + ", tx size = "+String.valueOf(ps_data.length)
                );
                */
                if( context != null){
                    //send intent to updateActivitiy.
                    Intent intent = new Intent(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO);
                    intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR, n_sector);
                    intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR_CHAIN, w_chain);
                    context.sendBroadcast(intent);
                }

                w_chain++;
                //send data.
                n_tx = this.write(out_p.get_raw_packet());
                if( n_tx != get_out_report_size() ){
                    b_result = false;
                    break;//error
                }

                n_remainder -= n_read;

                if( !b_parent_run.get() ){
                    b_result = false;
                    break;
                }
            }//end while

            if( !b_result )
                continue;
            //get response.
            InPacket in_p = new InPacket(this.get_in_report_size());
            n_rx = _read_with_passing_zeros_response( in_p );
            if( n_rx != get_in_report_size() ){
                b_result = false;
                break;//error
            }
            if( !in_p.is_success() ) {
                b_result = false;
                break;//error
            }

        }while (false);

        if( b_close ){
            this.close();
        }
        return b_result;
    }

    public boolean df_erase_all(){
        boolean b_result = false;
        boolean b_close = false;

        do{
            OutPacket out_p = new OutPacket(get_out_report_size(), HidBootLoaderRequest.cmdErase, (int)1,(short)0,null);
            int n_tx = 0;
            int n_rx = 0;

            if( !this.open() )
                continue;

            b_close = true;

            n_tx = this.write(out_p.get_raw_packet());
            if( n_tx != get_out_report_size() ){
                continue;
            }
            //get response.
            InPacket in_p = new InPacket(this.get_in_report_size());
            n_rx = _read_with_passing_zeros_response( in_p );
            if( n_rx != get_in_report_size() ){
                continue;//error
            }

            if( !in_p.is_success() )
                continue;//error

            //
            b_result = true;
        }while(false);

        if( b_close ){
            this.close();
        }
        return b_result;
    }
    public boolean set_rom_file(File rom_file,String s_dev_name, FwVersion dev_version ){
        boolean b_result = false;

        do{
            if( rom_file == null )
                continue;
            if( s_dev_name == null )
                continue;
            if( dev_version == null )
                continue;
            if( m_rom.load_rom_header(rom_file) != RomResult.result_success )
                continue;

            m_n_fw_index = m_rom.set_updatable_firmware_index(s_dev_name,dev_version);
            if( m_n_fw_index < 0 )
                continue;
            //
            if( m_rom.set_firmware(m_n_fw_index) != RomResult.result_success )
                continue;
            //
            m_n_cur_sector_index = 0;
            b_result = true;
        }while(false);
        return b_result;
    }
    public boolean set_rom_file(File rom_file,int n_index_fw ){
        boolean b_result = false;

        do{
            if( rom_file == null )
                continue;
            if( m_rom.load_rom_header(rom_file) != RomResult.result_success )
                continue;
            if( n_index_fw < 0)
                continue;
            if( n_index_fw >= m_rom.get_the_number_of_firmware() )
                continue;

            m_n_fw_index = n_index_fw;
            //
            if( m_rom.set_firmware(m_n_fw_index) != RomResult.result_success )
                continue;
            //
            m_n_cur_sector_index = 0;
            b_result = true;
        }while(false);
        return b_result;
    }


}
