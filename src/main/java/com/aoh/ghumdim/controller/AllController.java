package com.aoh.ghumdim.controller;

import com.aoh.ghumdim.adminReview.service.AdminReviewService;
import com.aoh.ghumdim.cosineSim.TestCosine;
import com.aoh.ghumdim.places.dto.DestinationRequestDto;
import com.aoh.ghumdim.places.dto.DestinationResponseDto;
import com.aoh.ghumdim.places.entity.Destinations;
import com.aoh.ghumdim.places.service.ImageService;
import com.aoh.ghumdim.places.service.DestinationService;
import com.aoh.ghumdim.review.dto.ReviewDto;
import com.aoh.ghumdim.review.dto.ReviewResponseDto;
import com.aoh.ghumdim.review.entity.Review;
import com.aoh.ghumdim.review.service.ReviewService;
import com.aoh.ghumdim.security.dto.AuthenticationRequestDto;
import com.aoh.ghumdim.security.dto.AuthenticationResponseDto;
import com.aoh.ghumdim.security.dto.RegisterRequestDto;
import com.aoh.ghumdim.security.dto.UserResponseDto;
import com.aoh.ghumdim.security.service.AuthenticationService;
import com.aoh.ghumdim.security.service.UserService;
import com.aoh.ghumdim.shared.MessageConstant;
import com.aoh.ghumdim.shared.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ghumdim")
@RequiredArgsConstructor
public class AllController {



    //Authentication Controller

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody @Validated RegisterRequestDto request){
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponseDto authenticate(@RequestBody AuthenticationRequestDto request){
        return service.authenticate(request);
    }

    @GetMapping("/user/{id}")
    public UserResponseDto getUser(@PathVariable Integer id) {
        UserResponseDto userResponseDto=userService.getUserById(id);
        return userResponseDto;
    }
    @GetMapping("/getUserByEmail")
    public int getUserByEmail(@RequestParam String email) {
        int userId=userService.getUserIdByEmail(email);
        return userId;
    }



    //destination Controller
    private final DestinationService destinationService;
    private final ImageService imageService;

    @GetMapping("/viewAllDestination")
    public List<DestinationResponseDto> getAllDestination(){
        return destinationService.getDestinationDetail();
    }
  @GetMapping("/viewAllDestination/sort/rating")
  public List<DestinationResponseDto> getAllDestinationWithRating(){
    return destinationService.getDestinationDetailWithSortRating("rating");
  }


    @PostMapping(value = {"/createDestination"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UserResponse createDestination(@RequestPart DestinationRequestDto placeRequestDto, @RequestPart MultipartFile[] multipartFile){
        return destinationService.createDestination(placeRequestDto, multipartFile[0]);
    }
    @PostMapping("/addDestinationPhoto")
    public String addDestinationPhoto(@RequestPart("file") MultipartFile multipartFile){
        return imageService.upload(multipartFile);
    }
    @GetMapping("/viewDestination/{id}")
    public ResponseEntity<DestinationResponseDto> viewDestination(@PathVariable Integer id){
        DestinationResponseDto placeResponseDto = destinationService.getDestinationById(id);
        return new ResponseEntity<>(placeResponseDto, HttpStatus.OK);
    }

  @GetMapping("/viewDestinationPage/{offset}/{pageSize}")
  public ResponseEntity<Page<Destinations>> viewDestinationWithPagination(@PathVariable int offset,@PathVariable int pageSize){
    Page<Destinations> destinationWithPagination = destinationService.findDestinationWithPagination(offset, pageSize);
    return new ResponseEntity<>(destinationWithPagination, HttpStatus.OK);
  }


    @PostMapping(value = "/updateDestination/{id}")
    public UserResponse updateDestination(@PathVariable Integer id, @RequestBody DestinationRequestDto placeRequestDto){
        destinationService.updateDestination(id, placeRequestDto);
        return new UserResponse(MessageConstant.SAVED_SUCCESSFULLY);
    }
   @DeleteMapping("/deleteDestination/{id}")
   public UserResponse deleteDestination(@PathVariable Integer id){
       return destinationService.deleteDestination(id);
//     return destinationService.put
   }

    @GetMapping("/viewDestinationsSortedByDistance")
    public ResponseEntity<List<DestinationResponseDto>> viewDestinationsSortedByDistance(@RequestParam double userLatitude, @RequestParam double userLongitude) {
        List<DestinationResponseDto> sortedDestinations = destinationService.getDestinationsSortedByDistance(userLatitude, userLongitude);
        return ResponseEntity.ok(sortedDestinations);
    }

    @GetMapping("/viewDestination/search/{key}")
    public List<Destinations> searchDestination(@PathVariable String key){
        return destinationService.searchDestination(key);
    }

    @GetMapping("viewAllDestination/{cat}")
    public List<Destinations> getAllByDestination(@PathVariable String cat){
        return destinationService.findByCategory(cat);
    }

    private final TestCosine testCosine;
  @GetMapping("/viewDestination/cosearch/{key}")
  public List<Destinations> searchDestinationCosine(@PathVariable String key){
    return testCosine.getDestinationsByCosineSimilarity(key);
  }



    //review controller
    private final ReviewService reviewService;


    @PostMapping("/createReview")
    public UserResponse createReview(@RequestBody ReviewDto reviewDto){
        return  reviewService.createReview(reviewDto);
    }

    @GetMapping("/getAllReview")

    public List<Review> getAllReview(){
        return reviewService.getAllReview();
    }

    @GetMapping("/getReview/{id}")
    public List<ReviewResponseDto> getIdReview(@PathVariable Integer id){
      return reviewService.getReviewById(id);
    }




    //admin action for status:
    private final AdminReviewService adminReviewService;

    @PostMapping("/changeStatus/{id}")
    public UserResponse changeDestinationStatus(@PathVariable Integer id, @RequestBody String status){
        return adminReviewService.changeStatus(id, status);
    }

}
