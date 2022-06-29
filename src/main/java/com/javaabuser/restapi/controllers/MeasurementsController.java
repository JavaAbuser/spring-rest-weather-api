package com.javaabuser.restapi.controllers;

import com.javaabuser.restapi.DTO.MeasurementDTO;
import com.javaabuser.restapi.exceptions.ErrorResponse;
import com.javaabuser.restapi.exceptions.NotCreatedException;
import com.javaabuser.restapi.models.Measurement;
import com.javaabuser.restapi.services.MeasurementsService;
import com.javaabuser.restapi.util.MeasurementValidator;
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
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public ResponseEntity<List<MeasurementDTO>> getMeasurements(){
        return ResponseEntity.status(HttpStatus.OK).body(measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDaysCount(){
        Long rainyDaysCount = measurementsService.findAll().stream().filter(Measurement::isRaining).count();
        return new ResponseEntity<Long>(rainyDaysCount, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurement, bindingResult);

        if(!bindErrors(bindingResult).isEmpty()){
            throw new NotCreatedException(bindErrors(bindingResult));
        }

        measurementsService.save(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
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
}
