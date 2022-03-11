package kr.pe.sheep_transform.lpu237_adr;

import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;

//import junit.runner.Version;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashSet;


interface Lpu237Request{
    byte cmdChangeAuthkey = 'C';
    byte cmdChangeEnkey = 'K';
    byte cmdChangeStatus = 'M';
    byte cmdChangeSN = 'S';
    byte cmdConfig = 'A';
    byte cmdApply = 'B';
    byte cmdEnterCS = 'X';
    byte cmdLeaveCS = 'Y';
    byte cmdGotoBootLoader = 'G';
    byte cmdEnterOps = 'I';
    byte cmdLeaveOps = 'J';
    byte cmdHwIsStandard = 'D';
    byte cmdHwIsOnlyiButton = 'W';
    byte cmdReadUID = 'U';
    byte cmdHwIsMMD1000 = 'N';
}

interface Lpu237RequestSub{
    byte subConfigSet = (byte) 200;
    byte subConfigGet = (byte) 201;
}

interface  Lpu237Response{
    byte prefix = 'R';

    byte resultGood = -1;
    byte resultGoodNegative = -128;//0x80;
    byte resultErrorCrc = 1;
    byte resultErrorMislength = 2;
    byte resultErrorMiskey = 3;
    byte resultErrorMisCheckBlock = 4;
    byte resultErrorinvalid = 5;
    byte resultErrorVerify = 6;

}

interface Lpu237Interface{
    byte usbKeyboard = 0;
    byte usbVendorHid = 1;
    byte uart = 10;
    byte ps2StandAlone = 20;
    byte ps2Bypass = 21;
    byte hwSetting = 100;
}

interface Lpu237InterfaceString{
    String sUsbKeyboard = "USB Keyboard mode";
    String sUsbHid = "USB HID Vendor mode";
    String sRS232 = "RS232 mode";
}

interface Lpu237LanguageIndex{
    byte english = 0;//USA
    byte spanish = 1;
    byte danish = 2;
    byte french = 3;
    byte german = 4;
    byte italian = 5;
    byte norwegian = 6;
    byte swedish = 7;
    byte ukEnglish = 8;
    byte israel = 9;
    byte turkey = 10;
}

interface Lpu237ChangedParameter{
    int EnableTrack1 = 0;
    int EnableTrack2 = 1;
    int EnableTrack3 = 2;
    int DeviceInterface = 3;
    int DeviceLanguage = 4;
    int BuzzerFrequency = 5;
    int GlobalPrefix = 6;
    int GlobalPostfix = 7;
    int PrivatePrefixTrack1 = 8;
    int PrivatePostfixTrack1 = 9;
    int PrivatePrefixTrack2 = 10;
    int PrivatePostfixTrack2 = 11;
    int PrivatePrefixTrack3 = 12;
    int PrivatePostfixTrack3 = 13;
    int iButtonPrefix = 14;
    int iButtonPostfix = 15;
    int UartPrefix = 16;
    int UartPostfix = 17;
    int GlobalSendCondition = 18;
    int BlankField = 19;//ibutton type
}

interface Lpu237SystemStructureOffset{
    int cBlank = 0;
    int dwSize = 1+3;
    int sStructVer = 5+3;
    int sName = 9+3;
    int sSysVer = 25+3;
    int ModeBL = 29+3;
    int ModeAP = 30+3;
    int sSN = 31+3;
    int Interface = 39+3;
    int nBuzzerFrequency = 40+3;
    int nNormalWDT = 44+3;
    int nBootRunTime = 48+3;

    int Uart_nCom = 52+3;
    int Uart_nBaud = 56+3;

    int	ContainerInfoMsrObj_pInfoMsrObj0 = 60+3;
    int ContainerInfoMsrObj_pInfoMsrObj1 = 64+3;
    int ContainerInfoMsrObj_pInfoMsrObj2 = 68+3;
    int ContainerInfoMsrObj_nCpdSystickMin = 72+3;
    int ContainerInfoMsrObj_nCpdSystickMax = 76+3;
    int ContainerInfoMsrObj_nGlobalTagCondition = 80+3;
    int ContainerInfoMsrObj_nNumItem = 84+3;
    int ContainerInfoMsrObj_nOrderObject0 = 88+3;
    int ContainerInfoMsrObj_nOrderObject1 = 92+3;
    int ContainerInfoMsrObj_nOrderObject2 = 96+3;
    int ContainerInfoMsrObj_Keymap_nMappingIndex = 100+3;
    int ContainerInfoMsrObj_Keymap_nNumMapTableItem = 104+3;
    int ContainerInfoMsrObj_TagPre_cSize = 108+3;
    int ContainerInfoMsrObj_TagPre_sTag= 109+3;
    int ContainerInfoMsrObj_TagPost_cSize = 123+3;
    int ContainerInfoMsrObj_TagPost_sTag = 124+3;
    int ContainerInfoMsrObj_GlobalPrefix_cSize = 138+3;
    int ContainerInfoMsrObj_GlobalPrefix_sTag = 139+3;
    int ContainerInfoMsrObj_GlobalPostfix_cSize = 153+3;
    int ContainerInfoMsrObj_GlobalPostfix_sTag = 154+3;

    int InfoMsr0_cEnableTack = 168+3;
    int InfoMsr0_cSupportNum = 169+3;
    int InfoMsr0_cActiveCombination = 170+3;
    int InfoMsr0_cMaxSize = 171+3;
    int InfoMsr0_cBitSize = 174+3;
    int InfoMsr0_cDataMask = 177+3;
    int InfoMsr0_bUseParity = 180+3;
    int InfoMsr0_cParityType = 183+3;
    int InfoMsr0_cSTX = 186+3;
    int InfoMsr0_cETX = 189+3;
    int InfoMsr0_bUseErrorCorrect = 192+3;
    int InfoMsr0_cECMType = 195+3;
    int InfoMsr0_cRDirect = 198+3;
    int InfoMsr0_nBufSize = 201+3;
    int InfoMsr0_cAddValue = 205+3;
    int InfoMsr0_bEnableEncryption = 208+3;
    int InfoMsr0_sMasterKey = 209+3;
    int InfoMsr0_sChangeKey = 225+3;
    int InfoMsr0_PrivatePrefix0_cSize = 241+3;
    int InfoMsr0_PrivatePrefix0_sTag = 242+3;
    int InfoMsr0_PrivatePrefix1_cSize = 256+3;
    int InfoMsr0_PrivatePrefix1_sTag = 257+3;
    int InfoMsr0_PrivatePrefix2_cSize = 271+3;
    int InfoMsr0_PrivatePrefix2_sTag = 272+3;
    int InfoMsr0_PrivatePostfix0_cSize = 286+3;
    int InfoMsr0_PrivatePostfix0_sTag = 287+3;
    int InfoMsr0_PrivatePostfix1_cSize = 301+3;
    int InfoMsr0_PrivatePostfix1_sTag = 302+3;
    int InfoMsr0_PrivatePostfix2_cSize = 316+3;
    int InfoMsr0_PrivatePostfix2_sTag =  317+3;
    int InfoMsr0_Keymap0_nMappingIndex = 331+3;
    int InfoMsr0_Keymap0_nNumMapTableItem = 335+3;
    int InfoMsr0_Keymap1_nMappingIndex = 339+3;
    int InfoMsr0_Keymap1_nNumMapTableItem = 343+3;
    int InfoMsr0_Keymap2_nMappingIndex = 347+3;
    int InfoMsr0_Keymap2_nNumMapTableItem = 351+3;

    int InfoMsr1_cEnableTack = 355+3;
    int InfoMsr1_cSupportNum = 356+3;
    int InfoMsr1_cActiveCombination = 357+3;
    int InfoMsr1_cMaxSize = 358+3;
    int InfoMsr1_cBitSize = 361+3;
    int InfoMsr1_cDataMask = 364+3;
    int InfoMsr1_bUseParity = 367+3;
    int InfoMsr1_cParityType = 370+3;
    int InfoMsr1_cSTX = 373+3;
    int InfoMsr1_cETX = 376+3;
    int InfoMsr1_bUseErrorCorrect = 379+3;
    int InfoMsr1_cECMType = 382+3;
    int InfoMsr1_cRDirect = 385+3;
    int InfoMsr1_nBufSize = 388+3;
    int InfoMsr1_cAddValue = 392+3;
    int InfoMsr1_bEnableEncryption = 395+3;
    int InfoMsr1_sMasterKey = 396+3;
    int InfoMsr1_sChangeKey = 412+3;
    int InfoMsr1_PrivatePrefix0_cSize = 428+3;
    int InfoMsr1_PrivatePrefix0_sTag = 429+3;
    int InfoMsr1_PrivatePrefix1_Size = 443+3;
    int InfoMsr1_PrivatePrefix1_sTag = 444+3;
    int InfoMsr1_PrivatePrefix2_cSize = 458+3;
    int InfoMsr1_PrivatePrefix2_sTag = 459+3;
    int InfoMsr1_PrivatePostfix0_cSize = 473+3;
    int InfoMsr1_PrivatePostfix0_sTag = 474+3;
    int InfoMsr1_PrivatePostfix1_cSize = 488+3;
    int InfoMsr1_PrivatePostfix1_sTag = 489+3;
    int InfoMsr1_PrivatePostfix2_cSize = 503+3;
    int InfoMsr1_PrivatePostfix2_sTag = 504+3;
    int InfoMsr1_Keymap0_nMappingIndex = 518+3;
    int InfoMsr1_Keymap0_nNumMapTableItem = 522+3;
    int InfoMsr1_Keymap1_nMappingIndex = 526+3;
    int InfoMsr1_Keymap1_nNumMapTableItem = 530+3;
    int InfoMsr1_Keymap2_nMappingIndex = 534+3;
    int InfoMsr1_Keymap2_nNumMapTableItem = 538+3;

