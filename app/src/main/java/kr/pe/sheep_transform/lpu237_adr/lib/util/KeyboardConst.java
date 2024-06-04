package kr.pe.sheep_transform.lpu237_adr.lib.util;

public interface KeyboardConst {
//<><><><><><><>
    int FOR_CVT_MAX_ASCII_CODE = 130;		//covertable maximmum of ascii-code.
    int SUPPORT_KB_MAP = 11;
    int HIDKEY_MAP_NUMBER = SUPPORT_KB_MAP;	//the number of map table
    int PS2KEY_MAP_NUMBER = SUPPORT_KB_MAP;	//the number of map table

    /////////////////////////////////////////////////////
    //definition of key code.......  for USB keyboard
    //USA.......(default definition)

    // HID MODIFIERS KEYS
    byte HIDKEY_MOD__NONE = 0x00;//none modifier
    byte HIDKEY_MOD_L_CTL = 0x01;//left control
    byte HIDKEY_MOD_L_SFT = 0x02;//left shift
    byte HIDKEY_MOD_L_ALT = 0x04;//left alt
    byte HIDKEY_MOD_L_GUI = 0x08;//left windows key
    byte HIDKEY_MOD_R_CTL = 0x10;//right control
    byte HIDKEY_MOD_R_SFT = 0x20;//right shift
    byte HIDKEY_MOD_R_ALT = 0x40;//right alt
    byte HIDKEY_MOD_R_GUI = (byte)0x80;//right windows key

    byte HIDKEY____a____A = (byte)0x04;// custom_simple_dropdown_item_1line A
    byte HIDKEY____b____B = (byte)0x05;// b B
    byte HIDKEY____c____C = (byte)0x06;// c C
    byte HIDKEY____d____D = (byte)0x07;// d D
    byte HIDKEY____e____E = (byte)0x08;// e E
    byte HIDKEY____f____F = (byte)0x09;// f F
    byte HIDKEY____g____G = (byte)0x0a;// g G
    byte HIDKEY____h____H = (byte)0x0b;// h H
    byte HIDKEY____i____I = (byte)0x0c;// i I
    byte HIDKEY____j____J = (byte)0x0d;// j J
    byte HIDKEY____k____K = (byte)0x0e;// k K
    byte HIDKEY____l____L = (byte)0x0f;// l L
    byte HIDKEY____m____M = (byte)0x10;// m M
    byte HIDKEY____n____N = (byte)0x11;// n N
    byte HIDKEY____o____O = (byte)0x12;// o O
    byte HIDKEY____p____P = (byte)0x13;// p P
    byte HIDKEY____q____Q = (byte)0x14;// q Q
    byte HIDKEY____r____R = (byte)0x15;// r R
    byte HIDKEY____s____S = (byte)0x16;// s S
    byte HIDKEY____t____T = (byte)0x17;// t T
    byte HIDKEY____u____U = (byte)0x18;// u U
    byte HIDKEY____v____V = (byte)0x19;// v V
    byte HIDKEY____w____W = (byte)0x1a;// w W
    byte HIDKEY____x____X = (byte)0x1b;// x X
    byte HIDKEY____y____Y = (byte)0x1c;// y Y
    byte HIDKEY____z____Z = (byte)0x1d;// z Z
    byte HIDKEY____1_EXCL = (byte)0x1e;// 1 ! Exclamation point
    byte HIDKEY____2_QUOT = (byte)0x1f;// 2 @ Quotation mark
    byte HIDKEY____3_SHAR = (byte)0x20;// 3 # Sharp
    byte HIDKEY____4_DOLL = (byte)0x21;// 4 $ Dollar sign
    byte HIDKEY____5_PERC = (byte)0x22;// 5 % Percent sign
    byte HIDKEY____6_CIRC = (byte)0x23;// 6 ^ Circumflex
    byte HIDKEY____7_AMPE = (byte)0x24;// 7 & ampersand
    byte HIDKEY____8_ASTE = (byte)0x25;// 8 * asterisk
    byte HIDKEY____9_L_PA = (byte)0x26;// 9 ( left parenthesis
    byte HIDKEY____0_R_PA = (byte)0x27;// 0 ) right parenthesis
    byte HIDKEY____RETURN = (byte)0x28;// Return
    byte HIDKEY____ESCAPE = (byte)0x29;// Escape
    byte HIDKEY_BACKSPACE = (byte)0x2a;// Backspace
    byte HIDKEY_______TAB = (byte)0x2b;// Tab
    byte HIDKEY_____SPACE = (byte)0x2c;// Space
    byte HIDKEY_MIN_UNDER = (byte)0x2d;// - kb_lower underline
    byte HIDKEY_EQU__PLUS = (byte)0x2e;// = +

