package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    OrderRepository orderRepository= new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner= new DeliveryPartner(partnerId);
        orderRepository.addPartner(partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) throws RuntimeException {
        Optional<Order> orderOpt = orderRepository.getOrderById(orderId);
        Optional<DeliveryPartner> partnerOpt = orderRepository.getPartnerById(partnerId);
        if (orderOpt.isPresent() && partnerOpt.isPresent()) {
            DeliveryPartner partner = partnerOpt.get();
            partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);
            orderRepository.addPartner(partner);
            orderRepository.addOrderPartnerPair(orderId, partnerId);
        }
    }

    public Order getOrderById(String orderId) throws RuntimeException{
        Optional<Order> orderOpt=orderRepository.getOrderById(orderId);
        if(orderOpt.isPresent()){
            return orderOpt.get();
        }
        throw new RuntimeException("Order Not Found");
    }

    public DeliveryPartner getPartnerById(String partnerId)throws RuntimeException {
        Optional<DeliveryPartner> partnerOpt=orderRepository.getPartnerById(partnerId);
        if(partnerOpt.isPresent()){
            return partnerOpt.get();
        }
        throw new RuntimeException("Partner Not Found");
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Optional<DeliveryPartner>p=orderRepository.getPartnerById(partnerId);
        if(p.isPresent()){
            return p.get().getNumberOfOrders();
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getAllOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getAllOrders().size()-orderRepository.getCountOfUnassignedOrders().size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String>orderID=orderRepository.getAllOrdersByPartnerId(partnerId);
//        List<Order>orders=new ArrayList<>();
        int currTime=TimeUtils.convertTime(time);
        int orderLeft=0;
        for(String id:orderID){
            int deliveryTime=orderRepository.getOrderById(id).get().getDeliveryTime();
            if(currTime<deliveryTime){
                orderLeft++;
            }
        }
        return orderLeft;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orderIds=orderRepository.getAllOrdersByPartnerId(partnerId);
        int max=0;
        for(String order:orderIds){
            int deliveryTime=orderRepository.getOrderById(order).get().getDeliveryTime();
            if(deliveryTime>max){
                max=deliveryTime;
            }
        }
        return TimeUtils.convertTime(max);
    }

    public void deletePartnerById(String partnerId) {
        List<String> orders=orderRepository.getAllOrdersByPartnerId(partnerId);
        orderRepository.deletePartner(partnerId);
        for(String id:orders){
            orderRepository.unAssignOrder(id);
        }
    }

    public void deleteOrderById(String orderId) {
        String partnerId = orderRepository.getPartnerForOrder(orderId);
        orderRepository.deleteOrder(orderId);
        if (Objects.nonNull(partnerId)) {
//            DeliveryPartner p=orderRepository.getPartnerById(partnerId).get();
//            Integer initialOrder=p.getNumberOfOrders();
//            initialOrder--;
//            p.setNumberOfOrders(initialOrder);
//            orderRepository.addPartner(p);
//            orderRepository.un(partnerId,orderId);
//            }

            List<String> orderIds = orderRepository.getAllOrdersByPartnerId(partnerId);
            orderIds.remove(orderId);
        }
    }
}
