package Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CicloCine extends Evento{

    private List<String> listaPeliculas;
    private boolean tieneCharla;

    public void Evento(){

    }

    public CicloCine(){

    }

    // Constructor completo para usar desde la interfaz gráfica
    public CicloCine(String nombre, java.time.LocalDateTime fechaInicio, int duracionHoras, boolean tieneCharla) {
        super(nombre, fechaInicio, duracionHoras); // Le pasa los datos básicos al padre (Evento)
        this.tieneCharla = tieneCharla;
    }

    public List<String> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(List<String> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public boolean isTieneCharla() {
        return tieneCharla;
    }

    public void setTieneCharla(boolean tieneCharla) {
        this.tieneCharla = tieneCharla;
    }

    // El orphanRemoval = true es la clave de la Composición fuerte
    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ciclo_id") // Le avisa a la base de datos a quién le pertenece la película
    private java.util.List<Proyeccion> peliculas = new java.util.ArrayList<>();

    // Método para agregar películas al ciclo
    public void agregarPelicula(Proyeccion p) {
        this.peliculas.add(p);
    }

    // Y el getter por si lo necesitamos después
    public java.util.List<Proyeccion> getPeliculas() {
        return peliculas;
    }

}
