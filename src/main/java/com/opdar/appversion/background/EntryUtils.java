package com.opdar.appversion.background;

import java.util.Random;

public class EntryUtils {

    public static String build(int len){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < len; j++) {
            int i = random.nextInt(62);
            if(i<10){
                char a = (char) (48 + i);
                builder.append(a);
            }else if(i < 36){
                char a = (char) (65 - 10 + i);
                builder.append(a);
            }else{
                char a = (char) (97 - 36 + i);
                builder.append(a);
            }
        }
        return builder.toString();
    }

    public static String build(){
        return build(10);
    }
}
