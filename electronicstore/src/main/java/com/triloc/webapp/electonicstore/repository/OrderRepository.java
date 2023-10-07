package com.triloc.webapp.electonicstore.repository;

import com.triloc.webapp.electonicstore.dto.OrderDetailDto;
import com.triloc.webapp.electonicstore.model.Order;
import com.triloc.webapp.electonicstore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("SELECT o FROM Order o JOIN FETCH o.orderDetails WHERE o.user.username = :username")
    List<Order> findByUsernameAndFetchOrderDetailsEagerly(@Param("username") String username);


    @Query("SELECT new com.triloc.webapp.electonicstore.dto.OrderDetailDto(od, od.order.order_id) FROM OrderDetail od JOIN od.order o WHERE o.user.username = :username")
    List<OrderDetailDto> findOrderDetailsByUsername(@Param("username") String username);




}
