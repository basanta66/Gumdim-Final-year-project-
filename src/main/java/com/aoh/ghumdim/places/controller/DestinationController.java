//package com.aoh.ghumdim.places.controller;
//
//import com.aoh.ghumdim.places.service.ImageService;
//import com.aoh.ghumdim.places.dto.DestinationRequestDto;
//import com.aoh.ghumdim.places.dto.DestinationResponseDto;
//import com.aoh.ghumdim.places.service.RequestService;
//import com.aoh.ghumdim.shared.MessageConstant;
//import com.aoh.ghumdim.shared.UserResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/form")
//@RequiredArgsConstructor
//public class DestinationController {
//    private final RequestService requestService;
//    private final ImageService imageService;
//
//    @GetMapping("/viewAllDestination")
//    public List<DestinationResponseDto> getAllDestination(){
//        return requestService.getDestinationDetail();
//    }
//
//
//    @PostMapping("/createDestination")
//    public UserResponse createDestination(@RequestPart DestinationRequestDto placeRequestDto, @RequestPart MultipartFile file){
//        return requestService.createDestination(placeRequestDto, file);
//    }
//    @PostMapping("/addDestinationPhoto")
//    public String addDestinationPhoto(@RequestPart("file") MultipartFile multipartFile){
//        return imageService.upload(multipartFile);
//    }
//    @GetMapping("/viewDestination/{id}")
//    public ResponseEntity<DestinationResponseDto> viewDestination(@PathVariable Integer id){
//        DestinationResponseDto placeResponseDto = requestService.getDestinationById(id);
//        return new ResponseEntity<>(placeResponseDto, HttpStatus.OK);
//    }
//
//    @PostMapping("/updateDestination/{id}")
//    public UserResponse updateDestination(@PathVariable Integer id, @RequestBody DestinationRequestDto placeRequestDto){
//        requestService.updateDestination(id, placeRequestDto);
//        return new UserResponse(MessageConstant.SAVED_SUCCESSFULLY);
//    }
//    @GetMapping("/viewDestinationsSortedByDistance")
//    public ResponseEntity<List<DestinationResponseDto>> viewDestinationsSortedByDistance(@RequestParam double userLatitude, @RequestParam double userLongitude) {
//        List<DestinationResponseDto> sortedDestinations = requestService.getDestinationsSortedByDistance(userLatitude, userLongitude);
//        return ResponseEntity.ok(sortedDestinations);
//    }
//}
