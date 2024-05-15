package com.aoh.ghumdim.places.service.impl;

import com.aoh.ghumdim.places.service.DestinationService;
import com.aoh.ghumdim.places.service.ImageService;
import com.aoh.ghumdim.places.dto.DestinationRequestDto;
import com.aoh.ghumdim.places.dto.DestinationResponseDto;
import com.aoh.ghumdim.places.entity.Destinations;
import com.aoh.ghumdim.places.repo.DestinationRepository;
import com.aoh.ghumdim.places.service.ModelMapperService;
import com.aoh.ghumdim.security.entity.User;
import com.aoh.ghumdim.security.repo.UserRepository;
import com.aoh.ghumdim.cosineSim.DistanceCalculatorService;
import com.aoh.ghumdim.shared.MessageConstant;
import com.aoh.ghumdim.shared.UserResponse;
import com.aoh.ghumdim.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    private final ModelMapperService modelMapperService;

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final DistanceCalculatorService distanceCalculatorService;
//    private final BM25 bm25;


    @Override
    public List<DestinationResponseDto> getDestinationDetail() {
        List<Destinations> places = destinationRepository.findAll();
        return modelMapperService.entityToListDto(places);
    }

    @Override
    public List<DestinationResponseDto> getDestinationDetailWithSortRating(String field) {
        List<Destinations> places = destinationRepository.findAll(Sort.by(Sort.Direction.ASC, field));
        return modelMapperService.entityToListDto(places);
    }

    public Page<Destinations> findDestinationWithPagination(int offset, int pageSize){
        return   destinationRepository.findAll(PageRequest.of(offset,pageSize));
    }

    @Override
    public UserResponse createDestination(DestinationRequestDto placeRequestDto, MultipartFile multipartFile) {
//        Photos photos = new Photos();
//        photos.setPhoto(imageService.upload(multipartFile));
//        imageRepository.save(photos);
        log.info("creating change request");
        Destinations places = modelMapperService.crfRequestDtoToChangeForm(placeRequestDto);
        log.info("author ko kuraa");
        log.info(String.valueOf(placeRequestDto.getAuthor()));
        Optional<User> mapUser = userRepository.findById(placeRequestDto.getAuthor());
        places.setAuthor(mapUser.get());
        log.info("testing ");
//        places.setPhoto(imageService.upload(placeRequestDto.getMultiFile()));
        places.setPhoto(imageService.upload(multipartFile));

        destinationRepository.save(places);
        return new UserResponse(MessageConstant.SAVED_SUCCESSFULLY+ places.getPhoto());

    }



    @Override
    public DestinationResponseDto getDestinationById(Integer id) {
        Destinations places = destinationRepository.findById(id)
                .orElseThrow(() -> {
                log.error("got error" );
                throw new RuntimeException("error finding id with" + id);
                });
        //user object
        //user id
        log.info("printing dto by id");
        log.info(String.valueOf(places.getAuthor().getId()));
        return modelMapperService.changeFormToCRFRequestDto(places);
    }

    public UserResponse updateDestination(Integer id, DestinationRequestDto placeRequestDto) {
        Destinations destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(placeRequestDto.getName()));
        Destinations updatedDestination = modelMapperService.crfRequestDtoToChangeForm(placeRequestDto);
        destination.setName(updatedDestination.getName());
        destination.setAddress(updatedDestination.getAddress());
        destination.setCategory(updatedDestination.getCategory());
        destination.setLatitude(updatedDestination.getLatitude());
        destination.setLongitude(updatedDestination.getLongitude());
        destination.setContactNumber(updatedDestination.getContactNumber());
        destination.setDescription(updatedDestination.getDescription());
        destination.setStatus(updatedDestination.getStatus());
        destinationRepository.save(destination);
        return new UserResponse(MessageConstant.SAVED_SUCCESSFULLY);
    }
    @Override
    public List<DestinationResponseDto> getDestinationsSortedByDistance(double userLatitude, double userLongitude) {
        List<Destinations> allDestinations = destinationRepository.findAll();
        List<Destinations> sortedDestinations = allDestinations.stream()
                .sorted(Comparator.comparingDouble(destination ->
                         distanceCalculatorService.calculateDistance(destination.getLatitude(), destination.getLongitude(), userLatitude, userLongitude)))
                .collect(Collectors.toList());
        return modelMapperService.entityToListDto(sortedDestinations);
    }

    public UserResponse deleteDestination(Integer id){
        destinationRepository.deleteById(id);
        return new UserResponse(MessageConstant.DELETED_SUCCESSFULLY);
    }

    public List<Destinations> searchDestination(String searchKey){
       return destinationRepository.findAll(searchKey);
    }

    public List<Destinations> findByCategory(String category){
        return destinationRepository.findDestinationsByCategory(category);
    }

//    public List<DestinationResponseDto> bm25Search(String[] query, List<DestinationResponseDto> documents, int n){
////        String[] queryWords = query.split("\\s+"); // Split the query into words
//        return bm25.getTopN(query , documents, n);
//    }

}
