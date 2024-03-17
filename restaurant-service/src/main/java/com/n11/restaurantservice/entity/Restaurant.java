package com.n11.restaurantservice.entity;

import com.n11.restaurantservice.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.UUID;
/**
 * @author BeytullahBilek
 */
@Getter
@Setter
@AllArgsConstructor
@SolrDocument(collection = "restaurants")
public class Restaurant extends BaseEntity {
    @Id
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String location;
    @Field
    private Double averageScore;
    @Field
    private Boolean isActive;

    public Restaurant() {
        this.id = generateUniqeId();
    }

    private String generateUniqeId(){
        return UUID.randomUUID().toString();

    }


}
