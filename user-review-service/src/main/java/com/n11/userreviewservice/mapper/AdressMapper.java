package com.n11.userreviewservice.mapper;

import com.n11.userreviewservice.dto.adress.AddressResponse;
import com.n11.userreviewservice.dto.adress.AddressSaveRequest;
import com.n11.userreviewservice.dto.adress.AddressUpdateRequest;
import com.n11.userreviewservice.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface AdressMapper {
    @Mapping(target = "status",constant = "ACTIVE")
    Address convertToAdress(AddressSaveRequest request);

    AddressResponse convertToAdressResponse(Address address);

    Address convertToUpdateToAdress(@MappingTarget Address address, AddressUpdateRequest request);

    List<AddressResponse> convertToAdressResponseList(List<Address> addressList);
}
