package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.OrderDetailDto;
import com.triloc.webapp.electonicstore.dto.UserDTO;
import com.triloc.webapp.electonicstore.exception.UserNotFoundException;
import com.triloc.webapp.electonicstore.model.User;
import com.triloc.webapp.electonicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/createuser")
    public String createUser(Model model, User user) {
        User createdUser = userService.createUser(user);
        model.addAttribute("userName", createdUser.getUsername());
        return "menu";
    }

    @GetMapping("/getuserinfo")
    public @ResponseBody UserDTO getUserInfo(Principal principal) {
        return userService.getUserDTOByUsername(principal.getName());
    }

    @GetMapping("/getuser")
    public @ResponseBody User getUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

//    @PostMapping("/update-customer")
//    public @ResponseBody String updateCustomer(@ModelAttribute("customer") User customer, @RequestParam("newPassword") String newPassword) {
//        boolean isPasswordUpdated = userService.checkAndChangePassword(customer, newPassword);
//        return isPasswordUpdated ? "success" : "failure. Password doesn't match";
//    }

    @GetMapping("/getUserData")
    public @ResponseBody UserDTO getUserData(Principal principal) {
        return userService.getUserDTOByUsername(principal.getName());
    }

    @PostMapping("/update-password")
    public @ResponseBody String updatePassword(Principal principal, @RequestBody PasswordChangeRequest request) {
        User currentUser = userService.findByUsername(principal.getName());
        boolean isPasswordUpdated = userService.checkAndUpdatePassword(request.getOldPassword(), request.getNewPassword(), currentUser);
        return isPasswordUpdated ? "success" : "failure. Old password doesn't match";
    }

    @PostMapping("/orders")
    public ResponseEntity<List<OrderDetailDto>> getPastOrders(Principal principal) {
        List<OrderDetailDto> orderDetailList = userService.findOrderDetailsByUsername(principal.getName());
        return ResponseEntity.ok(orderDetailList);
    }

    @PostMapping("/update-address")
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(@RequestBody AddressUpdateRequest addressUpdateRequest, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        if (user != null) {
            userService.updateAddress(user, addressUpdateRequest);
        } else {
            throw new BadCredentialsException("Invalid username");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody User updatedUser) {
        try {
            userService.editUser(updatedUser);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user details", HttpStatus.BAD_REQUEST);
        }
    }



    // You can continue to add any additional methods or handlers required for your application.

    // ... any other endpoints or methods for this controller ...

    // For the sake of completeness, let's include the static inner class for password change requests:
    public static class PasswordChangeRequest {
        private String oldPassword;
        private String newPassword;

        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }


    // Similarly, for address updates:
    public static class AddressUpdateRequest {
        private String addressStreetNo;
        private String addressApartment;
        private String addressCity;
        private String addressState;
        private String addressZip;

        // getters and setters


        public String getAddressStreetNo() {
            return addressStreetNo;
        }

        public void setAddressStreetNo(String addressStreetNo) {
            this.addressStreetNo = addressStreetNo;
        }

        public String getAddressApartment() {
            return addressApartment;
        }

        public void setAddressApartment(String addressApartment) {
            this.addressApartment = addressApartment;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressState() {
            return addressState;
        }

        public void setAddressState(String addressState) {
            this.addressState = addressState;
        }

        public String getAddressZip() {
            return addressZip;
        }

        public void setAddressZip(String addressZip) {
            this.addressZip = addressZip;
        }
    }
}
