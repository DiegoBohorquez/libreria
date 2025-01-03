package com.alura.literatura.model.libro;

import com.alura.literatura.model.autor.Autor;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private String idiomas;
    private Integer descargas;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;

    public Libro() {

    }

    public Libro(String titulo, List<String> idioma, Integer descargas) {
        this.titulo = titulo;
        this.idiomas  = String.join(",", idioma);
        this.descargas = descargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autores) {
        autores.forEach(a -> a.setLibro(this));
        this.autor = autores;
    }

    @Override
    public String toString() {
        return "\n***********************" +
                "\nLibro: " +
                "\ntitulo = " + titulo +
                "\nidiomas = " + idiomas +
                "\ndescargas = " + descargas +
                "\n***********************";
    }
}
