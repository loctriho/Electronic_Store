//package com.triloc.webapp.electonicstore.controller;
//
//import com.triloc.webapp.electonicstore.dto.UserDTO;
//import com.triloc.webapp.electonicstore.model.Address;
//import com.triloc.webapp.electonicstore.model.User;
//import com.triloc.webapp.electonicstore.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.ui.Model;
//
//import javax.servlet.http.HttpServletRequest;
//import java.security.Principal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class LoginControllerTest {
//
//    @InjectMocks
//    private LoginController loginController;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private Principal principal;
//
//    @Mock
//    private Model model;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testLoginValidPrincipal() {
//        String username = "testUser";
//        User mockUser = new User();
//        mockUser.setEmail("test@example.com");
//        mockUser.setFirst_name("Test");
//        mockUser.setLast_name("User");
//        Address address = new Address();
//        address.setCity("TestCity");
//        mockUser.setAddress(address);
//        CsrfToken mockToken = mock(CsrfToken.class);
//
//        when(principal.getName()).thenReturn(username);
//        when(userRepository.findByUsername(username)).thenReturn(mockUser);
//        when(request.getAttribute(CsrfToken.class.getName())).thenReturn(mockToken);
//        when(mockToken.getToken()).thenReturn("testToken");
//
//        ResponseEntity<?> response = loginController.login(principal, request);
//
//        assertEquals(200, response.getStatusCodeValue());
//        UserDTO userDTO = (UserDTO) response.getBody();
//        assertEquals("test@example.com", userDTO.getEmail());
//        assertEquals("TestCity", userDTO.getAddress().getCity());
//    }
//
//    @Test
//    public void testLoginWithValidUsernameButNoUser() {
//        // Given the Principal returns a valid username, but no associated User in the DB
//        String mockUsername = "mockUsername";
//        when(principal.getName()).thenReturn(mockUsername);
//        when(userRepository.findByUsername(mockUsername)).thenReturn(null);
//
//        // When we call the login method
//        ResponseEntity<?> response = loginController.login(principal, request);
//
//        // Then we expect a 400 BAD_REQUEST response
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("Invalid username/password", response.getBody());
//    }
//
//    @Test
//    public void testLoginWithNullPrincipal() {
//        // Given the Principal is null
//        ResponseEntity<?> response = loginController.login(null, request);
//
//        // Then we expect a 400 BAD_REQUEST response
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("Invalid username/password", response.getBody());
//    }
//
//    @Test
//    public void testRegisterUserExistingUsername() {
//        User mockUser = new User();
//        mockUser.setUsername("existingUser");
//
//        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser);
//
//        ResponseEntity<?> response = loginController.registerUser(mockUser);
//
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("Sorry. Username already been taken", response.getBody());
//    }
//
//    @Test
//    public void testRegisterUserNewUsername() {
//        User mockUser = new User();
//        mockUser.setUsername("newUser");
//        mockUser.setPassword("plainPassword");
//
//        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(null);
//        when(userRepository.save(any(User.class))).thenReturn(mockUser);
//
//        ResponseEntity<?> response = loginController.registerUser(mockUser);
//
//        assertEquals(201, response.getStatusCodeValue());
//        User returnedUser = (User) response.getBody();
//        assertEquals("newUser", returnedUser.getUsername());
//        // Note: You might not want to assert on encoded password, since its value can change due to salting
//    }
//}