    byte HIDKEY_LBT___LBR = (byte)0x2f;// [ { left bracket,left brace
    byte HIDKEY_RBT___RBR = (byte)0x30;// ] } right bracket,right brace
    byte HIDKEY_BSLA_VBAR = (byte)0x31;// \ | back slash, vertical bar
    byte HIDKEY_SEMI__COL = (byte)0x33;// ; : semicolon, colon
    byte HIDKEY_APOS_QUOT = (byte)0x34;// ' " apostrophe, Double Quotation
    byte HIDKEY_GRAV_TILD = (byte)0x35;// ` ~ Grave, Tilde
    byte HIDKEY_COMA___LT = (byte)0x36;// , < comma, less then sign
    byte HIDKEY_PERIOD_GT = (byte)0x37;// . > period, greater then sign
    byte HIDKEY_SLASH__QM = (byte)0x38;// / ? slash
    byte HIDKEY__CAPSLOCK = (byte)0x39;//Caps Lock
    byte HIDKEY________F1 = (byte)0x3a;//F1
    byte HIDKEY________F2 = (byte)0x3b;//F2
    byte HIDKEY________F3 = (byte)0x3c;//F3
    byte HIDKEY________F4 = (byte)0x3d;//F4
    byte HIDKEY________F5 = (byte)0x3e;//F5
    byte HIDKEY________F6 = (byte)0x3f;//F6
    byte HIDKEY________F7 = (byte)0x40;//F7
    byte HIDKEY________F8 = (byte)0x41;//F8
    byte HIDKEY________F9 = (byte)0x42;//F19
    byte HIDKEY_______F10 = (byte)0x43;//F10
    byte HIDKEY_______F11 = (byte)0x44;//F11
    byte HIDKEY_______F12 = (byte)0x45;//F12
    byte HIDKEY_PRINT_SCR = (byte)0x46;//Print Screen
    byte HIDKEY_SCROLLLOC = (byte)0x47;//Scroll Lock
    byte HIDKEY_____BREAK = (byte)0x48;//Break (Ctrl-Pause)
    byte HIDKEY____INSERT = (byte)0x49;//Insert
    byte HIDKEY______HOME = (byte)0x4a;//Home
    byte HIDKEY____PAGEUP = (byte)0x4b;//Page Up
    byte HIDKEY____DELETE = (byte)0x4c;//Delete
    byte HIDKEY_______END = (byte)0x4d;//End
    byte HIDKEY__PAGEDOWN = (byte)0x4e;//Page Down

    byte HIDKEY_ARROW___R = (byte)0x4f;//Right Arrow
    byte HIDKEY_ARROW___L = (byte)0x50;//Left Arrow
    byte HIDKEY_ARROW___D = (byte)0x51;//Down Arrow
    byte HIDKEY_ARROW___U = (byte)0x52;//Up Arrow

    byte HIDKEY_KPAD__DIV = (byte)0x54;//Keypad /
    byte HIDKEY_KPAD__MUL = (byte)0x55;//Keypad *
    byte HIDKEY_KPAD_MINU = (byte)0x56;//Keypad -
    byte HIDKEY_KPAD_PLUS = (byte)0x57;//Keypad +
    byte HIDKEY_KEYPAD_EN = (byte)0x58;//Keypad Enter

