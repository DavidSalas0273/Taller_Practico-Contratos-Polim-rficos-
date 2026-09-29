package com.hotel.Hotel.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hotel.Hotel.domain.EstadoHabitacion;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HabitacionEstandarResponse.class, name = "ESTANDAR"),
        @JsonSubTypes.Type(value = SuitePresidencialResponse.class, name = "SUITE")
})
public interface HabitacionResponse {
    UUID id();

    String numero();

    int capacidadMaxima();

    double precioPorNoche();

    EstadoHabitacion estado();
}
