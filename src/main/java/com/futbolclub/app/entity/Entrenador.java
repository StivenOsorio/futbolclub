package com.futbolclub.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "entrenadores")
public class Entrenador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(length = 50)
    private String nacionalidad;
    
    @Column(name = "años_experiencia")
    private Integer añosExperiencia;
    
    @Column
    private Double salario;
    
    // ========== RELACIÓN @OneToOne con Club ==========
    @OneToOne(mappedBy = "entrenador", fetch = FetchType.LAZY)
    private Club club;
    
    // Constructor vacío
    public Entrenador() {}
    
    // Constructor con parámetros
    public Entrenador(String nombre, String apellido, LocalDate fechaNacimiento, 
                     String nacionalidad, Integer añosExperiencia, Double salario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.añosExperiencia = añosExperiencia;
        this.salario = salario;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    
    public Integer getAñosExperiencia() { return añosExperiencia; }
    public void setAñosExperiencia(Integer añosExperiencia) { this.añosExperiencia = añosExperiencia; }
    
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    
    public Club getClub() { return club; }
    public void setClub(Club club) { this.club = club; }
}