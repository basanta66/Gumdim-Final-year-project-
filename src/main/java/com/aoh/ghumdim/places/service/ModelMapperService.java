package com.aoh.ghumdim.places.service;

import com.aoh.ghumdim.places.dto.DestinationRequestDto;
import com.aoh.ghumdim.places.dto.DestinationResponseDto;
import com.aoh.ghumdim.places.entity.Destinations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ModelMapperService {

    private final ModelMapper modelMapper;

    public Destinations crfRequestDtoToChangeForm(DestinationRequestDto placeRequestDto){
        return modelMapper.map(placeRequestDto, Destinations.class);
    }

    public DestinationResponseDto changeFormToCRFRequestDto(Destinations places){
        if(places.getStatus().equals("REJECTED")){
            return null;
        }
        DestinationResponseDto placeResponseDto = this.modelMapper.map(places, DestinationResponseDto.class);
//        placeResponseDto.setAuthorId(places.getAuthor().getId());
//        log.info(String.valueOf(placeResponseDto.getAuthorId()));
        return placeResponseDto;
    }

    public List<DestinationResponseDto> entityToListDto(List<Destinations> places){
        return places.stream().map(this:: changeFormToCRFRequestDto).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
