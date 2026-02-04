package com.reselling.Book.dto;

import com.reselling.Book.model.enums.Conditions;
import lombok.Data;

@Data
public class ReviewRequest {

    private int productRating;
    private Conditions receivedCondition;
}
