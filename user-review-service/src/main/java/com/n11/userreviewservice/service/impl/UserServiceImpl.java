package com.n11.userreviewservice.service.impl;

import com.n11.userreviewservice.common.error.GeneralErrorMessage;
import com.n11.userreviewservice.dto.user.UserResponse;
import com.n11.userreviewservice.dto.user.UserSaveRequest;
import com.n11.userreviewservice.dto.user.UserUpdateRequest;
import com.n11.userreviewservice.entity.User;
import com.n11.userreviewservice.entity.enums.Status;
import com.n11.userreviewservice.exception.UserNotFoundException;
import com.n11.userreviewservice.mapper.UserMapper;
import com.n11.userreviewservice.repository.UserRepository;
import com.n11.userreviewservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author BeytullahBilek
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse save(UserSaveRequest request) {
        User user=userMapper.convertToUser(request);
        userRepository.save(user);

        return userMapper.convertToUserResponse(user);
    }

    @Override
    public UserResponse getById(Long id) {
        User user=getUserByID(id);
        return userMapper.convertToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> userList=userRepository.findAll();
        return userMapper.convertToUserResponseList(userList);
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user=getUserByID(id);
        User updatedUser=userMapper.convertUpdateToUser(user,request);
        userRepository.save(updatedUser);
        return userMapper.convertToUserResponse(updatedUser);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

    }

    @Override
    public UserResponse activate(Long id) {
        User user = getUserByID(id);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        return userMapper.convertToUserResponse(user);
    }
    @Override
    public User findEntityById(Long id) {
      return getUserByID(id);

    }

    @Override
    public UserResponse deactivate(Long id) {
        User user = getUserByID(id);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
        return userMapper.convertToUserResponse(user);
    }

    public User getUserByID(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                     new UserNotFoundException(GeneralErrorMessage.USER_NOT_FOUND) );
    }

}
