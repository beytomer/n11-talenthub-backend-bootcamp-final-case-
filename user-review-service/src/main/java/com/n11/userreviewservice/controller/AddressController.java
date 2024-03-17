package com.n11.userreviewservice.controller;
import com.n11.userreviewservice.common.base.BaseRestResponse;
import com.n11.userreviewservice.common.error.GeneralErrorMessageResponseDetails;
import com.n11.userreviewservice.dto.adress.AddressResponse;
import com.n11.userreviewservice.dto.adress.AddressSaveRequest;
import com.n11.userreviewservice.dto.adress.AddressUpdateRequest;
import com.n11.userreviewservice.service.AddressService;
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
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "AddressController", description = "Address Management")
public class AddressController {

    private final AddressService addressService;
    @PostMapping
    @Operation(summary = "Creates a new address",
            description = "Saves a new address into the system. Requires address details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Address created",
                            content = @Content(schema = @Schema(implementation = AddressResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid address data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body to create a new address",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddressSaveRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Save address",
                                    summary = "SaveAddressRequest",
                                    description = "Complete request with all available " +
                                                  "fields for Address",
                                    value = """
                                            {
                                            "city": "Istanbul",
                                            "county": "Levent",
                                            "location": "23.23456,18.23415",
                                            "userId": "1"
                                            }"""
                            )
                    }
            )
    )


    public ResponseEntity<BaseRestResponse<AddressResponse>> save(@Valid @RequestBody AddressSaveRequest request){
        return new ResponseEntity<>(BaseRestResponse.of(addressService.save(request)), CREATED);
    }

    @GetMapping("with-userId/{userId}")
    @Operation(summary = "Get addresses by user ID",
            description = "Fetches a list of addresses associated with a given user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Addresses got successfully",
                            content = @Content(schema = @Schema(implementation = AddressResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid address data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    public ResponseEntity<BaseRestResponse<List<AddressResponse>>>getById(@Valid @PathVariable Long userId){
        return new ResponseEntity<>(BaseRestResponse.of(addressService.getById(userId)),OK);
    }

    @GetMapping
    @Operation(summary = "Get all addresses",
            description = "Fetches all addresses stored in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All addresses got successfully",
                            content = @Content(schema = @Schema(implementation = AddressResponse.class)))
            })
    public ResponseEntity<BaseRestResponse<List<AddressResponse>>>getAll(){
        return new ResponseEntity<>(BaseRestResponse.of(addressService.getAll()), OK);
    }
    @PatchMapping("/{id}")
    @Operation(summary = "Updates an address by its ID",
            description = "Updates address details for a given address ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address updated successfully",
                            content = @Content(schema = @Schema(implementation = AddressResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Invalid address data provided",
                            content = @Content(schema = @Schema(implementation = GeneralErrorMessageResponseDetails.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request body to create a new address",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddressUpdateRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Update address",
                                    summary = "UpdateAddressRequest",
                                    description = "Complete request with all available " +
                                                  "fields for Address",
                                    value = """
                                            {
                                            "city": "Istanbul",
                                            "county": "Levent",
                                            "location": "23.23456,18.23415"
                                            }"""
                            )
                    }
            )
    )
    public ResponseEntity<BaseRestResponse<AddressResponse>>update(@Valid @PathVariable Long id , @Valid @RequestBody AddressUpdateRequest request){
        return new ResponseEntity<>(BaseRestResponse.of(addressService.update(id,request)), OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Deletes an address by its ID",
            description = "Removes an address from the system based on the address ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address deleted successfully"),

            })
    public void delete(@Valid @PathVariable Long id){
        addressService.delete(id);
    }


}
