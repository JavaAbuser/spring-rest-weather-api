package com.javaabuser.restapi.controllers;

import com.javaabuser.restapi.DTO.SensorDTO;
import com.javaabuser.restapi.exceptions.sensor.ErrorResponse;
import com.javaabuser.restapi.exceptions.sensor.NotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController("/sensor")
public class SensorsController {
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorsMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorsMessage
                        .append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NotCreatedException(errorsMessage.toString());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotCreatedException(NotCreatedException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), new Date());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
