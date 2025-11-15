package com.futbolclub.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "jugadores")
public class Jugador {
    
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
    private String posicion;
    
    @Column(name = "numero_camiseta")
    private Integer numeroCamiseta;
    
    @Column
    private Double salario;
    
    // ========== RELACIÓN @ManyToOne con Club ==========
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
    
    // Constructor vacío (requerido por JPA)
    public Jugador() {}
    
    // Constructor con parámetros
    public Jugador(String nombre, String apellido, LocalDate fechaNacimiento, 
                   String posicion, Integer numeroCamiseta, Double salario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.posicion = posicion;
        this.numeroCamiseta = numeroCamiseta;
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
    
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    
    public Integer getNumeroCamiseta() { return numeroCamiseta; }
    public void setNumeroCamiseta(Integer numeroCamiseta) { this.numeroCamiseta = numeroCamiseta; }
    
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    
    public Club getClub() { return club; }
    public void setClub(Club club) { this.club = club; }
}