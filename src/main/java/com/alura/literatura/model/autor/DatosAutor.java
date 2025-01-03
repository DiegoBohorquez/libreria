package com.alura.literatura.model.autor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor (
        @JsonAlias("name")
        String nombre,
        @JsonAlias("birth_year")
        String fechaDeNacimiento,
        @JsonAlias("death_year")
        String fechaDeMuerte
){
}
