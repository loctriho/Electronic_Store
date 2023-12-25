package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.ItemsOrder;
import com.triloc.webapp.electonicstore.dto.OrderDTO;
import com.triloc.webapp.electonicstore.exception.ProductNotFoundException;
import com.triloc.webapp.electonicstore.exception.ResourceNotFoundException;
import com.triloc.webapp.electonicstore.model.Order;
import com.triloc.webapp.electonicstore.model.Product;
import com.triloc.webapp.electonicstore.model.User;
import com.triloc.webapp.electonicstore.repository.OrderRepository;
import com.triloc.webapp.electonicstore.repository.PaymentRepository;
import com.triloc.webapp.electonicstore.repository.ProductRepository;
import com.triloc.webapp.electonicstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this); is not needed since we're using @ExtendWith with MockitoExtension
    }

    @Test
    void testGetAllOrdersWhenOrdersExistThenReturnOrders() {
        List<Order> orders = Collections.singletonList(new Order());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderController.getAllOrders();

        assertEquals(1, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void testGetOrderByIdWhenOrderExistsThenReturnOrder() throws ResourceNotFoundException {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(order, response.getBody());
        verify(orderRepository).findById(1L);
    }

    @Test
    void testCreateOrderWhenOrderDTOIsValidAndEntitiesExistThenReturn201() {
        // Arrange
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUsername("testUser");
        orderDTO.setTotalAmount(200.0);
        ItemsOrder itemsOrder = new ItemsOrder();
        itemsOrder.setProductId(1L);
        itemsOrder.setQuantity(2);
        itemsOrder.setPrice(100.0);
        orderDTO.setCartDetails(Collections.singletonList(itemsOrder));

        User user = new User();
        user.setUsername("testUser");

        Product product = new Product();
        product.setProduct_id(1L);
        product.setPrice(100.0);

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // Mock the save method to return the first argument of the call (the order being saved)

        // Act & Assert
        try {
            ResponseEntity<?> response = orderController.createOrder(orderDTO);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody() instanceof Map);
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            assertTrue(responseBody.containsKey("orderId"));
            assertTrue(responseBody.containsKey("message"));
            assertEquals("Order created successfully", responseBody.get("message"));

            verify(userRepository).findByUsername("testUser");
            verify(productRepository).findById(1L);
        } catch (Exception e) {
            fail("Should not have thrown any exception, but threw " + e.getClass().getSimpleName());
        }
    }

    @Test
    void testUpdateOrderWhenOrderExistsThenReturnUpdatedOrder() throws ResourceNotFoundException {
        Order existingOrder = new Order();
        existingOrder.setOrder_id("existingOrderId");
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));

        Order updatedOrderDetails = new Order();
        updatedOrderDetails.setTotal_amount(300.0);
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrderDetails);

        ResponseEntity<Order> response = orderController.updateOrder(1L, updatedOrderDetails);

        assertEquals(updatedOrderDetails, response.getBody());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testDeleteOrderWhenOrderExistsThenReturn200AndDeletedTrue() throws ResourceNotFoundException {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        ResponseEntity<Map<String, Boolean>> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().get("deleted"));
        verify(orderRepository).findById(1L);
        verify(orderRepository).delete(order);
    }
}