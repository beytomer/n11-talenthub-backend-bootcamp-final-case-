package com.n11.userreviewservice.dto.user;

import com.n11.userreviewservice.entity.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
public record UserUpdateRequest (
        @Size(max = 100)
        @NotBlank(message = "Name cant be null or blank")
        String name,
        @NotBlank(message = "Surname cant be blank or null")
        @Size(max = 100)
        String surname,
        @Past
        LocalDateTime birthDate,
        @Email
        String email,
        @NotNull
        Gender gender
){
}
