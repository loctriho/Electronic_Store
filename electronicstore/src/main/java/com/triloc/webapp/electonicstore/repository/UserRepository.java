package com.triloc.webapp.electonicstore.repository;

import com.triloc.webapp.electonicstore.dto.UserDTO;
import com.triloc.webapp.electonicstore.model.Order;
import com.triloc.webapp.electonicstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select c from Customer c join fetch c.orders")
List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);


    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.username = :username")
    User findByUsernameAndFetchOrdersEagerly(@Param("username") String username);

    @Query("SELECT new com.triloc.webapp.electonicstore.dto.UserDTO(u.username, u.first_name, u.last_name, u.email, u.phoneNumber, u.address) FROM User u WHERE u.username = :username")
    public UserDTO getUserDTOByUsername(@Param("username") String username);

//
//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders o LEFT JOIN FETCH o.orderDetails WHERE u.username = :username")
//    User findByUsernameWithOrdersAndOrderDetails(@Param("username") String username);


}
