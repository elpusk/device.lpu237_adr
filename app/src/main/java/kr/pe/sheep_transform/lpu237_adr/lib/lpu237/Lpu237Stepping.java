package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

/**
 * this class save the information of a transaction type work that is the sum of phases.
 */
abstract public class Lpu237Stepping {

    protected int m_n_cur_Step = 0;
    protected int m_n_total_step = 0;
    protected boolean m_b_cur_success = false;

    /**
     * initialize stepping work
     */
    public void Reset(){
        m_n_cur_Step = 0;
        m_b_cur_success = false;
    }

    /**
     * Get description of Current step.
     * @return String description.
     */
    abstract public String GetCurDescription();

    /**
     * Gets the current working step zero-base number.
     * this step number is the zero-base number of working or will be processed step.
     * @return the current working step ( 0~ GetTotalStep()-1 )
     */
    public int GetCurStep(){
        return m_n_cur_Step;
    }

    /**
     * Sets the current working step zero-base number.
     * @param nNewStep - this step number is the zero-base number of working or will be processed step.
     * @return the current working step ( 0~ GetTotalStep()-1 )
     */
    public void SetCurStep(int nNewStep ){
        if(nNewStep>m_n_total_step){
            m_n_cur_Step = m_n_total_step;
        }
        else{
            m_n_cur_Step = nNewStep;
        }
    }

    /**
     * Gets the total step number
     * @return the total step number
     */
    public int GetTotalStep(){
        return m_n_total_step;
    }

    /**
     * check the end of step-processing.
     * the result of complete can be success or error.
     * If the result of a step is error, this function will return true for notifying complete with error.
     * @return true - complete, false - not yet
     */
    public boolean IsComplete(){
        if(m_n_cur_Step == m_n_total_step){
            return true;
        }
        if(!m_b_cur_success){
            return true;
        }
        return false;
    }


    /**
     * Get the result of the current step.
     * @return true - success, false - error.
     */
    public boolean IsCurResultSuccess(){
        return m_b_cur_success;
    }

    /**
     * Set the result of the current step.
     * @param bSuccess result value
     * @return true - success, false - error.
     */
    public void SetCurResult(boolean bSuccess){
        m_b_cur_success = bSuccess;
    }
}
