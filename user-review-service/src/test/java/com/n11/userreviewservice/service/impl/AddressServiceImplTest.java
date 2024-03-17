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
import com.n11.userreviewservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
/**
 * @author BeytullahBilek
 */
@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AdressMapper adressMapper;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl adressService;

    private AddressSaveRequest addressSaveRequest;
    private AddressUpdateRequest addressUpdateRequest;
    private Address address;
    private User user;
    private AddressResponse addressResponse;

    @BeforeEach
    void setUp() {
        addressSaveRequest = mock(AddressSaveRequest.class);
        addressUpdateRequest = mock(AddressUpdateRequest.class);
        address = mock(Address.class);
        user = mock(User.class);
        addressResponse = mock(AddressResponse.class);
    }

    @Test
    void shouldSaveWhenAllDependenciesAreSetUpThenReturnAddressResponse() {
        when(userService.findEntityById(anyLong())).thenReturn(user);
        when(adressMapper.convertToAdress(addressSaveRequest)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(adressMapper.convertToAdressResponse(address)).thenReturn(addressResponse);

        AddressResponse result = adressService.save(addressSaveRequest);

        assertThat(result).isEqualTo(addressResponse);
        verify(userService).findEntityById(anyLong());
        verify(adressMapper).convertToAdress(addressSaveRequest);
        verify(addressRepository).save(address);
        verify(adressMapper).convertToAdressResponse(address);
    }

    @Test
    void shouldGetByIdWhenAllDependenciesAreSetUpThenReturnListOfAddressResponses() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(addressRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(address));
        when(adressMapper.convertToAdressResponse(address)).thenReturn(addressResponse);

        List<AddressResponse> result = adressService.getById(1L);

        assertThat(result).containsExactly(addressResponse);
        verify(userRepository).findById(anyLong());
        verify(addressRepository).findByUserId(anyLong());
        verify(adressMapper).convertToAdressResponse(address);
    }

    @Test
    void shouldGetAllWhenAddressRepositoryIsSetUpThenReturnListOfAddressResponses() {
        when(addressRepository.findAll()).thenReturn(Collections.singletonList(address));
        when(adressMapper.convertToAdressResponseList(Collections.singletonList(address))).thenReturn(Collections.singletonList(addressResponse));

        List<AddressResponse> result = adressService.getAll();

        assertThat(result).containsExactly(addressResponse);
        verify(addressRepository).findAll();
        verify(adressMapper).convertToAdressResponseList(Collections.singletonList(address));
    }

    @Test
    void shouldUpdateWhenAllDependenciesAreSetUpThenReturnAddressResponse() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(adressMapper.convertToUpdateToAdress(address, addressUpdateRequest)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(adressMapper.convertToAdressResponse(address)).thenReturn(addressResponse);

        AddressResponse result = adressService.update(1L, addressUpdateRequest);

        assertThat(result).isEqualTo(addressResponse);
        verify(addressRepository).findById(anyLong());
        verify(adressMapper).convertToUpdateToAdress(address, addressUpdateRequest);
        verify(addressRepository).save(address);
        verify(adressMapper).convertToAdressResponse(address);
    }

    @Test
    void shouldDeleteWhenAddressRepositoryIsSetUpThenCallDeleteById() {
        doNothing().when(addressRepository).deleteById(anyLong());

        adressService.delete(1L);

        verify(addressRepository).deleteById(1L);
    }

    @Test
    void shouldDeactivateWhenAllDependenciesAreSetUpThenSaveAndConvertToAddressResponse() {
        lenient().when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        lenient().when(address.getStatus()).thenReturn(Status.ACTIVE);
        lenient().when(addressRepository.save(address)).thenReturn(address);
        lenient().when(adressMapper.convertToAdressResponse(address)).thenReturn(addressResponse);

        AddressResponse result = adressService.deactivate(1L);

        assertThat(result).isEqualTo(addressResponse);
        verify(addressRepository).findById(anyLong());
        verify(address).setStatus(Status.INACTIVE);
        verify(addressRepository).save(address);
        verify(adressMapper).convertToAdressResponse(address);
    }

    @Test
    void shouldGetAdressByIDWhenAddressRepositoryIsSetUpThenReturnAddress() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        Address result = adressService.getAddressByID(1L);

        assertThat(result).isEqualTo(address);
        verify(addressRepository).findById(1L);
    }

    @Test
    void shouldGetAdressByIDWhenAddressNotFoundThenThrowException() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adressService.getAddressByID(1L))
                .isInstanceOf(AddressNotFoundException.class)
                .hasMessageContaining(GeneralErrorMessage.ADDRESS_NOT_FOUND.getMessage());

        verify(addressRepository).findById(1L);
    }
}