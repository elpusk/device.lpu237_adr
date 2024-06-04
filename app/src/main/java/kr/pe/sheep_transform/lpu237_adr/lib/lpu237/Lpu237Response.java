package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

public interface Lpu237Response {
    byte prefix = 'R';//0x52 the prefix code of MSR response.
    byte plaintextPrefix = 'P';// the prefix code of MSR raw data.
    byte cipherPrefix = 'C';// the prefix code of MSR cipher data.

    byte resultGood = -1;
    byte resultGoodNegative = -128;//0x80;
    byte resultErrorCrc = 1;
    byte resultErrorMislength = 2;
    byte resultErrorMiskey = 3;
    byte resultErrorMisCheckBlock = 4;
    byte resultErrorinvalid = 5;
    byte resultErrorVerify = 6;

}
