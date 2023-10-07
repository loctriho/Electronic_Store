package com.triloc.webapp.electonicstore.service;

import com.triloc.webapp.electonicstore.controller.UserController;
import com.triloc.webapp.electonicstore.dto.OrderDetailDto;
import com.triloc.webapp.electonicstore.dto.UserDTO;
import com.triloc.webapp.electonicstore.exception.UserNotFoundException;
import com.triloc.webapp.electonicstore.model.User;
import com.triloc.webapp.electonicstore.repository.OrderRepository;
import com.triloc.webapp.electonicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean checkAndChangePassword(User customer, String newPassword) {
        String encodedPassword = customer.getPassword();
        if (passwordEncoder.matches(customer.getPassword(), encodedPassword)) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            customer.setPassword(encodedNewPassword);
            userRepository.save(customer);
            return true;
        }
        return false;
    }

    public UserDTO getUserDTOByUsername(String username) {
        return userRepository.getUserDTOByUsername(username);
    }

    public List<OrderDetailDto> findOrderDetailsByUsername(String username) {
        return orderRepository.findOrderDetailsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findOptionalByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }


    public void editUser(User updatedUser) throws UserNotFoundException {
        Optional<User> existingUserOptional = Optional.ofNullable(userRepository.findByUsername(updatedUser.getUsername()));

        if (!existingUserOptional.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        User existingUser = existingUserOptional.get();
        existingUser.cloneUserFrom(updatedUser);
        userRepository.save(existingUser);
    }
    public boolean checkAndUpdatePassword(String oldPassword, String newPassword, User currentUser) {
        String encodedPassword = currentUser.getPassword();
        if (passwordEncoder.matches(oldPassword, encodedPassword)) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            currentUser.setPassword(encodedNewPassword);
            userRepository.save(currentUser);
            return true;
        }
        return false;
    }

    public void updateAddress(User user, UserController.AddressUpdateRequest addressUpdateRequest) {
        user.getAddress().setStreet(addressUpdateRequest.getAddressStreetNo());
        user.getAddress().setApartment(addressUpdateRequest.getAddressApartment());
        user.getAddress().setCity(addressUpdateRequest.getAddressCity());
        user.getAddress().setState(addressUpdateRequest.getAddressState());
        user.getAddress().setZipCode(addressUpdateRequest.getAddressZip());
        userRepository.save(user);
    }
}