    int InfoMsr2_cEnableTack = 542+3;
    int InfoMsr2_cSupportNum = 543+3;
    int InfoMsr2_cActiveCombination = 544+3;
    int InfoMsr2_cMaxSize = 545+3;
    int InfoMsr2_cBitSize = 548+3;
    int InfoMsr2_cDataMask = 551+3;
    int InfoMsr2_bUseParity = 554+3;
    int InfoMsr2_cParityType = 557+3;
    int InfoMsr2_cSTX = 560+3;
    int InfoMsr2_cETX = 563+3;
    int InfoMsr2_bUseErrorCorrect = 566+3;
    int InfoMsr2_cECMType = 569+3;
    int InfoMsr2_cRDirect = 572+3;
    int InfoMsr2_nBufSize = 575+3;
    int InfoMsr2_cAddValue = 579+3;
    int InfoMsr2_bEnableEncryption = 582+3;
    int InfoMsr2_sMasterKey = 583+3;
    int InfoMsr2_sChangeKey = 599+3;
    int InfoMsr2_PrivatePrefix0_cSize = 615+3;
    int InfoMsr2_PrivatePrefix0_sTag = 616+3;
    int InfoMsr2_PrivatePrefix1_cSize = 630+3;
    int InfoMsr2_PrivatePrefix1_sTag = 631+3;
    int InfoMsr2_PrivatePrefix2_cSize = 645+3;
    int InfoMsr2_PrivatePrefix2_sTag = 646+3;
    int InfoMsr2_PrivatePostfix0_cSize = 660+3;
    int InfoMsr2_PrivatePostfix0_sTag = 661+3;
    int InfoMsr2_PrivatePostfix1_cSize = 675+3;
    int InfoMsr2_PrivatePostfix1_sTag = 676+3;
    int InfoMsr2_PrivatePostfix2_cSize = 690+3;
    int InfoMsr2_PrivatePostfix2_sTag = 691+3;
    int InfoMsr2_Keymap0_nMappingIndex = 705+3;
    int InfoMsr2_Keymap0_nNumMapTableItem = 709+3;
    int InfoMsr2_Keymap1_nMappingIndex = 713+3;
    int InfoMsr2_Keymap1_nNumMapTableItem = 717+3;
    int InfoMsr2_Keymap2_nMappingIndex = 721+3;
    int InfoMsr2_Keymap2_nNumMapTableItem = 725+3;
    // additional item from version 3.0
    int InfoiButton_TagPre_cSize = 729+3;
    int InfoiButton_TagPre_sTag = 730+3;
    int InfoiButton_TagPost_cSize = 744+3;
    int InfoiButton_TagPost_sTag = 745+3;
    int InfoiButton_GlobalPrefix_cSize = 759+3;
    int InfoiButton_GlobalPrefix_sTag = 760+3;
    int InfoiButton_GlobalPostfix_cSize = 774+3;
    int InfoiButton_GlobalPostfix_sTag = 775+3;

    int InfoUart_TagPre_cSize = 789+3;
    int InfoUart_TagPre_sTag = 790+3;
    int InfoUart_TagPost_cSize = 804+3;
    int InfoUart_TagPost_sTag = 805+3;
    int InfoUart_GlobalPrefix_cSize = 819+3;
    int InfoUart_GlobalPrefix_sTag = 820+3;
    int InfoUart_GlobalPostfix_cSize = 834+3;
    int InfoUart_GlobalPostfix_sTag = 835+3;
}

interface Lpu237SystemStructureSize{
    int cBlank = 4;
    int dwSize = 4;
    int sStructVer = 4;
    int sName = 16;
    int sSysVer = 4;
    int ModeBL = 1;
    int ModeAP = 1;
    int sSN = 8;
    int Interface = 1;
    int nBuzzerFrequency = 4;
    int nNormalWDT = 4;
    int nBootRunTime = 4;

    int Uart_nCom = 4;
    int Uart_nBaud = 4;

    int	ContainerInfoMsrObj_pInfoMsrObj0 = 4;
    int ContainerInfoMsrObj_pInfoMsrObj1 = 4;
    int ContainerInfoMsrObj_pInfoMsrObj2 = 4;
    int ContainerInfoMsrObj_nCpdSystickMin = 4;
    int ContainerInfoMsrObj_nCpdSystickMax = 4;
    int ContainerInfoMsrObj_nGlobalTagCondition = 4;
    int ContainerInfoMsrObj_nNumItem = 4;
    int ContainerInfoMsrObj_nOrderObject0 = 4;
    int ContainerInfoMsrObj_nOrderObject1 = 4;
    int ContainerInfoMsrObj_nOrderObject2 = 4;
    int ContainerInfoMsrObj_Keymap_nMappingIndex = 4;
    int ContainerInfoMsrObj_Keymap_nNumMapTableItem = 4;
    int ContainerInfoMsrObj_TagPre_cSize = 1;
    int ContainerInfoMsrObj_TagPre_sTag = 14;
    int ContainerInfoMsrObj_TagPost_cSize = 1;
    int ContainerInfoMsrObj_TagPost_sTag = 14;
    int ContainerInfoMsrObj_GlobalPrefix_cSize = 1;
    int ContainerInfoMsrObj_GlobalPrefix_sTag = 14;
    int ContainerInfoMsrObj_GlobalPostfix_cSize = 1;
    int ContainerInfoMsrObj_GlobalPostfix_sTag = 14;

    int InfoMsr0_cEnableTack = 1;
    int InfoMsr0_cSupportNum = 1;
    int InfoMsr0_cActiveCombination = 1;
    int InfoMsr0_cMaxSize = 3;
    int InfoMsr0_cBitSize = 3;
    int InfoMsr0_cDataMask = 3;
    int InfoMsr0_bUseParity = 3;
    int InfoMsr0_cParityType = 3;
    int InfoMsr0_cSTX = 3;
    int InfoMsr0_cETX = 3;
    int InfoMsr0_bUseErrorCorrect = 3;
    int InfoMsr0_cECMType = 3;
    int InfoMsr0_cRDirect = 3;
    int InfoMsr0_nBufSize = 4;
    int InfoMsr0_cAddValue = 3;
    int InfoMsr0_bEnableEncryption = 1;
    int InfoMsr0_sMasterKey = 16;
    int InfoMsr0_sChangeKey = 16;
    int InfoMsr0_PrivatePrefix0_cSize = 1;
    int InfoMsr0_PrivatePrefix0_sTag = 14;
    int InfoMsr0_PrivatePrefix1_cSize = 1;
    int InfoMsr0_PrivatePrefix1_sTag = 14;
    int InfoMsr0_PrivatePrefix2_cSize = 1;
    int InfoMsr0_PrivatePrefix2_sTag = 14;
    int InfoMsr0_PrivatePostfix0_cSize = 1;
    int InfoMsr0_PrivatePostfix0_sTag = 14;
    int InfoMsr0_PrivatePostfix1_cSize = 1;
    int InfoMsr0_PrivatePostfix1_sTag = 14;
    int InfoMsr0_PrivatePostfix2_cSize = 1;
    int InfoMsr0_PrivatePostfix2_sTag =  14;
    int InfoMsr0_Keymap0_nMappingIndex = 4;
    int InfoMsr0_Keymap0_nNumMapTableItem = 4;
    int InfoMsr0_Keymap1_nMappingIndex = 4;
    int InfoMsr0_Keymap1_nNumMapTableItem = 4;
    int InfoMsr0_Keymap2_nMappingIndex = 4;
    int InfoMsr0_Keymap2_nNumMapTableItem = 4;

    int InfoMsr1_cEnableTack = 1;
    int InfoMsr1_cSupportNum = 1;
    int InfoMsr1_cActiveCombination = 1;
    int InfoMsr1_cMaxSize = 3;
    int InfoMsr1_cBitSize = 3;
    int InfoMsr1_cDataMask = 3;
    int InfoMsr1_bUseParity = 3;
    int InfoMsr1_cParityType = 3;
    int InfoMsr1_cSTX = 3;
    int InfoMsr1_cETX = 3;
    int InfoMsr1_bUseErrorCorrect = 3;
    int InfoMsr1_cECMType = 3;
    int InfoMsr1_cRDirect = 3;
    int InfoMsr1_nBufSize = 4;
    int InfoMsr1_cAddValue = 3;
    int InfoMsr1_bEnableEncryption = 1;
    int InfoMsr1_sMasterKey = 16;
    int InfoMsr1_sChangeKey = 16;
    int InfoMsr1_PrivatePrefix0_cSize = 1;
    int InfoMsr1_PrivatePrefix0_sTag = 14;
    int InfoMsr1_PrivatePrefix1_Size = 1;
    int InfoMsr1_PrivatePrefix1_sTag = 14;
    int InfoMsr1_PrivatePrefix2_cSize = 1;
    int InfoMsr1_PrivatePrefix2_sTag = 14;
    int InfoMsr1_PrivatePostfix0_cSize = 1;
    int InfoMsr1_PrivatePostfix0_sTag = 14;
    int InfoMsr1_PrivatePostfix1_cSize = 1;
    int InfoMsr1_PrivatePostfix1_sTag = 14;
    int InfoMsr1_PrivatePostfix2_cSize = 1;
    int InfoMsr1_PrivatePostfix2_sTag = 14;
    int InfoMsr1_Keymap0_nMappingIndex = 4;
    int InfoMsr1_Keymap0_nNumMapTableItem = 4;
    int InfoMsr1_Keymap1_nMappingIndex = 4;
    int InfoMsr1_Keymap1_nNumMapTableItem = 4;
    int InfoMsr1_Keymap2_nMappingIndex = 4;
    int InfoMsr1_Keymap2_nNumMapTableItem = 4;

    int InfoMsr2_cEnableTack = 1;
    int InfoMsr2_cSupportNum = 1;
    int InfoMsr2_cActiveCombination = 1;
    int InfoMsr2_cMaxSize = 3;
    int InfoMsr2_cBitSize = 3;
    int InfoMsr2_cDataMask = 3;
    int InfoMsr2_bUseParity = 3;
    int InfoMsr2_cParityType = 3;
    int InfoMsr2_cSTX = 3;
    int InfoMsr2_cETX = 3;
    int InfoMsr2_bUseErrorCorrect = 3;
    int InfoMsr2_cECMType = 3;
    int InfoMsr2_cRDirect = 3;
    int InfoMsr2_nBufSize = 4;
    int InfoMsr2_cAddValue = 3;
    int InfoMsr2_bEnableEncryption = 1;
    int InfoMsr2_sMasterKey = 16;
    int InfoMsr2_sChangeKey = 16;
    int InfoMsr2_PrivatePrefix0_cSize = 1;
    int InfoMsr2_PrivatePrefix0_sTag = 14;
    int InfoMsr2_PrivatePrefix1_cSize = 1;
    int InfoMsr2_PrivatePrefix1_sTag = 14;
    int InfoMsr2_PrivatePrefix2_cSize = 1;
    int InfoMsr2_PrivatePrefix2_sTag = 14;
    int InfoMsr2_PrivatePostfix0_cSize = 1;
    int InfoMsr2_PrivatePostfix0_sTag = 14;
    int InfoMsr2_PrivatePostfix1_cSize = 1;
    int InfoMsr2_PrivatePostfix1_sTag = 14;
    int InfoMsr2_PrivatePostfix2_cSize = 1;
    int InfoMsr2_PrivatePostfix2_sTag = 14;
    int InfoMsr2_Keymap0_nMappingIndex = 4;
    int InfoMsr2_Keymap0_nNumMapTableItem = 4;
    int InfoMsr2_Keymap1_nMappingIndex = 4;
    int InfoMsr2_Keymap1_nNumMapTableItem = 4;
    int InfoMsr2_Keymap2_nMappingIndex = 4;
    int InfoMsr2_Keymap2_nNumMapTableItem = 4;
    // additional item from version 3.0
    int InfoiButton_TagPre_cSize = 1;
    int InfoiButton_TagPre_sTag = 14;
    int InfoiButton_TagPost_cSize = 1;
    int InfoiButton_TagPost_sTag = 14;
    int InfoiButton_GlobalPrefix_cSize = 1;
    int InfoiButton_GlobalPrefix_sTag = 14;
    int InfoiButton_GlobalPostfix_cSize = 1;
    int InfoiButton_GlobalPostfix_sTag = 14;

