package Modelo;

import jakarta.persistence.*;

@Entity
public class Proyeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tituloPelicula;
    private int orden;

    // Constructor vacío exigido por JPA
    public Proyeccion() {}

    // Constructor para usar nosotros
    public Proyeccion(String tituloPelicula, int orden) {
        this.tituloPelicula = tituloPelicula;
        this.orden = orden;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getTituloPelicula() { return tituloPelicula; }
    public void setTituloPelicula(String tituloPelicula) { this.tituloPelicula = tituloPelicula; }
    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
}