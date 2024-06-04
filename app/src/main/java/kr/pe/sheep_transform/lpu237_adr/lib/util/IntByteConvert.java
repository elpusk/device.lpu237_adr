package kr.pe.sheep_transform.lpu237_adr.lib.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class IntByteConvert {
    public static byte[] intTobyte(int integer, ByteOrder order) {

        ByteBuffer buff = ByteBuffer.allocate(Integer.BYTES);
        buff.order(order);

        // 인수로 넘어온 integer을 putInt로설정
        buff.putInt(integer);
        return buff.array();
    }

    public static int byteToInt(byte[] bytes, ByteOrder order) {

        ByteBuffer buff = ByteBuffer.allocate(Integer.BYTES);
        buff.order(order);

        // buff사이즈는 4인 상태임
        // bytes를 put하면 position과 limit는 같은 위치가 됨.
        buff.put(bytes);
        // flip()가 실행 되면 position은 0에 위치 하게 됨.
        buff.flip();

        return buff.getInt(); // position위치(0)에서 부터 4바이트를 int로 변경하여 반환
    }

    public static byte[] shortTobyte(short integer, ByteOrder order) {

        ByteBuffer buff = ByteBuffer.allocate(Short.BYTES);
        buff.order(order);

        buff.putShort(integer);
        return buff.array();
    }

    public static short byteToShort(byte[] bytes, ByteOrder order) {

        ByteBuffer buff = ByteBuffer.allocate(Short.BYTES);
        buff.order(order);

        buff.put(bytes);
        buff.flip();

        return buff.getShort();
    }

}
