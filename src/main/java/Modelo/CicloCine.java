package Modelo;

import jakarta.persistence.Entity;
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

    public CicloCine(String nombre, LocalDateTime fechaInicio, int duracionHoras, List<String> listaPeliculas, boolean tieneCharla) {
        super(nombre, fechaInicio, duracionHoras);
        this.listaPeliculas = listaPeliculas;
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
}
