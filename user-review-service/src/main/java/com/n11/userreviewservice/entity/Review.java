package com.n11.userreviewservice.entity;

import com.n11.userreviewservice.common.base.BaseEntity;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id" )
    private User user;

    @Column(name = "restaurantId")
    private String restaurantId;
    @Column(name = "rate")
    private byte rate;
    @Column(name = "comment",length = 150)
    private String comment;


}
