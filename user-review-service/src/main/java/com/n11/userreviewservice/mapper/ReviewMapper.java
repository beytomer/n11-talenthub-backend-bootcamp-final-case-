package com.n11.userreviewservice.mapper;

import com.n11.userreviewservice.dto.review.ReviewResponse;
import com.n11.userreviewservice.dto.review.ReviewSaveRequest;
import com.n11.userreviewservice.dto.review.ReviewUpdateRequest;
import com.n11.userreviewservice.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface ReviewMapper {

    Review convertToReview( ReviewSaveRequest request);

    ReviewResponse convertToReviewResponse(Review review);

    Review convertToUpdateToReview(@MappingTarget Review review, ReviewUpdateRequest request);

    List<ReviewResponse> convertToReviewResponseList(List<Review> reviewList);
}
