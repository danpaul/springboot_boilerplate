package com.example.demo.mapper;

import com.example.demo.dto.ReviewResponseDto;
import com.example.demo.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper {
    ReviewResponseDto toResponseDto(Review review);

    Iterable<ReviewResponseDto> toResponseDto(Iterable<Review> reviews);
}
