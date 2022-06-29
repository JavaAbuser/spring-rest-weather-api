package com.javaabuser.restapi.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class MeasurementDTO {
    @Min(value = -100)
    @Max(value = 100)
    private double value;
    @NotNull
    private boolean isRaining;
    private SensorDTO sensor;
}
