package com.reselling.Book.scheduler;


import com.reselling.Book.model.enums.OrderStatus;
import com.reselling.Book.model.order.Order;
import com.reselling.Book.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderDeliveryScheduler{
    @Autowired
    private OrderRepo orderRepo;

    @Scheduled(fixedRate = 60000) // runs every 1 min
    public void deliverOrders() {

        LocalDateTime threshold = LocalDateTime.now().minusMinutes(10);

        List<Order> orders = orderRepo.findByStatusAndCreatedAtBefore(
                OrderStatus.PLACED, threshold);

        for (Order order : orders) {
            order.setStatus(OrderStatus.DELIVERED);
            order.setDeliveredAt(LocalDateTime.now());
        }

        orderRepo.saveAll(orders);
    }
}
