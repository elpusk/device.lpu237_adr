package kr.pe.sheep_transform.lpu237_adr;

import android.support.v7.app.AppCompatActivity;

public class PageTrack extends PageTag{
    private int m_n_track = -1;

    public PageTrack(AppCompatActivity activity, int n_dev_index, int n_track ) {
        super(activity,n_dev_index,
                ManagerDevice.getInstance().lpu237_get_private_prefix(n_track)
                ,ManagerDevice.getInstance().lpu237_get_private_postfix(n_track));
        m_n_track = n_track;
    }

}
