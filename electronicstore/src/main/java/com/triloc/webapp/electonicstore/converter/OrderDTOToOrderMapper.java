package com.triloc.webapp.electonicstore.mapper;

import com.triloc.webapp.electonicstore.dto.ItemsOrder;
import com.triloc.webapp.electonicstore.dto.OrderDTO;
import com.triloc.webapp.electonicstore.model.*;
import com.triloc.webapp.electonicstore.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTOToOrderMapper {


    @Autowired
    UserRepository userRepository;
    public  Order mapToOrder(OrderDTO orderDTO, User user) {
        Order order = new Order();


        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(ItemsOrder itemsOrder: orderDTO.getCartDetails()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(itemsOrder.getQuantity());
            orderDetail.setProductId(itemsOrder.getProductId());
            orderDetailList.add(orderDetail);

        }
        order.setOrderDetails(orderDetailList);
        // Copy properties from OrderDTO to Order. This will include properties such as totalAmount

        // Set the order date to the current time
        order.setOrder_date(new Date());

        // Link the order to the User
        order.setTotal_amount(orderDTO.getTotalAmount());
//        if(orderDTO.isDefaultAddress()){
//            Address address = new Address();
//            address.setState(orderDTO.getAddress().getState());
//            address.setCity(orderDTO.getAddress().getCity());
//            address.setStreet(orderDTO.getAddress().getStreetNo());
//            address.setApartment(orderDTO.getAddress().getApartment());
//            user.setAddress(address);
//            userRepository.save(user);
//        }
        Payment payment = new Payment();
        payment.setCardholder_name(orderDTO.getCardholder());
        payment.setCard_number(orderDTO.getCardNumber());
        payment.setAmount(orderDTO.getTotalAmount());
        payment.setCvv(orderDTO.getCvv());
        payment.setExpiration_date(orderDTO.getExpiryDate());
        order.setPayment(payment);

        // The orderDetails list will be set in the service layer,
        // where you can look up the current product prices.
        order.setUser(user);
        return order;
    }
}
