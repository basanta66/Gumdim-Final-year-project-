package com.aoh.ghumdim.places.dto;

import com.aoh.ghumdim.shared.Category;
import com.aoh.ghumdim.shared.DestinationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@JsonIgnoreProperties
public class DestinationResponseDto {

    private Integer id;

//    @JsonIgnore
//    private User author;
//    private Integer authorId;
    private String name;
    private String address;
    private Category category;
    private Double latitude;
    private Double Longitude;
    private DestinationStatus status;
    private String photo;
    private double rating;
    private String description;

}
