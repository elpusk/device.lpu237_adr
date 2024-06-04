package kr.pe.sheep_transform.lpu237_adr.lib.util;

import java.util.HashMap;

public interface MapCodeToString {
    HashMap<Byte, String> CvtUnshiftCodeToString= new HashMap<Byte, String>() {

        {
            put(KeyboardConst.HIDKEY____a____A, "a");
            put(KeyboardConst.HIDKEY____b____B, "b");
            put(KeyboardConst.HIDKEY____c____C, "c");
            put(KeyboardConst.HIDKEY____d____D, "d");
            put(KeyboardConst.HIDKEY____e____E, "e");
            put(KeyboardConst.HIDKEY____f____F, "f");
            put(KeyboardConst.HIDKEY____g____G, "g");
            put(KeyboardConst.HIDKEY____h____H, "h");
            put(KeyboardConst.HIDKEY____i____I, "i");
            put(KeyboardConst.HIDKEY____j____J, "j");
            put(KeyboardConst.HIDKEY____k____K, "k");
            put(KeyboardConst.HIDKEY____l____L, "l");
            put(KeyboardConst.HIDKEY____m____M, "m");
            put(KeyboardConst.HIDKEY____n____N, "n");
            put(KeyboardConst.HIDKEY____o____O, "o");
            put(KeyboardConst.HIDKEY____p____P, "p");
            put(KeyboardConst.HIDKEY____q____Q, "q");
            put(KeyboardConst.HIDKEY____r____R, "r");
            put(KeyboardConst.HIDKEY____s____S, "s");
            put(KeyboardConst.HIDKEY____t____T, "t");
            put(KeyboardConst.HIDKEY____u____U, "u");
            put(KeyboardConst.HIDKEY____v____V, "v");
            put(KeyboardConst.HIDKEY____w____W, "w");
            put(KeyboardConst.HIDKEY____x____X, "x");
            put(KeyboardConst.HIDKEY____y____Y, "y");
            put(KeyboardConst.HIDKEY____z____Z, "z");
            put(KeyboardConst.HIDKEY____1_EXCL, "1");
            put(KeyboardConst.HIDKEY____2_QUOT, "2");
            put(KeyboardConst.HIDKEY____3_SHAR, "3");
            put(KeyboardConst.HIDKEY____4_DOLL, "4");
            put(KeyboardConst.HIDKEY____5_PERC, "5");
            put(KeyboardConst.HIDKEY____6_CIRC, "6");
            put(KeyboardConst.HIDKEY____7_AMPE, "7");
            put(KeyboardConst.HIDKEY____8_ASTE, "8");
            put(KeyboardConst.HIDKEY____9_L_PA, "9");
            put(KeyboardConst.HIDKEY____0_R_PA, "0");
            put(KeyboardConst.HIDKEY____RETURN, "Return");
            put(KeyboardConst.HIDKEY____ESCAPE, "Escape");
            put(KeyboardConst.HIDKEY_BACKSPACE, "Backspace");
            put(KeyboardConst.HIDKEY_______TAB, "Tab");
            put(KeyboardConst.HIDKEY_____SPACE, "Space");
            put(KeyboardConst.HIDKEY_MIN_UNDER, "-");
            put(KeyboardConst.HIDKEY_EQU__PLUS, "=");

            put(KeyboardConst.HIDKEY_LBT___LBR, "[");
            put(KeyboardConst.HIDKEY_RBT___RBR, "]");
            put(KeyboardConst.HIDKEY_BSLA_VBAR, "\\");
            put(KeyboardConst.HIDKEY_SEMI__COL, ";");
            put(KeyboardConst.HIDKEY_APOS_QUOT, "'");
            put(KeyboardConst.HIDKEY_GRAV_TILD, "`");
            put(KeyboardConst.HIDKEY_COMA___LT, ",");
            put(KeyboardConst.HIDKEY_PERIOD_GT, ".");
            put(KeyboardConst.HIDKEY_SLASH__QM, "/");
            put(KeyboardConst.HIDKEY__CAPSLOCK, "Caps Lock");
            put(KeyboardConst.HIDKEY________F1, "F1");
            put(KeyboardConst.HIDKEY________F2, "F2");
            put(KeyboardConst.HIDKEY________F3, "F3");
            put(KeyboardConst.HIDKEY________F4, "F4");
            put(KeyboardConst.HIDKEY________F5, "F5");
            put(KeyboardConst.HIDKEY________F6, "F6");
            put(KeyboardConst.HIDKEY________F7, "F7");
            put(KeyboardConst.HIDKEY________F8, "F8");
            put(KeyboardConst.HIDKEY________F9, "F19");
            put(KeyboardConst.HIDKEY_______F10, "F10");
            put(KeyboardConst.HIDKEY_______F11, "F11");
            put(KeyboardConst.HIDKEY_______F12, "F12");
            put(KeyboardConst.HIDKEY_PRINT_SCR, "Print Screen");
            put(KeyboardConst.HIDKEY_SCROLLLOC, "Scroll Lock");
            put(KeyboardConst.HIDKEY_____BREAK, "Break");
            put(KeyboardConst.HIDKEY____INSERT, "Insert");
            put(KeyboardConst.HIDKEY______HOME, "Home");
            put(KeyboardConst.HIDKEY____PAGEUP, "Page Up");
            put(KeyboardConst.HIDKEY____DELETE, "Delete");
            put(KeyboardConst.HIDKEY_______END, "End");
            put(KeyboardConst.HIDKEY__PAGEDOWN, "Page Down");

            put(KeyboardConst.HIDKEY_ARROW___R, "Right Arrow");
            put(KeyboardConst.HIDKEY_ARROW___L, "Left Arrow");
            put(KeyboardConst.HIDKEY_ARROW___D, "Down Arrow");
            put(KeyboardConst.HIDKEY_ARROW___U, "Up Arrow");

            put(KeyboardConst.HIDKEY_KPAD__DIV, "Keypad /");
            put(KeyboardConst.HIDKEY_KPAD__MUL, "Keypad *");
            put(KeyboardConst.HIDKEY_KPAD_MINU, "Keypad -");
            put(KeyboardConst.HIDKEY_KPAD_PLUS, "Keypad +");
            put(KeyboardConst.HIDKEY_KEYPAD_EN, "Keypad Enter");

            put(KeyboardConst.HIDKEY_KEYPAD__1, "Keypad 1");
            put(KeyboardConst.HIDKEY_KEYPAD__2, "Keypad 2");
            put(KeyboardConst.HIDKEY_KEYPAD__3, "Keypad 3");
            put(KeyboardConst.HIDKEY_KEYPAD__4, "Keypad 4");
            put(KeyboardConst.HIDKEY_KEYPAD__5, "Keypad 5");
            put(KeyboardConst.HIDKEY_KEYPAD__6, "Keypad 6");
            put(KeyboardConst.HIDKEY_KEYPAD__7, "Keypad 7");
            put(KeyboardConst.HIDKEY_KEYPAD__8, "Keypad 8");
            put(KeyboardConst.HIDKEY_KEYPAD__9, "Keypad 9");
            put(KeyboardConst.HIDKEY_KEYPAD__0, "Keypad 0");
            put(KeyboardConst.HIDKEY_KPAD__DOT, "Keypad .");

        }

    };

