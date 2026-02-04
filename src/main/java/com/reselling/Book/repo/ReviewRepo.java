package com.reselling.Book.repo;

import com.reselling.Book.model.order.OrderItem;
import com.reselling.Book.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {

    Optional<Review> findByOrderItem(OrderItem orderItem);
}
