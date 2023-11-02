package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.ItemsOrder;
import com.triloc.webapp.electonicstore.dto.OrderDTO;
import com.triloc.webapp.electonicstore.exception.ProductNotFoundException;
import com.triloc.webapp.electonicstore.exception.ResourceNotFoundException;
import com.triloc.webapp.electonicstore.model.*;
import com.triloc.webapp.electonicstore.repository.OrderRepository;
import com.triloc.webapp.electonicstore.repository.PaymentRepository;
import com.triloc.webapp.electonicstore.repository.ProductRepository;
import com.triloc.webapp.electonicstore.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final PaymentRepository paymentRepository;


    @Autowired
    public OrderController(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) throws ResourceNotFoundException {
        Order order = findOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order order = buildOrder(orderDTO);


            Map<String, Object> response = new HashMap<>();
            response.put("orderId", order.getOrder_id());
            response.put("message", "Order created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>("An error occurred while processing the order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order orderDetails) throws ResourceNotFoundException {
        Order order = findOrderById(id);

        order.setUser(orderDetails.getUser());
        order.setTotal_amount(orderDetails.getTotal_amount());

        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long id) throws ResourceNotFoundException {
        Order order = findOrderById(id);
        orderRepository.delete(order);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    private Order findOrderById(Long id) throws ResourceNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
    }

    private Order buildOrder(OrderDTO orderDTO) throws ProductNotFoundException {
        User user = userRepository.findByUsername(orderDTO.getUsername());
        Order order = new Order();

        // Set initial attributes of the order
        order.setOrder_date(new Date());
        order.setTotal_amount(orderDTO.getTotalAmount());
        order.setAddress(orderDTO.getAddress());
        order.setUser(user);

        // Save the initial order first

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ItemsOrder itemsOrder : orderDTO.getCartDetails()) {
            Product product = productRepository.findById(itemsOrder.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemsOrder.getProductId()));

            if (itemsOrder.getPrice() != product.getPrice()) {
                throw new IllegalArgumentException("Price mismatch for product " + product.getProduct_name());
            }

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(itemsOrder.getQuantity());
            orderDetail.setProductId(itemsOrder.getProductId());
            orderDetail.setPrice(itemsOrder.getPrice() * itemsOrder.getQuantity());
            orderDetail.setDate(new Date());
            orderDetail.setOrder(order);

            // Save each orderDetail before adding it to the list
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetails(orderDetailList);

        Payment payment = new Payment();
        payment.setCardholder_name(orderDTO.getCardholder());
        payment.setCard_number(orderDTO.getCardNumber());
        payment.setAmount(orderDTO.getTotalAmount());
        payment.setCvv(orderDTO.getCvv());
        payment.setExpiration_date(orderDTO.getExpiryDate());
        payment.setCardholder_name(user.getUsername());
        payment.setOrder(order);
        order.setPayment(payment);
        paymentRepository.save(payment);
//        orderRepository.save(order);

        // Save the payment



        return order;
    }

}