    int InfoUart_TagPre_cSize = 1;
    int InfoUart_TagPre_sTag = 14;
    int InfoUart_TagPost_cSize = 1;
    int InfoUart_TagPost_sTag = 14;
    int InfoUart_GlobalPrefix_cSize = 1;
    int InfoUart_GlobalPrefix_sTag = 14;
    int InfoUart_GlobalPostfix_cSize = 1;
    int InfoUart_GlobalPostfix_sTag = 14;
}

interface Lpu237Address{
    int HidKeymapAddressOffset = 1024;
    int Ps2KeymapAddressOffset = 2048;
}

interface Lpu237DeviceType{
    int Standard = 0;
    int Compact = 1;
    int IbuttonOny = 2;
}

interface Lpu237iButtonType{
    int Zeros = 0;
    int Zeros7 = 1;
    int F12 = 2;
    int Addmit = 3;
}

interface Lpu237Info{
    int USB_VID = 0x134b;
    int USB_PID = 0x0206;
    int USB_INF = 1;
    int SIZE_REPORT_IN = 220;
    int SIZE_REPORT_OUT = 64;
    int SIZE_REQ_HEAD = 3;

    int NUMBER_ISO_TRACK = 3;
    int OFFSET_IN_BLANK_OF_IBUTTON_TYPE = 3;
    byte MASK_IN_BLANK_OF_IBUTTON_ZEROS7 = 0x04;
    byte MASK_IN_BLANK_OF_IBUTTON_F12 = 0x01;
    byte MASK_IN_BLANK_OF_IBUTTON_ADDMIT = 0x08;
    byte MASK_IN_BLANK_OF_IBUTTON_ZEROS = 0x02;
}

public class Lpu237 extends HidDevice
{
    //
    private HashSet<Integer> m_set_changed = new HashSet<>();// data set is Lpu237ChangedParameter's member

    public boolean is_changed(){
        return !m_set_changed.isEmpty();
    }
    private boolean _is_changed( int n_change ) {
        boolean b_changed = m_set_changed.contains(n_change);
        return b_changed;
    }
    private void _set_change( int n_change ){
        if( !m_set_changed.contains(n_change) )
            m_set_changed.add(n_change);
    }
    @Override
    public int get_vid() {
        return Lpu237Info.USB_VID;
    }

    @Override
    public int get_pid() {
        return Lpu237Info.USB_PID;
    }

    @Override
    public int get_interface_number() {
        return Lpu237Info.USB_INF;
    }

    @Override
    public int get_in_report_size() {
        return Lpu237Info.SIZE_REPORT_IN;
    }

    @Override
    public int get_out_report_size() {
        return Lpu237Info.SIZE_REPORT_OUT;
    }

    public String getDescription()
    {
        String s_description = m_parameters.getDescription();
        return s_description;
    }

    ////////////////////////////////
    public boolean get_decoder_mmd1000(){
        return m_parameters.get_decoder_mmd1000();
    }
    public String getDecoderMmd1000(){
        if( get_decoder_mmd1000() )
            return "MMD1100";
        else
            return "DeltaAsic";
    }

    public int get_ibutton_type(){
        return m_parameters.get_ibutton_type();
    }
    public void set_ibutton_type( int n_type ){
        if( m_parameters.set_ibutton_type(n_type) )
            _set_change( Lpu237ChangedParameter.BlankField);
    }
    public String getiButtonType(){
        String s_type = "unknown";

        switch (get_ibutton_type()){
            case Lpu237iButtonType.Zeros:
                s_type = "Zeros";
                break;
            case Lpu237iButtonType.Zeros7:
                s_type = "Zeros7Times";
                break;
            case Lpu237iButtonType.F12:
                s_type = "F12";
                break;
            case Lpu237iButtonType.Addmit:
                s_type = "Addmit";
                break;
            default:
                break;
        }//end switch
        return s_type;
    }

    public String getDeviceType(){
        String s_type = "unknown";

        switch (get_device_type()){
            case Lpu237DeviceType.Standard:
                s_type = "standard";
                break;
            case Lpu237DeviceType.Compact:
                s_type = "compact";
                break;
            case Lpu237DeviceType.IbuttonOny:
                s_type = "i-button only";
                break;
            default:
                break;
        }//end switch
        return s_type;
    }
    public int get_device_type(){
        int n_type = Lpu237DeviceType.Standard;

        if( m_parameters.get_is_ibutton_only_type() )
            n_type = Lpu237DeviceType.IbuttonOny;
        else{
            if( !m_parameters.get_is_standard_type() )
                n_type = Lpu237DeviceType.Compact;
        }
        return n_type;
    }

    public String getUid(){
        byte[] s_id = get_uid();
        return Tools.byteArrayToHex(s_id);

    }
    public byte[] get_uid(){
        return m_parameters.get_uid();
    }

    public byte[] get_name(){
        return m_parameters.get_name();
    }
    public String getName(){
        String s_name = "";
        for(byte c: get_name()){
            if( c != ' ' && c != 0 ){
                s_name += (char)c;
            }
        }
        return s_name;
    }

    public void set_enable_track( int n_track, boolean b_enable ){
        if( m_parameters.set_enable_track( n_track, b_enable ) ) {
            switch( n_track ){
                case 0: _set_change(Lpu237ChangedParameter.EnableTrack1); break;
                case 1: _set_change(Lpu237ChangedParameter.EnableTrack2); break;
                case 2: _set_change(Lpu237ChangedParameter.EnableTrack3); break;
            }//end switch
        }
    }
    public boolean get_enable_track( int n_track ){
        return m_parameters.get_enable_track(n_track);
    }
    public String getEnableTrack( int n_track ){
        if( get_enable_track(n_track))
            return "Enable";
        else
            return "Disable";
    }

    public FwVersion get_version_system(){
        return m_parameters.get_version_system();
    }
    public String getVersionSystem(){
        FwVersion v = get_version_system();
        return v.toString();
    }

    public FwVersion get_version_structure(){
        return m_parameters.get_version_structure();
    }
    public String getVersionStructure(){
        return get_version_structure().toString();
    }

    public void set_interface( int n_interface ){
        if( m_parameters.set_interface(n_interface) )
            _set_change(Lpu237ChangedParameter.DeviceInterface);
    }
    public int get_interface(){
        return m_parameters.get_interface();
    }
    public String getInterface(){
        String s_interface = "";
        switch( get_interface() ){
            case Lpu237Interface.usbKeyboard:
                s_interface = "USB keyboard";
                break;
            case Lpu237Interface.usbVendorHid:
                s_interface = "USB Hid Vendor";
                break;
            case Lpu237Interface.uart:
                s_interface = "RS232";
                break;
            default:
                break;
        }//end switch
        return s_interface;
    }

    public void set_language_index( int n_language ){
        if( m_parameters.set_language_index( n_language) )
            _set_change(Lpu237ChangedParameter.DeviceLanguage);
    }
    public int get_language_index(){
        return m_parameters.get_language_index();
    }
    public String getLanguageIndex(){
        String s_lang = "";

        switch( get_language_index()){
            case Lpu237LanguageIndex.english:
                s_lang = "USA English";
                break;
            case Lpu237LanguageIndex.spanish:
                s_lang = "Spanish";
                break;
            case Lpu237LanguageIndex.danish:
                s_lang = "Danish";
                break;
            case Lpu237LanguageIndex.french:
                s_lang = "French";
                break;
            case Lpu237LanguageIndex.german:
                s_lang = "German";
                break;
            case Lpu237LanguageIndex.italian:
                s_lang = "Italian";
                break;
            case Lpu237LanguageIndex.norwegian:
                s_lang = "Norwegian";
                break;
            case Lpu237LanguageIndex.swedish:
                s_lang = "Swedish";
                break;
            case Lpu237LanguageIndex.israel:
                s_lang = "Hebrew";
                break;
            case Lpu237LanguageIndex.turkey:
                s_lang = "Turkey";
                break;
            default:
                s_lang = "USA English";
                break;
        }//end switch
        //
        return s_lang;
    }

    public void set_buzzer_frequency( int n_frequency ){
        if( m_parameters.set_buzzer_frequency(n_frequency) )
            _set_change(Lpu237ChangedParameter.BuzzerFrequency);
    }
    public int get_buzzer_frequency(){
        return m_parameters.get_buzzer_frequency();
    }
    public String getBuzzerFrequency(){
        String s_data = "ON";
        if( Parameters.DEFAULT_FREQUENCY_BUZZER > get_buzzer_frequency() )
            s_data = "OFF";
        //
        return s_data;
    }

    public void set_global_prefix( Tags tag ){
        if( m_parameters.set_global_prefix(tag) )
            _set_change(Lpu237ChangedParameter.GlobalPrefix);
    }
    public Tags get_global_prefix(){
        return m_parameters.get_global_prefix();
    }
    public String getGlobalPrefix(){
        Tags tag = get_global_prefix();
        return tag.toString();
    }

    public void set_global_postfix( Tags tag ){
        if( m_parameters.set_global_postfix(tag) )
            _set_change(Lpu237ChangedParameter.GlobalPostfix);
    }
    public Tags get_global_postfix(){
        return m_parameters.get_global_postfix();
    }
    public String getGlobalPostfix(){
        return get_global_postfix().toString();
    }

