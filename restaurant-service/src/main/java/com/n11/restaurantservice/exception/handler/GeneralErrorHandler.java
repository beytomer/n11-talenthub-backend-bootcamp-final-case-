package com.n11.restaurantservice.exception.handler;

import com.n11.restaurantservice.common.error.BaseRestResponse;
import com.n11.restaurantservice.common.error.GeneralErrorMessageResponse;
import com.n11.restaurantservice.common.error.GeneralErrorMessageResponseDetails;
import com.n11.restaurantservice.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author BeytullahBilek
 */
@ControllerAdvice
public class GeneralErrorHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception exception, WebRequest request){
        String message=exception.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessageResponse(LocalDateTime.now(), message, description);
        var restResponse = BaseRestResponse.error(generalErrorMessage);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    protected  ResponseEntity<Object>handleEntityNotFoundException(EntityNotFoundException exception,WebRequest request){
        String message= exception.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessageResponse(LocalDateTime.now(), message, description);
        var restResponse = BaseRestResponse.error(generalErrorMessage);
        return new ResponseEntity<>(restResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object>handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request){
        String description = request.getDescription(false);
        List<FieldError> fieldErrors=exception.getFieldErrors();

        Map<String,String> errorMap=new HashMap<>();

        for(FieldError element :fieldErrors ){
            errorMap.put(element.getField(),  element.getDefaultMessage().toString());
        }

        var generalErrorMessage = new GeneralErrorMessageResponseDetails(LocalDateTime.now(), errorMap,"Method arguments are not valid", description);
        var restResponse = BaseRestResponse.error(generalErrorMessage);

        return new ResponseEntity<>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
