package com.aoh.ghumdim.review.service.impl;

import com.aoh.ghumdim.places.entity.Destinations;
import com.aoh.ghumdim.places.repo.DestinationRepository;
import com.aoh.ghumdim.review.dto.ReviewDto;
import com.aoh.ghumdim.review.dto.ReviewResponseDto;
import com.aoh.ghumdim.review.entity.Review;
import com.aoh.ghumdim.review.repository.ReviewRepository;
import com.aoh.ghumdim.review.service.ReviewService;
import com.aoh.ghumdim.security.repo.UserRepository;
import com.aoh.ghumdim.shared.MessageConstant;
import com.aoh.ghumdim.shared.UserResponse;
import com.aoh.ghumdim.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;


    public UserResponse createReview(ReviewDto reviewDto){
        Destinations destination = destinationRepository.findById(reviewDto.getDestination()).orElseThrow(UserNotFoundException::new);
        destination.setRating(((destination.getRating()+reviewDto.getRating())/2));

        Review review = new Review();
        review.setReviewDetail(reviewDto.getReviewDetail());
        review.setUser(userRepository.findById(reviewDto.getUser()).orElseThrow(UserNotFoundException::new));
        review.setDestinations(destination);

        reviewRepository.save(review);
        destinationRepository.save(destination);

        return new UserResponse(MessageConstant.SAVED_SUCCESSFULLY);
    }

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }

    public List<ReviewResponseDto> getReviewById(Integer id){
        List<Review> review = reviewRepository.findAllReviewWithDestinationId(id);
        return getReviewDto(review);
    }



    public ReviewResponseDto reviewEntityToDto(Review review){
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
        reviewResponseDto.setReviewDetail(review.getReviewDetail());
        reviewResponseDto.setUserName(review.getUser().getFirstname());
        return reviewResponseDto;
    }
    public List<ReviewResponseDto> getReviewDto(List<Review> reviews){
        return reviews.stream().map(this:: reviewEntityToDto).collect(Collectors.toList());
    }



}
