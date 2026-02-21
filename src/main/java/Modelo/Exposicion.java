package Modelo;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity

public class Exposicion extends Evento{

    private String tipoArte;
    private String curador;

    public Exposicion(String nombre, LocalDateTime fechaInicio, int duracionHoras, String tipoArte, String curador) {
        super(nombre, fechaInicio, duracionHoras);
        this.tipoArte = tipoArte;
        this.curador = curador;
    }

    public Exposicion(){

    }

    public void Evento(){

    }

    public String getTipoArte() {
        return tipoArte;
    }

    public void setTipoArte(String tipoArte) {
        this.tipoArte = tipoArte;
    }

    public String getCurador() {
        return curador;
    }

    public void setCurador(String curador) {
        this.curador = curador;
    }
}
