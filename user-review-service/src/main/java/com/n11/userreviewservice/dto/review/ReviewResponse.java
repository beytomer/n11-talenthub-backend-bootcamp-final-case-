package com.n11.userreviewservice.dto.review;
/**
 * @author BeytullahBilek
 */
public record ReviewResponse (
        Long id,
        byte rate,
        String comment


){
}
