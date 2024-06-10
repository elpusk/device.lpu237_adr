package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

public interface Lpu237Const {
    int USB_VID = 0x134b;
    int USB_PID = 0x0206;
    int USB_INF = 1;
    int SIZE_REPORT_IN = 220;
    int SIZE_REPORT_OUT = 64;
    int SIZE_REQ_HEAD = 3;
    int SIZE_IBUTTON_DATA = 8;
    byte[] IBUTTON_TAG_DATA = new byte[]{'t','h','i','s','_','i','s','_','i','b','u','t','t','o','n','_','d','a','t','a'};

    int NUMBER_ISO_TRACK = 3;
    int OFFSET_IN_BLANK_OF_IBUTTON_TYPE = 2;
    byte MASK_IN_BLANK_OF_IBUTTON_ZEROS7 = 0x04;
    byte MASK_IN_BLANK_OF_IBUTTON_F12 = 0x01;
    byte MASK_IN_BLANK_OF_IBUTTON_ADDMIT = 0x08;
    byte MASK_IN_BLANK_OF_IBUTTON_ZEROS = 0x02;

    int OFFSET_IN_BLANK_OF_SUCCESS_INDICATE = 1;
    byte MASK_IN_BLANK_OF_SUCCESS_INDICATE = 0x01;
    int OFFSET_IN_BLANK_OF_MMD1100_RESET_INTERVAL = 1;
    byte MASK_IN_BLANK_OF_MMD1100_RESET_INTERVAL = -16;//=0xf0;

    int OFFSET_IN_BLANK_OF_IBUTTON_POSITION = 0;
    byte MASK_IN_BLANK_OF_IBUTTON_POSITION_START = -16;//=0xf0 zero base
    byte MASK_IN_BLANK_OF_IBUTTON_POSITION_END = 0x0f;//zero base


}
