package kr.pe.sheep_transform.lpu237_adr.lib;

import android.hardware.usb.UsbDevice;

/**
 * usb device handle class of android system.
 */
public class UsbDevHandle {

    public UsbDevHandle(String path, UsbDevice usbdevice) {
        m_usbdevice = usbdevice;
        m_s_path = path;
    }

    /**
     * Returns the path of the current UsbDevHandle object.
     *
     * @return the path of the UsbDevHandle object as a String
     */    
    public String get_path() {
        return m_s_path;
    }

    /**
     * Returns the UsbDevice object stored in this UsbDevHandle.
     *
     * @return the UsbDevice object
     */    
    public UsbDevice get_usbdevice() {
        return m_usbdevice;
    }

    /**
     * Checks if the USB device stored in the UsbDevHandle object is empty.
     *
     * @return true if the USB device is null, false otherwise
     */    
    public boolean is_empty(){
        if(m_usbdevice == null)
            return true;
        //
        return false;
    }

    /**
     * Resets the UsbDevHandle object by setting the USB device to null and
     * clearing the path.
     *
     * @return none
     */    
    public void reset() {
        m_usbdevice = null;
        m_s_path = "";
    }

    /**
     * Compares the given UsbDevice object with the UsbDevice stored in this UsbDevHandle object.
     *
     * @param  dev  the UsbDevice object to compare with
     * @return      true if the UsbDevices are equal, false otherwise
     */    
    public boolean	equals(UsbDevice dev){
        boolean b_equal = false;
        do{
            if(dev==null){
                continue;
            }
            if(m_usbdevice==null){
                continue;
            }
            b_equal = m_usbdevice.equals(dev);
        }while(false);
        return b_equal;
    }

    /**
     * Compares the given string path with the path stored in this UsbDevHandle object.
     *
     * @param  spath  the string path to compare with
     * @return        true if the paths are equal, false otherwise
     */    
    public boolean	equals(String spath){
        boolean b_equal = false;
        do{
            if(spath==null){
                continue;
            }
            if(m_s_path==null){
                continue;
            }
            if(m_s_path.isEmpty()){
                continue;
            }

            if( m_s_path.compareTo(spath) != 0 ){
                continue;
            }

            b_equal = true;
        }while(false);
        return b_equal;
    }


    private String m_s_path  ="";
    private UsbDevice m_usbdevice = null;
}