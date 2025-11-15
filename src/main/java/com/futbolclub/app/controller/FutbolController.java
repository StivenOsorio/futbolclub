package com.futbolclub.app.controller;

import com.futbolclub.app.entity.*;
import com.futbolclub.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class FutbolController {
    
    @Autowired
    private ClubRepository clubRepository;
    
    @Autowired 
    private JugadorRepository jugadorRepository;
    
    @Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private AsociacionRepository asociacionRepository;
    
    // ========== RUTAS WEB PRINCIPALES ==========
    
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
    
    @GetMapping("/demo")
    public String demo(Model model) {
        model.addAttribute("clubes", clubRepository.findAll());
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        
        return "demo";
    }
    
    // ========== DATOS DE PRUEBA ==========
    
    @GetMapping("/crear-datos-simple")
    public String crearDatosSimple() {
        try {
            // Limpiar datos existentes
            jugadorRepository.deleteAll();
            clubRepository.deleteAll();
            entrenadorRepository.deleteAll();
            asociacionRepository.deleteAll();
            
            // Crear Asociaciones
            Asociacion laLiga = new Asociacion("La Liga", "España", "Javier Tebas");
            Asociacion premier = new Asociacion("Premier League", "Inglaterra", "Richard Masters");
            asociacionRepository.save(laLiga);
            asociacionRepository.save(premier);
            
            // Crear Entrenadores
            Entrenador ancelotti = new Entrenador("Carlo", "Ancelotti", LocalDate.of(1959, 6, 10), "Italia", 25, 6000000.0);
            Entrenador guardiola = new Entrenador("Pep", "Guardiola", LocalDate.of(1971, 1, 18), "España", 15, 8000000.0);
            entrenadorRepository.save(ancelotti);
            entrenadorRepository.save(guardiola);
            
            // Crear Clubes con relaciones
            Club realMadrid = new Club();
            realMadrid.setEntrenador(ancelotti);
            realMadrid.setAsociacion(laLiga);
            
            Club manCity = new Club();
            manCity.setEntrenador(guardiola);
            manCity.setAsociacion(premier);
            
            clubRepository.save(realMadrid);
            clubRepository.save(manCity);
            
            // Crear Jugadores
            Jugador benzema = new Jugador("Karim", "Benzema", LocalDate.of(1987, 12, 19), "Delantero", 9, 10000000.0);
            benzema.setClub(realMadrid);
            
            Jugador haaland = new Jugador("Erling", "Haaland", LocalDate.of(2000, 7, 21), "Delantero", 9, 20000000.0);
            haaland.setClub(manCity);
            
            jugadorRepository.save(benzema);
            jugadorRepository.save(haaland);
            
            System.out.println("¡Datos de prueba creados exitosamente!");
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "redirect:/demo";
    }
    
    @GetMapping("/limpiar-simple")
    public String limpiarDatos() {
        jugadorRepository.deleteAll();
        clubRepository.deleteAll();
        entrenadorRepository.deleteAll();
        asociacionRepository.deleteAll();
        System.out.println("Todos los datos han sido eliminados");
        return "redirect:/demo";
    }
    
    // ========== FORMULARIOS PARA CREAR DATOS PERSONALIZADOS ==========
    
    @GetMapping("/crear-asociacion")
    public String mostrarFormularioAsociacion() {
        return "crear-asociacion";
    }
    
    @PostMapping("/crear-asociacion")
    public String crearAsociacion(@RequestParam String nombre,
                                 @RequestParam String pais,
                                 @RequestParam String presidente) {
        try {
            Asociacion asociacion = new Asociacion(nombre, pais, presidente);
            asociacionRepository.save(asociacion);
            System.out.println("Asociación creada: " + nombre);
            
        } catch (Exception e) {
            System.out.println("ERROR creando asociación: " + e.getMessage());
        }
        
        return "redirect:/demo";
    }
    
    @GetMapping("/crear-entrenador")
    public String mostrarFormularioEntrenador() {
        return "crear-entrenador";
    }
    
    @PostMapping("/crear-entrenador")
    public String crearEntrenador(@RequestParam String nombre,
                                 @RequestParam String apellido,
                                 @RequestParam String nacionalidad,
                                 @RequestParam Integer experiencia) {
        try {
            Entrenador entrenador = new Entrenador(nombre, apellido, null, nacionalidad, experiencia, 0.0);
            entrenadorRepository.save(entrenador);
            System.out.println("Entrenador creado: " + nombre + " " + apellido);
            
        } catch (Exception e) {
            System.out.println("ERROR creando entrenador: " + e.getMessage());
        }
        
        return "redirect:/demo";
    }
    
    @GetMapping("/crear-club")
    public String mostrarFormularioClub(Model model) {
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        return "crear-club";
    }
    
    @PostMapping("/crear-club")
    public String crearClub(@RequestParam(required = false) Long entrenadorId, 
                           @RequestParam(required = false) Long asociacionId) {
        try {
            Club club = new Club();
            
            if (entrenadorId != null) {
                Entrenador entrenador = entrenadorRepository.findById(entrenadorId).orElse(null);
                club.setEntrenador(entrenador);
            }
            
            if (asociacionId != null) {
                Asociacion asociacion = asociacionRepository.findById(asociacionId).orElse(null);
                club.setAsociacion(asociacion);
            }
            
            clubRepository.save(club);
            System.out.println("Club creado exitosamente!");
            
        } catch (Exception e) {
            System.out.println("ERROR creando club: " + e.getMessage());
        }
        
        return "redirect:/demo";
    }
    
    @GetMapping("/crear-jugador")
    public String mostrarFormularioJugador(Model model) {
        model.addAttribute("clubes", clubRepository.findAll());
        return "crear-jugador";
    }
    
    @PostMapping("/crear-jugador")
    public String crearJugador(@RequestParam String nombre, 
                              @RequestParam String apellido,
                              @RequestParam String posicion,
                              @RequestParam Integer numero,
                              @RequestParam(required = false) Long clubId) {
        try {
            Jugador jugador = new Jugador(nombre, apellido, null, posicion, numero, 0.0);
            
            if (clubId != null) {
                Club club = clubRepository.findById(clubId).orElse(null);
                jugador.setClub(club);
            }
            
            jugadorRepository.save(jugador);
            System.out.println("Jugador creado: " + nombre + " " + apellido);
            
        } catch (Exception e) {
            System.out.println("ERROR creando jugador: " + e.getMessage());
        }
        
        return "redirect:/demo";
    }
    
    // ========== RUTAS API REST ==========
    
    @GetMapping("/api/clubes")
    @ResponseBody
    public Iterable<Club> obtenerClubes() {
        return clubRepository.findAll();
    }
    
    @GetMapping("/hola")
    @ResponseBody
    public String hola() {
        return "¡Hola! El servidor web está funcionando correctamente.";
    }
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test: Conexión exitosa con Spring Boot y JPA.";
    }
}