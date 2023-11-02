//package com.triloc.webapp.electonicstore.controller;
//
//import com.triloc.webapp.electonicstore.dto.ItemsOrder;
//import com.triloc.webapp.electonicstore.dto.OrderDTO;
//import com.triloc.webapp.electonicstore.exception.ProductNotFoundException;
//import com.triloc.webapp.electonicstore.exception.ResourceNotFoundException;
//import com.triloc.webapp.electonicstore.model.Order;
//import com.triloc.webapp.electonicstore.model.Product;
//import com.triloc.webapp.electonicstore.model.User;
//import com.triloc.webapp.electonicstore.repository.OrderRepository;
//import com.triloc.webapp.electonicstore.repository.ProductRepository;
//import com.triloc.webapp.electonicstore.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class OrderControllerTest {
//
//    @InjectMocks
//    private OrderController orderController;
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();  // Initialize the mockMvc
//        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
//
//    }
//
//    @Test
//    void testGetAllOrders() {
//        when(orderRepository.findAll()).thenReturn(Collections.singletonList(new Order()));
//        assertEquals(1, orderController.getAllOrders().size());
//    }
//
//    @Test
//    void testGetOrderById() throws ResourceNotFoundException {
//        Order order = new Order();
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
//        ResponseEntity<Order> response = orderController.getOrderById(1L);
//        assertEquals(order, response.getBody());
//    }
//
//    @Test
//    void testCreateOrder() {
//        // Create mock data
//        Product mockProduct = new Product();
//        mockProduct.setPrice(100.0); // Set a sample price for the mock product
//
//        User mockUser = new User();
//        // Populate the mock user if necessary...
//
//        OrderDTO orderDTO = new OrderDTO();
//        List<ItemsOrder> cartDetails = new ArrayList<>();
//
//        ItemsOrder mockItemsOrder = new ItemsOrder();
//        mockItemsOrder.setProductId(1L);    // Sample product ID for the mock product
//        mockItemsOrder.setPrice(100.0);     // This should match the price of the mock product
//        mockItemsOrder.setQuantity(2);      // Set a sample quantity
//        // Add other necessary fields...
//
//        cartDetails.add(mockItemsOrder);
//        orderDTO.setCartDetails(cartDetails);
//        orderDTO.setUsername("sampleUsername");  // Set a sample username
//        // Populate other fields of orderDTO if necessary...
//
//        // Mocking repository methods
//        when(userRepository.findByUsername(anyString())).thenReturn(mockUser);
//        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
//        when(orderRepository.save(any(Order.class))).thenReturn(new Order());
//
//        // Execute the method to test
//        ResponseEntity<?> response = orderController.createOrder(orderDTO);
//
//        // Assertions
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        // You can add more assertions to validate the response body or other behaviors...
//    }
//
//
//
//
//    @Test
//    void testUpdateOrder() throws ResourceNotFoundException {
//        Order order = new Order();
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
//        when(orderRepository.save(any(Order.class))).thenReturn(order);
//        ResponseEntity<Order> response = orderController.updateOrder(1L, order);
//        assertEquals(order, response.getBody());
//    }
//
//    @Test
//    void testDeleteOrder() throws ResourceNotFoundException {
//        Order order = new Order();
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
//        ResponseEntity<Map<String, Boolean>> response = orderController.deleteOrder(1L);
//        assertEquals(true, response.getBody().get("deleted"));
//    }
//
////    @Test
////    public void testCaching() throws Exception {
////        // First request
////        mockMvc.perform(get("/orders"))
////                .andExpect(status().isOk());
////
////        // Second request
////        mockMvc.perform(get("/orders"))
////                .andExpect(status().isOk());
////
////        // Verify that the repository method was only called once even though the controller method was invoked twice.
////        verify(orderRepository, times(1)).findAll();
////    }
////
//
//}
