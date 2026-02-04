package com.reselling.Book.authenticityAlgorithm;

import com.reselling.Book.model.enums.Conditions;
import org.springframework.stereotype.Component;

@Component
public class AuthenticityAlgorithm {

    public double calculateSellerRating(
            Conditions sellerCondition,
            Conditions customerCondition
    ) {
        int diff = Math.abs(
                sellerCondition.ordinal() - customerCondition.ordinal()
        );

        return switch (diff) {
            case 0 -> 5.0;
            case 1 -> 4.85;
            case 2 -> 4.75;
            case 3 -> 4.6;
            case 4 -> 4.3;
            default -> 3.7;
        };
    }



}
