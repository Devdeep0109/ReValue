package com.reselling.Book.repo;

import com.reselling.Book.model.details.User;
import com.reselling.Book.model.enums.OrderStatus;
import com.reselling.Book.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime dateTime);

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    Optional<Order> findByIdAndUser(Long id, User user);
}
