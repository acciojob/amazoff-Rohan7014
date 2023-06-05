package com.driver;

import java.util.Arrays;
import java.util.List;

public class TimeUtils {
    private TimeUtils(){

    }
    public static int convertTime(String deliveryTime) {
        List<String> list = Arrays.asList(deliveryTime.split(":")); //String[] -> List<String>
        int HH = Integer.parseInt(list.get(0)); //String "123" -> int 123
        int MM = Integer.parseInt(list.get(1));
        return HH * 60 + MM;
    }
    public static String convertTime(int deliveryTime){
        int hh=deliveryTime/60;
        int mm=deliveryTime%60;
        String HH=String.valueOf(hh);
        String MM=String.valueOf(mm);
        if(HH.length()==1){
            HH='0'+HH;
        }
        if(MM.length()==1){
            MM='0'+MM;
        }
        return String.format("%s:%s"+HH,MM);
    }
}