    byte HIDKEY_KEYPAD__1 = (byte)0x59;//Keypad 1 End
    byte HIDKEY_KEYPAD__2 = (byte)0x5a;//Keypad 2 Down
    byte HIDKEY_KEYPAD__3 = (byte)0x5b;//Keypad 3 PageDn
    byte HIDKEY_KEYPAD__4 = (byte)0x5c;//Keypad 4 Left
    byte HIDKEY_KEYPAD__5 = (byte)0x5d;//Keypad 5
    byte HIDKEY_KEYPAD__6 = (byte)0x5e;//Keypad 6 Right
    byte HIDKEY_KEYPAD__7 = (byte)0x5f;//Keypad 7 Home
    byte HIDKEY_KEYPAD__8 = (byte)0x60;//Keypad 8 Up
    byte HIDKEY_KEYPAD__9 = (byte)0x61;//Keypad 9 PageUp
    byte HIDKEY_KEYPAD__0 = (byte)0x62;//Keypad 0 Insert
    byte HIDKEY_KPAD__DOT = (byte)0x63;//Keypad . Delete



/////////////////////////////////////////////////////
//definition of key code.......  for PS/2 keyboard scancode set2
//make code only, break code is 0xf0,make-code
//USA.......(default definition)

    byte PS2_BREAK_PRFIXCODE = (byte)0xf0;

//control key
    byte PS2KEY______NONE = (byte)0x00;
    byte PS2KEY_____L_CTL = (byte)0x14;
    byte PS2KEY_____L_SFT = (byte)0x12;
    byte PS2KEY_____L_ALT = (byte)0x11;

//#define	PS2KEY_____R_CTL			0xE0 0x14
    byte PS2KEY_____R_CTL = (byte)0xF4;	//using you must parsing data to 0xE0 0x14
    byte PS2KEY_____R_SFT = (byte)0x59;
    byte PS2KEY_____R_ALT = (byte)0xF1;	//using you must parsing data to 0xE0 0x11
//#define	PS2KEY_____R_ALT			0xE0 0x11

//general key
    byte PS2KEY____a____A = (byte)0x1c;// custom_simple_dropdown_item_1line A
    byte PS2KEY____b____B = (byte)0x32;// b B
    byte PS2KEY____c____C = (byte)0x21;// c C
    byte PS2KEY____d____D = (byte)0x23;// d D
    byte PS2KEY____e____E = (byte)0x24;// e E
    byte PS2KEY____f____F = (byte)0x2b;// f F
    byte PS2KEY____g____G = (byte)0x34;// g G
    byte PS2KEY____h____H = (byte)0x33;// h H
    byte PS2KEY____i____I = (byte)0x43;// i I
    byte PS2KEY____j____J = (byte)0x3b;// j J
    byte PS2KEY____k____K = (byte)0x42;// k K
    byte PS2KEY____l____L = (byte)0x4b;// l L
    byte PS2KEY____m____M = (byte)0x3a;// m M
    byte PS2KEY____n____N = (byte)0x31;// n N
    byte PS2KEY____o____O = (byte)0x44;// o O
    byte PS2KEY____p____P = (byte)0x4d;// p P
    byte PS2KEY____q____Q = (byte)0x15;// q Q
    byte PS2KEY____r____R = (byte)0x2d;// r R
    byte PS2KEY____s____S = (byte)0x1b;// s S
    byte PS2KEY____t____T = (byte)0x2c;// t T
    byte PS2KEY____u____U = (byte)0x3c;// u U
    byte PS2KEY____v____V = (byte)0x2a;// v V
    byte PS2KEY____w____W = (byte)0x1d;// w W
    byte PS2KEY____x____X = (byte)0x22;// x X
    byte PS2KEY____y____Y = (byte)0x35;// y Y
    byte PS2KEY____z____Z = (byte)0x1a;// z Z
    byte PS2KEY____1_EXCL = (byte)0x16;// 1 ! Exclamation point
    byte PS2KEY____2_QUOT = (byte)0x1e;// 2 @ Quotation mark
    byte PS2KEY____3_SHAR = (byte)0x26;// 3 # Sharp
    byte PS2KEY____4_DOLL = (byte)0x25;// 4 $ Dollar sign
    byte PS2KEY____5_PERC = (byte)0x2e;// 5 % Percent sign
    byte PS2KEY____6_CIRC = (byte)0x36;// 6 ^ Circumflex
    byte PS2KEY____7_AMPE = (byte)0x3d;// 7 & ampersand
    byte PS2KEY____8_ASTE = (byte)0x3e;// 8 * asterisk
    byte PS2KEY____9_L_PA = (byte)0x46;// 9 ( left parenthesis
    byte PS2KEY____0_R_PA = (byte)0x45;// 0 ) right parenthesis
    byte PS2KEY____RETURN = (byte)0x5a;// Return
    byte PS2KEY____ESCAPE = (byte)0x76;// Escape
    byte PS2KEY_BACKSPACE = (byte)0x66;// Backspace
    byte PS2KEY_______TAB = (byte)0x0d;// Tab
    byte PS2KEY_____SPACE = (byte)0x29;// Space
    byte PS2KEY_MIN_UNDER = (byte)0x4e;// - kb_lower underline
    byte PS2KEY_EQU__PLUS = (byte)0x55;// = +

