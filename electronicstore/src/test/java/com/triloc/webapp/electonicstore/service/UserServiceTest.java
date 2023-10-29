//package com.triloc.webapp.electonicstore.service;
//
//import com.triloc.webapp.electonicstore.controller.UserController;
//import com.triloc.webapp.electonicstore.dto.OrderDetailDto;
//import com.triloc.webapp.electonicstore.dto.UserDTO;
//import com.triloc.webapp.electonicstore.exception.UserNotFoundException;
//import com.triloc.webapp.electonicstore.model.Address;
//import com.triloc.webapp.electonicstore.model.User;
//import com.triloc.webapp.electonicstore.repository.OrderRepository;
//import com.triloc.webapp.electonicstore.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateUser() {
//        User user = new User();
//        user.setPassword("plainPassword");
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User savedUser = userService.createUser(user);
//
//        assertNotEquals("plainPassword", savedUser.getPassword());
//        assertTrue(savedUser.isEnabled());
//    }
//
//    @Test
//    void testUpdateUser() {
//        User user = new User();
//        when(userRepository.save(user)).thenReturn(user);
//
//        User updatedUser = userService.updateUser(user);
//
//        assertEquals(user, updatedUser);
//    }
//
//    // Similar tests for deleteUser, checkAndChangePassword, getUserDTOByUsername, etc.
//
//    @Test
//    void testEditUser() throws UserNotFoundException {
//        User existingUser = new User();
//        existingUser.setUsername("existing");
//
//        when(userRepository.findByUsername("existing")).thenReturn(existingUser);
//
//        User updatedUser = new User();
//        updatedUser.setUsername("existing");
//        updatedUser.setPassword("newPassword");
//
//        userService.editUser(updatedUser);
//
//        verify(userRepository).save(existingUser);
//        assertNotEquals("newPassword", existingUser.getPassword());
//    }
//
//    @Test
//    void testCheckAndUpdatePassword() {
//        User currentUser = new User();
//        currentUser.setPassword(passwordEncoder.encode("oldPassword"));
//
//        when(userRepository.save(any(User.class))).thenReturn(currentUser);
//
//        boolean result = userService.checkAndUpdatePassword("oldPassword", "newPassword", currentUser);
//
//        assertTrue(result);
//        assertNotEquals(passwordEncoder.encode("oldPassword"), currentUser.getPassword());
//    }
//
//    @Test
//    void testFindOrderDetailsByUsername() {
//        when(orderRepository.findOrderDetailsByUsername("testUser")).thenReturn(Collections.singletonList(new OrderDetailDto()));
//
//        List<OrderDetailDto> orderDetailDtos = userService.findOrderDetailsByUsername("testUser");
//
//        assertFalse(orderDetailDtos.isEmpty());
//    }
//
//    @Test
//    void testUpdateAddress() {
//        // 1. Initialize the user object with an address
//        User user = new User();
//        user.setAddress(new Address());  // Ensure the Address is initialized
//
//        // 2. Create and populate the AddressUpdateRequest object
//        UserController.AddressUpdateRequest request = new UserController.AddressUpdateRequest();
//        request.setAddressStreetNo("123");
//        request.setAddressApartment("Apt 1A");
//        request.setAddressCity("TestCity");
//        request.setAddressState("TestState");
//        request.setAddressZip("12345");
//
//        // 3. Call the method you want to test
//        userService.updateAddress(user, request);
//
//        // 4. Verify the method interactions and assert the outcomes
//        verify(userRepository).save(user);
//        assertEquals("123", user.getAddress().getStreet());
//    }
//
//
//
//    // Add other test methods as necessary...
//}
