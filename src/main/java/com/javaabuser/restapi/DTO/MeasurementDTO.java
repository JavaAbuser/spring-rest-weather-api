package com.javaabuser.restapi.DTO;

import com.javaabuser.restapi.models.Sensor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

public class MeasurementDTO {

    @Range(min = -100, max = 100)
    private double value;
    @NotEmpty
    private boolean isRaining;
    @NotEmpty
    private Sensor sensor;

    public MeasurementDTO() {
    }
}
