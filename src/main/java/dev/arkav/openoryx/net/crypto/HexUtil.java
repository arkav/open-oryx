package dev.arkav.openoryx.net.crypto;

import java.util.ArrayList;

public class HexUtil {
    public static byte[] hexStringToByteArray(String key)
    {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        int index = 0;

        while(index < key.length())
        {

            int e = Integer.parseInt(key.substring(index, index+2),16);
            bytes.add((byte)e);

            index = index + 2;
        }
        byte[] result = new byte[bytes.size()];
        for(int i = 0; i < bytes.size() ; i ++) {
            result[i]=bytes.get(i);
        }
        return result;
    }
}
