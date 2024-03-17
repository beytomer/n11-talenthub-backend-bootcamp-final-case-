package com.n11.userreviewservice.dto.user;

import com.n11.userreviewservice.entity.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
public record UserSaveRequest(

         @NotBlank(message = "Name cant be null or blank")
         @Size(max = 100)
         String name,
         @Size(max = 100)
         @NotBlank(message = "Surname cant be null or blank")
         String surname,
         @Past
         LocalDateTime birthDate,
         @Email
         String email,
         @NotNull
         Gender gender
) {

}
