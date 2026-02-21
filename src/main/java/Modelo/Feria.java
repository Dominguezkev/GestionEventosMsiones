package Modelo;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity

public class  Feria extends Evento {

    private int cantidadStands;
    private boolean lugar;

    public Feria(){

    }

    public Feria(String nombre, LocalDateTime fechaInicio, int duracionHoras, int cantidadStands, boolean lugar) {
        super(nombre, fechaInicio, duracionHoras);
        this.cantidadStands = cantidadStands;
        this.lugar = lugar;
    }

    public void Evento(){

    }

    // Getters and Setters Básicos

    public int getCantidadStands() {
        return cantidadStands;
    }

    public void setCantidadStands(int cantidadStands) {
        this.cantidadStands = cantidadStands;
    }

    public boolean getLugar() {
        return lugar;
    }

    public void setLugar(boolean lugar) {
        this.lugar = lugar;
    }
}
