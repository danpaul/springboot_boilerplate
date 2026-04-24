package com.example.demo.mapper;

import com.example.demo.dto.ReviewRequestDto;
import com.example.demo.dto.ReviewResponseDto;
import com.example.demo.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper {
    ReviewResponseDto toResponseDto(Review review);

    Iterable<ReviewResponseDto> toResponseDto(Iterable<Review> reviews);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    Review toEntity(ReviewRequestDto reviewRequestDto);

//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "book", ignore = true)
//    Review updateEntityFromDto(ReviewRequestDto reviewRequestDto, @MappingTarget Review review);
}
