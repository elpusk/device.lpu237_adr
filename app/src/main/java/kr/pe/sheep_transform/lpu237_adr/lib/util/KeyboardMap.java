package kr.pe.sheep_transform.lpu237_adr.lib.util;

import kr.pe.sheep_transform.lpu237_adr.lib.util.KeyboardConst;

public class KeyboardMap {
    //<><><><><><><>
//////////////////////////////////////////////////////////////////////////////
// the definition of Keyboard mapping table
//////////////////////////////////////////////////////////////////////////////

//maps ASCII to USB HID-key code.
//gASCToHIDKeyMap[i][j][k] ....... index i language map index, index j is ascii code.. editing 200 item
    // KeyboardConst.HIDKEY_MAP_NUMBER*KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2
    //static final byte[] gASCToPS2KeyMap  = new byte[KeyboardConst.PS2KEY_MAP_NUMBER * KeyboardConst.FOR_CVT_MAX_ASCII_CODE*2]={
    public static final byte[] gASCToHIDKeyMap;
    public static final byte[] gASCToPS2KeyMap;

    static{
    gASCToHIDKeyMap = new byte[]{
        //xxx.English.Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
                KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
                KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
                KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_APOS_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_APOS_QUOT ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//2 *
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_EQU__PLUS ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SEMI__COL ,//8 :
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SEMI__COL ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//0 <
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_EQU__PLUS ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//3 ?
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_LBT___LBR ,//1 [
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BSLA_VBAR ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,

        //xxx.Spanish.Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD_L_SFT,0x33 ,//7 - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,0x31 ,//8 - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,0x34 ,//9 - Spanish
            // 2 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,0x35 ,//0 - Spanish
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 " - Spanish
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 & - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//9 ' - Spanish
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 ( - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 ) - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//2 * - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//3 + - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//5 - - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 / - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//8 : - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//9 ; - Spanish
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 < - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 = - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 > - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ? - Spanish
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____2_QUOT ,//4 @ - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_LBT___LBR ,//1 [ - Spanish
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_GRAV_TILD ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_RBT___RBR ,//3 ] - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//4 ^ - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//5 kb_lower - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,0x2f ,//6 ` - Spanish
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,0x33 ,//3 { - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,0x34 ,//5 } - Spanish
             KeyboardConst.HIDKEY_MOD_L_SFT,0x35 ,//6 ~ - Spanish
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.Danish.Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN, //3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,0x32 ,//2 *
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//8 :
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____2_QUOT ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____8_ASTE ,//1 [
             KeyboardConst.HIDKEY_MOD_R_ALT,0x64 ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____9_L_PA ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.French Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//3 !
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//4 "
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_APOS_QUOT ,//7 %
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//0 (
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//1 )
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//2 *
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_EQU__PLUS ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//5 -
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//7 /
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//8 :
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_EQU__PLUS ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____0_R_PA ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SEMI__COL ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____5_PERC ,//1 [
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____8_ASTE ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ]
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____9_L_PA ,//4 ^
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.German Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 ==================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//5 #
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 &
             KeyboardConst.HIDKEY_MOD_L_SFT,0x32 ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//2 *
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//8 :
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____q____Q ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____8_ASTE ,//1 [
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_MIN_UNDER ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____9_L_PA ,//3 ]
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.Italian Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_APOS_QUOT ,//5 #
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//2 *
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//8 :
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_SEMI__COL ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_LBT___LBR ,//1 [
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_RBT___RBR ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_EQU__PLUS ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.Norwegian	Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 ==================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,0x32 ,//2 *
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//8 :
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____2_QUOT ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____8_ASTE ,//1 [
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_EQU__PLUS ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____9_L_PA ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.Swedish Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,0x32 ,//2 *
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//8 :
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____2_QUOT ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____8_ASTE ,//1 [
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_MIN_UNDER ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____9_L_PA ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.UK_English Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//5 #
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_APOS_QUOT ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//2 *
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_EQU__PLUS ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_COMA___LT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_PERIOD_GT ,//6 .
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SEMI__COL ,//8 :
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SEMI__COL ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//0 <
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_EQU__PLUS ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//3 ?
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_APOS_QUOT ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_LBT___LBR ,//1 [
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.Israel Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_APOS_QUOT ,//4 "
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//8 &
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//2 *
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_EQU__PLUS ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_APOS_QUOT ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//6 .
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SEMI__COL ,//8 :
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_PERIOD_GT ,//0 <
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_EQU__PLUS ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_COMA___LT ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//3 ?
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_RBT___RBR ,//1 [
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_LBT___LBR ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        //xxx.Turkey Table......
            //{ Modified key,HID key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_BACKSPACE ,//8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_____SPACE ,//2
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____1_EXCL ,//3 !
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//4 "
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____3_SHAR ,//5 #
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____4_DOLL ,//6 $
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____5_PERC ,//7 %
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____6_CIRC ,//8 &
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____2_QUOT ,//9 '
            // 4 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____8_ASTE ,//0 (
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____9_L_PA ,//1 )
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_MIN_UNDER ,//2 *
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____4_DOLL ,//3 +
             KeyboardConst.HIDKEY_MOD__NONE,0x32 ,//4 ,
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_EQU__PLUS ,//5 -
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_SLASH__QM ,//6 .
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____7_AMPE ,//7 /
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____0_R_PA ,//8 0
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____2_QUOT ,//0 2
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____3_SHAR ,//1 3
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____4_DOLL ,//2 4
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____5_PERC ,//3 5
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____6_CIRC ,//4 6
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____7_AMPE ,//5 7
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____8_ASTE ,//6 8
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____9_L_PA ,//7 9
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_SLASH__QM ,//8 :
             KeyboardConst.HIDKEY_MOD_L_SFT,0x32 ,//9 ;
            // 6 ==================
             KeyboardConst.HIDKEY_MOD__NONE,0x64 ,//0 <
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____0_R_PA ,//1 =
             KeyboardConst.HIDKEY_MOD_L_SFT,0x64 ,//2 >
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_MIN_UNDER ,//3 ?
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____q____Q ,//4 @
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____a____A ,//5 A
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____b____B ,//6 B
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____c____C ,//7 C
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____d____D ,//8 D
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____f____F ,//0 F
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____g____G ,//1 G
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____h____H ,//2 H
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____i____I ,//3 I
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____j____J ,//4 J
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____k____K ,//5 K
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____l____L ,//6 L
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____m____M ,//7 M
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____n____N ,//8 N
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____p____P ,//0 P
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____q____Q ,//1 Q
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____r____R ,//2 R
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____s____S ,//3 S
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____t____T ,//4 T
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____u____U ,//5 U
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____v____V ,//6 V
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____w____W ,//7 W
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____x____X ,//8 X
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____z____Z ,//0 Z
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____8_ASTE ,//1 [
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY_MIN_UNDER ,//2
             KeyboardConst.HIDKEY_MOD_R_ALT,KeyboardConst.HIDKEY____9_L_PA ,//3 ]
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY____3_SHAR ,//4 ^
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_EQU__PLUS ,//5 kb_lower
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY_GRAV_TILD ,//6 `
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____b____B ,//8 b
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____d____D ,//0 d
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____e____E ,//1 e
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____f____F ,//2 f
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____g____G ,//3 g
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____h____H ,//4 h
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____i____I ,//5 i
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____j____J ,//6 j
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____k____K ,//7 k
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____l____L ,//8 l
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____n____N ,//0 n
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____o____O ,//1 o
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____p____P ,//2 p
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____q____Q ,//3 q
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____r____R ,//4 r
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____s____S ,//5 s
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____t____T ,//6 t
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____u____U ,//7 u
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____v____V ,//8 v
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____x____X ,//0 x
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____y____Y ,//1 y
             KeyboardConst.HIDKEY_MOD__NONE,KeyboardConst.HIDKEY____z____Z ,//2 z
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_LBT___LBR ,//3 {
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_BSLA_VBAR ,//4 |
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_RBT___RBR ,//5 }
             KeyboardConst.HIDKEY_MOD_L_SFT,KeyboardConst.HIDKEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9

    };

/////////////////////////////////////////////////////////////////////////////
//maps ASCII to PS/2 set2 scan-code.
//gAsciiToPS2KeyTable[i][j][k] ....... index i language map index, index j is ascii code.. editing 200 item
gASCToPS2KeyMap
        = new byte[]{
        // xxx.English PS2 keymap
            //{ control key key,general key }
            // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_APOS_QUOT ,//4 "
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//8 &
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_APOS_QUOT ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//2 *
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_EQU__PLUS ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SEMI__COL ,//8 :
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SEMI__COL ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//0 <
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//3 ?
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_LBT___LBR ,//1 [
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BSLA_VBAR ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Spanish PS2 keymap
            //{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY_____L_SFT,0x4c ,//7 - Spanish
             KeyboardConst.PS2KEY_____L_SFT,0x5d ,//8 - Spanish
             KeyboardConst.PS2KEY_____L_SFT,0x52 ,//9 - Spanish
            // 2 ==================
             KeyboardConst.PS2KEY_____L_SFT,0x0e ,//0 - Spanish
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 ==================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 " - Spanish
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 & - Spanish
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//9 ' - Spanish
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 ( - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 ) - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//2 * - Spanish
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//3 + - Spanish
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 - - Spanish
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 / - Spanish
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//8 : - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//9 ; - Spanish
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 < - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 = - Spanish
             KeyboardConst.PS2KEY______NONE,0x61 ,//2 > - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ? - Spanish
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____2_QUOT ,//4 @ - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_LBT___LBR ,//1 [ - Spanish
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_GRAV_TILD ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_RBT___RBR ,//3 ] - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//4 ^ - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//5 kb_lower - Spanish
             KeyboardConst.PS2KEY______NONE,0x54 ,//6 ` - Spanish
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,0x4c ,//3 { - Spanish
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,0x52 ,//5 } - Spanish
             KeyboardConst.PS2KEY_____L_SFT,0x0e ,//6 ~ - Spanish
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Danish PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 "
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 &
             KeyboardConst.PS2KEY______NONE,0x5D ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,0x5D ,//2 *
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//8 :
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____2_QUOT ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____8_ASTE ,//1 [
             KeyboardConst.PS2KEY_____R_ALT,0x61 ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____9_L_PA ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.French PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//3 !
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//4 "
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_APOS_QUOT ,//7 %
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//8 &
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//0 (
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//1 )
             KeyboardConst.PS2KEY______NONE,0x5D ,//2 *
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_EQU__PLUS ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//6 .
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//8 :
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____0_R_PA ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SEMI__COL ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____5_PERC ,//1 [
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____8_ASTE ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ]
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____9_L_PA ,//4 ^
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.German PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 "
             KeyboardConst.PS2KEY______NONE,0x5D ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 &
             KeyboardConst.PS2KEY_____L_SFT,0x5D ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//2 *
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//8 :
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____q____Q ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____8_ASTE ,//1 [
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_MIN_UNDER ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____9_L_PA ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//4 ^
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Italian PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 "
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_APOS_QUOT ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 &
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//2 *
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//8 :
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_SEMI__COL ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_LBT___LBR ,//1 [
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_RBT___RBR ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_EQU__PLUS ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Norwegian PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 "
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 &
             KeyboardConst.PS2KEY______NONE,0x5D ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,0x5D ,//2 *
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//8 :
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____2_QUOT ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____8_ASTE ,//1 [
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____9_L_PA ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Swedish PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 "
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 &
             KeyboardConst.PS2KEY______NONE,0x5D ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,0x5D ,//2 *
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//8 :
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____2_QUOT ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____8_ASTE ,//1 [
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_MIN_UNDER ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____9_L_PA ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.UK_English PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 "
             KeyboardConst.PS2KEY______NONE,0x5D ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//8 &
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_APOS_QUOT ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//2 *
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_EQU__PLUS ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_COMA___LT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_PERIOD_GT ,//6 .
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SEMI__COL ,//8 :
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SEMI__COL ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//0 <
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//3 ?
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_APOS_QUOT ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_LBT___LBR ,//1 [
             KeyboardConst.PS2KEY______NONE,0x61 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Israel PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_APOS_QUOT ,//4 "
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//8 &
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//2 *
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_EQU__PLUS ,//3 +
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_APOS_QUOT ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//6 .
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SEMI__COL ,//8 :
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_PERIOD_GT ,//0 <
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_COMA___LT ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//3 ?
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_RBT___RBR ,//1 [
             KeyboardConst.PS2KEY______NONE,0x61 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_LBT___LBR ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9
        ,
        // xxx.Turkey PS2 keymap		//{ control key key,general key }
             // 0 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_BACKSPACE ,//8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_______TAB ,//9
            // 1 ==================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____RETURN ,//3 ... CR
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             0,0 ,//7
             0,0 ,//8
             0,0 ,//9
            // 2 =================
             0,0 ,//0
             0,0 ,//1
             0,0 ,//2
             0,0 ,//3
             0,0 ,//4
             0,0 ,//5
             0,0 ,//6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____ESCAPE ,//7
             0,0 ,//8
             0,0 ,//9
            // 3 =================
             0,0 ,//0
             0,0 ,//1
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_____SPACE ,//2
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____1_EXCL ,//3 !
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//4 "
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____3_SHAR ,//5 #
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____4_DOLL ,//6 $
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____5_PERC ,//7 %
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____6_CIRC ,//8 &
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____2_QUOT ,//9 '
            // 4 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____8_ASTE ,//0 (
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____9_L_PA ,//1 )
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_MIN_UNDER ,//2 *
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____4_DOLL ,//3 +
             KeyboardConst.PS2KEY______NONE,0x5D ,//4 ,
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_EQU__PLUS ,//5 -
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_SLASH__QM ,//6 .
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____7_AMPE ,//7 /
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____0_R_PA ,//8 0
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____1_EXCL ,//9 1
            // 5 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____2_QUOT ,//0 2
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____3_SHAR ,//1 3
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____4_DOLL ,//2 4
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____5_PERC ,//3 5
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____6_CIRC ,//4 6
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____7_AMPE ,//5 7
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____8_ASTE ,//6 8
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____9_L_PA ,//7 9
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_SLASH__QM ,//8 :
             KeyboardConst.PS2KEY_____L_SFT,0x5D ,//9 ;
            // 6 ==================
             KeyboardConst.PS2KEY______NONE,0x61 ,//0 <
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____0_R_PA ,//1 =
             KeyboardConst.PS2KEY_____L_SFT,0x61 ,//2 >
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_MIN_UNDER ,//3 ?
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____q____Q ,//4 @
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____a____A ,//5 A
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____b____B ,//6 B
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____c____C ,//7 C
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____d____D ,//8 D
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____e____E ,//9 E
            // 7 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____f____F ,//0 F
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____g____G ,//1 G
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____h____H ,//2 H
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____i____I ,//3 I
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____j____J ,//4 J
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____k____K ,//5 K
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____l____L ,//6 L
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____m____M ,//7 M
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____n____N ,//8 N
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____o____O ,//9 O
            // 8 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____p____P ,//0 P
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____q____Q ,//1 Q
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____r____R ,//2 R
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____s____S ,//3 S
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____t____T ,//4 T
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____u____U ,//5 U
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____v____V ,//6 V
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____w____W ,//7 W
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____x____X ,//8 X
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____y____Y ,//9 Y
            // 9 ==================
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____z____Z ,//0 Z
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____8_ASTE ,//1 [
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY_MIN_UNDER ,//2
             KeyboardConst.PS2KEY_____R_ALT,KeyboardConst.PS2KEY____9_L_PA ,//3 ]
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY____3_SHAR ,//4 ^
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_EQU__PLUS ,//5 kb_lower
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY_GRAV_TILD ,//6 `
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____a____A ,//7 custom_simple_dropdown_item_1line
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____b____B ,//8 b
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____c____C ,//9 c
            // 10 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____d____D ,//0 d
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____e____E ,//1 e
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____f____F ,//2 f
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____g____G ,//3 g
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____h____H ,//4 h
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____i____I ,//5 i
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____j____J ,//6 j
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____k____K ,//7 k
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____l____L ,//8 l
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____m____M ,//9 m
            // 11 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____n____N ,//0 n
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____o____O ,//1 o
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____p____P ,//2 p
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____q____Q ,//3 q
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____r____R ,//4 r
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____s____S ,//5 s
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____t____T ,//6 t
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____u____U ,//7 u
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____v____V ,//8 v
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____w____W ,//9 w
            // 12 ==================
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____x____X ,//0 x
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____y____Y ,//1 y
             KeyboardConst.PS2KEY______NONE,KeyboardConst.PS2KEY____z____Z ,//2 z
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_LBT___LBR ,//3 {
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_BSLA_VBAR ,//4 |
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_RBT___RBR ,//5 }
             KeyboardConst.PS2KEY_____L_SFT,KeyboardConst.PS2KEY_GRAV_TILD ,//6 ~
             0,0 ,//7
             0,0 ,//8
             0,0 //9

    };
        };

    /*
     * the end of file.
     */

    //<><><><><><><>
}
