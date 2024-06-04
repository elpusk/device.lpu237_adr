package kr.pe.sheep_transform.lpu237_adr;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237Tags;
import kr.pe.sheep_transform.lpu237_adr.lib.util.KeyboardConst;
import kr.pe.sheep_transform.lpu237_adr.lib.util.Tools;

/**
 * Keyboard surfaces
 * <br>
 * Keyboard[language][shift off/on][key-mode]
 */

public class TagKeyboard implements KeyboardView.OnKeyboardActionListener{
    private final static int PrefixIndex = 0;
    private final static int PostfixIndex = 1;
    /** A link to the KeyboardView that is used to render this CustomKeyboard. */
    private KeyboardView m_KeyboardView;
    /** A link to the activity that hosts the {@link #m_KeyboardView}. */
    private Activity m_HostActivity;
    private TextView[][] m_views_pre_post = new TextView[2][Lpu237Tags.NUMBER_TAG];
    private int m_n_view_id = -1;
    private Keyboard[][][] m_keyboards;
    private int m_n_cur_keylayout_id = R.xml.kb_en_abc_lower;
    private PageTag m_page = null;

    private ListView m_list_view_of_ibutton_remove;//for ibutton remove

    private ArrayList<String> m_arraylist_of_list_ibutton_remove;
    private ArrayAdapter<String> m_adapter_of_list_ibutton_remove;


    public final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
    public final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
    public final static int CodePrev     = 55000;
    public final static int CodeAllLeft  = 55001;
    public final static int CodeLeft     = 55002;
    public final static int CodeRight    = 55003;
    public final static int CodeAllRight = 55004;
    public final static int CodeNext     = 55005;
    private final static int CodeLeftCtrl = -20;
    private final static int CodeSymbol = -30;
    private final static int CodeAbc = -40;
    public final static int CodeClear = -50;

    public final static int LanguageIndexEnglish = 0;
    public final static int LanguageIndexLast = LanguageIndexEnglish;

    public final static int KeyboardShiftOff = 0;
    public final static int KeyboardShiftOn = 1;
    public final static int KeyboardShiftLast = KeyboardShiftOn;

    public final static int KeyboardModeAbc = 0;
    public final static int KeyboardModeSymbol = 1;
    public final static int KeyboardModeLast = KeyboardModeSymbol;
    //
    private int m_n_cur_language = LanguageIndexEnglish;
    private int m_n_shift = KeyboardShiftOff;
    private boolean m_b_ctrl = false;
    private boolean m_b_alt = false;
    private int m_n_cur_key_mode = KeyboardModeAbc;//abc or symbol.

