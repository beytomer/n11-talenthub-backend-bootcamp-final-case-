package com.n11.restaurantservice.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
@Getter
@Setter
public abstract class BaseEntity  {
    @Field
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Field
    private LocalDateTime updatedAt;
    @Field
    private Long createdBy;
    @Field
    private Long updatedBy;
}