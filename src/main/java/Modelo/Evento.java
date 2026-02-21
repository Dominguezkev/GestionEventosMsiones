package Modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)

// Definimos la clase como abstracta
public abstract class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos comunes como abstracta como pide el requerimiento
    private String nombre;
    private LocalDateTime fechaInicio;
    private int duracionHoras;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    // Relación con Personas (Organizadores y Participantes) [ cité: 186, 187]
    // Usamos Listas por ahora, luego las convertiremos a relaciones JPA
    // Relación Muchos a Muchos para Organizadores
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name = "evento_organizadores", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private List<Persona> organizadores;

    // Relación Muchos a Muchos para Participantes
    @ManyToMany(cascade = CascadeType.ALL)
    // Tabla especifica para los Participantes
    @JoinTable(
            name = "evento_participantes", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private List<Persona> participantes;

    public Evento(){

    }

    public Evento(String nombre, LocalDateTime fechaInicio, int duracionHoras){
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.duracionHoras = duracionHoras;
        this.estado = Estado.PLANIFICACION; // Estado inicial por defecto
        this.organizadores = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    // --- Lógica de Negocio ("Modelo Rico") ---

    // El sistema debe evitar inscribir si no está confirmado [cite: 190]
    public void inscribirParticipante(Persona persona) throws Exception{
        if (this.estado != Estado.CONFIRMADO){
            throw new Exception("No se puede inscribir: El evento no esta confirmado. ");
        }
        // Aquí luego validaremos si la persona ya existe
        this.participantes.add(persona);
    }

    public void confirmarEvento(){
        this.estado = Estado.CONFIRMADO;
    }

    // Getters and Setters básicos

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getDuracionHoras() {return duracionHoras;}

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Persona> getOrganizadores() {
        return organizadores;
    }

    public void setOrganizadores(List<Persona> organizadores) {
        this.organizadores = organizadores;
    }

    public List<Persona> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Persona> participantes) {
        this.participantes = participantes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
