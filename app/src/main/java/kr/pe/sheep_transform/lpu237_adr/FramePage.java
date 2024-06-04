package kr.pe.sheep_transform.lpu237_adr;

public interface FramePage{
    int PageNone = -1;
    int PageDevice = 0;
    int PageCommon = 1;
    int PageGlobal = 2;
    int PageTrack1 = 3;
    int PageTrack2 = 4;
    int PageTrack3 = 5;
    int PageiButtonTag = 6;
    int PageiButtonRemove = 7;
    int PageiButtonRemoveTag = 8;
    int PageTotal = PageiButtonRemoveTag+1;
}
