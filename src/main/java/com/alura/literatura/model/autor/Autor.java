package com.alura.literatura.model.autor;

import com.alura.literatura.model.libro.Libro;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private LocalDate fechaDeNacimiento;
    private LocalDate fechaDeMuerte;
    @ManyToOne
    private Libro libro;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        try {
            this.fechaDeNacimiento = LocalDate.of(Integer.parseInt(datosAutor.fechaDeNacimiento()),1,1);
        } catch (DateTimeParseException e) {
            this.fechaDeNacimiento = null;
        }

        try {
            this.fechaDeMuerte = LocalDate.of(Integer.parseInt(datosAutor.fechaDeMuerte()),1,1);
            } catch (DateTimeParseException e) {
            this.fechaDeMuerte = null;
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public LocalDate getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(LocalDate fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "\n*******************" +
                "\nAutor" +
                "\nnombre = " + nombre +
                "\nFecha De Nacimiento =" + fechaDeNacimiento +
                "\nFecha De Muerte = " + fechaDeMuerte +
                "\n******************";
    }
}
