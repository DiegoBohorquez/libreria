package com.alura.literatura.principal;

import com.alura.literatura.model.autor.Autor;
import com.alura.literatura.model.autor.DatosAutor;
import com.alura.literatura.model.libro.Datos;
import com.alura.literatura.model.libro.DatosLibro;
import com.alura.literatura.model.libro.Libro;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner in = new Scanner(System.in);
    private static final String url_base = "https://gutendex.com/books/?search=";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void ejecutarMenu() {
        int opcion = 1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {

                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    libros = listarLibros();
                    mostrarDatosLibro(libros);
                    break;
                case 3:
                    var autoresRegistrados = listarAutores();
                    autoresRegistrados.stream()
                            .sorted(Comparator.comparing(Autor::getNombre))
                            .forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Digite el año en el que desea listar los autores: ");
                    int ano = in.nextInt();
                    var fecha = LocalDate.of(ano,1,1);
                    autores = listarAutoresPorAño(fecha);
                    autores.stream()
                            .sorted(Comparator.comparing(Autor::getNombre))
                            .forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Digite el idioma para el libro: ");
                    in.nextLine();
                    var idioma = in.nextLine();
                    libros = listarLibrosPorIdioma(idioma);
                    mostrarDatosLibro(libros);
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;

            }
        }
    }

    private Datos getDatosLibro() {
        System.out.print("Digite el titulo del libro: ");
        in.nextLine();
        String tituloLibro = in.nextLine();
        String json = consumoAPI.obtenerDatos(url_base + tituloLibro.replace(" ", "%20"));
        return convierteDatos.obtenerDatos(json, Datos.class);
    }

    private void buscarLibroPorTitulo() {
        Datos datos = getDatosLibro();
        DatosLibro libro = datos.resultados().getFirst();
        if (libro != null) {
            System.out.println("Libro encontrado: " + libro.titulo());
            var libroBuscado = new Libro(libro.titulo(), libro.idioma(), libro.descargas());
            var autor = libro.autor().stream().map(Autor::new)
                    .collect(Collectors.toList());

            libroBuscado.setAutor(autor);
            libroRepository.save(libroBuscado);

        }
    }

    private List<Libro> listarLibros() {
        libros = libroRepository.findAll();

        return libros;
    }

    private List<Autor> listarAutores() {
        autores = autorRepository.findAll();
        return autores;
    }

    private List<Autor> listarAutoresPorAño(LocalDate ano) {
        autores = autorRepository.findByFechaDeNacimiento(ano);
        return autores.stream()
                .filter(a -> a.getFechaDeMuerte().getYear() == 0 || a.getFechaDeMuerte().isAfter(ano))
                .collect(Collectors.toList());
    }

    private List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdiomas(idioma);
    }

    private void mostrarDatosLibro(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println("********************" +
                    "\nLibro:");
            System.out.println("Título: " + libro.getTitulo());
            for (Autor autor : libro.getAutor()) {
                System.out.println("Autor: " + autor.getNombre());
            }
            System.out.println("Idioma(s): " + libro.getIdiomas());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("********************");
        }
    }

    private void mostrarMenu(){
        System.out.println("-----------------");
        System.out.println("Elija la opcion que desea ejecutar:" +
                "\n1- Buscar libro por titulo" +
                "\n2- Listar libros registrados" +
                "\n3- Listar autores registrados" +
                "\n4- Listar autores vivos en un año determinado" +
                "\n5- Listar libros por idioma" +
                "\n0- Salir");
        System.out.println("-----------------");
    }

    private int leerOpcion() {
        int opcion = -1;

        while (true) {
            System.out.print("Digite su opcion: ");
            try {
                opcion = in.nextInt();

                if (opcion < 0 || opcion > 5) {
                    System.out.println("Opción fuera de rango. Ingrese un número entre 0 y 5.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite un número válido.");
                in.next();
            }
        }
        return opcion;
    }
}
