package kr.pe.sheep_transform.lpu237_adr.lib.hid;

import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;

import java.nio.ByteBuffer;


public abstract class HidDevice{
    protected UsbManager m_usbManager;
    protected UsbDevice m_usbDevice;
    protected UsbInterface m_usbInterface;
    protected UsbDeviceConnection m_usbDeviceConnection;
    protected UsbEndpoint m_usbEndpointIn;
    protected UsbEndpoint m_usbEndpointOut;
    protected Object m_locker;
    protected UsbRequest m_usbRequestIn;
    protected UsbRequest m_usbRequestOut;
    protected ByteBuffer m_buffer_in;
    protected ByteBuffer m_buffer_out;

    ///////////////////////////////
    //abstract methods
    public abstract int get_vid();
    public abstract int get_pid();
    public abstract int get_interface_number();
    public abstract int get_in_report_size();
    public abstract int get_out_report_size();
    //
    protected HidDevice(){
        m_locker = new Object();
        m_buffer_in = ByteBuffer.allocate(get_in_report_size());
        m_buffer_out = ByteBuffer.allocate(get_out_report_size());
    }

    public HidDevice(UsbManager usbManager, UsbDevice usbDevice){
        this();
        //
        m_usbManager = usbManager;
        m_usbDevice = usbDevice;
        m_usbInterface = m_usbDevice.getInterface(get_interface_number());

        for (int n_endpont = 0; n_endpont < m_usbInterface.getEndpointCount(); n_endpont++) {
            UsbEndpoint ep = m_usbInterface.getEndpoint(n_endpont);
            if (ep.getDirection() == UsbConstants.USB_DIR_IN)
                m_usbEndpointIn = ep;
            else
                m_usbEndpointOut = ep;
        }//end for
        //
        m_usbRequestIn = new UsbRequest();
        m_usbRequestOut = new UsbRequest();
    }

    public boolean open()
    {
        boolean b_result = false;

        do{
            synchronized (m_locker) {
                if (m_usbManager == null)
                    continue;
                if (m_usbDevice == null)
                    continue;
                if (m_usbInterface == null)
                    continue;
                m_usbDeviceConnection = m_usbManager.openDevice(m_usbDevice);
                if (m_usbDeviceConnection == null)
                    continue;
                if (!m_usbDeviceConnection.claimInterface(m_usbInterface, true))
                    continue;
                //
                if( !m_usbRequestIn.initialize(m_usbDeviceConnection,m_usbEndpointIn) )
                    continue;
                if(!m_usbRequestOut.initialize(m_usbDeviceConnection,m_usbEndpointOut) )
                    continue;
                m_buffer_out.clear();
                m_buffer_in.clear();
            }
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("HidDevice","error : open");
        return b_result;
    }

    public boolean close()
    {
        boolean b_result = false;

        do{
            synchronized (m_locker) {
                if (m_usbDeviceConnection == null)
                    continue;
                m_buffer_in.clear();
                m_buffer_out.clear();
                //
                m_usbRequestIn.close();
                m_usbRequestOut.close();
                m_usbDeviceConnection.close();
            }

            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("HidDevice","error : close");

        return b_result;
    }

    protected int write( byte[] s_write )
    {
        int n_tx = 0;

        do{
            synchronized (m_locker){
                if (s_write.length > m_buffer_out.capacity())
                    continue;
                //
                m_buffer_out.rewind();
                m_buffer_out.clear();
                m_buffer_out.put(s_write);
                if(!m_usbRequestOut.queue(m_buffer_out, m_buffer_out.position())){
                    Log.e("write", "Failed to queue the USB request.");
                    continue;
                }

                UsbRequest r = m_usbDeviceConnection.requestWait();
                if (r == m_usbRequestOut) {
                    //Log.i("write", "ok.\n");
                    n_tx = s_write.length;
                } else {
                    Log.e("write", "error.\n");
                }
            }

        }while(false);

        return n_tx;
    }

    protected int read( byte[] s_read )
    {
        int n_rx = 0;

        do{
            if( s_read == null )
                continue;
            //
            synchronized (m_locker){
                m_buffer_in.rewind();
                m_buffer_in.clear();

                if(!m_usbRequestIn.queue(m_buffer_in, m_buffer_in.capacity())){
                    Log.e("read", "Failed to queue the USB request.");
                    continue;
                }

                UsbRequest r = m_usbDeviceConnection.requestWait();
                if( r == m_usbRequestIn ){
                    //Log.i("read","ok.\n");
                    n_rx = m_buffer_in.position();
                    m_buffer_in.position(0);
                    m_buffer_in.get(s_read, 0, s_read.length);
                }
                else{
                    Log.i("read","error.\n");
                }
            }
        }while(false);
        return n_rx;
    }
}
