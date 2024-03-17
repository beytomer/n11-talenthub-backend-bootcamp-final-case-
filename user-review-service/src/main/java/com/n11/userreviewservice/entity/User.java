package com.n11.userreviewservice.entity;

import com.n11.userreviewservice.common.base.BaseEntity;
import com.n11.userreviewservice.entity.enums.Gender;
import com.n11.userreviewservice.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @author BeytullahBilek
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",length = 100)
    @NotNull
    private String name;
    @Column(name = "surname",length = 100)
    @NotNull
    private String surname;
    @Column(name = "birth_date")
    @NotNull
    private LocalDateTime birthDate;
    @Column(name = "email",length = 60)
    @NotNull
    private String email;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addressList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review>reviewList;


}
