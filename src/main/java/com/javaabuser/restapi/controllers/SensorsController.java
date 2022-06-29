package com.javaabuser.restapi.controllers;

import com.javaabuser.restapi.DTO.SensorDTO;
import com.javaabuser.restapi.exceptions.*;
import com.javaabuser.restapi.models.Sensor;
import com.javaabuser.restapi.services.SensorsService;
import com.javaabuser.restapi.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public ResponseEntity<List<SensorDTO>> get(@RequestParam(value = "name", required = false) String name){
        if(name != null){
            if(sensorsService.findByName(name).isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(sensorsService.findByName(name).stream().map(this::convertToSensorDTO).collect(Collectors.toList()));
            }
            else {
                throw new NotFoundException("Not found sensor with this name");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(sensorsService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList()));
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult){
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if(!bindErrors(bindingResult).isEmpty()){
            throw new NotCreatedException(bindErrors(bindingResult));
        }

        sensorsService.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult){
        Sensor sensor = convertToSensor(sensorDTO);

        sensorValidator.validate(sensor, bindingResult);

        if(!bindErrors(bindingResult).isEmpty()){
            throw new NotDeletedException(bindErrors(bindingResult));
        }

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }

    private String bindErrors(BindingResult bindingResult){
        StringBuilder errorsMessage = new StringBuilder();
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorsMessage
                        .append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
        }

        return errorsMessage.toString();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleNotCreatedException(NotCreatedException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleNotDeletedException(NotDeletedException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), new Date());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_MODIFIED);
    }
}
