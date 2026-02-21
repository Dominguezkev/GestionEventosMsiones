package Vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Al heredar de Application, Java sabe que esto es una interfaz gráfica
public class VentanaPrincipal extends Application{

    //El método start() es el punto de entrada de la pantalla (como el main, pero visual)
    @Override
    public void start(Stage primaryStage){

        // 1. Creamos un texto simple
        Label saludo = new Label("¡Bienvenido al Sistema de Gestion de Eventos del Municipio!");

        // 2. Elegimos un "Layout" (como se acomodan las cosas). StackPane centra todo.
        StackPane raiz = new StackPane();
        raiz.getChildren().add(saludo);

        // 3. Creamos la "Escena" (el contenido interior) dándole un ancho y un alto
        Scene escena = new Scene(raiz, 600, 400);

        // 4. Configuramos el "Escenario" (la ventana con sus botones de cerrar/minimizar)
        primaryStage.setTitle("Gestion de Eventos Culturales - Apóstoles, Misiones");
        primaryStage.setScene(escena);

        // ¡Que se abra el telón!
        primaryStage.show();
    }

    // Este main es necesario para darle "Arranque" a la aplicación Java
    public static void main(String[] args){
        launch(args);
    }

}
