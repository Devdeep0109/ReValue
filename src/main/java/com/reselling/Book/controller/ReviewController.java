package com.reselling.Book.controller;

import com.reselling.Book.dto.ReviewRequest;
import com.reselling.Book.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-item")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{itemId}/review")
    public ResponseEntity<?> addReview(
            @PathVariable Long itemId,
            @RequestBody ReviewRequest request) {

        reviewService.addReview(itemId, request);
        return ResponseEntity.ok("Review submitted successfully");
    }
}
