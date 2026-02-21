package org.example;

import Modelo.Concierto;
import Modelo.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Iniciando conexión con la Base de Datos...");

        // 1. La "Fabrica" que lee tu archivo persistence.xml y se conecta a la BD
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventosPU");
        // 2. El "Manager" que se encarga de hacer los INSERT, UPDATE, DELETE
        EntityManager em = emf.createEntityManager();

        try{
            // 3. Abrimos una transacción (como decirle a la BD: "prepárate que voy a escribir")
            em.getTransaction().begin();

            // --- CREAMOS NUESTROS OBJETOS ---

            Persona organizador = new Persona("Kevin Dominguez", "42777863", "3751-538252","kevdominguez20@gmail.com");

            List<String> bandas = new ArrayList<>();
            bandas.add("LFDA");
            bandas.add("Los Piojos");
            bandas.add("Divididos");
            bandas.add("Airbag");

            Concierto recital = new Concierto("Festival de Rock", LocalDateTime.now().plusDays(15), 5, bandas, false);

            // Vinculamos la persona al evento (agregándolo a la lista de organizadores)
            recital.getOrganizadores().add(organizador);

            // --- GUARDAMOS EN LA BASE DE DATOS ---

            em.persist(organizador);  // Guarda a Kevin en la tabla Persona
            em.persist(recital); //  // Guarda el recital en la tabla Evento y Concierto

            // 4. Confirmamos los cambios (El famoso "Commit")
            em.getTransaction().commit();

            System.out.println("¡Éxito! Los datos se guardaron correctamente en a base de datos.");
        } catch (Exception e) {
            // Solo hacemos rollback si la transacción sigue activa
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("--- EL VERDADERO ERROR ES ESTE: ---");
            e.printStackTrace();
        } finally{
            // 5. Cerramos las conexiones para liberar memoria
            em.close();
            emf.close();
        }
    }
}