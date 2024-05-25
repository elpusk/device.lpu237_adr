package kr.pe.sheep_transform.lpu237_adr;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;//android.support.v7.widget.RecyclerView
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class DeviceRecyclerAdapter
        extends RecyclerView.Adapter<DeviceRecyclerAdapter.DeviceViewHolder>
        implements OnListItemClickListener{

    Context context;

    public DeviceRecyclerAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.device_recycler_view
                ,parent
                ,false
        );
        DeviceViewHolder holder =  new DeviceViewHolder(itemView);
        holder.setClickListener( this );
        return holder;
    }


    // View 의 내용을 해당 포지션의 데이터로 바꿉니다.
    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {

        ManagerDevice.getInstance().select_lpu237(position);
        holder.m_tvDeivceDescription.setText(ManagerDevice.getInstance().lpu237_getDescription());

    }

    // 데이터 셋의 크기를 리턴해줍니다.
    @Override
    public int getItemCount()
    {
        return ManagerDevice.getInstance().size_lpu237();
    }

    @Override
    public void onListItemClick(int n_pos) {
        Toast.makeText(
                context,
                " Please Waits.......\n Loading the device data." ,
                Toast.LENGTH_SHORT).show();
        //
        ManagerDevice.getInstance().select_lpu237(0);
        Tools.start_main_activity(context);
    }

    static public class DeviceViewHolder extends RecyclerView.ViewHolder
    {
        private TextView m_tvDeivceDescription;
        OnListItemClickListener clickListener;

        public DeviceViewHolder(View itemView) {
            super(itemView);
            m_tvDeivceDescription = (TextView) itemView.findViewById(R.id.id_textview_item_device);
            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickListener.onListItemClick(getAdapterPosition());
                    }
                }

            );
        }

        public void setClickListener(OnListItemClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }
}
