package com.n11.userreviewservice.service;

import com.n11.userreviewservice.dto.adress.AddressResponse;
import com.n11.userreviewservice.dto.adress.AddressSaveRequest;
import com.n11.userreviewservice.dto.adress.AddressUpdateRequest;

import java.util.List;
/**
 * @author BeytullahBilek
 */
public interface AddressService {
    AddressResponse save(AddressSaveRequest request);

    List<AddressResponse> getById(Long id);

    List<AddressResponse> getAll();

    AddressResponse update(Long id, AddressUpdateRequest request);

    void delete(Long id);

    AddressResponse deactivate(Long id);

}
