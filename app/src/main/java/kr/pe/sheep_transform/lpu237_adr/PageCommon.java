package kr.pe.sheep_transform.lpu237_adr;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class PageCommon {
    private AppCompatActivity m_activity = null;
    private int m_n_dev_index = 0;

    private Spinner m_spinner_interface;
    private Spinner m_spinner_buzzer;
    private Spinner m_spinner_language;
    private Spinner[] m_spinner_enable = new Spinner[3];
    private Spinner m_spinner_global_send_condition;
    private Spinner m_spinner_ibutton_mode;
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

        m_spinner_global_send_condition = (Spinner)m_activity.findViewById(R.id.id_spinner_global_send_condition);
        m_spinner_ibutton_mode = (Spinner)m_activity.findViewById(R.id.id_spinner_ibutton_function);

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
        m_spinner_ibutton_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int n_chnaged = Lpu237iButtonType.Zeros;
                switch(position){
                    case 1://Zero is transmitted 7 times When i-button is removed
                        n_chnaged = Lpu237iButtonType.Zeros7;
                        break;
                    case 2://Send F12 When i-button is removed
                        n_chnaged = Lpu237iButtonType.F12;
                        break;
                    case 3://Addimat company's Code stick
                        n_chnaged = Lpu237iButtonType.Addmit;
                        break;
                    case 4://user defined
                        n_chnaged = Lpu237iButtonType.None;
                        break;
                    case 0://Send Zeros When i-button is removed
                    default:
                        break;
                }//end switch
                ManagerDevice.getInstance().lpu237_set_ibutton_type(n_chnaged);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
}
