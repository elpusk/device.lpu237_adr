package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

abstract public class Lpu237GetSetCallback {
    /**
     * if m_b_get is true, this callback instance will be used for getting,
     * else setting.
     */
    protected boolean m_b_get = true;

    abstract public boolean Run(Lpu237Callback.Result result, String sDescription, int nCurZeroBaseStep, int nTotalStep );

    public Lpu237GetSetCallback(boolean b_get){
        m_b_get = b_get;
    }
    public boolean is_getting_type(){
        return m_b_get;
    }

}
