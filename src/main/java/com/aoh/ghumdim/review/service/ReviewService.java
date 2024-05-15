package com.aoh.ghumdim.review.service;

import com.aoh.ghumdim.review.dto.ReviewDto;
import com.aoh.ghumdim.review.dto.ReviewResponseDto;
import com.aoh.ghumdim.review.entity.Review;
import com.aoh.ghumdim.shared.UserResponse;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReview();
    UserResponse createReview(ReviewDto reviewDto);
    List<ReviewResponseDto> getReviewById(Integer id);
}