    public void set_private_prefix( int n_track,Tags tag){
        if( m_parameters.set_private_prefix(n_track,tag) ) {
            switch( n_track ){
                case 0: _set_change(Lpu237ChangedParameter.PrivatePrefixTrack1); break;
                case 1: _set_change(Lpu237ChangedParameter.PrivatePrefixTrack2); break;
                case 2: _set_change(Lpu237ChangedParameter.PrivatePrefixTrack3); break;
            }//end switch
        }
    }
    public Tags get_private_prefix( int n_track ){
        return m_parameters.get_private_prefix(n_track);
    }
    public String getPrivatePrefix( int n_track ){
        return get_private_prefix(n_track).toString();
    }

    public void set_private_postfix( int n_track,Tags tag ){
        if( m_parameters.set_private_postfix(n_track,tag) ) {
            switch( n_track ){
                case 0: _set_change(Lpu237ChangedParameter.PrivatePostfixTrack1); break;
                case 1: _set_change(Lpu237ChangedParameter.PrivatePostfixTrack2); break;
                case 2: _set_change(Lpu237ChangedParameter.PrivatePostfixTrack3); break;
            }//end switch
        }
    }
    public Tags get_private_postfix( int n_track ){
        return m_parameters.get_private_postfix(n_track);
    }
    public String getPrivatePostfix( int n_track ){
        return get_private_postfix(n_track).toString();
    }

    public void set_ibutton_prefix( Tags tag ){
        if( m_parameters.set_ibutton_prefix(tag) )
            _set_change(Lpu237ChangedParameter.iButtonPrefix);
    }
    public Tags get_ibutton_prefix(){
        return m_parameters.get_ibutton_prefix();
    }
    public String getIbuttonPrefix(){
        return get_ibutton_prefix().toString();
    }

    public void set_ibutton_postfix( Tags tag ){
        if( m_parameters.set_ibutton_postfix(tag) )
            _set_change(Lpu237ChangedParameter.iButtonPostfix);
    }
    public Tags get_ibutton_postfix(){
        return m_parameters.get_ibutton_postfix();
    }
    public String getIbuttonPostfix(){
        return get_ibutton_postfix().toString();
    }

    public void set_uart_prefix( Tags tag ){
        if( m_parameters.set_uart_prefix(tag) )
            _set_change(Lpu237ChangedParameter.UartPrefix);
    }
    public Tags get_uart_prefix(){
        return m_parameters.get_uart_prefix();
    }
    public String getUartPrefix(){
        return get_uart_prefix().toString();
    }

    public void set_uart_postfix( Tags tag ){
        if( m_parameters.set_uart_postfix( tag ) )
            _set_change(Lpu237ChangedParameter.UartPostfix);
    }
    public Tags get_uart_postfix(){
        return m_parameters.get_uart_postfix();
    }
    public String getUartPostfix(){
        return get_uart_postfix().toString();
    }

    public void set_global_send_condition( boolean b_all_no_error ){
        if( m_parameters.set_global_send_condition( b_all_no_error) )
            _set_change(Lpu237ChangedParameter.GlobalSendCondition);
    }
    public boolean get_global_send_condition(){
        return m_parameters.get_global_send_condition();
    }
    public String getGlobalSendCondition(){
        String s_data = "";
        if( get_global_send_condition() ){
            s_data = "No Error in all tracks";
        }
        else
            s_data = "One or more tracks are normal";
        return s_data;
    }

    ////////////////////////////////

    private Lpu237()
    {
        super();
    }

    public  Lpu237(UsbManager usbManager, UsbDevice usbDevice){
        super(usbManager,usbDevice);
    }

    private boolean _df_io( byte c_cmd, byte c_sub, byte[] s_data, InPacket packet ){
        boolean b_result = false;

        do{
            int n_tx = 0;

            if(s_data == null)
                n_tx = Lpu237Info.SIZE_REQ_HEAD;
            else if( s_data.length > 0  )
                n_tx = Lpu237Info.SIZE_REQ_HEAD + s_data.length;
            //

            // send request.
            byte[] s_tx = new byte[n_tx];
            s_tx[0] = c_cmd;
            s_tx[1] = c_sub;
            s_tx[2] = (byte)(s_tx.length-Lpu237Info.SIZE_REQ_HEAD);
            if( n_tx > Lpu237Info.SIZE_REQ_HEAD )
                System.arraycopy(s_data,0, s_tx, Lpu237Info.SIZE_REQ_HEAD, s_tx.length-Lpu237Info.SIZE_REQ_HEAD);

            byte[] s_out_packet = new byte[Lpu237Info.SIZE_REPORT_OUT];
            int n_out_packet = 0;
            int n_offset = 0;
            boolean b_error = false;

            do {
                n_tx = (s_tx.length - n_offset) - s_out_packet.length;
                if (n_tx >= 0) {
                    n_tx = s_out_packet.length;
                } else {
                    n_tx = (s_tx.length - n_offset);
                }
                System.arraycopy(s_tx, n_offset, s_out_packet, 0, n_tx);
                n_out_packet = write(s_out_packet);
                if( n_out_packet <= 0 ){
                    b_error = true;
                    break;
                }
                n_offset += n_tx;
            }while( n_offset < s_tx.length);

            if( b_error)
                continue;

            // get response.
            int n_in_report = 0;
            byte[] s_in_report = new byte[Lpu237Info.SIZE_REPORT_IN];
            s_in_report[0] = 0;

            n_in_report = read(s_in_report);
            if( n_in_report <= 0 ) {
                continue;
            }

            if( s_in_report[0] != Lpu237Response.prefix )
                continue;
            int n_length = (int)s_in_report[2];
            if( n_length < 0)
                n_length = 0;
            //
            byte[] s_rx = new byte[Lpu237Info.SIZE_REQ_HEAD+n_length];
            n_offset = 0;
            int n_rx = s_rx.length - n_offset;
            if( n_rx > s_in_report.length )
                n_rx = s_in_report.length;

            do{
                System.arraycopy(s_in_report,0,s_rx,n_offset,n_rx);
                n_offset += n_rx;
                if( n_offset >= s_rx.length )
                    continue;
                //
                n_in_report = read(s_in_report);
                if( n_in_report <= 0 ) {
                    b_error = true;
                    continue;
                }
                n_rx = s_rx.length - n_offset;
                if( n_rx > s_in_report.length )
                    n_rx = s_in_report.length;

            }while( n_offset < s_rx.length );

            if( b_error )
                continue;
            //
            if( packet == null )
                packet = new InPacket(s_rx);
            else{
                packet.set(s_rx);
            }
            if( !packet.isPrefx())
                continue;
            if( !packet.isSuccess())
                continue;

            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _df_get( int n_offset, int n_size,InPacket packet) {
        boolean b_result = false;

        do{
            byte[] s_offset = IntByteConvert.intTobyte( n_offset, ByteOrder.LITTLE_ENDIAN );
            byte[] s_size = IntByteConvert.intTobyte( n_size, ByteOrder.LITTLE_ENDIAN );
            byte[] s_data = new byte[s_offset.length+s_size.length];
            System.arraycopy(s_offset,0,s_data,0,s_offset.length);
            System.arraycopy(s_size,0,s_data, s_offset.length, s_size.length);

            if( !_df_io(Lpu237Request.cmdConfig,Lpu237RequestSub.subConfigGet,s_data,packet) )
                continue;
            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _df_set( int n_offset, int n_size, byte[] s_data, InPacket packet){
        boolean b_result = false;

        do{
            byte[] s_offset = IntByteConvert.intTobyte( n_offset, ByteOrder.LITTLE_ENDIAN );
            byte[] s_size = IntByteConvert.intTobyte( n_size, ByteOrder.LITTLE_ENDIAN );
            int n_data_field = s_offset.length+s_size.length;
            if( s_data != null)
                n_data_field += s_data.length;

            byte[] s_data_field = new byte[n_data_field];
            System.arraycopy(s_offset,0,s_data_field, 0, s_offset.length);
            System.arraycopy(s_size,0,s_data_field, s_offset.length, s_size.length);
            if( s_data != null)
                System.arraycopy(s_data,0,s_data_field, s_offset.length+s_size.length,s_data.length);
            //
            if( !_df_io(Lpu237Request.cmdConfig,Lpu237RequestSub.subConfigSet,s_data_field,packet) )
                continue;
            b_result = true;
        }while(false);
        return b_result;
    }

    private boolean _df_set_keymap_table(){
        boolean b_result = false;

        do{
            FwVersion v = m_parameters.get_version_system();
            FwVersion v3401 = new FwVersion(3,4,0,1);
            if( v.less(v3401) ){
                b_result = true;
                continue;
            }
            //usb map
            int n_map = m_parameters.get_language_index();
            int n_offset = Lpu237Address.HidKeymapAddressOffset;
            int n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            byte[] s_data = new byte[n_size];
            System.arraycopy(KeyboardMap.gASCToHIDKeyMap,n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2
            ,s_data,0,n_size);
            //
            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            n_offset = Lpu237Address.HidKeymapAddressOffset+KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            System.arraycopy(KeyboardMap.gASCToHIDKeyMap,
                    n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2 + KeyboardConst.FOR_CVT_MAX_ASCII_CODE
                    ,s_data,0,n_size);
            //
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            //PS2 map
            n_offset = Lpu237Address.Ps2KeymapAddressOffset;
            n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            System.arraycopy(KeyboardMap.gASCToPS2KeyMap,n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2
                    ,s_data,0,n_size);
            //
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            n_offset = Lpu237Address.Ps2KeymapAddressOffset+KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            n_size = KeyboardConst.FOR_CVT_MAX_ASCII_CODE;
            System.arraycopy(KeyboardMap.gASCToPS2KeyMap,
                    n_map * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2 + KeyboardConst.FOR_CVT_MAX_ASCII_CODE
                    ,s_data,0,n_size);
            //
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while (false);
        return b_result;
    }
    public boolean df_get_parameter()
    {
        boolean b_result = false;
        boolean b_leave_config = false;

        do{
            if( !df_enter_config() )
                continue;
            b_leave_config = true;

            df_get_decoder_mmd1000();

            if( !df_get_blanks() )
                continue;
            if( !df_get_type() )
                continue;
            if( !df_get_ibutton_only_type())
                continue;
            if( !df_get_uid() )
                continue;
            if( !df_get_name() )
                continue;
            if( !df_get_enable_tracK(0))
                continue;
            if( !df_get_enable_tracK(1))
                continue;
            if( !df_get_enable_tracK(2))
                continue;
            if( !df_get_version_system() )
                continue;
            if( !df_get_version_structure() )
                continue;
            if(!df_get_interface())
                continue;
            if(!df_get_language_index())
                continue;
            if(!df_get_buzzer_frequency())
                continue;
            if(!df_get_boot_run_time())
                continue;
            if(!df_get_global_prefix())
                continue;
            if(!df_get_global_postfix())
                continue;
            if(!df_get_private_prefix(0))
                continue;
            if(!df_get_private_postfix(0))
                continue;
            if(!df_get_private_prefix(1))
                continue;
            if(!df_get_private_postfix(1))
                continue;
            if(!df_get_private_prefix(2))
                continue;
            if(!df_get_private_postfix(2))
                continue;
            if(!df_get_ibutton_prefix())
                continue;
            if(!df_get_ibutton_postfix())
                continue;
            if(!df_get_uart_prefix())
                continue;
            if(!df_get_uart_postfix())
                continue;
            if( !df_get_global_send_condition())
                continue;
            //
            m_set_changed.clear();
            b_result = true;
        }while (false);

        if( b_leave_config )
            b_result = df_leave_config();
        //
        return b_result;
    }

    public boolean df_run_bootloader()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdGotoBootLoader,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_run_bootloader");
        return b_result;
    }

    public boolean df_apply()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdApply,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_apply");
        return b_result;
    }

    public boolean df_enter_config()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdEnterCS,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_enter_config");
        return b_result;
    }
    public boolean df_leave_config()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdLeaveCS,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_leave_config");

        return b_result;
    }

    public boolean df_enter_opos()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdEnterOps,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_enter_opos");

        return b_result;
    }
    public boolean df_leave_opos()
    {
        boolean b_result = false;

        do{
            if( !_df_io(Lpu237Request.cmdLeaveOps,(byte)0,null,null))
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_leave_opos");

        return b_result;
    }

    private boolean df_get_blanks(){
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.cBlank;
            n_size = Lpu237SystemStructureSize.cBlank;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_ibutton_type(packet.get_ibutton_type());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_blanks");

        return b_result;
    }
    public boolean df_get_type(){
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdHwIsStandard,(byte)0,null,packet))
                continue;
            m_parameters.set_is_standard_type(packet.isPositive());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_type");

