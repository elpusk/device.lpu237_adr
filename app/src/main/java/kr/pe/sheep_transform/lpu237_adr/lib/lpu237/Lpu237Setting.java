package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

import android.util.Pair;

public class Lpu237Setting extends Lpu237Stepping{

    public interface Step{
        int ENTER_CONFIG = 0;
        int BLANKS = 1;
        int ENABLE_TRACK1 = 2;
        int ENABLE_TRACK2 = 3;
        int ENABLE_TRACK3 = 4;
        int INTERFACE = 5;
        int LANGUAGE_INDEX = 6;

        int BUZZER_FRQ = 7;
        int MSR_G_PREFIX = 8;
        int MSR_G_POSTFIX = 9;
        int MSR_PRIVATE_PREFIX1 = 10;
        int MSR_PRIVATE_POSTFIX1 = 11;
        int MSR_PRIVATE_PREFIX2 = 12;
        int MSR_PRIVATE_POSTFIX2 = 13;
        int MSR_PRIVATE_PREFIX3 = 14;
        int MSR_PRIVATE_POSTFIX3 = 15;
        int IB_TAG_PREFIX = 16;
        int IB_TAG_POSTFIX = 17;
        int IB_REMOVE = 18;
        int IB_RTAG_PREFIX = 19;
        int IB_RTAG_POSTFIX = 20;
        int UART_PREFIX = 21;
        int UART_POSTFIX = 22;
        int GLOBAL_SEND_CONDITION = 23;
        int READ_DIRECTION = 24;
        int TRACK_ORDER = 25;
        int LEAVE_CONFIG = 26;
        int TOTAL = 27;//this member always to be last.

    }

    final public Pair<Integer,String>[] m_array_pair_step = new Pair[]{
            new Pair(Lpu237Setting.Step.ENTER_CONFIG,"ENTER_CONFIG"),
            new Pair(Lpu237Setting.Step.BLANKS,"BLANKS"),
            new Pair(Lpu237Setting.Step.ENABLE_TRACK1,"ENABLE_TRACK1"),
            new Pair(Lpu237Setting.Step.ENABLE_TRACK2,"ENABLE_TRACK2"),
            new Pair(Lpu237Setting.Step.ENABLE_TRACK3,"ENABLE_TRACK3"),
            new Pair(Lpu237Setting.Step.INTERFACE,"INTERFACE"),
            new Pair(Lpu237Setting.Step.LANGUAGE_INDEX,"LANGUAGE_INDEX"),
            new Pair(Lpu237Setting.Step.BUZZER_FRQ,"BUZZER_FRQ"),
            new Pair(Lpu237Setting.Step.MSR_G_PREFIX,"MSR_G_PREFIX"),
            new Pair(Lpu237Setting.Step.MSR_G_POSTFIX,"MSR_G_POSTFIX"),
            new Pair(Lpu237Setting.Step.MSR_PRIVATE_PREFIX1,"MSR_PRIVATE_PREFIX1"),
            new Pair(Lpu237Setting.Step.MSR_PRIVATE_POSTFIX1,"MSR_PRIVATE_POSTFIX1"),
            new Pair(Lpu237Setting.Step.MSR_PRIVATE_PREFIX2,"MSR_PRIVATE_PREFIX2"),
            new Pair(Lpu237Setting.Step.MSR_PRIVATE_POSTFIX2,"MSR_PRIVATE_POSTFIX2"),
            new Pair(Lpu237Setting.Step.MSR_PRIVATE_PREFIX3,"MSR_PRIVATE_PREFIX3"),
            new Pair(Lpu237Setting.Step.MSR_PRIVATE_POSTFIX3,"MSR_PRIVATE_POSTFIX3"),
            new Pair(Lpu237Setting.Step.IB_TAG_PREFIX,"IB_TAG_PREFIX"),
            new Pair(Lpu237Setting.Step.IB_TAG_POSTFIX,"IB_TAG_POSTFIX"),
            new Pair(Lpu237Setting.Step.IB_REMOVE,"IB_REMOVE"),
            new Pair(Lpu237Setting.Step.IB_RTAG_PREFIX,"IB_RTAG_PREFIX"),
            new Pair(Lpu237Setting.Step.IB_RTAG_POSTFIX,"IB_RTAG_POSTFIX"),
            new Pair(Lpu237Setting.Step.UART_PREFIX,"UART_PREFIX"),
            new Pair(Lpu237Setting.Step.UART_POSTFIX,"UART_POSTFIX"),
            new Pair(Lpu237Setting.Step.GLOBAL_SEND_CONDITION,"GLOBAL_SEND_CONDITION"),
            new Pair(Lpu237Setting.Step.READ_DIRECTION,"READ_DIRECTION"),
            new Pair(Lpu237Setting.Step.TRACK_ORDER,"TRACK_ORDER"),
            new Pair(Lpu237Setting.Step.LEAVE_CONFIG,"LEAVE_CONFIG")
    };
    public Lpu237Setting(){
        super();
        m_n_total_step =m_array_pair_step.length;
        Reset();
    }
    @Override
    public String GetCurDescription(){
        if(m_n_cur_Step >= m_array_pair_step.length){
            return "";
        }
        return m_array_pair_step[m_n_cur_Step].second;
    }
}
