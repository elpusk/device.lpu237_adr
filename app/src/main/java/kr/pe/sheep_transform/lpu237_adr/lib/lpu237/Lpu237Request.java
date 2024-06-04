package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

public interface Lpu237Request{
    byte cmdChangeAuthkey = 'c';
    byte cmdChangeEnkey = 'k';
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
    byte cmdDebInterface	='Z';//debugging
    byte cmdRawMMD1000		='E';//Raw command for MMD1000
    byte cmdUartBypass		='T';//bypass data to UART. supported in version 10.0 greater then equal
    byte cmdConfigSecurity = 'a';//config security
    byte cmdGetRandom = 'r';//get true random number
    byte cmdChangeKsn = 's';//change KSN
    byte cmdExternalAuthen = 'e';//external authentication.
    byte cmdChangeInitalVector = 'i';//change initial vector for CBC mode.
    byte cmdGetSecurityChangeInfo = 't';//get security changig info

}
