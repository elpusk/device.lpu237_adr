package kr.pe.sheep_transform.lpu237_adr;

import android.support.v7.app.AppCompatActivity;

public class PageiButton extends PageTag{

    public PageiButton(AppCompatActivity activity,int n_dev_index ) {
        super(activity,n_dev_index,
                ManagerDevice.getInstance().lpu237_get_ibutton_prefix()
                ,ManagerDevice.getInstance().lpu237_get_ibutton_postfix()
                );
    }

}
