package com.n11.userreviewservice.service.impl;

import com.n11.userreviewservice.common.error.GeneralErrorMessage;
import com.n11.userreviewservice.dto.adress.AddressResponse;
import com.n11.userreviewservice.dto.adress.AddressSaveRequest;
import com.n11.userreviewservice.dto.adress.AddressUpdateRequest;
import com.n11.userreviewservice.entity.Address;
import com.n11.userreviewservice.entity.User;
import com.n11.userreviewservice.entity.enums.Status;
import com.n11.userreviewservice.exception.AddressNotFoundException;
import com.n11.userreviewservice.mapper.AdressMapper;
import com.n11.userreviewservice.repository.AddressRepository;
import com.n11.userreviewservice.repository.UserRepository;
import com.n11.userreviewservice.service.AddressService;
import com.n11.userreviewservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author BeytullahBilek
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AdressMapper adressMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    @Override
    public AddressResponse save(AddressSaveRequest request) {
        User user=userService.findEntityById(request.userId());
        Address address = adressMapper.convertToAdress(request);
        address.setUser(user);
        addressRepository.save(address);
        return adressMapper.convertToAdressResponse(address);
    }

    @Override
    public List<AddressResponse> getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(GeneralErrorMessage.ADDRESS_NOT_FOUND) );
        List<Address> addressList = addressRepository.findByUserId(user.getId());
        return addressList.stream().map(adressMapper::convertToAdressResponse).collect(Collectors.toList());
    }

    @Override
    public List<AddressResponse> getAll() {
        List<Address> addressList = addressRepository.findAll();
        return adressMapper.convertToAdressResponseList(addressList);
    }

    @Override
    public AddressResponse update(Long id, AddressUpdateRequest request) {
       Address address = getAddressByID(id);

       address = adressMapper.convertToUpdateToAdress(address,request);
       addressRepository.save(address);
       return adressMapper.convertToAdressResponse(address);

    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);

    }

    @Override
    public AddressResponse deactivate(Long id) {
        Address address = getAddressByID(id);
        address.setStatus(Status.INACTIVE);
        addressRepository.save(address);
        return adressMapper.convertToAdressResponse(address);
    }
    public Address getAddressByID(Long id){
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(GeneralErrorMessage.ADDRESS_NOT_FOUND));
    }
}
