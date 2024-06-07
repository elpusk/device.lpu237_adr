package kr.pe.sheep_transform.lpu237_adr.lib.hidboot;

public interface HidBootLoaderRequest{
    byte cmdUnknown = 0;
    byte cmdWrite = 10;
    byte cmdRead = 20;
    byte cmdErase = 30;
    byte cmdRunApp = 40;

    //get sector info(from MH1902T), return data field : 4 bytes little endian start sector number,
    // 4 bytes little endian the number of sector(except boot area)
    byte cmdGetSectorInfo = 50;
}