package com.n11.restaurantservice.controller;

import com.n11.restaurantservice.common.error.BaseRestResponse;
import com.n11.restaurantservice.common.error.GeneralErrorMessageResponseDetails;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateAverageScoreRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import com.n11.restaurantservice.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
/**
 * @author BeytullahBilek
 */

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurant Controller", description = "Restaurant Management")
public class RestaurantController {
    private final RestaurantService restaurantService;

   @PostMapping
   @Operation(summary = "Creates a new restaurant",
           description = "Saves a new restaurant into the system. Requires restaurant details.",
           responses = {
                   @ApiResponse(responseCode = "201", description = "Restaurant created successfully",
                           content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
                   @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                           content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
           })
   @io.swagger.v3.oas.annotations.parameters.RequestBody(
           description = "Request body to create a new restaurant",
           required = true,
           content = @Content(
                   mediaType = "application/json",
                   schema = @Schema(implementation = RestaurantSaveRequest.class),
                   examples = {
                           @ExampleObject(
                                   name = "Save restaurant",
                                   summary = "SaveRestaurantRequest",
                                   description = "Complete request with all available " +
                                                 "fields for Restaurant",
                                   value = """
                                            {
                                            "name": "Efsane Dürüm",
                                            "location": "23.23456,18.23415"
                                            }"""
                           )
                   }
           )
   )
    public ResponseEntity<BaseRestResponse<RestaurantResponse>>save(@Valid @RequestBody RestaurantSaveRequest restaurantSaveRequest){
       return new ResponseEntity<>(BaseRestResponse.of(restaurantService.save(restaurantSaveRequest)),CREATED);
   }
   @GetMapping("/{id}")
   @Operation(summary = "Fetches a restaurant by its ID",
           description = "Get restaurant details for a given restaurant ID.",
           responses = {
                   @ApiResponse(responseCode = "200", description = "Restaurant retrieved successfully",
                           content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
                   @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                           content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
           })
    public ResponseEntity<BaseRestResponse<RestaurantResponse>>getById(@Valid@PathVariable String id){
       return new ResponseEntity<>(BaseRestResponse.of(restaurantService.getById(id)),OK);
   }
   @GetMapping("/RecommendRestaurants/{userId}")
   @Operation(summary = "Gets recommended restaurants by user ID",
           description = "Fetches a list of recommended restaurants based on the user's location.",
           responses = {
                   @ApiResponse(responseCode = "200", description = "Recommended restaurants got successfully",
                           content = @Content(schema = @Schema(implementation =RestaurantResponse.class))),
                   @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                           content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
           })
   public ResponseEntity<BaseRestResponse<List<RestaurantResponse>>>getRecommendedRestaurantsByUserId(@PathVariable Long userId){
       return new ResponseEntity<>(BaseRestResponse.of(restaurantService.getRecommendedRestaurantsByUserId(userId)),OK);
   }
   @PutMapping("/averageScore/{restaurantId}")
   @Operation(summary = "Updates the average score of a restaurant",
           description = "Updates the average score of a restaurant based on user reviews.",
           responses = {
                   @ApiResponse(responseCode = "200", description = "Restaurant average score updated successfully",
                           content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
                   @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                           content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
           })
   @io.swagger.v3.oas.annotations.parameters.RequestBody(
           description = "Request body to create a new restaurant",
           required = true,
           content = @Content(
                   mediaType = "application/json",
                   schema = @Schema(implementation = RestaurantUpdateAverageScoreRequest.class),
                   examples = {
                           @ExampleObject(
                                   name = "Update restaurant averageScore",
                                   summary = "RestaurantUpdateAverageScoreRequest",
                                   description = "Complete request with all available " +
                                                 "fields for Restaurant",
                                   value = """
                                            {
                                            "restaurantId": "12SDFJHSKSL",
                                            "averageScore": "2"
                                            }"""
                           )
                   }
           )
   )
   public ResponseEntity<BaseRestResponse<RestaurantResponse>> updateRestaurantAverageScore(@PathVariable String restaurantId, @RequestBody RestaurantUpdateAverageScoreRequest request){
       return new ResponseEntity<>(BaseRestResponse.of(restaurantService.updateRestaurantAverageScore(request)), OK);
   }

   @GetMapping
   @Operation(summary = "Fetches all restaurants",
           description = "Get all restaurants stored in the system.",
           responses = {
                   @ApiResponse(responseCode = "200", description = "All restaurants get successfully",
                           content = @Content(schema = @Schema(implementation = RestaurantResponse.class)))
           })
    public ResponseEntity<BaseRestResponse<List<RestaurantResponse>>>getAll(){
       return new ResponseEntity<>(BaseRestResponse.of(restaurantService.getAll()),OK);
   }

   @PatchMapping("/{id}")
   @Operation(summary = "Updates a restaurant's information by its ID",
           description = "Updates restaurant details for a given restaurant ID.",
           responses = {
                   @ApiResponse(responseCode = "200", description = "Restaurant updated successfully",
                           content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
                   @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                           content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
           })
   @io.swagger.v3.oas.annotations.parameters.RequestBody(
           description = "Request body to update a new restaurant",
           required = true,
           content = @Content(
                   mediaType = "application/json",
                   schema = @Schema(implementation = RestaurantUpdateRequest.class),
                   examples = {
                           @ExampleObject(
                                   name = "Update restaurant",
                                   summary = "UpdateRestaurantRequest",
                                   description = "Complete request with all available " +
                                                 "fields for Restaurant",
                                   value = """
                                            {
                                            "name": "Efsane Dürüm",
                                            "location": "23.23456,18.23415"
                                            }"""
                           )
                   }
           )
   )
    public ResponseEntity<BaseRestResponse<RestaurantResponse>>update(@Valid@PathVariable String id ,@Valid@RequestBody RestaurantUpdateRequest request){
       return new ResponseEntity<>(BaseRestResponse.of(restaurantService.update(id,request)),OK);
   }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Deactivates a restaurant by its ID",
            description = "Deactivates a restaurant account based on the restaurant ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant deactivated successfully",
                            content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    public ResponseEntity<BaseRestResponse<RestaurantResponse>>deactivate(@Valid@PathVariable String id ){
        return new ResponseEntity<>(BaseRestResponse.of(restaurantService.inactive(id)),OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Deletes a restaurant by its ID",
            description = "Removes a restaurant from the system based on the restaurant ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully")
            })
    public void delete(@Valid@PathVariable String id){
     restaurantService.delete(id);
    }

}