    byte PS2KEY_LBT___LBR = (byte)0x54;// [ { left bracket,left brace
    byte PS2KEY_RBT___RBR = (byte)0x5b;// ] } right bracket,right brace
    byte PS2KEY_BSLA_VBAR = (byte)0x5d;// \ | back slash, vertical bar
    byte PS2KEY_SEMI__COL = (byte)0x4c;// ; : semicolon, colon
    byte PS2KEY_APOS_QUOT = (byte)0x52;// ' " apostrophe, Quotation mark
    byte PS2KEY_GRAV_TILD = (byte)0x0e;// ` ~ Grave, Tilde
    byte PS2KEY_COMA___LT = (byte)0x41;// , < comma, less then sign
    byte PS2KEY_PERIOD_GT = (byte)0x49;// . > period, greater then sign
    byte PS2KEY_SLASH__QM = (byte)0x4a;// / ? slash
    byte PS2KEY__CAPSLOCK = (byte)0x58;//Caps Lock
    byte PS2KEY________F1 = (byte)0x05;//F1
    byte PS2KEY________F2 = (byte)0x06;//F2
    byte PS2KEY________F3 = (byte)0x04;//F3
    byte PS2KEY________F4 = (byte)0x0c;//F4
    byte PS2KEY________F5 = (byte)0x03;//F5
    byte PS2KEY________F6 = (byte)0x0b;//F6
    byte PS2KEY________F7 = (byte)0x83;//F7
    byte PS2KEY________F8 = (byte)0x0a;//F8
    byte PS2KEY________F9 = (byte)0x01;//F9
    byte PS2KEY_______F10 = (byte)0x09;//F10
    byte PS2KEY_______F11 = (byte)0x78;//F11
    byte PS2KEY_______F12 = (byte)0x07;//F12
    byte PS2KEY_SCROLLLOC = (byte)0x7e;//Scroll Lock

//#define PS2KEY_KPAD__DIV			//Keypad /
     byte PS2KEY_KPAD__MUL = (byte)0x7c;//Keypad *
     byte PS2KEY_KPAD_MINU = (byte)0x7b;//Keypad -
     byte PS2KEY_KPAD_PLUS = (byte)0x79;//Keypad +
//#define PS2KEY_KEYPAD_EN	        //Keypad Enter

    byte PS2KEY_KEYPAD__1 = (byte)0x69;//Keypad 1 End
    byte PS2KEY_KEYPAD__2 = (byte)0x72;//Keypad 2 Down
    byte PS2KEY_KEYPAD__3 = (byte)0x7a;//Keypad 3 PageDn
    byte PS2KEY_KEYPAD__4 = (byte)0x6b;//Keypad 4 Left
    byte PS2KEY_KEYPAD__5 = (byte)0x73;//Keypad 5
    byte PS2KEY_KEYPAD__6 = (byte)0x74;//Keypad 6 Right
    byte PS2KEY_KEYPAD__7 = (byte)0x6c;//Keypad 7 Home
    byte PS2KEY_KEYPAD__8 = (byte)0x75;//Keypad 8 Up
    byte PS2KEY_KEYPAD__9 = (byte)0x7d;//Keypad 9 PageUp
    byte PS2KEY_KEYPAD__0 = (byte)0x70;//Keypad 0 Insert
    byte PS2KEY_KPAD__DOT = (byte)0x71;//Keypad . Delete

//<><><><><><><>
}
