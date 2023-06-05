package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.deliveryTime=TimeUtils.convertTime(deliveryTime);
        this.id=id;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

//    private int convertDeliveryTime(String deliveryTime) {
//        String[] time=deliveryTime.split(":");
//        return Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
//    }
//    public String convertDeliveryTime(int deliveryTime){
//        int hh=deliveryTime/60;
//        int mm=deliveryTime%60;
//        return String.format("%s:%s"+hh,mm);
//    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
//    public String getDeliveryTimeAsString(){
//        return TimeUtils.convertTime(this.deliveryTime);
//    }
}
