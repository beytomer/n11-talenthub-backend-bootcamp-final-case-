package com.n11.userreviewservice.service;

import com.n11.userreviewservice.dto.user.UserResponse;
import com.n11.userreviewservice.dto.user.UserSaveRequest;
import com.n11.userreviewservice.dto.user.UserUpdateRequest;
import com.n11.userreviewservice.entity.User;

import java.util.List;
/**
 * @author BeytullahBilek
 */
public interface UserService {
    UserResponse save(UserSaveRequest request);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    UserResponse update(Long id, UserUpdateRequest request);

    void delete(Long id);

    UserResponse activate(Long id);
    User findEntityById(Long id);

    UserResponse deactivate(Long id);
}
