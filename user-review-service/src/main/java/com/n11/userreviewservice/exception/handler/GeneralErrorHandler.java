package com.n11.userreviewservice.exception.handler;

import com.n11.userreviewservice.common.base.BaseRestResponse;
import com.n11.userreviewservice.common.error.GeneralErrorMessageResponse;
import com.n11.userreviewservice.common.error.GeneralErrorMessageResponseDetails;
import com.n11.userreviewservice.exception.EntityNotFoundException;
import com.n11.userreviewservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GeneralErrorHandler {

    private final KafkaProducerService kafkaProducerService;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception exception, WebRequest request){
        String message=exception.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessageResponse(LocalDateTime.now(), message, description);
        var restResponse = BaseRestResponse.error(generalErrorMessage);

        kafkaProducerService.sendMessage("errorLog",message);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    protected  ResponseEntity<Object>handleEntityNotFoundException(EntityNotFoundException exception,WebRequest request){
        String message= exception.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessage = new GeneralErrorMessageResponse(LocalDateTime.now(), message, description);
        var restResponse = BaseRestResponse.error(generalErrorMessage);

        kafkaProducerService.sendMessage("errorLog",message);

        return new ResponseEntity<>(restResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object>handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request){
        String description = request.getDescription(false);
        List<FieldError>fieldErrors=exception.getFieldErrors();

        Map<String,String>errorMap=new HashMap<>();

        for(FieldError element :fieldErrors ){
            errorMap.put(element.getField(),  element.getDefaultMessage().toString());
        }

        var generalErrorMessage = new GeneralErrorMessageResponseDetails(LocalDateTime.now(), errorMap,"Method arguments are not valid", description);
        var restResponse = BaseRestResponse.error(generalErrorMessage);

        kafkaProducerService.sendMessage("errorLog","Method arguments are not valid");


        return new ResponseEntity<>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
