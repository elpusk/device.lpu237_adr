package kr.pe.sheep_transform.lpu237_adr;

import kr.pe.sheep_transform.lpu237_adr.lib.mgmt.ManagerDevice;

public class PageGlobal extends PageTag{

    public PageGlobal(MainActivity activity,int n_dev_index ) {
        super( activity, n_dev_index,
                ManagerDevice.getInstance().lpu237_get_global_prefix()
                ,ManagerDevice.getInstance().lpu237_get_global_postfix()
                );
    }

}

/*
 */