package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findBySessionId(String sessionId);
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
    void deleteBySessionId(String sessionId);
}
