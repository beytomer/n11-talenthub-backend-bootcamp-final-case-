package com.n11.userreviewservice.controller;

import com.n11.userreviewservice.common.base.BaseRestResponse;
import com.n11.userreviewservice.common.error.GeneralErrorMessageResponseDetails;
import com.n11.userreviewservice.dto.review.ReviewResponse;
import com.n11.userreviewservice.dto.review.ReviewSaveRequest;
import com.n11.userreviewservice.dto.review.ReviewUpdateRequest;
import com.n11.userreviewservice.service.KafkaProducerService;
import com.n11.userreviewservice.service.ReviewService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
/**
 * @author BeytullahBilek
 */

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "Review Management")
public class ReviewController {
    private final ReviewService reviewService;
    private final KafkaProducerService producerService;

    @PostMapping
    @Operation(summary = "Saves a new review",
            description = "Saves a new review into the system. Requires review details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Review created successfully",
                            content = @Content(schema = @Schema(implementation = ReviewResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid review data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body to create a new review",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReviewSaveRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Save review",
                                    summary = "SaveReviewRequest",
                                    description = "Complete request with all available " +
                                            "fields for Review",
                                    value = """
                                            {
                                            "userId": "1",
                                            "restaurantId": "asya",
                                            "rate": "3",
                                            "comment": "delicious food"
                                            }"""
                            )
                    }
            )
    )
    public ResponseEntity<BaseRestResponse<ReviewResponse>> save(@Valid @RequestBody ReviewSaveRequest request){

        try {
            producerService.sendMessage("ReviewControllerLog", "save received for request: " + request.restaurantId() + " " + request.rate() + " " + request.comment() + " " + request.userId());
            return new ResponseEntity<>(BaseRestResponse.of(reviewService.save(request)), CREATED);
        } catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing save request}");
            throw e;
        } finally {
            producerService.sendMessage("ReviewControllerLog", "save request processed for request");
        }
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Fetches a review by its ID",
            description = "Get review details for a given review ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review got successfully",
                            content = @Content(schema = @Schema(implementation = ReviewResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid review ID provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    public ResponseEntity<BaseRestResponse<ReviewResponse>>getById(@Valid @PathVariable Long reviewId){
        try {
            producerService.sendMessage("ReviewControllerLog","getById received for request: "+"reviewId = "+reviewId.toString());
            return new ResponseEntity<>(BaseRestResponse.of(reviewService.getById(reviewId)),OK);

        }catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing GET/{reviewId} request}");
            throw e;
        } finally {
            producerService.sendMessage("ReviewControllerLog", "getById request processed for request");
        }

    }

    @GetMapping
    @Operation(summary = "Fetches all reviews",
            description = "Get all reviews stored in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All got retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ReviewResponse.class)))
    })
    public ResponseEntity<BaseRestResponse<List<ReviewResponse>>>getAll(){
        try {
            producerService.sendMessage("ReviewControllerLog", "getAllRequest received for request ");
            return new ResponseEntity<>(BaseRestResponse.of(reviewService.getAll()), OK);
        }catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing getAll request}");
            throw e;
        } finally {
            producerService.sendMessage("ReviewControllerLog", "getAll request processed for request");
        }
    }
    @PatchMapping("/{id}")
    @Operation(summary = "Updates a review by its ID",
            description = "Updates review details for a given review ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review updated successfully",
                            content = @Content(schema = @Schema(implementation = ReviewResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid review data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body to create a new review",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReviewUpdateRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Update review",
                                    summary = "UpdateReviewRequest",
                                    description = "Complete request with all available " +
                                            "fields for Review",
                                    value = """
                                            {
                                            "rate": "3",
                                            "comment": "delicious food"
                                            }"""
                            )
                    }
            )
    )
    public ResponseEntity<BaseRestResponse<ReviewResponse>>update(@Valid @PathVariable Long id ,@Valid @RequestBody ReviewUpdateRequest request){
        try {
            producerService.sendMessage("ReviewControllerLog", "update /{id} received for request: " + "id = " + id.toString() + " UserUpdateRequest " + request.comment() + " " + request.rate());
            return new ResponseEntity<>(BaseRestResponse.of(reviewService.update(id,request)), OK);
        } catch (Exception e) {
            producerService.sendMessage("errorLog","Exception occurred while processing update request}");
            throw e;
        }finally {
            producerService.sendMessage("ReviewControllerLog","Update request processed for user id: "+ id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Deletes a review by its ID",
            description = "Removes a review from the system based on the review ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Review deleted successfully")
            })
    public void delete(@Valid@PathVariable Long id){
        try {
            producerService.sendMessage("ReviewControllerLog", "DELETE received for request: " + "id = " + id.toString());
            reviewService.delete(id);
        } finally {
            producerService.sendMessage("ReviewControllerLog", "delete request processed for request");
        }
    }

}