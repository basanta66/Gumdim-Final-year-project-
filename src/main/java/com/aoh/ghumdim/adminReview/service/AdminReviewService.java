package com.aoh.ghumdim.adminReview.service;

import com.aoh.ghumdim.places.entity.Destinations;
import com.aoh.ghumdim.places.repo.DestinationRepository;
import com.aoh.ghumdim.shared.DestinationStatus;
import com.aoh.ghumdim.shared.MessageConstant;
import com.aoh.ghumdim.shared.UserResponse;
import com.aoh.ghumdim.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminReviewService {
    private final DestinationRepository destinationRepository;

    public UserResponse changeStatus(Integer id, String status){
        Destinations destination = destinationRepository.findById(id).orElseThrow(UserNotFoundException::new);
        destination.setStatus(status.toUpperCase());
        destinationRepository.save(destination);
        return new UserResponse(MessageConstant.SAVED_SUCCESSFULLY);
    }


}
