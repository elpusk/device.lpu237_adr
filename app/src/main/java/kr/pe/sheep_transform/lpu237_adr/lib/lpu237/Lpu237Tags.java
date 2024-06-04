package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

import java.util.Arrays;

import kr.pe.sheep_transform.lpu237_adr.lib.util.Tools;

/**
 * YES0514
 * extended to variable size
 */
public class Lpu237Tags{
    public static final int NUMBER_IBUTTON_REMOVE_TAG=20;
    public static final int NUMBER_TAG=7;
    static final int SIZE_TAG=2;
    private byte[] m_s_tag;// = new byte[NUMBER_TAG*SIZE_TAG];
    private int m_n_offset = 0;

    public Lpu237Tags(){
        m_s_tag = new byte[NUMBER_TAG*SIZE_TAG];
    }

    public Lpu237Tags(int n_tag){
        if(n_tag>0){
            m_s_tag = new byte[n_tag*SIZE_TAG];
        }
        else{
            m_s_tag = new byte[NUMBER_TAG*SIZE_TAG];
        }

    }

    public Lpu237Tags( byte[] tags ){
        if( tags != null ){
            int n_length = m_s_tag.length;
            if( n_length > tags.length )
                n_length = tags.length;
            System.arraycopy(tags,0,m_s_tag,0,n_length );
            m_n_offset = n_length;
        }
    }

    public void push_back(byte c_modifier, byte c_key){
        do{
            if( is_full() )
                continue;
            //
            m_s_tag[m_n_offset++] = c_modifier;
            m_s_tag[m_n_offset++] = c_key;

        }while(false);
    }
    public boolean is_empty(){
        if( m_n_offset <= 0 )
            return true;
        else
            return false;
    }
    public boolean is_full(){
        if( m_n_offset >= m_s_tag.length )
            return true;
        else
            return false;
    }

    public int max_size(){
        int n_size = 0;

        if(m_s_tag!=null){
            n_size = m_s_tag.length/SIZE_TAG;
        }
        return n_size;
    }
    public void resize(int n_tag){
        m_s_tag = null;//release memory
        if(n_tag>0){
            m_s_tag = new byte[n_tag*SIZE_TAG];
        }
        else{
            m_s_tag = new byte[NUMBER_TAG*SIZE_TAG];
        }
    }

    public int get_length(){
        return m_n_offset/2;
    }
    public void clear(){
        Arrays.fill(m_s_tag,(byte)0 );
        m_n_offset = 0;
    }
    public void set_tag( Lpu237Tags tag ){
        System.arraycopy(tag.m_s_tag,0,m_s_tag, 0, tag.m_s_tag.length);
        m_n_offset = tag.m_n_offset;
    }
    public void set_tag( byte[] tags ){//tag isn't included length byte.
        clear();
        if( tags != null ){
            int n_length = m_s_tag.length;
            if( n_length > tags.length )
                n_length = tags.length;
            System.arraycopy(tags,0,m_s_tag,0,n_length );
            m_n_offset = n_length;
        }
    }

    public byte[] get_tag() {
        return m_s_tag;
    }
    public void copy_to( Lpu237Tags dst ){
        do{
            if( dst == null )
                continue;
            //
            dst.m_n_offset = this.m_n_offset;
            System.arraycopy(this.m_s_tag,0,dst.m_s_tag,0,this.m_s_tag.length);
        }while (false);
    }

    public byte[] get_tag_stream_with_length(){
        byte[] m_s_stream = new byte[1+m_s_tag.length];
        System.arraycopy(m_s_tag,0,m_s_stream,1,m_s_tag.length);
        m_s_stream[0] = (byte)m_n_offset;
        return m_s_stream;
    }
    public boolean equal( Lpu237Tags tag ){
        boolean b_equal = false;

        do{
            if( tag == null )
                continue;

            if( m_s_tag.length != tag.m_s_tag.length){
                continue;
            }
            b_equal = true;
            for( int i = 0; i<m_s_tag.length; i++ ){
                if( m_s_tag[i] != tag.m_s_tag[i]){
                    b_equal = false;
                    break;//exit for
                }
            }//end for

        }while(false);
        return b_equal;
    }

    @Override
    public String toString() {
        return Tools.byteArrayToHex(m_s_tag);
    }

}