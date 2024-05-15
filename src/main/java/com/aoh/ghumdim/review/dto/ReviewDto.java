package com.aoh.ghumdim.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String reviewDetail;
    private Integer user;
    private Integer destination;
    private int rating;
}
