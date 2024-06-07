package kr.pe.sheep_transform.lpu237_adr;

import androidx.appcompat.app.AppCompatActivity;//android.support.v7.app.AppCompatActivity

import kr.pe.sheep_transform.lpu237_adr.lib.util.Tools;
import kr.pe.sheep_transform.lpu237_adr.lib.lpu237.Lpu237Tags;

public class PageTag {
    protected AppCompatActivity m_activity = null;
    protected int m_n_dev_index = -1;

    protected Lpu237Tags m_tag_prefix = null;
    protected Lpu237Tags m_tag_postfix = null;
    protected boolean m_b_selected_prefix = true;

    public PageTag(AppCompatActivity activity,int n_dev_index ) {
        do {
            m_tag_prefix = new Lpu237Tags();
            m_tag_postfix = new Lpu237Tags();

            if( activity==null)
                continue;
            m_activity = activity;
            m_n_dev_index = n_dev_index;
            //
        }while (false);
    }

    public PageTag(AppCompatActivity activity, int n_dev_index, Lpu237Tags tag_pre, Lpu237Tags tag_post) {
        this( activity,n_dev_index);
        do {
            if( activity==null)
                continue;
            set_prefix_tag(tag_pre);
            set_postfix_tag(tag_post);
        }while (false);
    }

    public PageTag(AppCompatActivity activity, int n_dev_index, Lpu237Tags tag_pre) {
        do {
            m_tag_prefix = new Lpu237Tags(Lpu237Tags.NUMBER_IBUTTON_REMOVE_TAG);
            m_tag_postfix = new Lpu237Tags(Lpu237Tags.NUMBER_IBUTTON_REMOVE_TAG);

            if( activity==null)
                continue;
            m_activity = activity;
            m_n_dev_index = n_dev_index;
            set_prefix_tag(tag_pre);
        }while (false);
    }

    public void select_prefix_tag( boolean b_selected_prefix ){
        m_b_selected_prefix = b_selected_prefix;
    }
    public boolean is_selected_prefix_tag(){
        return m_b_selected_prefix;
    }
    public Lpu237Tags get_tag(){
        if( m_b_selected_prefix)
            return get_prefix_tag();
        else
            return get_postfix_tag();
    }
    public Lpu237Tags get_prefix_tag(){
        return m_tag_prefix;
    }
    public Lpu237Tags get_postfix_tag(){
        return m_tag_postfix;
    }

    public void set_prefix_tag( Lpu237Tags tag ){
        do{
            if( tag == null)
                continue;
            m_tag_prefix.set_tag(tag);
        }while(false);
    }
    public void set_postfix_tag( Lpu237Tags tag ){
        do{
            if( tag == null)
                continue;
            m_tag_postfix.set_tag(tag);
        }while(false);
    }
    public void set_tag( Lpu237Tags tag ){
        if( m_b_selected_prefix )
            set_prefix_tag(tag);
        else
            set_postfix_tag(tag);
    }
    public void clear_tag(){
        if( m_b_selected_prefix )
            clear_prefix_tag();
        else
            clear_postfix_tag();
    }
    public void clear_prefix_tag(){
        m_tag_prefix.clear();
    }
    public void clear_postfix_tag(){
        m_tag_postfix.clear();
    }

    public boolean is_full_tag(){
        if( m_b_selected_prefix )
            return is_full_prefix_tag();
        else
            return is_full_postfix_tag();
    }
    public boolean is_full_prefix_tag(){
        return m_tag_prefix.is_full();
    }
    public boolean is_full_postfix_tag(){
        return m_tag_postfix.is_full();
    }

    public boolean is_empty_tag(){
        if( m_b_selected_prefix )
            return is_empty_prefix_tag();
        else
            return is_empty_postfix_tag();
    }
    public boolean is_empty_prefix_tag(){
        return m_tag_prefix.is_empty();
    }
    public boolean is_empty_postfix_tag(){
        return m_tag_postfix.is_empty();
    }

    public void push_back_tag( byte c_modifier, byte c_key ){
        if( m_b_selected_prefix )
            push_back_prefix_tag(c_modifier, c_key);
        else
            push_back_postfix_tag(c_modifier, c_key);
    }
    public void push_back_prefix_tag(byte c_modifier, byte c_key ){
        m_tag_prefix.push_back( c_modifier, c_key );
    }
    public void push_back_postfix_tag(byte c_modifier, byte c_key ){
        m_tag_postfix.push_back( c_modifier, c_key);
    }

    public int get_length_tag(){
        if( m_b_selected_prefix)
            return get_length_prefix_tag();
        else
            return get_length_postfix_tag();
    }
    public int get_length_prefix_tag(){
        return m_tag_prefix.get_length();
    }
    public int get_length_postfix_tag(){
        return m_tag_postfix.get_length();
    }

    public String get_key( int n_pre_or_post, int n_index ){
        String s_key = "";
        do{
            if( n_pre_or_post != 0 && n_pre_or_post != 1 )
                continue;
            if( n_index < 0 ) {
                continue;
            }
            //
            Lpu237Tags tag = m_tag_postfix;
            if( n_pre_or_post == 0 )
                tag = m_tag_prefix;
            //
            if( tag == null ) {
                continue;
            }
            if(n_index >= tag.max_size()){
                continue;
            }
            //
            byte[] s_tag = tag.get_tag();
            if( s_tag == null )
                continue;
            byte c_modifier = s_tag[2*n_index];
            byte c_key = s_tag[2*n_index+1];
            if( c_key == 0 && c_modifier == 0 )
                continue;
            //
            s_key = Tools.StringOfHidKey(c_modifier,c_key);
        }while (false);
        return s_key;
    }
}
