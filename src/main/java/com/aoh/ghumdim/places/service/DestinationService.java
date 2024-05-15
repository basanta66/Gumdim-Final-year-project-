package com.aoh.ghumdim.places.service;

import com.aoh.ghumdim.places.dto.DestinationRequestDto;
import com.aoh.ghumdim.places.dto.DestinationResponseDto;
import com.aoh.ghumdim.places.entity.Destinations;
import com.aoh.ghumdim.shared.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DestinationService {
    UserResponse createDestination(DestinationRequestDto placeRequestDto, MultipartFile multipartFile);

    List<DestinationResponseDto> getDestinationDetail();

    DestinationResponseDto getDestinationById(Integer id);

    UserResponse updateDestination(Integer id, DestinationRequestDto placeRequestDto);
    List<DestinationResponseDto> getDestinationDetailWithSortRating(String field);
    List<DestinationResponseDto> getDestinationsSortedByDistance(double userLatitude, double userLongitude);
//    List<Destinations> sortDestinationsByDistance(List<Destinations> allDestinations, double userLatitude, double userLongitude);
//    String upload(MultipartFile multipartFile);
    UserResponse deleteDestination(Integer id);
    List<Destinations> searchDestination(String searchKey);

    List<Destinations> findByCategory(String category);

//    List<DestinationResponseDto> bm25Search(String[] query, List<DestinationResponseDto> documents, int n);

    Page<Destinations> findDestinationWithPagination(int offset, int pageSize);

}
