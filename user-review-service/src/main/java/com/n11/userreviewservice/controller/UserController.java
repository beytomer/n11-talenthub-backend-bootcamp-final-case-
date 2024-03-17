package com.n11.userreviewservice.controller;

import com.n11.userreviewservice.common.base.BaseRestResponse;
import com.n11.userreviewservice.common.error.GeneralErrorMessageResponseDetails;
import com.n11.userreviewservice.dto.user.UserResponse;
import com.n11.userreviewservice.dto.user.UserSaveRequest;
import com.n11.userreviewservice.dto.user.UserUpdateRequest;
import com.n11.userreviewservice.service.KafkaProducerService;
import com.n11.userreviewservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
/**
 * @author BeytullahBilek
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Management")
public class UserController {

    private final UserService userService;
    private final KafkaProducerService producerService;
    @PostMapping
    @Operation(summary = "Saves a new user",
            description = "Saves a new user into the system. Requires user details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body to create a new user",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserSaveRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "new user",
                                    summary = "CreateUserRequest",
                                    description = "Complete request with all available " +
                                            "fields for User",
                                    value = """
                                            {
                                            "name": "Alex",
                                            "surname": "Pim",
                                            "birthDate": "1994-03-16T22:05:28.7796862",
                                            "email": "alex.pim@example.com",
                                            "gender": "MALE"
                                            }"""
                            )
                    }
            )
    )
    public ResponseEntity<BaseRestResponse<UserResponse>>save(@Valid @RequestBody UserSaveRequest request) {
        try {
            producerService.sendMessage("UserControllerLog", "save received for request: " + request.name() + " " + request.surname() + " " + request.email() + " " + request.birthDate() + " " + request.gender());
            return new ResponseEntity<>(BaseRestResponse.of(userService.save(request)), CREATED);
        } catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing save request}");
            throw e;
        } finally {
            producerService.sendMessage("UserControllerLog", "save request processed for request");
        }
    }
    @GetMapping("/{userId}")
    @Operation(summary = "Fetches a user by their ID",
            description = "Get user details for a given user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User got successfully",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid user ID provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    public ResponseEntity<BaseRestResponse<UserResponse>>getById(@Valid@PathVariable Long userId){
        try {
            producerService.sendMessage("getByIdUserControllerLog","getById received for request: "+"userId = "+userId.toString());
            return new ResponseEntity<>(BaseRestResponse.of(userService.getById(userId)),OK);

        }catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing GET/{userId} request}");
            throw e;
        } finally {
            producerService.sendMessage("UserControllerLog", "getById request processed for request");
        }
    }

    @GetMapping
    @Operation(summary = "Fetches all users",
            description = "Get all users stored in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All users got successfully",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
            })
    public ResponseEntity<BaseRestResponse<List<UserResponse>>>getAll(){
        try {
            producerService.sendMessage("UserControllerLog", "getAllRequest received for request ");
            return new ResponseEntity<>(BaseRestResponse.of(userService.getAll()), OK);
        }catch (Exception e) {
            producerService.sendMessage("ErrorLog", "Exception occurred while processing getAll request}");
            throw e;
        } finally {
            producerService.sendMessage("UserControllerLog", "getAll request processed for request");
        }
    }
    @PatchMapping("/{id}")
    @Operation(summary = "Updates a user's information by their ID",
            description = "Updates user details for a given user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid user data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body to create a new user",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserUpdateRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Update user",
                                    summary = "UpdateUserRequest",
                                    description = "Complete request with all available " +
                                            "fields for User",
                                    value = """
                                            {
                                            "name": "Alex",
                                            "surname": "Pim",
                                            "birthDate": "1994-03-16T22:05:28.7796862",
                                            "email": "alex.pim@example.com",
                                            "gender": "MALE"
                                            }"""
                            )
                    }
            )
    )
    public ResponseEntity<BaseRestResponse<UserResponse>>update(@Valid@PathVariable Long id ,@Valid @RequestBody UserUpdateRequest request){
        try {
            producerService.sendMessage("updateUserControllerLog", "update /{id} received for request: " + "userId = " + id.toString() + " UserUpdateRequest " + request.name() + " " + request.surname() + " " + request.email() + " " + request.birthDate() + " " + request.gender());
            return new ResponseEntity<>(BaseRestResponse.of(userService.update(id, request)),OK);
        } catch (Exception e) {
            producerService.sendMessage("errorLog","Exception occurred while processing update request}");
            throw e;
        }finally {
            producerService.sendMessage("UserControllerLog","Update request processed for user id: "+ id);
        }
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Deactivates a user by their ID",
            description = "Deactivates a user account based on the user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deactivated successfully",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid user ID provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    public ResponseEntity<BaseRestResponse<UserResponse>>deactivate(@Valid@PathVariable Long id){
        try {
            producerService.sendMessage("UserControllerLog", "deactivate/{id} received for request: " + "id = " + id.toString());
            return new ResponseEntity<>(BaseRestResponse.of(userService.deactivate(id)),OK);
        }catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing deactivate request}");
            throw e;
        } finally {
            producerService.sendMessage("UserControllerLog", "deactivate request processed for request");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Deletes a user by their ID",
            description = "Removes a user from the system based on the user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully")

            })
    public void delete(@Valid@PathVariable Long id){
        try {
            producerService.sendMessage("ControllerLog", "DELETE received for request: " + "id = " + id.toString());
            userService.delete(id);
        }catch (Exception e) {
            producerService.sendMessage("errorLog", "Exception occurred while processing delete request}");
            throw e;
        } finally {
            producerService.sendMessage("ControllerLog", "delete request processed for request");
        }

    }


}
