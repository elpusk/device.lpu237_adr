package kr.pe.sheep_transform.lpu237_adr.lib.lpu237;

import android.util.Pair;

public class Lpu237Getting extends Lpu237Stepping {

    public interface Step{
        int ENTER_CONFIG = 0;
        int DECODER_MMD1100 = 1;
        int NAME = 2;
        int VERSION_SYSTEM = 3;
        int VERSION_STRUCTURE = 4;
        int BLANKS = 5;
        int TYPE = 6;
        int IBUTTON_ONLY_TYPE = 7;
        int UID = 8;
        int ENABLE_TRACK1 = 9;
        int ENABLE_TRACK2 = 10;
        int ENABLE_TRACK3 = 11;
        int INTERFACE = 12;
        int LANGUAGE_INDEX = 13;
        int BUZZER_FRQ = 14;
        int BOOT_RUN_TIME = 15;
        int MSR_G_PREFIX = 16;
        int MSR_G_POSTFIX = 17;
        int MSR_PRIVATE_PREFIX1 = 18;
        int MSR_PRIVATE_POSTFIX1 = 19;
        int MSR_PRIVATE_PREFIX2 = 20;
        int MSR_PRIVATE_POSTFIX2 = 21;
        int MSR_PRIVATE_PREFIX3 = 22;
        int MSR_PRIVATE_POSTFIX3 = 23;
        int IB_TAG_PREFIX = 24;
        int IB_TAG_POSTFIX = 25;
        int IB_REMOVE = 26;
        int IB_RTAG_PREFIX = 27;
        int IB_RTAG_POSTFIX = 28;
        int UART_PREFIX = 29;
        int UART_POSTFIX = 30;
        int GLOBAL_SEND_CONDITION = 31;
        int READ_DIRECTION = 32;
        int TRACK_ORDER = 33;
        int LEAVE_CONFIG = 34;
        int TOTAL = 35;//this member always to be last.
    }

    final public Pair<Integer,String>[] m_array_pair_step = new Pair[] {
            new Pair(Step.ENTER_CONFIG,"ENTER_CONFIG"),
            new Pair(Step.DECODER_MMD1100,"DECODER_MMD1100"),
            new Pair(Step.NAME,"NAME"),
            new Pair(Step.VERSION_SYSTEM,"VERSION_SYSTEM"),
            new Pair(Step.VERSION_STRUCTURE,"VERSION_STRUCTURE"),
            new Pair(Step.BLANKS,"BLANKS"),
            new Pair(Step.TYPE,"TYPE"),
            new Pair(Step.IBUTTON_ONLY_TYPE,"IBUTTON_ONLY_TYPE"),
            new Pair(Step.UID,"UID"),
            new Pair(Step.ENABLE_TRACK1,"ENABLE_TRACK1"),
            new Pair(Step.ENABLE_TRACK2,"ENABLE_TRACK2"),
            new Pair(Step.ENABLE_TRACK3,"ENABLE_TRACK3"),
            new Pair(Step.INTERFACE,"INTERFACE"),
            new Pair(Step.LANGUAGE_INDEX,"LANGUAGE_INDEX"),
            new Pair(Step.BUZZER_FRQ,"BUZZER_FRQ"),
            new Pair(Step.BOOT_RUN_TIME,"BOOT_RUN_TIME"),
            new Pair(Step.MSR_G_PREFIX,"MSR_G_PREFIX"),
            new Pair(Step.MSR_G_POSTFIX,"MSR_G_POSTFIX"),
            new Pair(Step.MSR_PRIVATE_PREFIX1,"MSR_PRIVATE_PREFIX1"),
            new Pair(Step.MSR_PRIVATE_POSTFIX1,"MSR_PRIVATE_POSTFIX1"),
            new Pair(Step.MSR_PRIVATE_PREFIX2,"MSR_PRIVATE_PREFIX2"),
            new Pair(Step.MSR_PRIVATE_POSTFIX2,"MSR_PRIVATE_POSTFIX2"),
            new Pair(Step.MSR_PRIVATE_PREFIX3,"MSR_PRIVATE_PREFIX3"),
            new Pair(Step.MSR_PRIVATE_POSTFIX3,"MSR_PRIVATE_POSTFIX3"),
            new Pair(Step.IB_TAG_PREFIX,"IB_TAG_PREFIX"),
            new Pair(Step.IB_TAG_POSTFIX,"IB_TAG_POSTFIX"),
            new Pair(Step.IB_REMOVE,"IB_REMOVE"),
            new Pair(Step.IB_RTAG_PREFIX,"IB_RTAG_PREFIX"),
            new Pair(Step.IB_RTAG_POSTFIX,"IB_RTAG_POSTFIX"),
            new Pair(Step.UART_PREFIX,"UART_PREFIX"),
            new Pair(Step.UART_POSTFIX,"UART_POSTFIX"),
            new Pair(Step.GLOBAL_SEND_CONDITION,"GLOBAL_SEND_CONDITION"),
            new Pair(Step.READ_DIRECTION,"READ_DIRECTION"),
            new Pair(Step.TRACK_ORDER,"TRACK_ORDER"),
            new Pair(Step.LEAVE_CONFIG,"LEAVE_CONFIG")
    };
    public Lpu237Getting(){
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
