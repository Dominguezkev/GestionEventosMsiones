package Modelo;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Concierto extends Evento{

    @ElementCollection
    private List<String> artistas;
    private boolean esGratuita;

    public Concierto(){

    }

    public Concierto(String nombre, LocalDateTime fechaInicio, int duracionHoras, List<String> artistas, boolean esGratuita) {
        super(nombre, fechaInicio, duracionHoras);
        this.artistas = artistas;
        this.esGratuita = esGratuita;
    }

    public void Evento(){

    }

    public List<String> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<String> artistas) {
        this.artistas = artistas;
    }

    public boolean isEsGratuita() {
        return esGratuita;
    }

    public void setEsGratuita(boolean esGratuita) {
        this.esGratuita = esGratuita;
    }
}
