package com.alura.literatura.repository;

import com.alura.literatura.model.libro.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomas(String idiomas);
}
