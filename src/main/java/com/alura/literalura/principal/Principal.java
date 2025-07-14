package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.IdiomaRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component

public class Principal {

    @Autowired
    private ConsumoApi consumoApi;
    @Autowired
    private ConvierteDatos convierteDatos;
    @Autowired
    LibroRepository libroRepository;
    @Autowired
    AutorRepository autorRepository;
    @Autowired
    IdiomaRepository idiomaRepository;

    private static final String URL_BASE = "https://gutendex.com/books/";


    Scanner teclado = new Scanner(System.in);

    private List<DatosLibros> datoslibros = new ArrayList<>();



        private static final Map<String, String> IDIOMAS_BONITOS = Map.of(
                "en", "inglés",
                "es", "español",
                "fr", "francés",
                "it", "italiano",
                "de", "alemán",
                "ru", "ruso",
                "ja", "japonés",
                "zh", "chino",
                "ko", "coreano");


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1- Buscar libros por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año\s
                    5- Listar libros por idioma
                    0- Salir \s
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosAño();
                    break;
                case 5:
                    listarLibrosIdiomas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Pon el nombre del libro que quieres buscar: ");
        var nombreLibro = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datosBusqueda = convierteDatos.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado: !");
            System.out.println(libroBuscado.get());

            String codigoIdioma = libroBuscado.get().idiomas().get(0);
            Optional<Idioma> optIdioma = idiomaRepository.findByCodigo(codigoIdioma);
            Idioma idiomaEntidad;
            if (optIdioma.isPresent()) {
                idiomaEntidad = optIdioma.get();
                System.out.println("El idioma ya existe en la base de datos");

            } else {
                System.out.println("El idioma aún no está en la base de datos");
                Idioma nuevo = new Idioma();
                nuevo.setCodigo(codigoIdioma);
                idiomaEntidad = idiomaRepository.save(nuevo);
            }

            DatosAutor datosAutor = libroBuscado.get().autor().get(0);
            Optional<Autor> optAutor = autorRepository.findByNombre(datosAutor.nombre());
            Autor autorEntidad;

            if (optAutor.isPresent()) {
                autorEntidad = optAutor.get();
                System.out.println("El Autor ya existe en la base de datos");
            } else {
                System.out.println("El autor aún no está en la base de datos");
                Autor nuevo = new Autor();
                nuevo.setNombre(datosAutor.nombre());
                nuevo.setFechaNacimiento(datosAutor.fechaNacimiento());
                nuevo.setAñoMuerte(datosAutor.añoMuerte());
                autorEntidad = autorRepository.save(nuevo);
            }


            Optional<Libro> libroExistente = libroRepository.findById(libroBuscado.get().id());
            Libro libroEntidad;
            if (libroExistente.isPresent()) {
                libroEntidad = libroExistente.get();
                System.out.println("El libro ya existe en la base de datos");
            } else {
                System.out.println("El libro aún no está en la base de datos");
                Libro nuevo = new Libro();
                nuevo.setId(libroBuscado.get().id());
                nuevo.setTitulo(libroBuscado.get().titulo());
                nuevo.setAutor(autorEntidad);
                nuevo.setIdioma(idiomaEntidad);
                nuevo.setNumeroDescargas(libroBuscado.get().numeroDescargas().intValue());

                libroEntidad = libroRepository.save(nuevo);
                System.out.println("Libro guardado exitosamente");
            }

        } else {
            System.out.println("Libro no encontrado");
        }


    }

    private void listarLibrosRegistrados() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados en la base de datos aún");
        } else {
            libros.forEach(libro -> {
                System.out.println("Titulo: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma().getCodigo());
                System.out.println("Numero de descargas: " + libro.getNumeroDescargas());
                System.out.println("*****************************************************");
            });
        }

    }

    private void listarAutoresRegistrados() {

        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos aún");
        } else {
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Año de muerte: " + autor.getAñoMuerte());
                System.out.println("******************************************************");
            });

        }
    }


    private void autoresVivosAño() {
        System.out.println("Ingresa el año en que quieres consultar: ");
        Integer añoIngresado = teclado.nextInt();
        List<Autor> autores = autorRepository.autoresVivosEnAño(añoIngresado);

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos aún");
        } else {
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de muerte: " + (autor.getAñoMuerte() != null ? autor.getAñoMuerte() : "Sigue vivo"));
            });
        }



    }

   private void listarLibrosIdiomas() {
        List<Idioma> idiomas = idiomaRepository.findAll();

        if (idiomas.isEmpty()) {
            System.out.println("No hay idiomas guardados en la base de datos aún");
        } else {
            System.out.println("Idiomas guardados: ");
            idiomas.forEach(idioma -> {
                String nombreCute = IDIOMAS_BONITOS.getOrDefault(idioma.getCodigo(), "Idioma no mapeado aún");
                System.out.println("Idioma: - " + idioma.getCodigo() + " - " + nombreCute);
            });
        }

       System.out.println("Escribe el codigo del idioma para listar los libros (Ej: en, it, es): ");
        String codigoIdioma = teclado.nextLine();
        List<Libro>  libros = libroRepository.librosPorIdioma(codigoIdioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma");
        } else {
            String nombreCute = IDIOMAS_BONITOS.getOrDefault(codigoIdioma, "Desconocido");
            System.out.println("Libros en idioma: " + codigoIdioma + "-" + nombreCute);
            libros.forEach(libro -> {
                System.out.println("Titulo: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Numero de descargas: " + libro.getNumeroDescargas());
                System.out.println("*****************************************************");
            }  );
        }



   }




}


