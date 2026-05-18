package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.Order;
import com.ecommerce.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findAllByOrderByOrderDateDesc();
    List<Order> findByOrderStatus(String status);
}
