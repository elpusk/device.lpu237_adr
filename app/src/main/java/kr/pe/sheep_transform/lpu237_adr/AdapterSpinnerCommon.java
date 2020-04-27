package kr.pe.sheep_transform.lpu237_adr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterSpinnerCommon extends BaseAdapter {

    private LayoutInflater m_inflater;
    private Context m_context;
    private String[] m_s_data;

    public AdapterSpinnerCommon(Context context, String[] s_data ){
        m_context = context;
        m_s_data = s_data;
        m_inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(m_s_data!=null)
            return m_s_data.length;
        else
            return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = m_inflater.inflate(R.layout.title_spinner_normal, parent, false);
        }

        if(m_s_data!=null){
            String text = m_s_data[position];
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = m_inflater.inflate(R.layout.title_spinner_dropdown, parent, false);
        }

        String text = m_s_data[position];
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return m_s_data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
