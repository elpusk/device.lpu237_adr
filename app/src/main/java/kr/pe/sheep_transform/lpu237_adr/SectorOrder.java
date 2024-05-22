package kr.pe.sheep_transform.lpu237_adr;

/**
 * this class manages writing & erasing sector oder
 */
public class SectorOrder {
    private int[] m_order;
    int m_n_start_sector = -1;//the starting sector of operation.
    int m_n_will_be_operated_index = -1;//sector index which the operation will be processed
    int m_n_min_sector = -1;//the minimum sector number of m_order.
    public SectorOrder(){
        m_order = new int[]{//liner order
                1,2,3,4,5,6,7
        };

        m_n_start_sector = m_order[0];
        m_n_will_be_operated_index = 0;
        m_n_min_sector = 1;
    }

    /**
     * increase the current index
     * @return the increased index.
     */
    boolean increased_index(){
        boolean b_result = false;
        do {
            if (m_n_will_be_operated_index < 0)
                continue;
            if (m_n_will_be_operated_index - 1 == m_order.length) {
                continue;
            }
            ++m_n_will_be_operated_index;
            b_result = true;
        }while (false);
        return b_result;
    }
    boolean reset_index(){
        boolean b_result = false;

        do{
            m_n_will_be_operated_index = -1;
            if(m_order == null)
                continue;
            if(m_order.length<=0)
                continue;
            //
            m_n_will_be_operated_index = 0;
        }while(false);
        return b_result;
    }

    /**
     * @param n_sector_index, if this is negative, return sector number of current index
     * @return negative : none order, 0 or positive : success
     */
    public int get_sector_number(int n_sector_index ){
        int n_sec = -1;

        do{
            if( m_order == null ){
                continue;
            }
            if(n_sector_index<0){
                if(m_n_will_be_operated_index<0){
                    continue;
                }
                n_sector_index = m_n_will_be_operated_index;
            }
            if(m_order.length<=n_sector_index){
                continue;
            }
            n_sec = m_order[n_sector_index];
        }while (false);
        return n_sec;
    }

    public int get_relative_currnet_sector_number_from_min_sector(){
        int n_r = -1;

        do{
            if(m_n_min_sector<0){
                continue;
            }
            int n_cur = get_current_sector_number();
            if(n_cur<0){
                continue;
            }
            n_r = n_cur - m_n_min_sector;
        }while(false);
        return n_r;
    }
    public int get_current_sector_number(){
        return get_sector_number(-1);
    }
    public int get_the_number_of_sectors(){
        if(m_order!=null){
            return m_order.length;
        }
        return 0;
    }
    public int[] get_order(){
        return m_order;
    }
    public boolean is_compete(){
        boolean b_complete = false;

        do{
            if(m_order == null ){
                continue;
            }
            if(m_n_will_be_operated_index<m_order.length){
                continue;
            }
            b_complete = true;
        }while(false);
        return b_complete;
    }

    public void set_order(int[] order){
        if(order!=null) {
            m_order = new int[order.length];
            System.arraycopy(order, 0, m_order, 0, order.length);
            m_n_start_sector = order[0];
            m_n_will_be_operated_index = 0;
            _set_min_sector();
        }
        else{
            _reset();
        }

    }
    public void set_order(int n_start_sector,int n_the_number_of_sectors){
        if(n_the_number_of_sectors<=0){
            _reset();
        }
        else{
            m_order = new int[n_the_number_of_sectors];
            for(int i=0; i<n_the_number_of_sectors; i++){
                m_order[i] = n_start_sector+i;
            }//end for
            m_n_start_sector = m_order[0];
            m_n_will_be_operated_index = 0;
            _set_min_sector();
        }
    }

    /**
     * this function doesn't change the m_n_start_sector
     * @param n_size : the size of order array
     */
    public void resize(int n_size){
        set_order(m_n_start_sector,n_size);
    }

    public void initialize_for_lpc1343_erase(){
        set_order(1,1);
    }
    public void initialize_for_lpc1343_write(){
        int[] w = {2,3,4,5,6,7,1};
        set_order(w);
    }


    private void _reset(){
        m_order = null;
        m_n_start_sector = -1;
        m_n_will_be_operated_index = -1;
        m_n_min_sector = -1;
    }

    private void _set_min_sector(){
        if (m_order == null || m_order.length == 0) {
            m_n_min_sector = -1;
        }
        else {

            m_n_min_sector = m_order[0];
            for (int i = 1; i < m_order.length; i++) {
                if (m_order[i] < m_n_min_sector) {
                    m_n_min_sector = m_order[i];
                }
            }//end for
        }
    }
}//the end of class
