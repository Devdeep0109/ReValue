package com.reselling.Book.service;

import com.reselling.Book.authenticityAlgorithm.AuthenticityAlgorithm;
import com.reselling.Book.dto.ReviewRequest;
import com.reselling.Book.model.details.Seller;
import com.reselling.Book.model.details.User;
import com.reselling.Book.model.enums.OrderStatus;
import com.reselling.Book.model.order.OrderItem;
import com.reselling.Book.model.products.Product;
import com.reselling.Book.model.review.Review;
import com.reselling.Book.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final OrderItemRepo orderItemRepo;
    private final UserRepo userRepo;
    private final SellerRepo sellerRepo;
    private final ProductRepo productRepo;
    private final AuthenticityAlgorithm authenticityAlgorithm;

    public void addReview(Long itemId, ReviewRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        OrderItem orderItem = orderItemRepo.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));

        // ownership check
        if (!orderItem.getOrder().getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You cannot review this item");
        }

        // delivery check
        if (orderItem.getOrder().getStatus() != OrderStatus.DELIVERED) {
            throw new IllegalArgumentException("Review allowed only after delivery");
        }

        // duplicate check
        if (reviewRepo.findByOrderItem(orderItem).isPresent()) {
            throw new IllegalArgumentException("Review already submitted");
        }

        // product rating validation
        if (request.getProductRating() < 1 || request.getProductRating() > 5) {
            throw new IllegalArgumentException("Product rating must be between 1 and 5");
        }

        Product product = productRepo.findById(orderItem.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        double sellerRating = authenticityAlgorithm.calculateSellerRating(
                product.getCondition(),
                request.getReceivedCondition()
        );

        Review review = new Review();
        review.setOrderItem(orderItem);
        review.setUser(user);
        review.setProduct(product);
        review.setSeller(orderItem.getSeller());
        review.setSellerClaimedCondition(product.getCondition());
        review.setCustomerReceivedCondition(request.getReceivedCondition());
        review.setProductRating(request.getProductRating());  //per purchase
        review.setSellerRating(sellerRating);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepo.save(review);

        // for average rating of the customer
        updateProductRating(product, request.getProductRating());
        productRepo.save(product);

        updateSellerRating(orderItem.getSeller(), sellerRating);
    }


    private void updateSellerRating(Seller seller, double newRating) {
        double total = seller.getRating() * seller.getTotalSales();
        seller.setTotalSales(seller.getTotalSales() + 1);
        seller.setRating((total + newRating) / seller.getTotalSales());
        sellerRepo.save(seller);
    }
    // setting the average rating of that product.
    private void updateProductRating(Product product, int newRating) {
        double total = product.getAverageRating() * product.getTotalNoOfRatings();
        product.setTotalNoOfRatings(product.getTotalNoOfRatings() + 1);
        product.setAverageRating(
                (total + newRating) / product.getTotalNoOfRatings()
        );
    }

}
