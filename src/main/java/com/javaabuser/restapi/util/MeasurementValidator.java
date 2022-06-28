package com.javaabuser.restapi.util;

import com.javaabuser.restapi.models.Measurement;
import com.javaabuser.restapi.services.MeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementValidator(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