        return b_result;
    }

    public boolean df_get_decoder_mmd1000(){
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdHwIsMMD1000,(byte)0,null,packet))
                continue;
            if( packet.isPositive() )
                m_parameters.set_decoder_mmd1000(true);
            else if( packet.isSuccess() )
                m_parameters.set_decoder_mmd1000(false);
            else {
                m_parameters.set_decoder_mmd1000(false);
                continue;
            }
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_decoder_mmd1000");

        return b_result;
    }

    public boolean df_get_ibutton_only_type()
    {
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdHwIsOnlyiButton,(byte)0,null,packet))
                continue;
            m_parameters.set_is_ibutton_only_type(packet.isPositive());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_only_type");

        return b_result;
    }

    public boolean df_get_uid()
    {
        boolean b_result = false;

        do{
            InPacket packet = new InPacket();
            if( !_df_io(Lpu237Request.cmdReadUID,(byte)0,null,packet))
                continue;
            m_parameters.set_uid( packet.s_data );
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_uid");

        return b_result;
    }

    public boolean df_get_name()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.sName;
            n_size = Lpu237SystemStructureSize.sName;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_name(packet.get_name());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_name");

        return b_result;
    }

    private boolean df_get_enable_tracK( int n_track )
    {
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr0_cEnableTack;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr1_cEnableTack;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr2_cEnableTack;
            }

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_enable_track( n_track, packet.getEnable() );
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_enable_track " + String.valueOf(n_track));

        return b_result;
    }

    public boolean df_get_version_system()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.sSysVer;
            n_size = Lpu237SystemStructureSize.sSysVer;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_version_system(packet.get_version());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_version_system");

        return b_result;
    }

    public boolean df_get_version_structure()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.sStructVer;
            n_size = Lpu237SystemStructureSize.sStructVer;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_version_structure(packet.get_version());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_version_structure");

        return b_result;
    }

    private boolean df_get_interface()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.Interface;
            n_size = Lpu237SystemStructureSize.Interface;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_interface((int)(packet.get_byte()));
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_interface");

        return b_result;
    }

    private boolean df_get_language_index()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_Keymap_nMappingIndex;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_Keymap_nMappingIndex;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_language_index(packet.get_int());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_language_index");

        return b_result;
    }

    private boolean df_get_buzzer_frequency()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.nBuzzerFrequency;
            n_size = Lpu237SystemStructureSize.nBuzzerFrequency;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_buzzer_frequency(packet.get_int()/10);
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_buzzer_frequency");

        return b_result;
    }

    private boolean df_get_boot_run_time()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.nBootRunTime;
            n_size = Lpu237SystemStructureSize.nBootRunTime;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_boot_run_time(packet.get_int()*10);
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_boot_run_time");

        return b_result;
    }

    private boolean df_get_global_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_global_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_global_prefix");

        return b_result;
    }

    private boolean df_get_global_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_global_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_global_postfix");

        return b_result;
    }

    private boolean df_get_private_prefix( int n_track )
    {
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePrefix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_cSize
                + Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_sTag;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePrefix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_sTag;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePrefix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_sTag;
            }

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_private_prefix( n_track, packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_private_prefix " + String.valueOf(n_track));

        return b_result;
    }

    private boolean df_get_private_postfix( int n_track )
    {
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_sTag;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_sTag;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_sTag;
            }

            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_private_postfix( n_track, packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_private_postfix " + String.valueOf(n_track));

        return b_result;
    }

    private boolean df_get_ibutton_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_prefix");

        return b_result;
    }

    private boolean df_get_ibutton_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_ibutton_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_ibutton_postfix");

        return b_result;
    }

    private boolean df_get_uart_prefix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPrefix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_uart_prefix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_uart_prefix");

        return b_result;

    }

    private boolean df_get_uart_postfix()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPostfix_sTag;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            packet.set_lagnuage_index(m_parameters.get_language_index());
            m_parameters.set_uart_postfix(packet.get_tag());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_uart_postfix");

        return b_result;
    }

    private boolean df_get_global_send_condition()
    {
        boolean b_result = false;

        do{
            int n_offset =0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_nGlobalTagCondition;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_nGlobalTagCondition;
            InPacket packet = new InPacket();
            if( !_df_get( n_offset, n_size, packet ) )
                continue;
            m_parameters.set_global_send_condition(packet.get_condition());
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_get_global_send_condition");

        return b_result;
    }

    public boolean df_set_parameter()
    {
        boolean b_result = false;
        boolean b_leave_config = false;

        do{
            if( !df_enter_config() )
                continue;
            b_leave_config = true;

            if( _is_changed( Lpu237ChangedParameter.BlankField)) {
                if (!df_set_blanks())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.EnableTrack1)) {
                if (!df_set_enable_tracK(0))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.EnableTrack2)) {
                if (!df_set_enable_tracK(1))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.EnableTrack3)) {
                if (!df_set_enable_tracK(2))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.DeviceInterface)) {
                if (!df_set_interface())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.DeviceLanguage)) {
                if (!df_set_language_index())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.BuzzerFrequency)) {
                if (!df_set_buzzer_frequency())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.GlobalPrefix)) {
                if (!df_set_global_prefix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.GlobalPostfix)) {
                if (!df_set_global_postfix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePrefixTrack1)) {
                if (!df_set_private_prefix(0))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePostfixTrack1)) {
                if (!df_set_private_postfix(0))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePrefixTrack2)) {
                if (!df_set_private_prefix(1))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePostfixTrack2)) {
                if (!df_set_private_postfix(1))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePrefixTrack3)) {
                if (!df_set_private_prefix(2))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.PrivatePostfixTrack3)) {
                if (!df_set_private_postfix(2))
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.iButtonPrefix)) {
                if (!df_set_ibutton_prefix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.iButtonPostfix)) {
                if (!df_set_ibutton_postfix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.UartPrefix)) {
                if (!df_set_uart_prefix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.UartPostfix)) {
                if (!df_set_uart_postfix())
                    continue;
            }
            if( _is_changed( Lpu237ChangedParameter.GlobalSendCondition)) {
                if (!df_set_global_send_condition())
                    continue;
            }
            if( !m_set_changed.isEmpty() )
                if( !df_apply() )
                    continue;
            //
            m_set_changed.clear();
            b_result = true;
        }while (false);

        if( b_leave_config )
            b_result = df_leave_config();
        //
        if( !b_result )
            Log.i("Lpu237","error : df_set_parameter");

        return b_result;
    }

    private boolean df_set_blanks(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.cBlank;
            n_size = Lpu237SystemStructureSize.cBlank;
            byte[] s_data = new byte[n_size];

            switch (m_parameters.get_ibutton_type()){
                case Lpu237iButtonType.Zeros7:
                    s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] = Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_ZEROS7;
                    break;
                case Lpu237iButtonType.F12:
                    s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] = Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_F12;
                    break;
                case Lpu237iButtonType.Addmit:
                    s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] = Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_ADDMIT;
                    break;
                case Lpu237iButtonType.Zeros:
                default:
                    s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] = Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_ZEROS;
                    break;
            }//end switch

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_blanks");

        return b_result;
    }

    private boolean df_set_enable_tracK( int n_track ){
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2)
                continue;
            boolean b_enable = m_parameters.get_enable_track(n_track);

            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr0_cEnableTack;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr1_cEnableTack;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_cEnableTack;
                n_size = Lpu237SystemStructureSize.InfoMsr2_cEnableTack;
            }

            byte[] s_data = new byte[n_size];
            if( b_enable )
                s_data[0] = 1;
            else
                s_data[0] = 0;

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_enable_track " + String.valueOf(n_track));

        return b_result;
    }
    private boolean df_set_interface(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.Interface;
            n_size = Lpu237SystemStructureSize.Interface;
            byte[] s_data = new byte[n_size];

            int n_inf = m_parameters.get_interface();
            s_data[0] = (byte)n_inf;

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_interface");

        return b_result;
    }

    private boolean df_set_language_index(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_Keymap_nMappingIndex;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_Keymap_nMappingIndex;
            int n_language = m_parameters.get_language_index();
            byte[] s_data = IntByteConvert.intTobyte(n_language,ByteOrder.LITTLE_ENDIAN);

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            n_offset = Lpu237SystemStructureOffset.InfoMsr0_Keymap0_nMappingIndex;
            n_size = Lpu237SystemStructureSize.InfoMsr0_Keymap0_nMappingIndex;
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            n_offset = Lpu237SystemStructureOffset.InfoMsr1_Keymap0_nMappingIndex;
            n_size = Lpu237SystemStructureSize.InfoMsr1_Keymap0_nMappingIndex;
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            n_offset = Lpu237SystemStructureOffset.InfoMsr2_Keymap0_nMappingIndex;
            n_size = Lpu237SystemStructureSize.InfoMsr2_Keymap0_nMappingIndex;
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            if( !_df_set_keymap_table() )
                continue;;
            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_language_index");

        return b_result;

    }

    private boolean df_set_buzzer_frequency(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.nBuzzerFrequency;
            n_size = Lpu237SystemStructureSize.nBuzzerFrequency;

            int n_data = m_parameters.get_buzzer_frequency();
            byte[] s_data = IntByteConvert.intTobyte(n_data*10,ByteOrder.LITTLE_ENDIAN);

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_buzzer_frequency");

        return b_result;
    }

    private boolean df_set_global_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_cSize
            + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_sTag;

            Tags tag = m_parameters.get_global_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_global_prefix");

        return b_result;
    }

    private boolean df_set_global_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPostfix_sTag;

            Tags tag = m_parameters.get_global_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_global_postfix");

        return b_result;
    }

    private boolean df_set_private_prefix(int n_track){
         boolean b_result = false;

         do{
             if( n_track < 0 || n_track > 2 )
                 continue;
             //
             int n_offset =0, n_size = 0;
             if( n_track == 0){
                 n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePrefix0_cSize;
                 n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_cSize
                         + Lpu237SystemStructureSize.InfoMsr0_PrivatePrefix0_sTag;
             }
             else if( n_track == 1 ){
                 n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePrefix0_cSize;
                 n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_cSize
                         + Lpu237SystemStructureSize.InfoMsr1_PrivatePrefix0_sTag;
             }
             else if( n_track == 2 ){
                 n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePrefix0_cSize;
                 n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_cSize
                         + Lpu237SystemStructureSize.InfoMsr2_PrivatePrefix0_sTag;
             }

             Tags tag = m_parameters.get_private_prefix(n_track);
             byte[] s_data = tag.get_tag_stream_with_length();

             InPacket packet = new InPacket();
             if( !_df_set( n_offset, n_size, s_data, packet) )
                 continue;
             b_result = true;
         }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_private_prefix " + String.valueOf(n_track));

        return b_result;
    }

    private boolean df_set_private_postfix(int n_track){
        boolean b_result = false;

        do{
            if( n_track < 0 || n_track > 2 )
                continue;
            //
            int n_offset =0, n_size = 0;
            if( n_track == 0){
                n_offset = Lpu237SystemStructureOffset.InfoMsr0_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr0_PrivatePostfix0_sTag;
            }
            else if( n_track == 1 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr1_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr1_PrivatePostfix0_sTag;
            }
            else if( n_track == 2 ){
                n_offset = Lpu237SystemStructureOffset.InfoMsr2_PrivatePostfix0_cSize;
                n_size = Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_cSize
                        + Lpu237SystemStructureSize.InfoMsr2_PrivatePostfix0_sTag;
            }

            Tags tag = m_parameters.get_private_postfix(n_track);
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_private_postfix " + String.valueOf(n_track));

        return b_result;

    }

    private boolean df_set_ibutton_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPrefix_sTag;

            Tags tag = m_parameters.get_ibutton_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_prefix");

        return b_result;
    }

    private boolean df_set_ibutton_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoiButton_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoiButton_GlobalPostfix_sTag;

            Tags tag = m_parameters.get_ibutton_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_ibutton_postfix");

        return b_result;
    }

    private boolean df_set_uart_prefix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPrefix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPrefix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPrefix_sTag;

            Tags tag = m_parameters.get_uart_prefix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_uart_prefix");

        return b_result;
    }

    private boolean df_set_uart_postfix(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.InfoUart_GlobalPostfix_cSize;
            n_size = Lpu237SystemStructureSize.InfoUart_GlobalPostfix_cSize
                    + Lpu237SystemStructureSize.InfoUart_GlobalPostfix_sTag;

            Tags tag = m_parameters.get_uart_postfix();
            byte[] s_data = tag.get_tag_stream_with_length();

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;
            //
            b_result = true;
        }while(false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_uart_postfix");

        return b_result;

    }

    private boolean df_set_global_send_condition(){
        boolean b_result = false;

        do{
            int n_offset = 0, n_size = 0;
            n_offset = Lpu237SystemStructureOffset.ContainerInfoMsrObj_nGlobalTagCondition;
            n_size = Lpu237SystemStructureSize.ContainerInfoMsrObj_nGlobalTagCondition;

            int n_data = 0;
            if( m_parameters.get_global_send_condition() )
                n_data = 1;
            byte[] s_data = IntByteConvert.intTobyte(n_data,ByteOrder.LITTLE_ENDIAN);

            InPacket packet = new InPacket();
            if( !_df_set( n_offset, n_size, s_data, packet) )
                continue;

            b_result = true;
        }while (false);
        if( !b_result )
            Log.i("Lpu237","error : df_set_global_send_condition");

        return b_result;
    }
    //////////////////////////////

    public boolean get_parameter( Parameters dst ){
        boolean b_result =false;

        do{
            if( dst == null )
                continue;
            //
            m_parameters.copy_all_parameter_to_( dst );
            //
            b_result = true;
        }while(false);
        return b_result;
    }

    public boolean set_parameter( Parameters src ){
        boolean b_result =false;

        do{
            if( src == null )
                continue;
            //
            m_parameters.set_changable_parameter(src);
            //
            _set_change(Lpu237ChangedParameter.EnableTrack1);
            _set_change(Lpu237ChangedParameter.EnableTrack2);
            _set_change(Lpu237ChangedParameter.EnableTrack3);
            _set_change(Lpu237ChangedParameter.DeviceInterface);
            _set_change(Lpu237ChangedParameter.DeviceLanguage);
            _set_change(Lpu237ChangedParameter.BuzzerFrequency);
            _set_change(Lpu237ChangedParameter.GlobalPrefix);
            _set_change(Lpu237ChangedParameter.GlobalPostfix);
            _set_change(Lpu237ChangedParameter.PrivatePrefixTrack1);
            _set_change(Lpu237ChangedParameter.PrivatePostfixTrack1);
            _set_change(Lpu237ChangedParameter.PrivatePrefixTrack2);
            _set_change(Lpu237ChangedParameter.PrivatePostfixTrack2);
            _set_change(Lpu237ChangedParameter.PrivatePrefixTrack3);
            _set_change(Lpu237ChangedParameter.PrivatePostfixTrack3);
            _set_change(Lpu237ChangedParameter.iButtonPrefix);
            _set_change(Lpu237ChangedParameter.iButtonPostfix);
            _set_change(Lpu237ChangedParameter.UartPrefix);
            _set_change(Lpu237ChangedParameter.UartPostfix);
            _set_change(Lpu237ChangedParameter.GlobalSendCondition);
            _set_change(Lpu237ChangedParameter.BlankField);
            //
            b_result = true;
        }while(false);
        return b_result;
    }

    //////////////////////////////
    static class InPacket{
        public int m_n_language_index = 0;//default is english language index.
        public byte c_prefix=0;
        public byte c_result=0;
        public byte c_length=0;
        public byte[] s_data=null;

        public void set_lagnuage_index( int n_language_index ){
            m_n_language_index = n_language_index;
        }

        boolean getEnable()
        {
            boolean b_enable = false;

            do{
                if( c_length != 1 )
                    continue;
                if( s_data == null )
                    continue;
                if( s_data[0] == 0 )
                    continue;
                b_enable = true;
            }while (false);
            return b_enable;
        }

        FwVersion get_version()
        {
            FwVersion v = null;

            do{
                if( c_length != FwVersion.SIZE_VERSION )
                    continue;
                //
                v = new FwVersion( s_data );
            }while (false);
            return v;
        }

        byte[] get_name()
        {
            byte[] s_name = null;

            do{
                if( c_length != Parameters.SIZE_NAME )
                    continue;
                //
                s_name = new byte[Parameters.SIZE_NAME];
                System.arraycopy(s_data,0,s_name,0,Parameters.SIZE_NAME);
            }while(false);
            return s_name;
        }
        byte getLength()
        {
            return c_length;
        }
        byte get_byte()
        {
            byte c_result = 0;
            do{
                if( c_length != 1 )
                    continue;
                c_result = s_data[0];
            }while(false);
            return c_result;
        }
        int get_int()
        {
            int n_result = 0;
            do{
                if( c_length != 4 )
                    continue;
                n_result = IntByteConvert.byteToInt( s_data,ByteOrder.LITTLE_ENDIAN);
            }while(false);
            return n_result;
        }
        Tags get_tag()
        {
            Tags tag = new Tags();

            do{
                int n_len = (int)c_length;
                if( n_len != (Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_cSize
                        + Lpu237SystemStructureSize.ContainerInfoMsrObj_GlobalPrefix_sTag ) )
                    continue;
                n_len = (int)s_data[0];

                if( n_len > 0 ) {
                    byte[] s_tag = new byte[n_len];
                    //ascii to hidkey from index 1.
                    int n_map_start_offset = 0;

                    for( int i = 0; i<n_len/2; i++ ){
                        if( s_data[1+2*i] == (byte)0xff ){
                            //ascii code
                            n_map_start_offset = m_n_language_index*KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2;
                            n_map_start_offset += s_data[1+2*i+1];
                            s_tag[2*i] = KeyboardMap.gASCToHIDKeyMap[2*n_map_start_offset+0];
                            s_tag[2*i+1] = KeyboardMap.gASCToHIDKeyMap[2*n_map_start_offset+1];
                        }
                        else{
                            //hid code
                            s_tag[2*i] = s_data[1+2*i];
                            s_tag[2*i+1] = s_data[1+2*i+1];
                        }
                    }//end for
                    tag.set_tag(s_tag);
                }
                else
                    tag.set_tag((byte[])null);

            }while (false);
            return tag;
        }
        boolean get_condition()
        {
            boolean b_enable = false;

            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.ContainerInfoMsrObj_nGlobalTagCondition )
                    continue;
                if( s_data == null )
                    continue;
                int n_condition =IntByteConvert.byteToInt( s_data, ByteOrder.LITTLE_ENDIAN);
                if( n_condition == 0 )
                    continue;
                b_enable = true;
            }while (false);
            return b_enable;
        }

        int get_ibutton_type(){
            int n_type = Lpu237iButtonType.Zeros;

            do{
                int n_len = (int)c_length;
                if( n_len != Lpu237SystemStructureSize.cBlank )
                    continue;
                if( s_data == null )
                    continue;
                byte c_f12 = (byte)(s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_F12);
                if( c_f12 != 0 ){
                    n_type = Lpu237iButtonType.F12;
                    continue;
                }

                byte c_zers = (byte)(s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_ZEROS);
                if( c_zers != 0 ) {
                    n_type = Lpu237iButtonType.Zeros;
                    continue;
                }

                byte c_zers7 = (byte)(s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_ZEROS7);
                if( c_zers7 != 0 ) {
                    n_type = Lpu237iButtonType.Zeros7;
                    continue;
                }

                byte c_addmit = (byte)(s_data[Lpu237Info.OFFSET_IN_BLANK_OF_IBUTTON_TYPE] & Lpu237Info.MASK_IN_BLANK_OF_IBUTTON_ADDMIT);
                if( c_addmit != 0 ) {
                    n_type = Lpu237iButtonType.Addmit;
                    continue;
                }

            }while (false);
            return n_type;
        }

        boolean isPrefx()
        {
            if( c_prefix == Lpu237Response.prefix )
                return true;
            else
                return false;
        }

        boolean isSuccess()
        {
            if( c_result == Lpu237Response.resultGood || c_result == Lpu237Response.resultGoodNegative )
                return true;
            else
                return false;
        }

        boolean isPositive()
        {
            if( c_result == Lpu237Response.resultGood )
                return true;
            else
                return false;
        }

        boolean isNegative()
        {
            if( c_result == Lpu237Response.resultGoodNegative )
                return true;
            else
                return false;
        }

        public void set( final InPacket packet ){
            if( packet == null ){
                c_result = c_length = c_prefix = 0;
                if( s_data != null ){
                    s_data = null;
                }
            }
            else{
                c_prefix = packet.c_prefix;
                c_result = packet.c_result;
                c_length = packet.c_length;
                if( packet.s_data == null )
                    s_data = null;
                else{
                    s_data = new byte[packet.s_data.length];
                    System.arraycopy(packet.s_data,0,s_data,0,s_data.length);
                }
            }
        }

        public void set( byte[] rawStream ){
            do {
                c_result = c_length = c_prefix = 0;
                s_data = null;

                if (rawStream.length < 3)
                    continue;
                //
                c_prefix = rawStream[0];
                c_result = rawStream[1];
                c_length = rawStream[2];

                int n_length = (int)c_length;
                if( n_length <= 0)
                    continue;
                //
                s_data = new byte[n_length];
                System.arraycopy(rawStream,3, s_data,0, n_length);
            }while(false);
        }

        public InPacket( byte[] rawStream ){
            do {
                if (rawStream.length < 3)
                    continue;
                //
                c_prefix = rawStream[0];
                c_result = rawStream[1];
                c_length = rawStream[2];

                int n_length = (int)c_length;
                if( n_length <= 0)
                    continue;
                //
                s_data = new byte[n_length];
                System.arraycopy(rawStream,3, s_data,0, n_length);
            }while(false);
        }

        public InPacket( ByteBuffer rawStream ){
            do {
                if (rawStream.capacity() < 3)
                    continue;
                c_prefix = rawStream.get(0);
                c_result = rawStream.get(1);
                c_length = rawStream.get(2);

                int n_length = (int)c_length;
                if( n_length <= 0)
                    continue;
                //
                s_data = new byte[n_length];
                rawStream.get(s_data,3, n_length);
            }while(false);
        }

        public InPacket(){

        }
    }

    static class Tags{
        static final int NUMBER_TAG=7;
        static final int SIZE_TAG=2;
        private byte[] m_s_tag = new byte[NUMBER_TAG*SIZE_TAG];
        private int m_n_offset = 0;

        public Tags(){

        }

        public Tags( byte[] tags ){
            if( tags != null ){
                int n_length = m_s_tag.length;
                if( n_length > tags.length )
                    n_length = tags.length;
                System.arraycopy(tags,0,m_s_tag,0,n_length );
                m_n_offset = n_length;
            }
        }

        void push_back( byte c_modifier, byte c_key ){
            do{
                if( is_full() )
                    continue;
                //
                m_s_tag[m_n_offset++] = c_modifier;
                m_s_tag[m_n_offset++] = c_key;

            }while(false);
        }
        boolean is_empty(){
            if( m_n_offset <= 0 )
                return true;
            else
                return false;
        }
        boolean is_full(){
            if( m_n_offset >= m_s_tag.length )
                return true;
            else
                return false;
        }
        public int get_length(){
            return m_n_offset/2;
        }
        public void clear(){
            Arrays.fill(m_s_tag,(byte)0 );
            m_n_offset = 0;
        }
        public void set_tag( Tags tag ){
            System.arraycopy(tag.m_s_tag,0,m_s_tag, 0, tag.m_s_tag.length);
            m_n_offset = tag.m_n_offset;
        }
        public void set_tag( byte[] tags ){//tag isn't included length byte.
            clear();
            if( tags != null ){
                int n_length = m_s_tag.length;
                if( n_length > tags.length )
                    n_length = tags.length;
                System.arraycopy(tags,0,m_s_tag,0,n_length );
                m_n_offset = n_length;
            }
        }

        public byte[] get_tag() {
            return m_s_tag;
        }
        public void copy_to( Tags dst ){
            do{
                if( dst == null )
                    continue;
                //
                dst.m_n_offset = this.m_n_offset;
                System.arraycopy(this.m_s_tag,0,dst.m_s_tag,0,this.m_s_tag.length);
            }while (false);
        }

        public byte[] get_tag_stream_with_length(){
            byte[] m_s_stream = new byte[1+m_s_tag.length];
            System.arraycopy(m_s_tag,0,m_s_stream,1,m_s_tag.length);
            m_s_stream[0] = (byte)m_n_offset;
            return m_s_stream;
        }
        public boolean equal( Tags tag ){
            boolean b_equal = false;

            do{
                if( tag == null )
                    continue;

                b_equal = true;
                for( int i = 0; i<m_s_tag.length; i++ ){
                    if( m_s_tag[i] != tag.m_s_tag[i]){
                        b_equal = false;
                        break;//exit for
                    }
                }//end for

            }while(false);
            return b_equal;
        }

        @Override
        public String toString() {
            return Tools.byteArrayToHex(m_s_tag);
        }

    }

    static public class Parameters{
        static final int SIZE_UID=16;
        static final int SIZE_NAME=16;
        static final int SIZE_SERIAL_NUMBER=8;
        static final int DEFAULT_FREQUENCY_BUZZER = 2500;

        private Object m_locker = new Object();

        private byte[] m_uid = new byte[SIZE_UID];
        private byte[] m_name = new byte[SIZE_NAME];
        private boolean m_is_standard_type = false;
        private boolean m_is_ibutton_only_type = false;
        private boolean[] m_enable_track = {false,false,false};
        private FwVersion m_version_system = new FwVersion();
        private FwVersion m_version_structure = new FwVersion();
        private int m_n_interface = (int)Lpu237Interface.usbKeyboard;
        private int m_n_language_index = (int)Lpu237LanguageIndex.english;
        private int m_n_buzzer_frequency = DEFAULT_FREQUENCY_BUZZER;
        private int m_n_boot_run_time = 0;
        private Tags m_tag_global_prefix = new Tags();
        private Tags m_tag_global_postfix = new Tags();
        private Tags[] m_tag_private_prefix = new Tags[Lpu237Info.NUMBER_ISO_TRACK];
        private Tags[] m_tag_private_postfix = new Tags[Lpu237Info.NUMBER_ISO_TRACK];
        private Tags m_tag_ibutton_prefix = new Tags();
        private Tags m_tag_ibutton_postfix = new Tags();
        private Tags m_tag_uart_prefix = new Tags();
        private Tags m_tag_uart_postfix = new Tags();
        private boolean m_is_all_no_false = true;//global tag send condition.
        private int m_n_ibutton_type = Lpu237iButtonType.Zeros;
        private boolean m_b_decoder_is_mmd1000 = false;

        //
        String m_s_description = "";

        public Parameters(){
            for( int i = 0; i< m_tag_private_prefix.length; i++ ){
                m_tag_private_prefix[i] = new Tags();
                m_tag_private_postfix[i] = new Tags();
            }//end for
        }

        public void copy_all_parameter_to_( Parameters dst ){
            synchronized (m_locker) {
                do{
                    if( dst == null )
                        continue;
                    //
                    System.arraycopy(m_uid,0,dst.m_uid,0,m_uid.length);
                    System.arraycopy(m_name,0,dst.m_name,0,m_name.length);
                    dst.m_is_standard_type = m_is_standard_type;
                    dst.m_is_ibutton_only_type = m_is_ibutton_only_type;

                    int i = 0;
                    for(i=0; i<3; i++ ){
                        dst.m_enable_track[i] = m_enable_track[i];
                        m_tag_private_prefix[i].copy_to(dst.m_tag_private_prefix[i]);
                        m_tag_private_postfix[i].copy_to(dst.m_tag_private_postfix[i]);
                    }//end for
                    dst.m_version_system.set_version(m_version_system.get_version());
                    dst.m_version_structure.set_version(m_version_structure.get_version());
                    dst.m_n_interface = m_n_interface;
                    dst.m_n_language_index = m_n_language_index;
                    dst.m_n_buzzer_frequency = m_n_buzzer_frequency;
                    dst.m_n_boot_run_time = m_n_boot_run_time;

                    m_tag_global_prefix.copy_to(dst.m_tag_global_prefix);
                    m_tag_global_postfix.copy_to(dst.m_tag_global_postfix);

                    m_tag_ibutton_prefix.copy_to(dst.m_tag_ibutton_prefix);
                    m_tag_ibutton_postfix.copy_to(dst.m_tag_ibutton_postfix);

                    m_tag_uart_prefix.copy_to(dst.m_tag_uart_prefix);
                    m_tag_uart_postfix.copy_to(dst.m_tag_uart_postfix);

                    dst.m_is_all_no_false = m_is_all_no_false;
                    dst.m_n_ibutton_type = m_n_ibutton_type;
                    dst.m_b_decoder_is_mmd1000 = m_b_decoder_is_mmd1000;

                }while (false);
            }
        }

        public void set_all_parameter( Parameters src ) {
            set_changable_parameter(src);

            synchronized (m_locker) {
                System.arraycopy(src.m_uid,0,m_uid,0,m_uid.length);
                System.arraycopy(src.m_name,0,m_name,0,m_name.length);
                m_is_standard_type = src.m_is_standard_type;
                m_is_ibutton_only_type = src.m_is_ibutton_only_type;

                m_version_system.set_version(src.m_version_system.get_version());
                m_version_structure.set_version(src.m_version_structure.get_version());
                m_n_boot_run_time = src.m_n_boot_run_time;

                m_b_decoder_is_mmd1000 = src.m_b_decoder_is_mmd1000;
            }

        }
        public void set_changable_parameter( Parameters src ){
            synchronized (m_locker){
                int i = 0;
                for(i=0;i<3;i++){
                    m_enable_track[i] = src.m_enable_track[i];
                }//end for
                //
                m_n_interface = src.m_n_interface;
                m_n_language_index = src.m_n_language_index;
                m_n_buzzer_frequency = src.m_n_buzzer_frequency;

                src.m_tag_global_prefix.copy_to(this.m_tag_global_prefix);
                src.m_tag_global_postfix.copy_to(this.m_tag_global_postfix);

                for( i = 0; i<3; i++ ){
                    src.m_tag_private_prefix[i].copy_to(this.m_tag_private_prefix[i]);
                    src.m_tag_private_postfix[i].copy_to(this.m_tag_private_postfix[i]);
                }

                src.m_tag_ibutton_prefix.copy_to(this.m_tag_ibutton_prefix);
                src.m_tag_ibutton_postfix.copy_to(this.m_tag_ibutton_postfix);

                src.m_tag_uart_prefix.copy_to(this.m_tag_uart_prefix);
                src.m_tag_uart_postfix.copy_to(this.m_tag_uart_postfix);

                m_is_all_no_false = src.m_is_all_no_false;
                m_n_ibutton_type = src.m_n_ibutton_type;
            }
        }

        public void set_decoder_mmd1000( boolean b_mmd1000 ){
            synchronized (m_locker){
                m_b_decoder_is_mmd1000 = b_mmd1000;
            }
        }

        public void set_is_standard_type( boolean is_standard_type ){
            synchronized (m_locker) {
                m_is_standard_type = is_standard_type;
            }
        }

        public void set_is_ibutton_only_type( boolean is_ibutton_only_type ){
            synchronized (m_locker){
                m_is_ibutton_only_type = is_ibutton_only_type;
            }
        }

        public void set_uid( byte[] s_id ){
            synchronized (m_locker) {
                do{
                    Arrays.fill( m_uid, (byte) 0);
                    if( s_id == null )
                        continue;
                    int n_length = m_uid.length;
                    if( m_uid.length > s_id.length )
                        n_length = s_id.length;
                    //
                    for(int i = 0; i<n_length; i++ ){
                        m_uid[i] = s_id[i];
                    }
                }while (false);
            }
        }

        public void set_name( byte[] s_name ){
            synchronized (m_locker) {
                do{
                    Arrays.fill( m_name, (byte) 0);
                    if( s_name == null )
                        continue;
                    int n_length = m_name.length;
                    if( m_name.length > s_name.length )
                        n_length = s_name.length;
                    //
                    for(int i = 0; i<n_length; i++ ){
                        m_name[i] = s_name[i];
                    }
                }while (false);
            }

        }
        public boolean set_enable_track(int n_track, boolean b_enable) {
            boolean b_changed = false;
            synchronized (m_locker) {
                do {
                    if (n_track < 0 || n_track > 2)
                        continue;
                    if( m_enable_track[n_track] != b_enable ) {
                        m_enable_track[n_track] = b_enable;
                        b_changed = true;
                    }
                } while (false);
            }
            return b_changed;
        }

        public void set_version_system( FwVersion version ) {
            synchronized (m_locker) {
                m_version_system = version;
            }
        }

        public void set_version_structure( FwVersion version ){
            synchronized (m_locker) {
                m_version_structure = version;
            }
        }

        public boolean set_interface( int n_interface ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_interface != n_interface ) {
                    m_n_interface = n_interface;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_type( int n_type ){
            boolean b_changed = false;
            do {
                if (n_type < Lpu237iButtonType.Zeros || n_type > Lpu237iButtonType.Addmit)
                    continue;
                synchronized (m_locker) {
                    if( m_n_ibutton_type != n_type ) {
                        m_n_ibutton_type = n_type;
                        b_changed = true;
                    }
                }

            }while (false);
            return b_changed;
        }
        public boolean set_language_index( int n_index ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_language_index != n_index) {
                    m_n_language_index = n_index;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_buzzer_frequency( int n_buzzer ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_buzzer_frequency != n_buzzer ) {
                    m_n_buzzer_frequency = n_buzzer;
                    b_changed  = true;
                }
            }
            return b_changed;
        }
        public boolean set_boot_run_time( int n_time ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( m_n_boot_run_time != n_time) {
                    m_n_boot_run_time = n_time;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_global_prefix( Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_global_prefix.equal(tag) ){
                    m_tag_global_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_global_postfix( Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_global_postfix.equal(tag)) {
                    m_tag_global_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_private_prefix( int n_track, Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if (n_track >= 0 && n_track < 3) {
                    if( !m_tag_private_prefix[n_track].equal(tag)) {
                        m_tag_private_prefix[n_track] = tag;
                        b_changed = true;
                    }
                }
            }
            return b_changed;
        }
        public boolean set_private_postfix( int n_track, Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if (n_track >=0 && n_track < 3) {
                    if( !m_tag_private_postfix[n_track].equal(tag)) {
                        m_tag_private_postfix[n_track] = tag;
                        b_changed = true;
                    }
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_prefix( Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_prefix.equal(tag)) {
                    m_tag_ibutton_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_ibutton_postfix( Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_ibutton_postfix.equal(tag)) {
                    m_tag_ibutton_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_uart_postfix( Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_uart_postfix.equal(tag)) {
                    m_tag_uart_postfix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_uart_prefix( Tags tag ){
            boolean b_changed = false;
            synchronized (m_locker) {
                if( !m_tag_uart_prefix.equal(tag) ) {
                    m_tag_uart_prefix = tag;
                    b_changed = true;
                }
            }
            return b_changed;
        }
        public boolean set_global_send_condition( boolean b_condition ){
            boolean b_changed = false;
            synchronized (m_locker){
                if( m_is_all_no_false != b_condition ) {
                    m_is_all_no_false = b_condition;
                    b_changed = true;
                }
            }
            return b_changed;
        }

        public boolean get_decoder_mmd1000(){
            synchronized (m_locker){
                return m_b_decoder_is_mmd1000;
            }
        }
        public int get_ibutton_type(){
            synchronized (m_locker){
                return m_n_ibutton_type;
            }
        }
        public  boolean get_is_standard_type(){
            synchronized (m_locker) {
                return m_is_standard_type;
            }
        }
        public  boolean get_is_ibutton_only_type(){
            synchronized (m_locker){
                return m_is_ibutton_only_type;
            }
        }
        public byte[] get_uid(){
            synchronized (m_locker) {
                return m_uid;
            }
        }
        public byte[] get_name(){
            synchronized (m_locker){
                return m_name;
            }
        }
        public boolean get_enable_track( int n_track ){
            boolean b_enable = true;
            synchronized (m_locker){
                do{
                    if( n_track < 0 || n_track >(Lpu237Info.NUMBER_ISO_TRACK-1))
                        continue;
                    b_enable = m_enable_track[n_track];
                }while (false);
            }
            return b_enable;
        }
        public FwVersion get_version_system(){
            synchronized (m_locker){
                return m_version_system;
            }
        }
        public FwVersion get_version_structure(){
            synchronized (m_locker){
                return m_version_structure;
            }
        }
        public int get_interface(){
            synchronized (m_locker){
                return m_n_interface;
            }
        }
        public int get_language_index(){
            synchronized (m_locker){
                return m_n_language_index;
            }
        }
        public int get_buzzer_frequency(){
            synchronized (m_locker){
                return m_n_buzzer_frequency;
            }
        }
        public int get_boot_run_time(){
            synchronized (m_locker){
                return m_n_boot_run_time;
            }
        }
        public Tags get_global_prefix(){
            synchronized (m_locker){
                return m_tag_global_prefix;
            }
        }
        public Tags get_global_postfix(){
            synchronized (m_locker){
                return m_tag_global_postfix;
            }
        }
        public Tags get_private_prefix( int n_track ){
            Tags tag = null;

            synchronized (m_locker){
                do{
                    if( n_track < 0 || n_track >(Lpu237Info.NUMBER_ISO_TRACK-1))
                        continue;
                    tag = m_tag_private_prefix[n_track];
                }while(false);
            }
            return tag;
        }
        public Tags get_private_postfix( int n_track ){
            Tags tag = null;

            synchronized (m_locker){
                do{
                    if( n_track < 0 || n_track >(Lpu237Info.NUMBER_ISO_TRACK-1))
                        continue;
                    tag = m_tag_private_postfix[n_track];
                }while(false);
            }
            return tag;
        }
        public Tags get_ibutton_prefix(){
            synchronized (m_locker){
                return m_tag_ibutton_prefix;
            }
        }
        public Tags get_ibutton_postfix(){
            synchronized (m_locker){
                return m_tag_ibutton_postfix;
            }
        }
        public Tags get_uart_prefix(){
            synchronized (m_locker){
                return m_tag_uart_prefix;
            }
        }
        public Tags get_uart_postfix(){
            synchronized (m_locker){
                return m_tag_uart_postfix;
            }
        }
        public boolean get_global_send_condition(){
            synchronized (m_locker){
                return m_is_all_no_false;
            }
        }
        public String getDescription()
        {
            synchronized (m_locker){
                if( m_is_standard_type )
                    m_s_description = new String("Standard Model");
                else
                    m_s_description = new String("Compact Model");

                m_s_description += " - ";
                m_s_description += Tools.byteArrayToHex(m_uid);
                return m_s_description;
            }
        }

    }

    //
    private Parameters m_parameters = new Parameters();
}