    @Override public void onKey(int primaryCode, int[] keyCodes) {
        // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this list in primaryCode
        // Get the EditText and its Editable
        int n_prepost_index = PrefixIndex;
        do{
            //
            switch(primaryCode){
                case Keyboard.KEYCODE_SHIFT:
                    setShiftKey( !IsShiftOn() );
                    m_KeyboardView.setKeyboard(m_keyboards[m_n_cur_language][m_n_shift][m_n_cur_key_mode]);
                    //m_KeyboardView.invalidateAllKeys();
                    Log.i("TagKeyboard:onKey",Character.toString((char) primaryCode));
                    break;
                case Keyboard.KEYCODE_ALT:
                    setAtlKey(!IsAltOn());
                    Log.i("TagKeyboard:onKey",Character.toString((char) primaryCode));
                    break;
                case CodeLeftCtrl:
                    setCtrlKey(!IsCtrlOn());
                    Log.i("TagKeyboard:onKey",Character.toString((char) primaryCode));
                    break;
                case CodeSymbol:
                    setKeyboardMode(KeyboardModeSymbol);
                    m_KeyboardView.setKeyboard(m_keyboards[m_n_cur_language][m_n_shift][m_n_cur_key_mode]);
                    Log.i("TagKeyboard:onKey","CodeSymbol");
                    break;
                case CodeAbc:
                    setKeyboardMode(KeyboardModeAbc);
                    m_KeyboardView.setKeyboard(m_keyboards[m_n_cur_language][m_n_shift][m_n_cur_key_mode]);
                    Log.i("TagKeyboard:onKey","CodeAbc");
                    break;
                case CodeClear:
                    clear_tag();
                    break;
                case CodeCancel:
                    hideTagKeyboard();
                    break;
                case CodeDelete:
                    break;
                case CodeLeft:
                    break;
                case CodeRight:
                    break;
                case CodeAllLeft:
                    break;
                case CodeAllRight:
                    break;
                case CodePrev:
                    break;
                case CodeNext:
                    break;
                case 0x37:
                    break;
                default:
                    if( m_page == null ) {
                        break;
                    }

                    byte c_modifiter = 0;

                    if( m_b_ctrl )
                        c_modifiter |= KeyboardConst.HIDKEY_MOD_L_CTL;
                    if( m_n_shift == KeyboardShiftOn )
                        c_modifiter |= KeyboardConst.HIDKEY_MOD_L_SFT;
                    if( m_b_alt )
                        c_modifiter |= KeyboardConst.HIDKEY_MOD_L_ALT;
                    //
                    String s_data = Tools.StringOfHidKey(c_modifiter,(byte) (0xFF & primaryCode));

                    if( !m_page.is_full_tag() ) {
                        if( !m_page.is_selected_prefix_tag())
                            n_prepost_index = PostfixIndex;
                        if(m_views_pre_post != null) {
                            m_views_pre_post[n_prepost_index][m_page.get_length_tag()].setText(s_data);
                        }
                        else{//using list
                            m_arraylist_of_list_ibutton_remove.add(s_data);
                            m_adapter_of_list_ibutton_remove.notifyDataSetChanged();
                        }
                        m_page.push_back_tag(c_modifiter, (byte) (0xff&primaryCode));
                    }

                    Log.i("TagKeyboard:onKey",s_data);
                    break;
            }
        }while(false);


    }
    @Override public void onPress(int arg0) {
    }
    @Override public void onRelease(int primaryCode) {
    }
    @Override public void onText(CharSequence text) {
        Log.d("DEBUG", "input text: " + text);
        int intValue = Integer.parseInt( text.toString(), 16 );
        Log.d("DEBUG", "input text CV: " + String.valueOf(intValue) );
    }
    @Override public void swipeDown() {
    }
    @Override public void swipeLeft() {
    }
    @Override public void swipeRight() {
    }
    @Override public void swipeUp() {
    }

