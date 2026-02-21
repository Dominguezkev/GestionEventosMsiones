package Modelo;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity

public class Taller extends Evento{

    private int cupoMaximo;
    private String instructor;
    private boolean modalidad;

    public Taller(){

    }

    public Taller(String nombre, LocalDateTime fechaInicio, int duracionHoras, int cupoMaximo, String instructor, boolean modalidad) {
        super(nombre, fechaInicio, duracionHoras);
        this.cupoMaximo = cupoMaximo;
        this.instructor = instructor;
        this.modalidad = modalidad;
    }

    public void Evento(){

    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public boolean isModalidad() {
        return modalidad;
    }

    public void setModalidad(boolean modalidad) {
        this.modalidad = modalidad;
    }
}
