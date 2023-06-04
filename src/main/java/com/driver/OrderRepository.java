package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private Map<String,Order> orderMap=new HashMap<>();
    private Map<String,DeliveryPartner> partnerMap=new HashMap<>();
    private Map<String,String> orderPartnerMap=new HashMap<>();
    private Map<String, ArrayList<String>> partnerOrderMap=new HashMap<>();
    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerMap.put(partner.getId(),partner);
    }

    public Optional<Order> getOrderById(String orderId) {
        if(orderMap.containsKey(orderId)){
            return Optional.of(orderMap.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerId));
        }
        return Optional.empty();
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        ArrayList<String> orders=partnerOrderMap.getOrDefault(partnerId,new ArrayList<>());
        orders.add(orderId);
        partnerOrderMap.put(partnerId,orders);
        orderPartnerMap.put(orderId,partnerId);
    }

    public List<String> getAllOrdersByPartnerId(String partnerId) {
        return partnerOrderMap.getOrDefault(partnerId,new ArrayList<>());
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public List<String > getCountOfUnassignedOrders() {
        return new ArrayList<>(orderPartnerMap.keySet());
    }

    public void deletePartner(String partnerId) {
        partnerMap.remove(partnerId);
        partnerOrderMap.remove(partnerId);
    }

    public void deleteOrder(String id) {
        orderMap.remove(id);
        orderPartnerMap.remove(id);
    }

    public void unAssignOrder(String id) {
        orderPartnerMap.remove(id);
    }
    public String getPartnerForOrder(String orderId) {
        return orderPartnerMap.get(orderId);
    }
}
