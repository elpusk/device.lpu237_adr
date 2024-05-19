package kr.pe.sheep_transform.lpu237_adr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;


public class PageCommon {
    private AppCompatActivity m_activity = null;
    private int m_n_dev_index = 0;

    private Spinner m_spinner_interface;
    private Spinner m_spinner_buzzer;
    private Spinner m_spinner_language;
    private Spinner[] m_spinner_enable = new Spinner[3];
    private Spinner m_spinner_reading_direction;
    private Spinner m_spinner_track_order;
    private Spinner m_spinner_mmd1100_reset_interval;
    private Spinner m_spinner_success_indication_condition;
    private Spinner m_spinner_global_send_condition;
    private Spinner m_spinner_ibutton_mode;
    private Button m_button_ibutton_extension;
    private Button m_button_set_prepostfix_opos;
    private Button m_button_set_prepostfix_type1;

    public PageCommon(AppCompatActivity activity,int n_dev_index ) {
        do {
            if( activity==null)
                continue;
            m_n_dev_index = n_dev_index;
            m_activity = activity;
            //////////////////////

        }while (false);
    }

    public void ini(){
        m_spinner_interface = (Spinner) m_activity.findViewById(R.id.id_spinner_interface);
        m_spinner_buzzer = (Spinner)m_activity.findViewById(R.id.id_spinner_buzzer);
        m_spinner_language = (Spinner)m_activity.findViewById(R.id.id_spinner_language);

        m_spinner_enable[0] = (Spinner)m_activity.findViewById(R.id.id_spinner_track1_enable);
        m_spinner_enable[1] = (Spinner)m_activity.findViewById(R.id.id_spinner_track2_enable);
        m_spinner_enable[2] = (Spinner)m_activity.findViewById(R.id.id_spinner_track3_enable);

        m_spinner_reading_direction = (Spinner)m_activity.findViewById(R.id.id_spinner_reading_direction);
        m_spinner_track_order = (Spinner)m_activity.findViewById(R.id.id_spinner_track_order);
        m_spinner_mmd1100_reset_interval = (Spinner)m_activity.findViewById(R.id.id_spinner_reset_interval);
        m_spinner_success_indication_condition = (Spinner)m_activity.findViewById(R.id.id_spinner_success_indication_condition);

        m_spinner_global_send_condition = (Spinner)m_activity.findViewById(R.id.id_spinner_global_send_condition);
        m_spinner_ibutton_mode = (Spinner)m_activity.findViewById(R.id.id_spinner_ibutton_function);

        m_button_ibutton_extension = (Button)m_activity.findViewById(R.id.id_button_extension);
        m_button_set_prepostfix_opos = (Button)m_activity.findViewById(R.id.id_button_insert_opos_sentinel);
        m_button_set_prepostfix_type1 = (Button)m_activity.findViewById(R.id.id_button_set_prepost_type1);

        int n_sel = 0;
        ////////////////////////////////////////////////////////////////////////////////////////////
        //interface .......
        String[] s_interface = {
                Lpu237InterfaceString.sUsbKeyboard,
                Lpu237InterfaceString.sUsbHid,
                Lpu237InterfaceString.sRS232
        };

        AdapterSpinnerCommon adapter_interface = new AdapterSpinnerCommon(
                m_activity,
                s_interface
        );

        m_spinner_interface.setAdapter(adapter_interface);
        m_spinner_interface.setPrompt("Interface");

        switch(ManagerDevice.getInstance().lpu237_get_interface()){
            case Lpu237Interface.usbVendorHid:
                n_sel = 1;
                break;
            case Lpu237Interface.uart:
                n_sel = 2;
                break;
            default:
                n_sel = 0;
                break;
        }//end switch
        m_spinner_interface.setSelection(n_sel);
        m_spinner_interface.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int n_chnaged = Lpu237Interface.usbKeyboard;
                switch(position){
                    case 1://hid
                        n_chnaged = Lpu237Interface.usbVendorHid;
                        break;
                    case 2://uart
                        n_chnaged = Lpu237Interface.uart;
                        break;
                    case 0://keyboard
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_interface(n_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
        //buzzer .......
        String[] s_on_off = {
                "ON", "OFF"
        };
        AdapterSpinnerCommon adapter_buzzer = new AdapterSpinnerCommon(
                m_activity,
                s_on_off
        );
        m_spinner_buzzer.setAdapter(adapter_buzzer);
        m_spinner_buzzer.setPrompt("Buzzer");
        n_sel = 0;
        if( Lpu237.Parameters.DEFAULT_FREQUENCY_BUZZER > ManagerDevice.getInstance().lpu237_get_buzzer_frequency())
            n_sel = 1;
        m_spinner_buzzer.setSelection(n_sel);
        m_spinner_buzzer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int n_chnaged = Lpu237.Parameters.DEFAULT_FREQUENCY_BUZZER;
                switch(position){
                    case 1://OFF
                        n_chnaged = 10;
                        ManagerDevice.getInstance().lpu237_set_buzzer_frequency(n_chnaged);
                        break;
                    case 0://ON
                        if( ManagerDevice.getInstance().lpu237_get_buzzer_frequency() != 2600 ){
                            ManagerDevice.getInstance().lpu237_set_buzzer_frequency(n_chnaged);
                        }
                    default:
                        break;
                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //language .......
        String[] s_language = {
                "USA English","Spanish","Danish","French","German"
                ,"Italian","Norwegian","Swedish","Hebrew","Turkey"
        };
        AdapterSpinnerCommon adapter_language = new AdapterSpinnerCommon(
                m_activity,
                s_language
        );
        m_spinner_language.setAdapter(adapter_language);
        m_spinner_language.setPrompt("Language");
        switch( ManagerDevice.getInstance().lpu237_get_language_index()){
            case Lpu237LanguageIndex.english: n_sel = 0;    break;
            case Lpu237LanguageIndex.spanish: n_sel = 1;    break;
            case Lpu237LanguageIndex.danish: n_sel = 2;     break;
            case Lpu237LanguageIndex.french: n_sel = 3;     break;
            case Lpu237LanguageIndex.german: n_sel = 4;     break;
            case Lpu237LanguageIndex.italian: n_sel = 5;    break;
            case Lpu237LanguageIndex.norwegian: n_sel = 6;  break;
            case Lpu237LanguageIndex.swedish: n_sel = 7;    break;
            case Lpu237LanguageIndex.israel: n_sel = 8;     break;
            case Lpu237LanguageIndex.turkey: n_sel = 9;     break;
            default: n_sel = 0;                             break;
        }//end switch

        m_spinner_language.setSelection(n_sel);
        m_spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int n_chnaged = Lpu237LanguageIndex.english;
                switch(position){
                    case 1://spanish
                        n_chnaged = Lpu237LanguageIndex.spanish;    break;
                    case 2://danish
                        n_chnaged = Lpu237LanguageIndex.danish;     break;
                    case 3://french
                        n_chnaged = Lpu237LanguageIndex.french;     break;
                    case 4://german
                        n_chnaged = Lpu237LanguageIndex.german;     break;
                    case 5://italian
                        n_chnaged = Lpu237LanguageIndex.italian;    break;
                    case 6://norwegian
                        n_chnaged = Lpu237LanguageIndex.norwegian;  break;
                    case 7://swedish
                        n_chnaged = Lpu237LanguageIndex.swedish;    break;
                    case 8://israel
                        n_chnaged = Lpu237LanguageIndex.israel;     break;
                    case 9://turkey
                        n_chnaged = Lpu237LanguageIndex.turkey;     break;
                    case 0://english
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_language_index(n_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        m_activity.findViewById(R.id.id_textview_language).setVisibility(View.GONE);
        m_spinner_language.setEnabled(false);
        m_spinner_language.setVisibility(View.GONE);
        ////////////////////////////////////////////////////////////////////////////////////////////
        //track enables .......
        String[] s_enable = {
           "Enable","Disable"
        };
        AdapterSpinnerCommon adapter_enable = new AdapterSpinnerCommon(
                m_activity,
                s_enable
        );
        for( int i = 0; i<3; i++ ) {
            m_spinner_enable[i].setAdapter(adapter_enable);
            m_spinner_enable[i].setPrompt("Track "+String.valueOf(i+1));
            if( ManagerDevice.getInstance().lpu237_get_enable_track(i) )
                m_spinner_enable[i].setSelection(0);
            else
                m_spinner_enable[i].setSelection(1);
        }//end for
        m_spinner_enable[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean b_chnaged = true;
                switch(position){
                    case 1://disable
                        b_chnaged = false;    break;
                    case 0://english
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_enable_track(0,b_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        m_spinner_enable[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean b_chnaged = true;
                switch(position){
                    case 1://disable
                        b_chnaged = false;    break;
                    case 0://english
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_enable_track(1,b_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        m_spinner_enable[2].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean b_chnaged = true;
                switch(position){
                    case 1://disable
                        b_chnaged = false;    break;
                    case 0://english
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_enable_track(2,b_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //reading direction .......
        String[] s_direction = {
          "Bidirection","Forward","Backward"
        };
        AdapterSpinnerCommon adapter_direction = new AdapterSpinnerCommon(
                m_activity,
                s_direction
        );
        m_spinner_reading_direction.setAdapter(adapter_direction);
        m_spinner_reading_direction.setPrompt("MSR Reading Direction");

        m_spinner_reading_direction.setSelection(
                (int)ManagerDevice.getInstance().lpu237_get_reading_direction()
        );
        m_spinner_reading_direction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                byte c_val = Lpu237Direction.biDirection;
                switch(position){
                    case 1://forward
                    case 2://backward
                    case 0://bidirection
                        c_val = (byte)position;
                        break;
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_reading_direction(c_val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //track order .......
        String[] s_order = {
                "123","132","213","231","312","321"
        };
        AdapterSpinnerCommon adapter_order = new AdapterSpinnerCommon(
                m_activity,
                s_order
        );
        m_spinner_track_order.setAdapter(adapter_order);
        m_spinner_track_order.setPrompt("MSR Track Order");

        byte[] order = ManagerDevice.getInstance().lpu237_get_track_order();
        String sOrder="";
        for(int i=0; i<order.length; i++ ){
            sOrder += String.valueOf(order[i]+1);
        }//end for i

        int n_order = 0;
        for(int i=0; i<s_order.length; i++ ){
            if(sOrder.compareTo(s_order[i])==0){
                n_order = i;
                break;
            }
        }//end for i

        m_spinner_track_order.setSelection(n_order);
        m_spinner_track_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                byte[] r = {0,1,2};
                switch(position){// "123","132","213","231","312","321"
                    case 1:
                        r[0] = 0;   r[1] = 2;   r[2] = 1;//132
                        break;
                    case 2:
                        r[0] = 1;   r[1] = 0;   r[2] = 2;//213
                        break;
                    case 3:
                        r[0] = 1;   r[1] = 2;   r[2] = 0;//231
                        break;
                    case 4:
                        r[0] = 2;   r[1] = 0;   r[2] = 1;//312
                        break;
                    case 5:
                        r[0] = 2;   r[1] = 1;   r[2] = 0;//321
                        break;
                    case 0:
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_track_order(r);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //reset interval  .......
        String[] s_reset = {
                "0(default, 03:22)",
                "16(06:43)",
                "32(13:27)",
                "48(20:10)",
                "64(26:53)",
                "80(33:36)",
                "96(40:19)",
                "112(47:03)",
                "128(53:46)",
                "144(01:00:29)",
                "160(01:07:12)",
                "176(01:13:55)",
                "192(01:20:39)",
                "208(01:27:22)",
                "224(01:34:05)",
                "240(disable)"
        };
        AdapterSpinnerCommon adapter_reset = new AdapterSpinnerCommon(
                m_activity,
                s_reset
        );
        m_spinner_mmd1100_reset_interval.setAdapter(adapter_reset);
        m_spinner_mmd1100_reset_interval.setPrompt("MSR MMD1100 Reset Interval");

        int n_rest  = ManagerDevice.getInstance().lpu237_get_mmd1100_reset_interval();
        n_rest >>= 4;
        m_spinner_mmd1100_reset_interval.setSelection(n_rest);
        m_spinner_mmd1100_reset_interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                byte c_val = (byte)position;
                if(c_val > 0x0F){
                    c_val = 0;
                }
                c_val <<= 4;
                ManagerDevice.getInstance().lpu237_set_mmd1100_reset_interval(c_val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //success indication condition .......
        String[] s_success_indication_condition = {
                "All Tracks Success",
                "Any Track Success"
        };
        AdapterSpinnerCommon adapter_success_indication_condition = new AdapterSpinnerCommon(
                m_activity,
                s_success_indication_condition
        );
        m_spinner_success_indication_condition.setAdapter(adapter_success_indication_condition);
        m_spinner_success_indication_condition.setPrompt("Success Indication Condition");

        if( ManagerDevice.getInstance().lpu237_get_any_good_indicate_success() ){
            m_spinner_success_indication_condition.setSelection(1);
        }
        else{
            m_spinner_success_indication_condition.setSelection(0);
        }
        m_spinner_success_indication_condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean b_val = false;
                if(position!=0){
                    b_val = true;
                }
                ManagerDevice.getInstance().lpu237_set_any_good_indicate_success(b_val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //sending condition .......
        String[] s_condition = {
                "No Error in all tracks", "One or more tracks are normal"
        };
        AdapterSpinnerCommon adapter_condition = new AdapterSpinnerCommon(
                m_activity,
                s_condition
        );
        m_spinner_global_send_condition.setAdapter(adapter_condition);
        m_spinner_global_send_condition.setPrompt("Global tag send condition");
        if( ManagerDevice.getInstance().lpu237_get_global_send_condition() )
            m_spinner_global_send_condition.setSelection(0);
        else
            m_spinner_global_send_condition.setSelection(1);

        m_spinner_global_send_condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean b_chnaged = true;
                switch(position){
                    case 1://One or more tracks are normal
                        b_chnaged = false;    break;
                    case 0://No Error in all tracks
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_global_send_condition(b_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        //i-button mode .......
        String[] s_ibutton = {
                "Send Zeros When i-button is removed"
                ,"Zero is transmitted 7 times When i-button is removed"
                ,"Send F12 When i-button is removed"
                ,"Addimat company's Code stick"
                ,"User definition"
        };
        AdapterSpinnerCommon adapter_ibutton = new AdapterSpinnerCommon(
                m_activity,
                s_ibutton
        );
        m_spinner_ibutton_mode.setAdapter(adapter_ibutton);
        m_spinner_ibutton_mode.setPrompt("i-Button Mode");

        n_sel = ManagerDevice.getInstance().lpu237_get_ibutton_type();
        m_spinner_ibutton_mode.setSelection(n_sel);
        if( n_sel == Lpu237iButtonType.None ){
            m_button_ibutton_extension.setEnabled(true);
        }
        else{
            m_button_ibutton_extension.setEnabled(false);
        }
        m_spinner_ibutton_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int n_chnaged = Lpu237iButtonType.Zeros;
                switch(position){
                    case 1://Zero is transmitted 7 times When i-button is removed
                        n_chnaged = Lpu237iButtonType.Zeros7;
                        m_button_ibutton_extension.setEnabled(false);
                        break;
                    case 2://Send F12 When i-button is removed
                        n_chnaged = Lpu237iButtonType.F12;
                        m_button_ibutton_extension.setEnabled(false);
                        break;
                    case 3://Addimat company's Code stick
                        n_chnaged = Lpu237iButtonType.Addmit;
                        m_button_ibutton_extension.setEnabled(false);
                        break;
                    case 4://user defined
                        n_chnaged = Lpu237iButtonType.None;
                        m_button_ibutton_extension.setEnabled(true);
                        break;
                    case 0://Send Zeros When i-button is removed
                    default:
                        m_button_ibutton_extension.setEnabled(false);
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_ibutton_type(n_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////
        // ibutton listener
        m_button_ibutton_extension.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                PageCommon.do_modal_for_ibutton_range(m_activity);
            }
        });
        ////////////////////////////////////////////
        //
        m_button_set_prepostfix_opos.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                ManagerDevice.getInstance().lpu237_set_Opos_sentinel();
            }
        });
        ////////////////////////////////////////////
        m_button_set_prepostfix_type1.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                ManagerDevice.getInstance().lpu237_set_pre_posfix_type1();
            }
        });

    }

    public static void do_modal_for_ibutton_range(AppCompatActivity activity) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Selects i-Button sending-Range");
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);

        final View Viewlayout = inflater.inflate(R.layout.dialog_ibutton_position,
                (ViewGroup) activity.findViewById(R.id.id_dialog_ibutton_position));

        alert.setView(Viewlayout);

        int n_start = ManagerDevice.getInstance().lpu237_get_ibutton_start();
        int n_end = ManagerDevice.getInstance().lpu237_get_ibutton_end();

        ////////////////////
        // TextView
        String s = "i-button Range : "+String.valueOf(n_start) +" ~ " +String.valueOf(n_end);
        TextView textViewRange = Viewlayout.findViewById(R.id.id_textview_ibutton_range);
        textViewRange.setText(s);

        //////////////////
        //dualThumbSeekBar
        DualThumbSeekBar dualThumbSeekBar = Viewlayout.findViewById(R.id.id_dualThumbSeekBar_ibutton);

        dualThumbSeekBar.setMaxMinRangeAndSelectedRange(
                15,0,
                n_start, n_end
        );
        dualThumbSeekBar.setOnRangeChangeListener(new DualThumbSeekBar.OnRangeChangeListener() {
            @Override
            public void onRangeChanged(int minValue, int maxValue) {

                String s = "i-button Range : "+ String.valueOf(minValue) + " ~ " + String.valueOf(maxValue);
                TextView textViewRange = Viewlayout.findViewById(R.id.id_textview_ibutton_range);
                textViewRange.setText(s);
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ManagerDevice.getInstance().lpu237_set_ibutton_start(dualThumbSeekBar.getSelectedMin());
                ManagerDevice.getInstance().lpu237_set_ibutton_end(dualThumbSeekBar.getSelectedMax());
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }
}
