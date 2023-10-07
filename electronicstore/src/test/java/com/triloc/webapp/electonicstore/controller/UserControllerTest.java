package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.OrderDetailDto;
import com.triloc.webapp.electonicstore.dto.UserDTO;
import com.triloc.webapp.electonicstore.exception.UserNotFoundException;
import com.triloc.webapp.electonicstore.model.User;
import com.triloc.webapp.electonicstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void createUserTest() {
        User user = new User();
        user.setUsername("testUser");

        when(userService.createUser(any(User.class))).thenReturn(user);

        String result = userController.createUser(model, user);

        assertEquals("menu", result);
        verify(model).addAttribute("userName", "testUser");
    }

    @Test
    void getUserInfoTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(principal.getName()).thenReturn("testUser");
        when(userService.getUserDTOByUsername("testUser")).thenReturn(userDTO);

        UserDTO result = userController.getUserInfo(principal);

        assertEquals(userDTO, result);
    }

    @Test
    void editUserTest() throws UserNotFoundException {
        User updatedUser = new User();
        updatedUser.setUsername("updatedUser");

        doNothing().when(userService).editUser(updatedUser);

        assertEquals(userController.editUser(updatedUser).getStatusCodeValue(), 200);
    }

    @Test
    void getUserTest() {
        User user = new User();
        user.setUsername("testUser");

        when(principal.getName()).thenReturn("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);

        User result = userController.getUser(principal);

        assertEquals(user, result);
    }

    @Test
    void getUserDataTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(principal.getName()).thenReturn("testUser");
        when(userService.getUserDTOByUsername("testUser")).thenReturn(userDTO);

        UserDTO result = userController.getUserData(principal);

        assertEquals(userDTO, result);
    }

    @Test
    void updatePasswordTest() {
        UserController.PasswordChangeRequest request = new UserController.PasswordChangeRequest();
        request.setOldPassword("oldPass");
        request.setNewPassword("newPass");

        User user = new User();
        user.setUsername("testUser");

        when(principal.getName()).thenReturn("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);
        when(userService.checkAndUpdatePassword(anyString(), anyString(), any(User.class))).thenReturn(true);

        String result = userController.updatePassword(principal, request);

        assertEquals("success", result);
    }

    @Test
    void getPastOrdersTest() {
        List<OrderDetailDto> orderDetailList = new ArrayList<>();

        when(principal.getName()).thenReturn("testUser");
        when(userService.findOrderDetailsByUsername("testUser")).thenReturn(orderDetailList);

        ResponseEntity<List<OrderDetailDto>> response = userController.getPastOrders(principal);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderDetailList, response.getBody());
    }

    @Test
    void updateAddressTest() {
        UserController.AddressUpdateRequest request = new UserController.AddressUpdateRequest();
        request.setAddressCity("testCity");

        User user = new User();
        user.setUsername("testUser");

        when(authentication.getName()).thenReturn("testUser");
        when(userService.findByUsername("testUser")).thenReturn(user);

        userController.updateAddress(request, authentication);

        verify(userService).updateAddress(any(User.class), any(UserController.AddressUpdateRequest.class));
    }
}
