package com.javaabuser.restapi.util;

import com.javaabuser.restapi.models.Measurement;
import com.javaabuser.restapi.services.MeasurementsService;
import com.javaabuser.restapi.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(MeasurementsService measurementsService, SensorsService sensorsService) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if(sensorsService.findByName(measurement.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor", "", "Sensor does not exist");
        }
    }
}
