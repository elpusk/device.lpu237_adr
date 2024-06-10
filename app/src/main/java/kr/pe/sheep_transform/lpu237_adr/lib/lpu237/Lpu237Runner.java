package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import kr.pe.sheep_transform.lpu237_adr.lib.ApiInterface;
import kr.pe.sheep_transform.lpu237_adr.lib.ApiLpu237;
import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237Getting.Step;

public class Lpu237Runner extends Lpu237 implements Runnable, AutoCloseable {
    public enum Do{
        NOTHING,
        READING,
        GETTING,
        SETTING
    }
    private enum QRequest{
        Q_REQUEST_READ
    }

    private Do m_doing = Do.NOTHING;
    /**
     * determines call callback when msr data is received.
     */
    private boolean m_b_enable_read_msr = false;

    /**
     * determines call callback when ibutton data is received.
     */
    private boolean m_b_enable_read_ibutton = false;

    private AtomicBoolean m_working;
    BlockingQueue<Object> m_queue = new LinkedBlockingQueue<>();
    private ExecutorService m_executorService;
    private Future<?> m_futureTask;
    @Override
    public void run() {
        Lpu237Callback cb = null;
        Lpu237GetSetCallback cb_gs = null;
        Lpu237DoneCallback cb_done = null;
        Object obj = null;
        Lpu237Getting GetInfo = new Lpu237Getting();
        Lpu237Setting SetInfo = new Lpu237Setting();

        while(m_working.get()){

            try {
                _SetDoing(Do.NOTHING);
                cb = null;
                cb_gs = null;
                cb_done = null;

                obj = m_queue.take();//cancelable
                if(obj == null){
                    continue;
                }
                if(obj instanceof Lpu237Callback) {
                    _SetDoing(Do.READING);
                    cb = (Lpu237Callback) obj;

                    //enabled read here.
                    byte[] s_rx = this.HidRead();//cancelable
                    List<Object> allItems = new ArrayList<>();
                    m_queue.drainTo(allItems);
                    allItems.add(0, obj);
                    Lpu237Callback.Result rx_result = Lpu237Callback.Result.RESULT_ERROR;
                    if (s_rx == null) {
                        //error
                        _run_callback(allItems, Lpu237Callback.Result.RESULT_ERROR, new byte[0],null,null);
                    } else {
                        _run_callback(allItems, Lpu237Callback.Result.RESULT_SUCCESS, s_rx,null,null);
                    }
                    continue;
                }

                if(obj instanceof Lpu237GetSetCallback) {
                    cb_gs = (Lpu237GetSetCallback) obj;
                    if(cb_gs.is_getting_type()){
                        //getting system parameters
                        _SetDoing(Do.GETTING);
                        GetInfo.Reset();
                        do {
                            GetInfo = _run_getting(GetInfo);
                            if(GetInfo.IsCurResultSuccess()){
                                if( !_run_callback(cb_gs, Lpu237Callback.Result.RESULT_SUCCESS, GetInfo)){
                                    break;//exit while by user
                                }
                                GetInfo.SetCurStep(GetInfo.GetCurStep()+1);
                            }
                            else{
                                _run_callback(cb_gs, Lpu237Callback.Result.RESULT_ERROR, GetInfo);
                            }
                        }while (!GetInfo.IsComplete());
                        continue;
                    }
                    //setting system parameters
                    _SetDoing(Do.SETTING);
                    SetInfo.Reset();
                    do {
                        SetInfo = _run_setting(SetInfo);
                        if(SetInfo.IsCurResultSuccess()){
                            if(!_run_callback(cb_gs, Lpu237Callback.Result.RESULT_SUCCESS, SetInfo)){
                                break;//exit while by user
                            }
                            SetInfo.SetCurStep(SetInfo.GetCurStep()+1);
                        }
                        else{
                            _run_callback(cb_gs, Lpu237Callback.Result.RESULT_ERROR, SetInfo);
                        }
                    }while (!GetInfo.IsComplete());
                }

                if( obj instanceof Lpu237DoneCallback){
                    cb_done = (Lpu237DoneCallback) obj;
                    if( this.df_apply() ){
                        cb_done.Run(Lpu237Callback.Result.RESULT_SUCCESS,"Apply");
                    }
                    else{
                        cb_done.Run(Lpu237Callback.Result.RESULT_ERROR,"Apply");
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//cancel

                if(m_working.get()){
                    if(cb != null) {
                        _run_callback(cb,Lpu237Callback.Result.RESULT_CANCEL,new byte[0]);
                    }
                    if(cb_gs != null){
                        if(cb_gs.is_getting_type()) {
                            _run_callback(cb_gs, Lpu237Callback.Result.RESULT_CANCEL, GetInfo);
                        }
                        else{
                            _run_callback(cb_gs, Lpu237Callback.Result.RESULT_CANCEL, SetInfo);
                        }
                    }
                    if(cb_done != null){
                        cb_done.Run(Lpu237Callback.Result.RESULT_CANCEL,"Apply");
                    }
                }
                else{
                    List<Object> allItems = new ArrayList<>();
                    m_queue.drainTo(allItems);//cancel by kill. all cancel
                    _run_callback(
                            allItems,
                            Lpu237Callback.Result.RESULT_CANCEL,
                            new byte[0],
                            GetInfo,
                            SetInfo
                    );
                    _SetDoing(Do.NOTHING);
                }
            }
        }//

    }

    private Lpu237Getting _run_getting( Lpu237Getting Getting){
        String s_n =this.getName();
        String s_v =this.getVersionSystem();
        boolean b_result = true;

        switch (Getting.GetCurStep()){
            case Lpu237Getting.Step.ENTER_CONFIG:
                b_result = df_enter_config();
                break;
            case Lpu237Getting.Step.DECODER_MMD1100:
                b_result = df_get_decoder_mmd1000();
                break;
            case Lpu237Getting.Step.NAME:
                b_result = df_get_name();
                break;
            case Lpu237Getting.Step.VERSION_SYSTEM:
                b_result = df_get_version_system();
                break;
            case Lpu237Getting.Step.VERSION_STRUCTURE:
                b_result = df_get_version_structure();
                break;
            case Lpu237Getting.Step.BLANKS:
                b_result = df_get_blanks();
                break;
            case Lpu237Getting.Step.TYPE:
                b_result = df_get_type();
                break;
            case Lpu237Getting.Step.IBUTTON_ONLY_TYPE:
                b_result = df_get_ibutton_only_type();
                break;
            case Lpu237Getting.Step.UID:
                b_result = df_get_uid();
                break;
            case Lpu237Getting.Step.ENABLE_TRACK1:
                b_result = df_get_enable_tracK(0);
                break;  //enable track1
            case Lpu237Getting.Step.ENABLE_TRACK2:
                b_result = df_get_enable_tracK(1);
                break;  //enable track2
            case Lpu237Getting.Step.ENABLE_TRACK3:
                b_result = df_get_enable_tracK(2);
                break;  //enable track3
            case Lpu237Getting.Step.INTERFACE:
                b_result = df_get_interface();
                break;
            case Lpu237Getting.Step.LANGUAGE_INDEX:
                b_result = df_get_language_index();
                break;
            case Lpu237Getting.Step.BUZZER_FRQ:
                b_result = df_get_buzzer_frequency();
                break;
            case Lpu237Getting.Step.BOOT_RUN_TIME:
                b_result = df_get_boot_run_time();
                break;
            case Lpu237Getting.Step.MSR_G_PREFIX:
                b_result = df_get_global_prefix();
                break;
            case Lpu237Getting.Step.MSR_G_POSTFIX:
                b_result = df_get_global_postfix();
                break;
            case Lpu237Getting.Step.MSR_PRIVATE_PREFIX1:
                b_result = df_get_private_prefix(0);
                break;
            case Lpu237Getting.Step.MSR_PRIVATE_POSTFIX1:
                b_result = df_get_private_postfix(0);
                break;
            case Lpu237Getting.Step.MSR_PRIVATE_PREFIX2:
                b_result = df_get_private_prefix(1);
                break;
            case Lpu237Getting.Step.MSR_PRIVATE_POSTFIX2:
                b_result = df_get_private_postfix(1);
                break;
            case Lpu237Getting.Step.MSR_PRIVATE_PREFIX3:
                b_result = df_get_private_prefix(2);
                break;
            case Lpu237Getting.Step.MSR_PRIVATE_POSTFIX3:
                b_result = df_get_private_postfix(2);
                break;
            case Lpu237Getting.Step.IB_TAG_PREFIX:
                b_result = df_get_ibutton_tag_prefix();
                break;
            case Lpu237Getting.Step.IB_TAG_POSTFIX:
                b_result = df_get_ibutton_tag_postfix();
                break;
            case Lpu237Getting.Step.IB_REMOVE:
                if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ){
                    b_result = df_get_ibutton_remove();
                }
                break;
            case Lpu237Getting.Step.IB_RTAG_PREFIX:
                if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ){
                    b_result = df_get_ibutton_remove_tag_prefix();
                }
                break;
            case Lpu237Getting.Step.IB_RTAG_POSTFIX:
                if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ){
                    b_result = df_get_ibutton_remove_tag_postfix();
                }
                break;
            case Lpu237Getting.Step.UART_PREFIX:
                b_result = df_get_uart_prefix();
                break;
            case Lpu237Getting.Step.UART_POSTFIX:
                b_result = df_get_uart_postfix();
                break;
            case Lpu237Getting.Step.GLOBAL_SEND_CONDITION:
                if( Lpu237Tools.is_support_msr_global_tag_send_condition(s_n,s_v) ) {
                    b_result = df_get_global_send_condition();
                }
                break;
            case Lpu237Getting.Step.READ_DIRECTION:
                b_result = df_get_reading_direction();
                break;
            case Lpu237Getting.Step.TRACK_ORDER:
                if( Lpu237Tools.is_support_msr_send_order(s_n,s_v) ) {
                    b_result = df_get_track_order();
                }
                break;
            case Lpu237Getting.Step.LEAVE_CONFIG:
                b_result = df_leave_config();
                break;
            default:
                break;            
        }//end switch

