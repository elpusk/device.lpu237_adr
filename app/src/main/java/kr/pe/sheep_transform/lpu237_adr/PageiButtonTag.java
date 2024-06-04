package kr.pe.sheep_transform.lpu237_adr;

import androidx.appcompat.app.AppCompatActivity;//android.support.v7.app.AppCompatActivity

import kr.pe.sheep_transform.lpu237_adr.lib.mgmt.ManagerDevice;

public class PageiButtonTag extends PageTag{

    public PageiButtonTag(AppCompatActivity activity,int n_dev_index ) {
        super(activity,n_dev_index,
                ManagerDevice.getInstance().lpu237_get_ibutton_tag_prefix()
                ,ManagerDevice.getInstance().lpu237_get_ibutton_tag_postfix()
                );
    }

}
