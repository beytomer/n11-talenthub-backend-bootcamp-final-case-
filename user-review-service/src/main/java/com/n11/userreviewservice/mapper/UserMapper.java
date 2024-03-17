package com.n11.userreviewservice.mapper;


import com.n11.userreviewservice.dto.user.UserResponse;
import com.n11.userreviewservice.dto.user.UserSaveRequest;
import com.n11.userreviewservice.dto.user.UserUpdateRequest;
import com.n11.userreviewservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
/**
 * @author BeytullahBilek
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "status",constant = "ACTIVE")
    User convertToUser(UserSaveRequest request);
    UserResponse convertToUserResponse(User user);

    User convertUpdateToUser(@MappingTarget User user, UserUpdateRequest request);

    List<UserResponse> convertToUserResponseList(List<User> userList);
}