        if(!b_result){
            df_leave_config();
        }
        Getting.SetCurResult(b_result);
        return Getting;
    }

    private Lpu237Setting _run_setting( Lpu237Setting Setting){
        String s_n =this.getName();
        String s_v =this.getVersionSystem();
        boolean b_result = true;

        switch (Setting.GetCurStep()){
            case Lpu237Setting.Step.ENTER_CONFIG:
                b_result = df_enter_config();
                break;
            case Lpu237Setting.Step.BLANKS:
                b_result = df_get_blanks();
                break;
            case Lpu237Setting.Step.ENABLE_TRACK1:
                b_result = df_get_enable_tracK(0);
                break;  //enable track1
            case Lpu237Setting.Step.ENABLE_TRACK2:
                b_result = df_get_enable_tracK(1);
                break;  //enable track2
            case Lpu237Setting.Step.ENABLE_TRACK3:
                b_result = df_get_enable_tracK(2);
                break;  //enable track3
            case Lpu237Setting.Step.INTERFACE:
                b_result = df_get_interface();
                break;
            case Lpu237Setting.Step.LANGUAGE_INDEX:
                b_result = df_get_language_index();
                break;
            case Lpu237Setting.Step.BUZZER_FRQ:
                b_result = df_get_buzzer_frequency();
                break;
            case Lpu237Setting.Step.MSR_G_PREFIX:
                b_result = df_get_global_prefix();
                break;
            case Lpu237Setting.Step.MSR_G_POSTFIX:
                b_result = df_get_global_postfix();
                break;
            case Lpu237Setting.Step.MSR_PRIVATE_PREFIX1:
                b_result = df_get_private_prefix(0);
                break;
            case Lpu237Setting.Step.MSR_PRIVATE_POSTFIX1:
                b_result = df_get_private_postfix(0);
                break;
            case Lpu237Setting.Step.MSR_PRIVATE_PREFIX2:
                b_result = df_get_private_prefix(1);
                break;
            case Lpu237Setting.Step.MSR_PRIVATE_POSTFIX2:
                b_result = df_get_private_postfix(1);
                break;
            case Lpu237Setting.Step.MSR_PRIVATE_PREFIX3:
                b_result = df_get_private_prefix(2);
                break;
            case Lpu237Setting.Step.MSR_PRIVATE_POSTFIX3:
                b_result = df_get_private_postfix(2);
                break;
            case Lpu237Setting.Step.IB_TAG_PREFIX:
                b_result = df_get_ibutton_tag_prefix();
                break;
            case Lpu237Setting.Step.IB_TAG_POSTFIX:
                b_result = df_get_ibutton_tag_postfix();
                break;
            case Lpu237Setting.Step.IB_REMOVE:
                if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ){
                    b_result = df_get_ibutton_remove();
                }
                break;
            case Lpu237Setting.Step.IB_RTAG_PREFIX:
                if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ){
                    b_result = df_get_ibutton_remove_tag_prefix();
                }
                break;
            case Lpu237Setting.Step.IB_RTAG_POSTFIX:
                if( Lpu237Tools.is_support_ibutton_remove(s_n,s_v) ){
                    b_result = df_get_ibutton_remove_tag_postfix();
                }
                break;
            case Lpu237Setting.Step.UART_PREFIX:
                b_result = df_get_uart_prefix();
                break;
            case Lpu237Setting.Step.UART_POSTFIX:
                b_result = df_get_uart_postfix();
                break;
            case Lpu237Setting.Step.GLOBAL_SEND_CONDITION:
                if( Lpu237Tools.is_support_msr_global_tag_send_condition(s_n,s_v) ) {
                    b_result = df_get_global_send_condition();
                }
                break;
            case Lpu237Setting.Step.READ_DIRECTION:
                b_result = df_get_reading_direction();
                break;
            case Lpu237Setting.Step.TRACK_ORDER:
                if( Lpu237Tools.is_support_msr_send_order(s_n,s_v) ) {
                    b_result = df_get_track_order();
                }
                break;
            case Lpu237Setting.Step.LEAVE_CONFIG:
                b_result = df_leave_config();
                break;
            default:
                break;
        }//end switch

        if(!b_result){
            df_leave_config();
        }
        Setting.SetCurResult(b_result);
        return Setting;
    }
    @Override
    public void close() throws Exception {
        m_working.set(false);//kill thread.
        m_futureTask.cancel(true);
    }

    public Lpu237Runner(UsbManager usbManager, UsbDevice usbDevice){
        super(usbManager,usbDevice);
        m_working = new AtomicBoolean(true);
        this.m_executorService = Executors.newSingleThreadExecutor();

        this.m_futureTask = this.m_executorService.submit(this);
    }
    public boolean IsEmpty(){
        if(m_usbDevice == null){
            return false;
        }
        return true;
    }

    public Lpu237Runner.Do WhatIsDoing(){
        synchronized (m_locker) {
            return m_doing;
        }
    }

    private void _SetDoing( Lpu237Runner.Do d){
        synchronized (m_locker) {
            m_doing = d;
        }
    }

    public boolean is_enable_read(){
        synchronized (m_locker) {
            return (m_b_enable_read_msr||m_b_enable_read_ibutton);
        }
    }
    public boolean is_enable_read_msr(){
        synchronized (m_locker) {
            return m_b_enable_read_msr;
        }
    }
    public boolean is_enable_read_ibutton(){
        synchronized (m_locker) {
            return m_b_enable_read_ibutton;
        }
    }
    public void set_read_msr(boolean b_enable){
        synchronized (m_locker) {
            m_b_enable_read_msr = b_enable;
        }
    }
    public void set_read_ibutton(boolean b_enable){
        synchronized (m_locker) {
            m_b_enable_read_ibutton = b_enable;
        }
    }

    public boolean Cancel(){
        return m_futureTask.cancel(true);
    }
    public boolean Reading(Lpu237Callback cb){
        boolean b_result = true;
        try {
            m_queue.put(cb);
        }
        catch (Exception e){
            b_result = false;
        }
        return b_result;
    }


    public boolean Doing(Object cb){
        boolean b_result = true;
        try {
            m_queue.put(cb);
        }
        catch (Exception e){
            b_result = false;
        }
        return b_result;
    }

    private void _run_callback(
            List<Object> allCallBack,
            Lpu237Callback.Result read_result,
            byte[] rx,
            Lpu237Stepping getInfo,
            Lpu237Stepping setInfo
    ){
        boolean b_ibutton_format = Lpu237Tools.is_hid_reponse_ibutton(rx);
        boolean b_msr = is_enable_read_msr();
        boolean b_ibutton = is_enable_read_msr();
        allCallBack.forEach(obj -> {
            if(obj instanceof Lpu237Callback) {
                Lpu237Callback cb = (Lpu237Callback)obj;
                if (b_ibutton_format) {
                    if (b_ibutton && cb.GetType() == Lpu237Callback.TypeRx.RX_IBUTTON) {
                        cb.Run(read_result, rx);
                    }
                } else if (b_msr && cb.GetType() == Lpu237Callback.TypeRx.RX_MSR) {
                    cb.Run(read_result, rx);
                }
            }
            else if(obj instanceof Lpu237GetSetCallback) {
                Lpu237GetSetCallback cb_gs = (Lpu237GetSetCallback) obj;
                if(cb_gs.is_getting_type()){
                    if(getInfo != null) {
                        cb_gs.Run(read_result,getInfo.GetCurDescription(),getInfo.GetCurStep(),getInfo.GetTotalStep());
                    }
                }
                else{
                    if(setInfo != null) {
                        cb_gs.Run(read_result,setInfo.GetCurDescription(),setInfo.GetCurStep(),setInfo.GetTotalStep());
                    }

                }
            }
        });
    }
    private void _run_callback(Lpu237Callback cb,Lpu237Callback.Result read_result,byte[] rx){

        boolean b_ibutton_format = Lpu237Tools.is_hid_reponse_ibutton(rx);
        boolean b_msr = is_enable_read_msr();
        boolean b_ibutton = is_enable_read_msr();
        if(b_ibutton_format){
            if(b_ibutton && cb.GetType() == Lpu237Callback.TypeRx.RX_IBUTTON){
                cb.Run(read_result, rx);
            }
        }
        else if(b_msr && cb.GetType() == Lpu237Callback.TypeRx.RX_MSR){
            cb.Run(read_result, rx);
        }
    }

    private boolean _run_callback(Lpu237GetSetCallback cb,Lpu237Callback.Result result,Lpu237Stepping StepInfo){
        boolean b_continue = true;

        do{
            if(cb != null){
                b_continue = cb.Run(result,StepInfo.GetCurDescription(),StepInfo.GetCurStep(),StepInfo.GetTotalStep());
            }

        }while(false);
        return b_continue;
    }

    /**
     * disable default constructor
     */
    private Lpu237Runner(){
        super();
    }

}