    HashMap<Byte, String> CvtShiftCodeToString= new HashMap<Byte, String>() {

        {
            put(KeyboardConst.HIDKEY____a____A, "A");
            put(KeyboardConst.HIDKEY____b____B, "B");
            put(KeyboardConst.HIDKEY____c____C, "C");
            put(KeyboardConst.HIDKEY____d____D, "D");
            put(KeyboardConst.HIDKEY____e____E, "E");
            put(KeyboardConst.HIDKEY____f____F, "F");
            put(KeyboardConst.HIDKEY____g____G, "G");
            put(KeyboardConst.HIDKEY____h____H, "H");
            put(KeyboardConst.HIDKEY____i____I, "I");
            put(KeyboardConst.HIDKEY____j____J, "J");
            put(KeyboardConst.HIDKEY____k____K, "K");
            put(KeyboardConst.HIDKEY____l____L, "L");
            put(KeyboardConst.HIDKEY____m____M, "M");
            put(KeyboardConst.HIDKEY____n____N, "N");
            put(KeyboardConst.HIDKEY____o____O, "O");
            put(KeyboardConst.HIDKEY____p____P, "P");
            put(KeyboardConst.HIDKEY____q____Q, "Q");
            put(KeyboardConst.HIDKEY____r____R, "R");
            put(KeyboardConst.HIDKEY____s____S, "S");
            put(KeyboardConst.HIDKEY____t____T, "T");
            put(KeyboardConst.HIDKEY____u____U, "U");
            put(KeyboardConst.HIDKEY____v____V, "V");
            put(KeyboardConst.HIDKEY____w____W, "W");
            put(KeyboardConst.HIDKEY____x____X, "X");
            put(KeyboardConst.HIDKEY____y____Y, "Y");
            put(KeyboardConst.HIDKEY____z____Z, "Z");
            put(KeyboardConst.HIDKEY____1_EXCL, "!");
            put(KeyboardConst.HIDKEY____2_QUOT, "@");
            put(KeyboardConst.HIDKEY____3_SHAR, "#");
            put(KeyboardConst.HIDKEY____4_DOLL, "$");
            put(KeyboardConst.HIDKEY____5_PERC, "%");
            put(KeyboardConst.HIDKEY____6_CIRC, "^");
            put(KeyboardConst.HIDKEY____7_AMPE, "&");
            put(KeyboardConst.HIDKEY____8_ASTE, "*");
            put(KeyboardConst.HIDKEY____9_L_PA, "(");
            put(KeyboardConst.HIDKEY____0_R_PA, ")");
            put(KeyboardConst.HIDKEY____RETURN, "Return");
            put(KeyboardConst.HIDKEY____ESCAPE, "Escape");
            put(KeyboardConst.HIDKEY_BACKSPACE, "Backspace");
            put(KeyboardConst.HIDKEY_______TAB, "Tab");
            put(KeyboardConst.HIDKEY_____SPACE, "Space");
            put(KeyboardConst.HIDKEY_MIN_UNDER, "_");
            put(KeyboardConst.HIDKEY_EQU__PLUS, "+");

            put(KeyboardConst.HIDKEY_LBT___LBR, "{");
            put(KeyboardConst.HIDKEY_RBT___RBR, "}");
            put(KeyboardConst.HIDKEY_BSLA_VBAR, "|");
            put(KeyboardConst.HIDKEY_SEMI__COL, ":");
            put(KeyboardConst.HIDKEY_APOS_QUOT, "\"");
            put(KeyboardConst.HIDKEY_GRAV_TILD, "~");
            put(KeyboardConst.HIDKEY_COMA___LT, "<");
            put(KeyboardConst.HIDKEY_PERIOD_GT, ">");
            put(KeyboardConst.HIDKEY_SLASH__QM, "?");
            put(KeyboardConst.HIDKEY__CAPSLOCK, "Caps Lock");
            put(KeyboardConst.HIDKEY________F1, "F1");
            put(KeyboardConst.HIDKEY________F2, "F2");
            put(KeyboardConst.HIDKEY________F3, "F3");
            put(KeyboardConst.HIDKEY________F4, "F4");
            put(KeyboardConst.HIDKEY________F5, "F5");
            put(KeyboardConst.HIDKEY________F6, "F6");
            put(KeyboardConst.HIDKEY________F7, "F7");
            put(KeyboardConst.HIDKEY________F8, "F8");
            put(KeyboardConst.HIDKEY________F9, "F19");
            put(KeyboardConst.HIDKEY_______F10, "F10");
            put(KeyboardConst.HIDKEY_______F11, "F11");
            put(KeyboardConst.HIDKEY_______F12, "F12");
            put(KeyboardConst.HIDKEY_PRINT_SCR, "Print Screen");
            put(KeyboardConst.HIDKEY_SCROLLLOC, "Scroll Lock");
            put(KeyboardConst.HIDKEY_____BREAK, "Break");
            put(KeyboardConst.HIDKEY____INSERT, "Insert");
            put(KeyboardConst.HIDKEY______HOME, "Home");
            put(KeyboardConst.HIDKEY____PAGEUP, "Page Up");
            put(KeyboardConst.HIDKEY____DELETE, "Delete");
            put(KeyboardConst.HIDKEY_______END, "End");
            put(KeyboardConst.HIDKEY__PAGEDOWN, "Page Down");

            put(KeyboardConst.HIDKEY_ARROW___R, "Right Arrow");
            put(KeyboardConst.HIDKEY_ARROW___L, "Left Arrow");
            put(KeyboardConst.HIDKEY_ARROW___D, "Down Arrow");
            put(KeyboardConst.HIDKEY_ARROW___U, "Up Arrow");

            put(KeyboardConst.HIDKEY_KPAD__DIV, "Keypad /");
            put(KeyboardConst.HIDKEY_KPAD__MUL, "Keypad *");
            put(KeyboardConst.HIDKEY_KPAD_MINU, "Keypad -");
            put(KeyboardConst.HIDKEY_KPAD_PLUS, "Keypad +");
            put(KeyboardConst.HIDKEY_KEYPAD_EN, "Keypad Enter");

            put(KeyboardConst.HIDKEY_KEYPAD__1, "Keypad 1");
            put(KeyboardConst.HIDKEY_KEYPAD__2, "Keypad 2");
            put(KeyboardConst.HIDKEY_KEYPAD__3, "Keypad 3");
            put(KeyboardConst.HIDKEY_KEYPAD__4, "Keypad 4");
            put(KeyboardConst.HIDKEY_KEYPAD__5, "Keypad 5");
            put(KeyboardConst.HIDKEY_KEYPAD__6, "Keypad 6");
            put(KeyboardConst.HIDKEY_KEYPAD__7, "Keypad 7");
            put(KeyboardConst.HIDKEY_KEYPAD__8, "Keypad 8");
            put(KeyboardConst.HIDKEY_KEYPAD__9, "Keypad 9");
            put(KeyboardConst.HIDKEY_KEYPAD__0, "Keypad 0");
            put(KeyboardConst.HIDKEY_KPAD__DOT, "Keypad .");

        }
    };

}
