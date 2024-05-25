package kr.pe.sheep_transform.lpu237_adr;

public class FwVersion {
    public final static int SIZE_VERSION = 4;

    private int m_n_major = 0;
    private int m_n_minor = 0;
    private int m_n_fix = 0;
    private int m_n_build = 0;

    public int get_major(){
        return m_n_major;
    }
    public int get_minor(){
        return m_n_minor;
    }
    public int get_fix(){
        return m_n_fix;
    }
    public int get_build(){
        return m_n_build;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d",m_n_major,m_n_minor,m_n_fix,m_n_build);
    }

    public byte[] get_version()
    {
        byte[] s_version = {(byte)m_n_major,(byte)m_n_minor, (byte)m_n_fix, (byte)m_n_build };
        return s_version;
    }

    public void set_version( byte[] s_version ){
        m_n_major = m_n_minor = m_n_fix = m_n_build = 0;
        if( s_version != null ){
            if( s_version.length > 0 )
                m_n_major = (int)s_version[0];
            if( s_version.length > 1)
                m_n_minor = (int)s_version[1];
            if( s_version.length > 2)
                m_n_fix = (int)s_version[2];
            if( s_version.length > 3 )
                m_n_build = (int)s_version[3];
        }
    }

    public void set_version(String version) {
        int[] v={0,0,0,0};
        do{
            if (version == null || version.isEmpty()) {
                continue;
            }
            //
            String[] parts = version.split("\\.");
            for (int i = 0; i < parts.length && i < v.length; i++) {
                if (!parts[i].isEmpty()) {
                    try {
                        v[i] = Integer.parseInt(parts[i]);
                    } catch (NumberFormatException e) {
                        // If parsing fails, leave the value as 0
                        v[i] = 0;
                    }
                }
            }//end for

        }while(false);
        m_n_major = v[0];
        m_n_minor =  v[1];
        m_n_fix =  v[2];
        m_n_build =  v[3];;
    }
    public FwVersion(){
    }

    public FwVersion( int n_major, int n_minor, int n_fix, int n_build ){
        this.m_n_major = n_major;    this.m_n_minor = n_minor;    this.m_n_fix = n_fix;    this.m_n_build = n_build;
    }

    public FwVersion( byte[] s_version ){
        set_version(s_version);
    }
    public FwVersion( String s_version ){
        set_version(s_version);
    }

    public boolean equal( FwVersion v ){
        boolean b_result = false;

        do{
            if( v==null)
                continue;
            //
            if( this.m_n_major != v.get_major() )
                continue;
            if( this.m_n_minor != v.get_minor() )
                continue;
            if( this.m_n_fix != v.get_fix() )
                continue;
            if( this.m_n_build != v.get_build() )
                continue;
            b_result = true;
        }while (false);
        return b_result;
    }

    public boolean greater( FwVersion v ){
        boolean b_result = false;

        do{
            if( v==null)
                continue;
            //
            if( this.m_n_major > v.get_major() ) {
                b_result = true;
                continue;
            }
            else if( this.m_n_major < v.get_major() )
                continue;
            if( this.m_n_minor > v.get_minor() ){
                b_result = true;
                continue;
            }
            else if( this.m_n_minor < v.get_minor() )
                continue;
            if( this.m_n_fix > v.get_fix() ){
                b_result = true;
                continue;
            }
            else if( this.m_n_fix < v.get_fix() )
                continue;
            if( this.m_n_build <= v.get_build() )
                continue;
            b_result = true;
        }while (false);
        return b_result;
    }

    public boolean less( FwVersion v ){
        boolean b_result = false;

        do{
            if( v==null)
                continue;
            //
            if( this.m_n_major < v.get_major() ) {
                b_result = true;
                continue;
            }
            else if( this.m_n_major > v.get_major() )
                continue;
            if( this.m_n_minor < v.get_minor() ){
                b_result = true;
                continue;
            }
            else if( this.m_n_minor > v.get_minor() )
                continue;
            if( this.m_n_fix < v.get_fix() ){
                b_result = true;
                continue;
            }
            else if( this.m_n_fix > v.get_fix() )
                continue;
            if( this.m_n_build >= v.get_build() )
                continue;
            b_result = true;
        }while (false);
        return b_result;
    }

    public boolean greater_then_equal(FwVersion v){
        if(greater(v)){
            return true;
        }
        if(equal(v)){
            return true;
        }
        return false;
    }
    public boolean less_then_equal(FwVersion v){
        if(less(v)){
            return true;
        }
        if(equal(v)){
            return true;
        }
        return false;
    }

}

