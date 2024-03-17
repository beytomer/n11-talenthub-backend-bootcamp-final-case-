package com.n11.userreviewservice.dto.user;

import com.n11.userreviewservice.entity.enums.Gender;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
public record UserResponse(
        Long id,
        String name,
        String surname,
        LocalDateTime birthDate,
        String email,
        Gender gender

) {
}
