package kr.pe.sheep_transform.lpu237_adr.lib.hidboot;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.File;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import kr.pe.sheep_transform.lpu237_adr.lib.hid.HidDevice;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.Rom;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.RomErrorCodeFirmwareIndex;
import kr.pe.sheep_transform.lpu237_adr.lib.rom.RomResult;
import kr.pe.sheep_transform.lpu237_adr.lib.util.FwVersion;
import kr.pe.sheep_transform.lpu237_adr.lib.util.IntByteConvert;
import kr.pe.sheep_transform.lpu237_adr.lib.util.SectorOrder;


public class HidBootLoader extends HidDevice {

    private HidBootCallback m_cb = null;
    private Rom m_rom = new Rom();
    private final int SIZE_SECTOR = 4096;
    private int m_n_fw_index = RomErrorCodeFirmwareIndex.error_firmware_index_none_matched_name;

    private boolean m_b_exist_sector_info = false;

    private SectorOrder m_order_erase = new SectorOrder();
    private SectorOrder m_order_write = new SectorOrder();
    public void set_all_order(int n_start_sector, int n_the_number_of_sector){
        m_order_erase.set_order(n_start_sector,n_the_number_of_sector);
        m_order_write.set_order(n_start_sector,n_the_number_of_sector);
    }
    public void set_order_for_lpc1343(){
        m_order_erase.initialize_for_lpc1343_erase();
        m_order_write.initialize_for_lpc1343_write();
    }

    public boolean set_adjust_order_for_reducing_sector(int n_firmware_size)
    {
        boolean b_result = false;
        int n_reduced_sec = n_firmware_size/SIZE_SECTOR;

        if(n_firmware_size%SIZE_SECTOR != 0){
            ++n_reduced_sec;
        }

        ////////////////////////////
        // adjust erase sectors
        do {
            int[] er = m_order_erase.get_order();
            if(er == null){
                continue;
            }
            if(er.length<=0){
                continue;
            }

            if(m_order_erase.get_the_number_of_sectors()<n_reduced_sec){
                continue;
            }
            b_result = true;
            if(m_order_erase.get_the_number_of_sectors() == n_reduced_sec){
                continue;//no need reduce.
            }

            int[] r = new int[n_reduced_sec+1];
            System.arraycopy(er,0,r,0,n_reduced_sec);
            r[n_reduced_sec] = er[er.length-1];//the last sector is system parameter erase. therefore must be erased.
            m_order_erase.set_order(r);
        }while (false);

        if(!b_result){
            return b_result;//error
        }

        b_result = false;

        ////////////////////////////
        // adjust write sectors
        do {
            if(m_order_write.get_the_number_of_sectors()<n_reduced_sec){
                continue;
            }
            b_result = true;
            if(m_order_write.get_the_number_of_sectors() > n_reduced_sec){
                //reduce.
                m_order_write.resize(n_reduced_sec);
            }

            int[] wr = m_order_write.get_order();
            int[] r = new int[wr.length];
            System.arraycopy(wr,1,r,0,wr.length-1);
            r[r.length-1] = wr[0];//the first sector must be written at the last.
            m_order_write.set_order(r);
        }while (false);

        return b_result;
    }

    public boolean is_exist_sector_info(){
        return m_b_exist_sector_info;
    }

    public int get_fw_index(){
        return m_n_fw_index;
    }

    public boolean is_write_complete(){
        return m_order_write.is_compete();
    }
    public int get_current_write_sector(){
        return m_order_write.get_current_sector_number();
    }
    public boolean is_erase_complete(){
        return m_order_erase.is_compete();
    }
    public int get_current_erase_sector(){
        return m_order_erase.get_current_sector_number();
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
        m_cb = null;
    }
    public  HidBootLoader(UsbManager usbManager, UsbDevice usbDevice,HidBootCallback cb){
        super(usbManager,usbDevice);
        m_cb = cb;
    }

    public boolean df_run_app(){
        boolean b_result = false;
        boolean b_close = false;

        do{
            if( !this.HidOpen() )
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
                n_tx = this.HidWrite(s_packet);
                if( n_tx != s_packet.length ) {
                    b_result = false;
                    break;
                }
            }//end for

        }while(false);
        if( b_close ){
            this.HidClose();
        }

