package com.n11.userreviewservice.entity;

import com.n11.userreviewservice.common.base.BaseEntity;
import com.n11.userreviewservice.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author BeytullahBilek
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="addresses")
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city",length = 60)
    private String city;
    @Column(name = "county",length = 60)
    private String county;
    @Column(name = "location")
    private String location;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id") //,nullable = false
    private User user;


}
