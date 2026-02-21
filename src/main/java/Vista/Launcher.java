package Vista;

// ¡Esta clase NO hereda de Application! Ese es el truco.
public class Launcher {
    public static void main(String[] args) {
        // Desde acá, llamamos al main de nuestra verdadera ventana
        VentanaPrincipal.main(args);
    }
}