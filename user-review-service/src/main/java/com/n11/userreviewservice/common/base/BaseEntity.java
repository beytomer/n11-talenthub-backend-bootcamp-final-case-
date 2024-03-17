package com.n11.userreviewservice.common.base;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
/**
 * @author BeytullahBilek
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements BaseModel{
    @Embedded
    private BaseAdditionalFields baseAdditionalFields;
}
