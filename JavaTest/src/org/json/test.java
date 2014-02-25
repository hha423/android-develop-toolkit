package org.json;

import java.io.ObjectInputStream.GetField;
import java.util.Collection;

public class test {
    
    private static final String ACK_TAG = "ack";
    private static final String DATA_TAG = "data";
    private static final String LIST_TAG = "list";
    private static final String DESC_TAG = "desc";
    
    private static final String ACK_VALUE_OK = "ok";
    private static final String ACK_VALUE_ERROR = "error";
    private static final String ACK_VALUE_EXCEPTION = "exception";


    public static void main(String[] args) {
        
        JSONObject object = new JSONObject();
        System.out.println(object.optString("desc"));

    }

    public static JSONObject getResponse(String cmdWithMac)
            throws JSONException {
        JSONObject obj = new JSONObject();
        
        byte[] result = new byte[10];
        if (result != null) {
            int len = result.length;
            if (result[len-2] == (byte) 0xff && result[len-1] == (byte) 0x00) {
                JSONObject data = new JSONObject();
                data.put("userAccount", "panxuewen").put("userPassword", "111111");
                obj.put(ACK_TAG, ACK_VALUE_OK).put(DATA_TAG, data);
            } else {
                obj.put(ACK_TAG, ACK_VALUE_EXCEPTION).put(DESC_TAG,"8400");
            }
        }
        return setException(obj);
    }

    private static JSONObject setException(final JSONObject obj) throws JSONException {
        obj.put(ACK_TAG, ACK_VALUE_ERROR);
        return obj;
    }
}
