package com.javaabuser.restapi.DTO;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

public class SensorDTO {
    @NotBlank
    @Range(min = 3, max = 30)
    private String name;

    public SensorDTO() {
    }
}
