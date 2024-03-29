package com.n11.userreviewservice.common.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
@Getter
@Setter
@NoArgsConstructor
public class BaseRestResponse<T> {

    private T data;
    private LocalDateTime responseDate;
    private boolean isSuccess;
    private String messages;

    public BaseRestResponse(T data, boolean isSuccess){
        this.data = data;
        this.isSuccess = isSuccess;
        this.responseDate = LocalDateTime.now();
    }

    public static <T> BaseRestResponse<T> of(T t){
        return new BaseRestResponse<>(t, true);
    }

    public static <T> BaseRestResponse<T> error(T t){
        return new BaseRestResponse<>(t, false);
    }

}