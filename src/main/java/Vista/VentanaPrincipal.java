package Vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VentanaPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) {

        // 1. Contenedor Principal
        BorderPane layoutPrincipal = new BorderPane();

        // 2. Crear el Menú Lateral (Izquierda)
        VBox menuLateral = new VBox(15); // 15 es el espacio entre botones
        menuLateral.setPadding(new Insets(20)); // Margen interior
        menuLateral.setStyle("-fx-background-color: #2c3e50;"); // Color de fondo oscuro

        Label tituloMenu = new Label("Menú Principal");
        tituloMenu.setTextFill(javafx.scene.paint.Color.WHITE);
        tituloMenu.setFont(new Font("Arial", 18));

        Button btnEventos = new Button("Gestión de Eventos");
        Button btnPersonas = new Button("Gestión de Personas");
        Button btnSalir = new Button("Salir del Sistema");

        // Hacemos que los botones ocupen todo el ancho del menú
        btnEventos.setMaxWidth(Double.MAX_VALUE);
        btnPersonas.setMaxWidth(Double.MAX_VALUE);
        btnSalir.setMaxWidth(Double.MAX_VALUE);

        // Agregamos todo al menú lateral
        menuLateral.getChildren().addAll(tituloMenu, btnEventos, btnPersonas, btnSalir);

        // 3. Crear el Área de Trabajo (Centro)
        StackPane areaTrabajo = new StackPane();
        Label mensajeBienvenida = new Label("Seleccione una opción del menú lateral para comenzar.");
        mensajeBienvenida.setFont(new Font("Arial", 16));
        areaTrabajo.getChildren().add(mensajeBienvenida);

        // 4. Ensamblar el BorderPane
        layoutPrincipal.setLeft(menuLateral);
        layoutPrincipal.setCenter(areaTrabajo);

        // 5. Configurar la Escena y mostrar
        Scene escena = new Scene(layoutPrincipal, 800, 600); // Pantalla más grande
        primaryStage.setTitle("Gestión de Eventos Culturales - Misiones");
        primaryStage.setScene(escena);
        primaryStage.show();

        // Acción básica para el botón salir
        btnSalir.setOnAction(e -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}