        return b_result;
    }

    private int _read_with_passing_zeros_response( InPacket in_p ){
        int n_rx = 0;
        int n_retry = 5;

        do {
            n_rx = HidRead(in_p.get_raw_packet());
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
            if(m_order_write.is_compete()){
                continue;
            }

            int n_sector = m_order_write.get_current_sector_number();
            if(n_sector<0){
                continue;
            }
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
            int n_offset_fw_data = (m_order_write.get_relative_currnet_sector_number_from_min_sector())*SIZE_SECTOR;

            if( !this.HidOpen() ){
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
                    m_order_write.increased_index();
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
                if(m_cb!=null){
                    if(!m_cb.cbWriteSectorBeforeDo(context,w_chain,n_sector,m_order_write.get_the_number_of_sectors())){
                        b_result = false;
                        break;//exit while by user
                    }
                }

                w_chain++;

                n_tx = this.HidWrite(out_p.get_raw_packet());
                if(m_cb!=null){
                    boolean b_tx = true;
                    if( n_tx != get_out_report_size() ){
                        b_tx = false;//error
                    }
                    if(!m_cb.cbWriteSectorAfterDone(context,b_tx,w_chain,n_sector,m_order_write.get_the_number_of_sectors())){
                        b_result = false;
                        break;//exit while by user
                    }
                }
                if( n_tx != get_out_report_size() ){
                    break;//exit while with error
                }

                n_remainder -= ps_data.length;
                if( n_remainder <= 0 || n_read < s_data.length ){
                    b_result = true;
                    b_run = false;
                    m_order_write.increased_index();
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
                if(m_cb!=null){
                    if(!m_cb.cbWriteSectorBeforeDo(context,w_chain,n_sector,m_order_write.get_the_number_of_sectors())){
                        b_result = false;
                        break;//exit while by user
                    }
                }

                w_chain++;
                //send data.
                n_tx = this.HidWrite(out_p.get_raw_packet());
                if(m_cb!=null){
                    boolean b_tx = true;
                    if( n_tx != get_out_report_size() ){
                        b_tx = false;//error
                    }
                    if(!m_cb.cbWriteSectorAfterDone(context,b_tx,w_chain,n_sector,m_order_write.get_the_number_of_sectors())){
                        b_result = false;
                        break;//exit while by user
                    }
                }
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
            this.HidClose();
        }
        return b_result;
    }

    public boolean df_get_sector_info(){
        boolean b_result = false;
        boolean b_close = false;

        m_b_exist_sector_info = false;
        set_order_for_lpc1343();

        do{
            OutPacket out_p = new OutPacket(get_out_report_size(), HidBootLoaderRequest.cmdGetSectorInfo, (int)0,(short)0,null);
            int n_tx = 0;
            int n_rx = 0;

            if( !this.HidOpen() )
                continue;

            b_close = true;

            n_tx = this.HidWrite(out_p.get_raw_packet());
            if( n_tx != get_out_report_size() ){
                continue;
            }
            //get response.
            InPacket in_p = new InPacket(this.get_in_report_size());
            n_rx = _read_with_passing_zeros_response( in_p );
            if( n_rx != get_in_report_size() ){
                continue;//error
            }

            b_result = true;

            if( in_p.is_success() ) {
                m_b_exist_sector_info = true;

                byte[] s_ss = in_p.get_data_field(0,4);
                byte[] s_sa = in_p.get_data_field(4,4);
                int n_start = IntByteConvert.byteToInt(s_ss,ByteOrder.LITTLE_ENDIAN);
                int n_size = IntByteConvert.byteToInt(s_sa,ByteOrder.LITTLE_ENDIAN);
                set_all_order(n_start,n_size);
                set_adjust_order_for_reducing_sector(m_rom.get_firmware_size());
            }
            else{
                //may be not support device(LPC1343)
                set_order_for_lpc1343();
            }
        }while(false);

        if( b_close ){
            this.HidClose();
        }
        return b_result;
    }
    public boolean df_erase_one_sector(Context context, AtomicBoolean b_parent_run){
        boolean b_result = false;

        do{
            if(m_order_erase.is_compete()){
                continue;
            }
            int n_sector = m_order_erase.get_current_sector_number();
            if(n_sector<0){
                continue;
            }

            b_result = _df_erase_sector(n_sector);//For LPC1343 chip

            if(m_cb != null){
                if( !m_cb.cbEraseSectorAfterDone(context,b_result,n_sector,m_order_erase.get_the_number_of_sectors()) ){
                    b_result = false;//break by user.
                }
            }

        }while (false);

        if(b_result){
            m_order_erase.increased_index();
        }
        return b_result;
    }

    private boolean _df_erase_sector(int n_erase_sector_number){
        boolean b_result = false;
        boolean b_close = false;

        do{
            OutPacket out_p = new OutPacket(get_out_report_size(), HidBootLoaderRequest.cmdErase, (int)n_erase_sector_number,(short)0,null);
            int n_tx = 0;
            int n_rx = 0;

            if( !this.HidOpen() )
                continue;

            b_close = true;

            n_tx = this.HidWrite(out_p.get_raw_packet());
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
            this.HidClose();
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
            b_result = true;
        }while(false);
        return b_result;
    }


}