    /**
     * Create a custom keyboard, that uses the KeyboardView (with resource id <var>viewid</var>) of the <var>host</var> activity,
     * and load the keyboard layout from xml file <var>layoutid</var> (see {@link Keyboard} for description).
     * Note that the <var>host</var> activity must have a <var>KeyboardView</var> in its layout (typically aligned with the bottom of the activity).
     * Note that the keyboard layout xml file may include key codes for navigation; see the constants in this class for their values.
     * Note that to enable EditText's to use this custom keyboard, call the {@link #registerEditText(int)}.
     *
     * @param host The hosting activity.
     * @param viewid The id of the KeyboardView.
     */
    public TagKeyboard(Activity host, int viewid) {
        m_HostActivity = host;

        //Keyboard[language][shift off/on][key-mode]
        m_keyboards = new Keyboard[LanguageIndexLast+1][KeyboardShiftLast+1][KeyboardModeLast+1];

        m_keyboards[LanguageIndexEnglish][KeyboardShiftOff][KeyboardModeAbc] = new Keyboard(m_HostActivity,R.xml.kb_en_abc_lower);
        m_keyboards[LanguageIndexEnglish][KeyboardShiftOn][KeyboardModeAbc] = new Keyboard(m_HostActivity,R.xml.kb_en_abc_upper);

        m_keyboards[LanguageIndexEnglish][KeyboardShiftOff][KeyboardModeSymbol] = new Keyboard(m_HostActivity,R.xml.kb_en_sym_lower);
        m_keyboards[LanguageIndexEnglish][KeyboardShiftOn][KeyboardModeSymbol] = new Keyboard(m_HostActivity,R.xml.kb_en_sym_upper);

        m_KeyboardView = (KeyboardView) m_HostActivity.findViewById(viewid);
        m_KeyboardView.setKeyboard(m_keyboards[m_n_cur_language][m_n_shift][m_n_cur_key_mode]);
        m_KeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        m_KeyboardView.setOnKeyboardActionListener(this);

        // Hide the standard keyboard initially
        m_HostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void clear_tag(){
        do {
            int n_prepost_index = PrefixIndex;
            int i = 0;

            if (m_page == null)
                continue;
            m_page.clear_tag();
            //
            if( !m_page.is_selected_prefix_tag())
                n_prepost_index = PostfixIndex;

            if(m_views_pre_post == null){
                m_arraylist_of_list_ibutton_remove.clear();
                m_adapter_of_list_ibutton_remove.notifyDataSetChanged();
                continue;
            }
            for( i =0 ; i< m_views_pre_post[n_prepost_index].length; i++  ) {
                m_views_pre_post[n_prepost_index][i].setText("");

            }//end for

        }while (false);
    }

    private void setKeyboardMode( int n_mode ){
        do{
            if( n_mode != KeyboardModeAbc && n_mode != KeyboardModeSymbol )
                continue;
            m_n_cur_key_mode = n_mode;
        }while(false);
    }
    private void setShiftKey( boolean b_on ){
        do{
            if( b_on ){
                if( m_n_shift == KeyboardShiftOn )
                    continue;
                else
                    m_n_shift = KeyboardShiftOn;
            }
            else{
                if( m_n_shift == KeyboardShiftOff )
                    continue;
                else
                    m_n_shift = KeyboardShiftOff;
            }
            //
            int i = 0, j = 0, k = 0;

            for( i = 0; i<LanguageIndexLast+1; i++ ){
                for( j = 0; j<KeyboardShiftLast+1; j++ ){
                    for( k=0; k<KeyboardModeLast+1; k++ ){
                        m_keyboards[i][j][k].setShifted(b_on);
                    }//end for k
                }//end for j
            }//end for i


        }while(false);
    }
    private void setCtrlKey( boolean b_on ){
        do {
            if( b_on ){
                if( m_b_ctrl )
                    continue;
            }
            else{
                if( !m_b_ctrl )
                    continue;
            }
            //
            m_b_ctrl = b_on;
            //

            int i = 0, j = 0, k = 0;

            for (i = 0; i < LanguageIndexLast + 1; i++) {
                for (j = 0; j < KeyboardShiftLast + 1; j++) {
                    for (k = 0; k < KeyboardModeLast + 1; k++) {
                        List<Keyboard.Key> keys = m_keyboards[i][j][k].getKeys();

                        Iterator iterator = keys.iterator();
                        while (iterator.hasNext()) {
                            Keyboard.Key key = (Keyboard.Key) iterator.next();
                            if( key.codes[0] == CodeLeftCtrl ){
                                key.on = b_on;
                            }
                        }
                    }//end for k
                }//end for j
            }//end for i
        }while(false);
    }
    private void setAtlKey( boolean b_on ){
        do {
            if( b_on ){
                if( m_b_alt )
                    continue;
            }
            else{
                if( !m_b_alt )
                    continue;
            }
            //
            m_b_alt = b_on;
            //

            int i = 0, j = 0, k = 0;

            for (i = 0; i < LanguageIndexLast + 1; i++) {
                for (j = 0; j < KeyboardShiftLast + 1; j++) {
                    for (k = 0; k < KeyboardModeLast + 1; k++) {
                        List<Keyboard.Key> keys = m_keyboards[i][j][k].getModifierKeys();

                        Iterator iterator = keys.iterator();
                        while (iterator.hasNext()) {
                            Keyboard.Key key = (Keyboard.Key) iterator.next();
                            if( key.codes[0] == Keyboard.KEYCODE_ALT ){
                                key.on = b_on;
                            }
                        }
                    }//end for k
                }//end for j
            }//end for i
        }while(false);
    }
    public boolean IsShiftOn(){
        if( m_n_shift == KeyboardShiftOn )
            return true;
        else
            return false;
    }
    public boolean IsCtrlOn(){
        return m_b_ctrl;
    }
    public boolean IsAltOn(){
        return m_b_alt;
    }
    public int getKeyboardMode(){
        return m_n_cur_key_mode;
    }


    /** Returns whether the CustomKeyboard is visible. */
    public boolean isTagKeyboardVisible() {
        return m_KeyboardView.getVisibility() == View.VISIBLE;
    }

    /** Make the CustomKeyboard visible, and hide the system keyboard for view v. */
    public void showTagKeyboard( View v ) {
        m_KeyboardView.setVisibility(View.VISIBLE);
        m_KeyboardView.setEnabled(true);
        if( v!=null ) {
            ((InputMethodManager) m_HostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /** Make the CustomKeyboard invisible. */
    public void hideTagKeyboard() {
        m_KeyboardView.setVisibility(View.GONE);
        m_KeyboardView.setEnabled(false);
    }

    /**
     * Register <var>EditText<var> with resource id <var>resid</var> (on the hosting activity) for using this custom keyboard.
     *
     * @param resid The resource id of the EditText that registers to the custom keyboard.
     */
    public void registerEditText(int resid) {
        // Find the EditText 'resid'
        EditText edittext= (EditText) m_HostActivity.findViewById(resid);
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showTagKeyboard(v);
                else hideTagKeyboard();
            }
        });
        edittext.setOnClickListener(new OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override public void onClick(View v) {
                showTagKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    public void registerPage( PageTag page ){
        m_page = page;
    }
    public void registerTagTextView(TextView[] views_pre, TextView[] views_post ){
        do{
            int i = 0;
            int n_view = 0;

            if(m_views_pre_post == null){
                m_arraylist_of_list_ibutton_remove.clear();
                m_adapter_of_list_ibutton_remove.notifyDataSetChanged();
                continue;
            }

            for( i=0; i<Lpu237Tags.NUMBER_TAG; i++ ){
                m_views_pre_post[PrefixIndex][i] = null;
            }//end for

            if( views_pre != null ){
                n_view = views_pre.length;
                if( views_pre.length > m_views_pre_post[PrefixIndex].length )
                    n_view = m_views_pre_post[PrefixIndex].length;

                for( i=0; i<n_view; i++ ){
                    m_views_pre_post[PrefixIndex][i] = views_pre[i];
                }//end for
            }
            //
            for( i=0; i<Lpu237Tags.NUMBER_TAG; i++ ){
                m_views_pre_post[PostfixIndex][i] = null;
            }//end for

            if( views_post != null ){
                n_view = views_post.length;
                if( views_post.length > m_views_pre_post[PostfixIndex].length )
                    n_view = m_views_pre_post[PostfixIndex].length;

                for( i=0; i<n_view; i++ ){
                    m_views_pre_post[PostfixIndex][i] = views_post[i];
                }//end for
            }

        }while(false);
    }

    public void registerTagTextView(int[] n_id_views_pre, int[] n_id_views_post ){
        do{
            int i = 0;
            int n_view = 0;

            if( m_HostActivity == null )
                continue;
            TextView[] views_pre = new TextView[Lpu237Tags.NUMBER_TAG];
            TextView[] views_post = new TextView[Lpu237Tags.NUMBER_TAG];
            if( n_id_views_pre != null ){
                n_view = n_id_views_pre.length;
                if( n_id_views_pre.length > views_pre.length )
                    n_view = views_pre.length;

                for( i=0; i<n_view; i++ ){
                    views_pre[i] = (TextView)m_HostActivity.findViewById(n_id_views_pre[i]);
                }//end for
            }
            if( n_id_views_post != null ){
                n_view = n_id_views_post.length;
                if( n_id_views_post.length > views_post.length )
                    n_view = views_post.length;

                for( i=0; i<n_view; i++ ){
                    views_post[i] = (TextView)m_HostActivity.findViewById(n_id_views_post[i]);
                }//end for
            }

            m_views_pre_post = new TextView[2][Lpu237Tags.NUMBER_TAG];
            registerTagTextView( views_pre, views_post);
        }while(false);
    }

    public void registerTagListView(
            int n_id_list_view,
            ArrayList<String> array_list_of_list_view

    ){
        do{
            if( m_HostActivity == null ) {
                continue;
            }

            m_arraylist_of_list_ibutton_remove = new ArrayList<>( array_list_of_list_view );//copy
            m_adapter_of_list_ibutton_remove = new ArrayAdapter<String>(m_HostActivity,
                    android.R.layout.simple_list_item_1, m_arraylist_of_list_ibutton_remove);
            //
            m_views_pre_post = null;
            m_list_view_of_ibutton_remove = (ListView)m_HostActivity.findViewById(n_id_list_view);
            m_list_view_of_ibutton_remove.setAdapter(m_adapter_of_list_ibutton_remove);
            m_adapter_of_list_ibutton_remove.notifyDataSetChanged();
        }while(false);
    }

}
