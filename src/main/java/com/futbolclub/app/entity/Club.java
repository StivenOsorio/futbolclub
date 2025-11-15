package com.futbolclub.app.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.List;

@Entity
@Table(name = "clubes")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ========== RELACIÓN @OneToOne con CASCADE ==========
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entrenador_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Entrenador entrenador;
    
    // ========== RELACIÓN @OneToMany ==========
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_club")
    private List<Jugador> jugadores;
    
    // ========== RELACIÓN @ManyToOne ==========
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asociacion_id")
    private Asociacion asociacion;
    
    // ========== RELACIÓN @ManyToMany ==========
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Competicion> competiciones;
    
    // Constructor vacío
    public Club() {}
    
    // Getters y Setters (los mismos de antes)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }
    
    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }
    
    public Asociacion getAsociacion() { return asociacion; }
    public void setAsociacion(Asociacion asociacion) { this.asociacion = asociacion; }
    
    public List<Competicion> getCompeticiones() { return competiciones; }
    public void setCompeticiones(List<Competicion> competiciones) { this.competiciones = competiciones; }
}