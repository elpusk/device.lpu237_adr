package kr.pe.sheep_transform.lpu237_adr;

public interface ManagerIntentAction{

    String USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    String USB_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    String UPDATE_LIST_NO_DEVICE = "kr.pe.sheep_transform.lpu237_adr.UPDATE_LIST_NO_DEVICE";

    String LPU237_PERMISSION = "kr.pe.sheep_transform.lpu237_adr.LPU237_PERMISSION";
    String GET_INFO_FOR_LIST = "kr.pe.sheep_transform.lpu237_adr.GET_INFO_FOR_LIST";
    String UPDATE_UID = "kr.pe.sheep_transform.lpu237_adr.UPDATE_UID";
    String GET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.GET_PARAMETERS";
    String SET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.SET_PARAMETERS";

    String BOOTLOADER_PERMISSION = "kr.pe.sheep_transform.lpu237_adr.BOOTLOADER_PERMISSION";
    String START_BOOTLOADER = "kr.pe.sheep_transform.lpu237_adr.START_BOOTLOADER";
    String SECTOR_INFO = "kr.pe.sheep_transform.lpu237_adr.SECTOR_INFO";
    String ERASE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.ERASE_SECTOR";
    String ERASE_COMPLETE = "kr.pe.sheep_transform.lpu237_adr.ERASE_COMPLETE";
    String WRITE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.WRITE_SECTOR";
    String WRITE_COMPLETE = "kr.pe.sheep_transform.lpu237_adr.WRITE_COMPLETE";
    String START_APP = "kr.pe.sheep_transform.lpu237_adr.START_APP";
    String RECOVER_PARAMETER = "kr.pe.sheep_transform.lpu237_adr.RECOVER_PARAMETER";

    String ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST";

    String ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS";
    String ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS";
    String ACTIVITY_MAIN_START_BOOT = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_MAIN_START_BOOT";

    String ACTIVITY_UPDATE_START_BOOT = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_START_BOOT";
    String ACTIVITY_UPDATE_SECTOR_INFO = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_SECTOR_INFO";
    String ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR";
    String ACTIVITY_UPDATE_DETAIL_ERASE_INFO = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_DETAIL_ERASE_INFO";
    String ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE";
    String ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR";
    String ACTIVITY_UPDATE_DETAIL_WRITE_INFO = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_DETAIL_WRITE_INFO";
    String ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE";
    String ACTIVITY_UPDATE_START_APP = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_START_APP";
    String ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS";
    String ACTIVITY_UPDATE_RECOVER_PARAMETER = "kr.pe.sheep_transform.lpu237_adr.ACTIVITY_UPDATE_RECOVER_PARAMETER";

    String GENERAL_TERMINATE_APP = "kr.pe.sheep_transform.lpu237_adr.GENERAL_TERMINATE_APP";
    //
    int INT_ALL_ACTION = 0xFFFFFFFF;
    int INT_UNKNOWN = 0;
    int INT_USB_ATTACHED =  0x01000001;
    int INT_USB_DETACHED =  0x01000002;
    int INT_UPDATE_LIST_NO_DEVICE =   0x01000004;
    int INT_ALL_USB =    0x010000FF;

    int INT_LPU237_PERMISSION =     0x02000000;
    int INT_GET_INFO_FOR_LIST =     0x02000001;
    int INT_UPDATE_UID =            0x02000002;
    int INT_GET_PARAMETERS =        0x02000004;
    int INT_SET_PARAMETERS =        0x02000008;
    int INT_ALL_LPU237 =         0x020000FF;

    int INT_BOOTLOADER_PERMISSION =     0x04000000;
    int INT_START_BOOTLOADER =          0x04000001;
    int INT_SECTOR_INFO =            0x04000002;
    int INT_ERASE_SECTOR =            0x04000004;
    int INT_ERASE_COMPLETE =          0x04000008;
    int INT_WRITE_SECTOR =            0x04000010;
    int INT_WRITE_COMPLETE =            0x04000020;
    int INT_START_APP =                 0x04000040;
    int INT_RECOVER_PARAMETER =         0x04000080;
    int INT_ALL_BOOTLOADER =         0x040000FF;

    int INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST = 0x08000000;
    int INT_ALL_ACTIVITY_STARTUP              = 0x080000FF;

    int INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS = 0x10000000;
    int INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS = 0x10000001;
    int INT_ACTIVITY_MAIN_START_BOOT              = 0x10000002;
    int INT_ALL_ACTIVITY_MAIN                     = 0x100000FF;

    int INT_ACTIVITY_UPDATE_START_BOOT              = 0x20000001;
    int INT_ACTIVITY_UPDATE_SECTOR_INFO             = 0x20000002;
    int INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR   = 0x20000004;
    int INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO       = 0x20000008;
    int INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE = 0x20000010;
    int INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR   = 0x20000020;
    int INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO       = 0x20000040;
    int INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE = 0x20000080;
    int INT_ACTIVITY_UPDATE_START_APP               = 0x20000100;
    int INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS = 0x20000200;
    int INT_ACTIVITY_UPDATE_RECOVER_PARAMETER       = 0x20000400;
    int INT_ALL_ACTIVITY_UPDATE                     = 0x2000FFFF;

    int INT_GENERAL_TERMINATE_APP         = 0x40000000;
    int INT_ALL_GENERAL                 = 0x400000FF;

    //extra data index of intent.
    String EXTRA_NAME_RESPONSE_INDEX = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_INDEX";
    String EXTRA_NAME_RESPONSE_SECTOR_INDEX = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_SECTOR_INDEX";
    String EXTRA_NAME_RESPONSE_SECTOR = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_SECTOR";
    String EXTRA_NAME_RESPONSE_SECTOR_CHAIN = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_SECTOR_CHAIN";
    String EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY";
    String EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY = "kr.pe.sheep_transform.lpu237_adr.EXTRA_NAME_RESPONSE_INT_RESULT_FOR_ACTIVITY";

}

