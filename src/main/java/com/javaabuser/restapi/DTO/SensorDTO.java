package com.javaabuser.restapi.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class SensorDTO {
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
}
