package kr.pe.sheep_transform.lpu237_adr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private boolean m_b_need_finish_this_activity = false;
    private BroadcastReceiver m_recevier = null;
    private int m_n_dev_index = -1;

    /////////////////////////////
    // control area
    private Button m_button_exit;
    private Button m_button_apply;

    /////////////////////////////
    // tabs area
    private Button m_button_info;
    private Button m_button_common;
    private Button m_button_global;
    private Button[] m_button_private = new Button[Lpu237Info.NUMBER_ISO_TRACK];
    private Button m_button_ibutton;
    private HashMap<Integer,Button> m_map_tab_button = new HashMap<>();

    /////////////////////////////
    // pages area
    private int m_n_current_page = FramePage.PageNone;
    private View[] m_view_pages = new View[FramePage.PageTotal];

    private PageDevice m_page_device = null;
    private PageCommon m_page_common = null;
    private PageGlobal m_page_global = null;
    private PageTrack[] m_pages_track = new PageTrack[Lpu237Info.NUMBER_ISO_TRACK];
    private PageiButton m_page_ibutton = null;
    private PageTag m_cur_tag_page = null;

    //////////////////////////////
    // keyboard
    private TagKeyboard m_tag_keyboard;

    //////////////////////////////
    // etc
    int[] m_n_id_pre = new int[]{
            R.id.id_textview_pretag0,
            R.id.id_textview_pretag1,
            R.id.id_textview_pretag2,
            R.id.id_textview_pretag3,
            R.id.id_textview_pretag4,
            R.id.id_textview_pretag5,
            R.id.id_textview_pretag6
    };
    int[] m_n_id_post = new int[]{
            R.id.id_textview_posttag0,
            R.id.id_textview_posttag1,
            R.id.id_textview_posttag2,
            R.id.id_textview_posttag3,
            R.id.id_textview_posttag4,
            R.id.id_textview_posttag5,
            R.id.id_textview_posttag6
    };

    private DialogInterface.OnClickListener m_listener_dlg_exit_yes = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is Yes. save parameters
            dialog.dismiss();
            //
            m_b_need_finish_this_activity = true;
            //
            if( ManagerDevice.getInstance().push_requst(TypeRequest.Request_set_parameters,getBaseContext())){
                Toast.makeText(getApplicationContext(), "Please Waits Updating.......", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Failure. Update parameters", Toast.LENGTH_SHORT).show();
                _close_activity();
            }

        }
    };
    private DialogInterface.OnClickListener m_listener_dlg_exit_no = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is No.
            dialog.dismiss();
            _close_activity();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_exit_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            dialog.dismiss();
        }
    };

    private DialogInterface.OnClickListener m_listener_dlg_save_ok = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // when firmware update ok.
            dialog.dismiss();
            if( m_b_need_finish_this_activity )
                _close_activity();
        }
    };
    private DialogInterface.OnCancelListener m_listener_dlg_save_cancel = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
            if( m_b_need_finish_this_activity )
                _close_activity();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean b_permitted = false;

        do{
            switch (requestCode) {
                case PageDevice.REQUEST_LOADFROM_EXTERNAL_STORAGE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        b_permitted = true;
                    }
                    break;
            }//end switch

            if( b_permitted ){
                m_page_device.select_firmware();
                continue;
            }

            Toast.makeText(getApplicationContext(),"File Permission Failure.",Toast.LENGTH_LONG).show();
        }while(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //
        _set_tag_data();
        if( ManagerDevice.getInstance().lpu237_is_changed() ) {
            Tools.showYesNoDialog(
                    this
                    , "Close Main Activity"
                    , "This activity will be closed.\n" +
                            "Before closing this activity, Do you want to save the changed parameter?"
                    , m_listener_dlg_exit_yes
                    , m_listener_dlg_exit_no
                    , m_listener_dlg_exit_cancel
            );
        }
        else {
            _close_activity();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
        Log.i("onDestroy","MainActivity : onDestroy");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        registerReceiver();

        ManagerDevice.getInstance().set_main_activity(this);
        //

        if( !ManagerDevice.getInstance().is_waits_attach_bootloader() ) {
            if (!ManagerDevice.getInstance().push_requst(TypeRequest.Request_get_parameters, this)) {
                Tools.showOkDialogForErrorTerminate(this, "FN08", "ERROR", getResources().getString(R.string.msg_dialog_error_terminate));
            } else {
                m_tag_keyboard = new TagKeyboard(this, R.id.keyboardview);
                ini_common_button();
                ini_tab_button();
            }
        }
        Log.i("onCreate","MainActivity : onCreate");
    }

    @Override
    protected void onPause() {
        ManagerDevice.getInstance().resume_main_activity(false);
        super.onPause();
        //unregisterReceiver();
        Log.i("onPause","MainActivity : onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver();
        ManagerDevice.getInstance().resume_main_activity(true);
        Log.i("onResume","MainActivity : onResume");
    }

    public void onClickPrefixSection(View v)
    {
        if( v.getId() == R.id.id_pretag_area ) {
            View v_old = (View)findViewById(R.id.id_posttag_area);
            //v_old.setBackground(getDrawable(R.drawable.non_select_border));
            v_old.setElevation(0*this.getResources().getDisplayMetrics().density);

            //v.setBackground(getDrawable(R.drawable.select_border));
            v.setElevation(8*this.getResources().getDisplayMetrics().density);
            m_cur_tag_page.select_prefix_tag(true);
        }
    }
    public void onClickPostfixSection(View v)
    {
        if( v.getId() == R.id.id_posttag_area ) {
            View v_old = (View)findViewById(R.id.id_pretag_area);
            //v_old.setBackground(getDrawable(R.drawable.non_select_border));
            v_old.setElevation(0*this.getResources().getDisplayMetrics().density);

            //v.setBackground(getDrawable(R.drawable.select_border));
            v.setElevation(8*this.getResources().getDisplayMetrics().density);
            m_cur_tag_page.select_prefix_tag(false);
        }
    }

    private void ini_common_button(){
        //
        String s_app_version = Tools.get_app_version_name(this);
        TextView view_title = (TextView)findViewById(R.id.id_textview_title);
        s_app_version = view_title.getText() + " - " + s_app_version;
        view_title.setText( s_app_version );

        m_button_apply = (Button)findViewById(R.id.id_button_apply_connect);
        m_button_apply.setEnabled(true);
        m_button_exit = (Button)findViewById(R.id.id_button_exit);

        m_button_exit.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                _set_tag_data();
                if( ManagerDevice.getInstance().lpu237_is_changed() ) {
                    Tools.showYesNoDialog(
                            v.getContext()
                            , "Close Main Activity"
                            , "This activity will be closed.\n" +
                                    "Before closing this activity, Do you want to save the changed parameter?"
                            , m_listener_dlg_exit_yes
                            , m_listener_dlg_exit_no
                            , m_listener_dlg_exit_cancel
                    );
                }
                else{
                    _close_activity();
                }
            }

        });
        m_button_apply.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                _set_tag_data();
                //
                if( ManagerDevice.getInstance().push_requst(TypeRequest.Request_set_parameters,getBaseContext())){
                    Toast.makeText(getApplicationContext(), "Please Waits Updating.......", Toast.LENGTH_SHORT).show();
                }
                else{
                    Tools.showOkDialogForError(MainActivity.this,"FN12","ERROR",MainActivity.this.getResources().getString(R.string.msg_dialog_error));
                    //Toast.makeText(getApplicationContext(), "Failure. Update parameters", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void ini_tab_button(){
        m_button_info = (Button)findViewById(R.id.id_button_info);
        m_button_common = (Button)findViewById(R.id.id_button_common);
        m_button_global = (Button)findViewById(R.id.id_button_global);

        m_button_private[0] = (Button)findViewById(R.id.id_button_track1);
        m_button_private[1] = (Button)findViewById(R.id.id_button_track2);
        m_button_private[2] = (Button)findViewById(R.id.id_button_track3);

        m_button_ibutton = (Button)findViewById(R.id.id_button_ibutton);


        m_button_info.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageDevice );
            }
        });
        m_button_common.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageCommon );
            }
        });
        m_button_global.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageGlobal );
            }
        });
        m_button_private[0].setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageTrack1 );
            }
        });
        m_button_private[1].setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageTrack2 );
            }
        });
        m_button_private[2].setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageTrack3 );
            }
        });
        m_button_ibutton.setOnClickListener( new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                change_page( FramePage.PageiButton );
            }
        });
    }
    private void ini_main_area(){
        do{
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            FrameLayout frameLayout = (FrameLayout)findViewById(R.id.id_area_main);

            m_view_pages[FramePage.PageDevice] = inflater.inflate(R.layout.page_device,frameLayout,false);
            m_view_pages[FramePage.PageCommon] = inflater.inflate(R.layout.page_common,frameLayout,false);
            m_view_pages[FramePage.PageGlobal] = inflater.inflate(R.layout.page_global,frameLayout,false);
            m_view_pages[FramePage.PageTrack1] = inflater.inflate(R.layout.page_track,frameLayout,false);
            m_view_pages[FramePage.PageTrack2] = inflater.inflate(R.layout.page_track,frameLayout,false);
            m_view_pages[FramePage.PageTrack3] = inflater.inflate(R.layout.page_track,frameLayout,false);
            m_view_pages[FramePage.PageiButton] = inflater.inflate(R.layout.page_ibutton,frameLayout,false);

            //
            m_page_device = new PageDevice(this);
            m_page_common = new PageCommon(this, m_n_dev_index);
            m_page_global = new PageGlobal(this,m_n_dev_index);
            for( int i = 0; i<Lpu237Info.NUMBER_ISO_TRACK; i++ )
                m_pages_track[i] = new PageTrack(this,m_n_dev_index,i);
            //
            m_page_ibutton = new PageiButton(this,m_n_dev_index);

        }while (false);
    }

    private void _set_tag_data(){
        ManagerDevice.getInstance().lpu237_set_global_prefix( m_page_global.get_prefix_tag() );
        ManagerDevice.getInstance().lpu237_set_global_postfix( m_page_global.get_postfix_tag() );

        for( int i =0 ; i<3; i++ ) {
            ManagerDevice.getInstance().lpu237_set_private_prefix(i, m_pages_track[i].get_prefix_tag());
            ManagerDevice.getInstance().lpu237_set_private_postfix(i, m_pages_track[i].get_postfix_tag());
        }//end for

        ManagerDevice.getInstance().lpu237_set_ibutton_prefix( m_page_ibutton.get_prefix_tag() );
        ManagerDevice.getInstance().lpu237_set_ibutton_postfix( m_page_ibutton.get_postfix_tag() );
    }
    private void _close_activity(){
        if( m_tag_keyboard.isTagKeyboardVisible() )
            m_tag_keyboard.hideTagKeyboard();

        ManagerDevice.getInstance().set_main_activity(null);
        this.finish();
    }
    private void registerReceiver(){
        do{
            if( m_recevier != null )
                continue;
            //
            this.m_recevier = new MainActivity.DeviceBroadcastReceiver(this);

            this.registerReceiver(this.m_recevier, Tools.getIntentFilter(
                    ManagerIntentAction.INT_ALL_ACTIVITY_MAIN | ManagerIntentAction.INT_GENERAL_TERMINATE_APP
            ));
        }while(false);
    }

    private void unregisterReceiver(){
        do{
            if( m_recevier == null )
                continue;
            this.unregisterReceiver(m_recevier);
            m_recevier = null;
        }while(false);
    }

    private void ini_post( int n_dev_index ){

        enable_all_button(true);
        m_map_tab_button.clear();
        //
        m_map_tab_button.put(new Integer(FramePage.PageDevice),m_button_info);
        m_map_tab_button.put(new Integer(FramePage.PageCommon),m_button_common);

        if( !ManagerDevice.getInstance().is_startup_with_bootloader() ) {

            switch (ManagerDevice.getInstance().lpu237_get_device_type()) {
                case Lpu237DeviceType.Compact:
                    m_map_tab_button.put(new Integer(FramePage.PageGlobal), m_button_global);
                    m_map_tab_button.put(new Integer(FramePage.PageTrack1), m_button_private[0]);
                    m_map_tab_button.put(new Integer(FramePage.PageTrack2), m_button_private[1]);
                    m_map_tab_button.put(new Integer(FramePage.PageTrack3), m_button_private[2]);

                    m_button_ibutton.setEnabled(false);
                    break;
                case Lpu237DeviceType.Standard:
                    m_map_tab_button.put(new Integer(FramePage.PageGlobal), m_button_global);
                    m_map_tab_button.put(new Integer(FramePage.PageTrack1), m_button_private[0]);
                    m_map_tab_button.put(new Integer(FramePage.PageTrack2), m_button_private[1]);
                    m_map_tab_button.put(new Integer(FramePage.PageTrack3), m_button_private[2]);
                    m_map_tab_button.put(new Integer(FramePage.PageiButton), m_button_ibutton);
                    break;
                case Lpu237DeviceType.IbuttonOny:
                    m_map_tab_button.put(new Integer(FramePage.PageiButton), m_button_ibutton);

                    m_button_global.setEnabled(false);
                    m_button_private[0].setEnabled(false);
                    m_button_private[1].setEnabled(false);
                    m_button_private[2].setEnabled(false);
                    break;
                default:
                    break;
            }//end switch

            // device page item
            ini_main_area();
            change_page( FramePage.PageDevice );
            m_page_device.display_device_information(n_dev_index);
        }
        else{//startup with bootloader.
            ini_main_area();
            change_page(FramePage.PageDevice);

            m_page_device.select_firmware();
        }
    }

    private void close_pages_except_device_page(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.id_area_main);

        do {
            if (m_n_current_page == FramePage.PageNone)
                continue;
            if( m_n_current_page == FramePage.PageDevice )
                continue;
            // change page to device
            change_page( FramePage.PageDevice );
        }while (false);

    }
    private void enable_common_buttons(Boolean b_enable){
        if( m_button_apply != null )
            m_button_apply.setEnabled(b_enable);
        if(m_button_exit != null )
            m_button_exit.setEnabled(b_enable);
    }
    private void enable_tab_buttons(Boolean b_enable){
        if(m_button_info != null )
            m_button_info.setEnabled(b_enable);
        if(m_button_common != null )
            m_button_common.setEnabled(b_enable);
        if(m_button_global != null)
            m_button_global.setEnabled(b_enable);
        for( int i = 0; i<3; i++ ){
            if( m_button_private[i] != null )
                m_button_private[i].setEnabled(b_enable);
        }//end for
        if( m_button_ibutton != null )
            m_button_ibutton.setEnabled(b_enable);
    }
    private void enable_all_button(Boolean b_enable){
        enable_common_buttons(b_enable);
        enable_tab_buttons(b_enable);
    }
    private void change_page( int n_page ){

        do{
            if( n_page < FramePage.PageDevice )
                continue;
            if( n_page > FramePage.PageiButton )
                continue;
            if( m_n_current_page == n_page )
                continue;
            if( m_cur_tag_page != null ) {
                m_tag_keyboard.hideTagKeyboard();
                if( m_cur_tag_page.is_selected_prefix_tag() ) {
                    //findViewById(R.id.id_pretag_area).setBackground(getDrawable(R.drawable.non_select_border));
                    findViewById(R.id.id_pretag_area).setElevation(0*this.getResources().getDisplayMetrics().density);
                }
                else {
                    //findViewById(R.id.id_posttag_area).setBackground(getDrawable(R.drawable.non_select_border));
                    findViewById(R.id.id_posttag_area).setElevation(0*this.getResources().getDisplayMetrics().density);
                }
                //save current data.
                if( m_cur_tag_page == m_page_global ){
                    ManagerDevice.getInstance().lpu237_set_global_prefix( m_page_global.get_prefix_tag() );
                    ManagerDevice.getInstance().lpu237_set_global_postfix( m_page_global.get_postfix_tag() );
                }
                else if( m_cur_tag_page == m_page_ibutton ){
                    ManagerDevice.getInstance().lpu237_set_ibutton_prefix( m_page_ibutton.get_prefix_tag() );
                    ManagerDevice.getInstance().lpu237_set_ibutton_postfix( m_page_ibutton.get_postfix_tag() );
                }
                else{
                    for( int i = 0; i<3; i++ ){
                        if( m_cur_tag_page == m_pages_track[i] ) {
                            ManagerDevice.getInstance().lpu237_set_private_prefix(i, m_pages_track[i].get_prefix_tag());
                            ManagerDevice.getInstance().lpu237_set_private_postfix(i, m_pages_track[i].get_postfix_tag());
                        }
                    }//end for
                }
                m_cur_tag_page = null;
            }

            FrameLayout frameLayout = (FrameLayout)findViewById(R.id.id_area_main);

            if( m_n_current_page != FramePage.PageNone ) {
                frameLayout.removeViewAt(0);
                m_map_tab_button.get(m_n_current_page).setBackgroundColor(Color.TRANSPARENT);

                m_tag_keyboard.registerTagTextView((TextView[]) null, (TextView[]) null);
                //
                Iterator<Integer> keys = m_map_tab_button.keySet().iterator();
                while (keys.hasNext()){
                    m_map_tab_button.get(keys.next()).setEnabled(true);
                }//end while
            }
            //
            m_map_tab_button.get(n_page).setEnabled(false);
            m_map_tab_button.get(n_page).setBackgroundColor(getResources().getColor(R.color.color_tiffany_blue,getTheme()));
            frameLayout.addView(m_view_pages[n_page]);

            boolean b_tag_page = false;
            Lpu237.Tags tag_pre = null;
            Lpu237.Tags tag_post = null;

            switch(n_page){
                case FramePage.PageDevice:
                    m_page_device.ini();
                    break;
                case FramePage.PageCommon:
                    m_page_common.ini();
                    break;
                case FramePage.PageGlobal:
                    b_tag_page = true;
                    m_cur_tag_page = m_page_global;
                    tag_pre = ManagerDevice.getInstance().lpu237_get_global_prefix();
                    tag_post = ManagerDevice.getInstance().lpu237_get_global_postfix();
                    break;
                case FramePage.PageTrack1:
                    b_tag_page = true;
                    m_cur_tag_page = m_pages_track[n_page-FramePage.PageTrack1];
                    tag_pre = ManagerDevice.getInstance().lpu237_get_private_prefix(0);
                    tag_post = ManagerDevice.getInstance().lpu237_get_private_postfix(0);
                    break;
                case FramePage.PageTrack2:
                    b_tag_page = true;
                    m_cur_tag_page = m_pages_track[n_page-FramePage.PageTrack1];
                    tag_pre = ManagerDevice.getInstance().lpu237_get_private_prefix(1);
                    tag_post = ManagerDevice.getInstance().lpu237_get_private_postfix(1);
                    break;
                case FramePage.PageTrack3:
                    b_tag_page = true;
                    m_cur_tag_page = m_pages_track[n_page-FramePage.PageTrack1];
                    tag_pre = ManagerDevice.getInstance().lpu237_get_private_prefix(2);
                    tag_post = ManagerDevice.getInstance().lpu237_get_private_postfix(2);
                    break;
                case FramePage.PageiButton:
                    b_tag_page = true;
                    m_cur_tag_page = m_page_ibutton;
                    tag_pre = ManagerDevice.getInstance().lpu237_get_ibutton_prefix();
                    tag_post = ManagerDevice.getInstance().lpu237_get_ibutton_postfix();
                    break;
                default:
                    continue;
            }//end switch

            if( b_tag_page ){
                m_cur_tag_page.set_prefix_tag(tag_pre);
                m_cur_tag_page.set_postfix_tag(tag_post);

                TextView[][] viewItem = new TextView[2][Lpu237.Tags.NUMBER_TAG];
                int i = 0, j= 0;
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag0);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag1);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag2);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag3);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag4);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag5);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_pretag6);
                //
                j=0; i++;
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag0);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag1);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag2);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag3);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag4);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag5);
                viewItem[i][j++] = (TextView)findViewById(R.id.id_textview_posttag6);
                //
                String s_key = "";
                for( i = 0; i<2; i++ ){
                    for( j = 0; j<Lpu237.Tags.NUMBER_TAG; j++ ){
                        s_key = m_cur_tag_page.get_key(i,j);
                        viewItem[i][j].setText(s_key);
                    }//end for
                }//end for


                if( m_cur_tag_page.is_selected_prefix_tag() ) {
                    //findViewById(R.id.id_pretag_area).setBackground(getDrawable(R.drawable.select_border));
                    findViewById(R.id.id_pretag_area).setElevation(8*this.getResources().getDisplayMetrics().density);
                }
                else {
                    //findViewById(R.id.id_posttag_area).setBackground(getDrawable(R.drawable.select_border));
                    findViewById(R.id.id_posttag_area).setElevation(8*this.getResources().getDisplayMetrics().density);
                }
                //
                m_tag_keyboard.registerPage(m_cur_tag_page);
                m_tag_keyboard.registerTagTextView(m_n_id_pre,m_n_id_post);
                m_tag_keyboard.showTagKeyboard(findViewById(R.id.id_act_main));
            }

            m_n_current_page = n_page;
        }while(false);
    }

    class DeviceBroadcastReceiver extends BroadcastReceiver{
        private AppCompatActivity m_activity;

        public DeviceBroadcastReceiver() {
            super();
        }
        public DeviceBroadcastReceiver(AppCompatActivity activity) {
            super();
            this.m_activity = activity;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            do{
                if( m_activity == null )
                    continue;
                ManagerDevice.Response response = null;

                switch(Tools.getActionIntFromActionString(intent.getAction())){
                    case ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS:
                        _callback_get_parameter( context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS:
                        _callback_set_parameter( context, intent);
                        break;
                    case ManagerIntentAction.INT_ACTIVITY_MAIN_START_BOOT:
                        _callback_start_boot( context, intent);
                        break;
                    case ManagerIntentAction.INT_GENERAL_TERMINATE_APP:
                        ManagerDevice.getInstance().set_main_activity(null);
                        finish();
                    default:
                        Log.i("MainActivity::onReceive", intent.getAction());
                        break;
                }//end switch
                //
            }while(false);

        }

        private boolean _callback_get_parameter(Context context, Intent intent){
            boolean b_result =false;

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    //Toast.makeText(getApplicationContext(), "ERROR : can not the detail information from device.", Toast.LENGTH_SHORT).show();
                    Tools.showOkDialogForErrorTerminate(m_activity,"FN11","ERROR",m_activity.getResources().getString(R.string.msg_dialog_error_terminate));
                    continue;
                }
                int n_dev_index = 0;
                m_n_dev_index = n_dev_index;
                ini_post( n_dev_index );

            }while(false);
            return b_result;
        }
        private boolean _callback_set_parameter(Context context, Intent intent){
            boolean b_result =false;

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    Tools.showOkDialog(
                            context
                            , "Save parameters.- FN14"
                            , "Sorry. I can't save the changed parameters. retry again."
                            , m_listener_dlg_save_ok
                            , m_listener_dlg_save_cancel
                    );
                }
                else {
                    Tools.showOkDialog(
                            context
                            , "Save parameters."
                            , "The changed parameters have been saved successfully."
                            , m_listener_dlg_save_ok
                            , m_listener_dlg_save_cancel
                    );
                }
            }while(false);
            return b_result;
        }
        private boolean _callback_start_boot(Context context, Intent intent){
            boolean b_result =false;

            do{
                b_result = intent.getBooleanExtra(ManagerIntentAction.EXTRA_NAME_RESPONSE_BOOL_RESULT_FOR_ACTIVITY,false);
                if( !b_result ){
                    Toast.makeText(getApplicationContext(), "ERROR : Starts Bootloader.", Toast.LENGTH_SHORT).show();
                }
                else {
                    enable_all_button(false);
                    close_pages_except_device_page();
                }
            }while(false);
            return b_result;
        }

    }

}
