package kr.pe.sheep_transform.lpu237_adr;

import android.content.Context;
import android.content.Intent;

import kr.pe.sheep_transform.lpu237_adr.lib.hidboot.HidBootCallback;

public class HidBootCallbackImpl implements HidBootCallback {

    public boolean cbEraseSectorAfterDone(Object user,boolean bResult,int nCurSector,int nTotalSector){
        if (user instanceof Context) {
            Context context = (Context)user;
            //
            //send intent to updateActivitiy.
            Intent intent = new Intent(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_ERASE_INFO);
            intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR, nCurSector);
            context.sendBroadcast(intent);
        }
        return true;//always continue.
    }

    public boolean cbWriteSectorBeforeDo(Object user,short wChain, int nCurSector,int nTotalSector){
        if (user instanceof Context) {
            Context context = (Context) user;
            //send intent to updateActivitiy.
            Intent intent = new Intent(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO);
            intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR, nCurSector);
            intent.putExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_SECTOR_CHAIN, wChain);
            context.sendBroadcast(intent);
        }
        return true;//always continue.
    }
    public boolean cbWriteSectorAfterDone(Object user,boolean bResult,short wChain,int nCurSector,int nTotalSector){
        return true;//always continue.
    }

}
